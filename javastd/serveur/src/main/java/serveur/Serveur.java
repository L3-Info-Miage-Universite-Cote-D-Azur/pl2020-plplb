package serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.*;
import file.Config;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.ClientSocketList;
import serveur.connectionStruct.LinkClientSocket;
import log.Logger;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;
import serveur.listener.*;

import java.io.File;

import static constantes.NET.*;

public class Serveur {

    /*CONFIG*/
    private Config config;

    /* FIELDS */
    /** Est l'objet qui permet de traduire du JSON */
    private final Gson gson = new GsonBuilder().create();

    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;

    /** Contient la liste de tous les clients actuellement connectes au serveur */
    private ClientSocketList allClient = new ClientSocketList();

    /*DATA BASE*/
    /** gere les sauvegarde des client*/
    private CourseDataBase courseDataBase;

    /** gere les donner des semestre*/
    private SemesterDataBase semesterDataBase;

    /** gere les donner partager*/
    private SharedCourseDataBase sharedCourseDataBase;

    /** gere les parcours type*/
    private TypeCourseDataBase courseTypeDataBase;

    /*DATA BASE*/
    public Serveur(Config config){
        this.config = config;
        Logger.log("Creating server..");

        Logger.log("Creating server configuration..");
        Configuration configuation = new Configuration();
        configuation.setHostname(config.getConfig("ip"));
        configuation.setPort(Integer.parseInt(config.getConfig("port")));
        allClient = new ClientSocketList();

        Logger.log("Server configuration created.");

        Logger.log("Load Database...");
        initCourseDataBase();
        initCourseTypeDataBas();
        initSemesterDataBase();
        initSharedCourseDataBase();

        // creation du serveur
        this.server = new SocketIOServer(configuation);
        Logger.log("Init Listener...");
        initListener();
        Logger.log("Server ready to start.");
    }

    /**
     * Constructeur pour les test (aucun init de database)
     * @param config la config que l'on charge
     * @param forTest un object lambda pour differentier les constructeur
     */
    protected Serveur(Config config,Object forTest){
        this.config = config;
        Configuration configuation = new Configuration();
        configuation.setHostname(config.getConfig("ip"));
        configuation.setPort(Integer.parseInt(config.getConfig("port")));
        this.server = new SocketIOServer(configuation);
    }

    /**
     * Permet d'initialiser la base de donner contenant les semestre/ue/rule
     * @return true si il a bien pue etre initialiser false si non
     */
    protected boolean initSemesterDataBase(){
        String directoryPath = config.getConfig("semestre_directory");
        int numberSemester = Integer.parseInt(config.getConfig("number_semester"));
        File directory = new File(directoryPath);
        if(!directory.isAbsolute()){
            directory = new File(config.getparentPath(),directoryPath);
        }

        if(!directory.exists() || !directory.isDirectory()){
            Logger.error("file not found: "+directory.getAbsolutePath());
            return false;
        }

        semesterDataBase = new SemesterDataBase(directory,numberSemester);
        semesterDataBase.initSemesterList();
        return true;
    }

    /**
     * Permet d'initialiser la base de donner qui gere les sauvergarde des parcours
     */
    protected void initCourseDataBase(){
        String directoryRelativePath = config.getConfig("save_directory");
        File directory = new File(directoryRelativePath);

        //le fichier n'existe pas on le crée
        if(!directory.exists()){
            directory.mkdir();
            if(!directory.exists()){
                Logger.error("cant find directory :"+directory.getAbsolutePath());
            }
        }
        courseDataBase = new CourseDataBase(directory);
    }

    /**
     * Permet d'initialiser la base de donner qui gere les partage
     */
    protected void initSharedCourseDataBase(){
        String directoryRelativePath = config.getConfig("share_directory");
        File directory = new File(directoryRelativePath);
        //le fichier n'existe pas on le crée
        if(!directory.exists()){
            directory.mkdir();
            if(!directory.exists()){
                Logger.error("cant find directory :"+directory.getParent());
            }
        }
        sharedCourseDataBase = new SharedCourseDataBase(directory);
    }

    /**
     * Permet d'initialiser la base de donner qui gere les parcours type
     * @return true si il a bien pue etre initialiser false si non
     */
    protected  boolean initCourseTypeDataBas(){
        String directoryPath = config.getConfig("courseType_directory");

        File directory = new File(directoryPath);
        if(!directory.isAbsolute()){
            directory = new File(config.getparentPath(),directoryPath);
        }


        if(!directory.exists() || !directory.isDirectory()){
            Logger.error("file not found: "+directory.getAbsolutePath());
            return false;
        }

        courseTypeDataBase = new TypeCourseDataBase(directory);
        courseTypeDataBase.initParcoursType();
        return true;
    }

    /**
     * Permet d'initialiser tout les listener du serveur
     */
    public void initListener(){
        server.addEventListener(ASKCODE,String.class, new AskCodeListener(allClient,sharedCourseDataBase));
        server.addEventListener(SENDCLIENTSAVE, String.class, new ClientSaveListener(allClient,courseDataBase));
        server.addEventListener(STUDENT,String.class, new ConnectionListener(allClient));
        server.addEventListener(LOADCOURSE,String.class, new CourseLoaderListener(allClient,courseDataBase));
        server.addEventListener(COURSESNAMES,String.class, new CourseNameListener(allClient,courseDataBase));
        server.addDisconnectListener(new DeconnectionListener(allClient));
        server.addEventListener(COURSECODE,String.class,new LoadShareCourseListener(allClient,sharedCourseDataBase,courseDataBase));
        server.addEventListener(SEMSTERDATA,String.class, new SemesterDataListener(allClient,semesterDataBase));
        server.addEventListener(PREDEFINEDCOURSE,String.class, new SendCoursTypeListener(allClient,courseTypeDataBase));
        server.addEventListener(DELETECOURSE,String.class, new DeleteSaveListener(allClient,courseDataBase));
        server.addEventListener(RENAMECOURSE,String.class, new RenameSaveListener(allClient,courseDataBase));
    }

    /**
     * Permet au serveur de commencer a listen des clients
     */
    public void startServer () {
        // Daemon sur la MAJ automatique des semestres
        Thread daemonThread = new Thread(new Updater(this));
        daemonThread.setDaemon(true);
        daemonThread.start();

        server.start();
        Logger.log("Server listening.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void stopServeur () {
        Logger.log("The application is about to shutdown..");
        server.stop();
        Logger.log("Shutdown.");
        Logger.saveLogs(this.config);
    }

    /**
     * Permet de mettre a jour les semestres et de les envoyer aux clients
     */
    public void
    updateSemestersOfClients ()
    {this.updateSemestersOfClients(this.semesterDataBase, this.allClient, this.gson);}

    /**
     * Permet de mettre a jour les semestres et de les envoyer aux clients
     * @param semesterDataBase | Base de donnees des semestres
     * @param allClient | La liste des clients
     * @param gson | Convertisseur JSON
     */
    public void
    updateSemestersOfClients (SemesterDataBase semesterDataBase, ClientSocketList allClient, Gson gson)
    {
        if (!semesterDataBase.initSemesterList())
            return;
        if (allClient.numberClient() == 0)
            return;
        Logger.log("--- Sending new semesters to clients... ---");
        // Creation du message
        String msg = gson.toJson(semesterDataBase.getSemesterList());
        // Pour chaque client, on lui envoie le message
        for (Client c : allClient)
        {
            Logger.log("Sending to " + c.getStudent().getNom());
            c.getSock().sendEvent(SERVERUPDATE, msg);
        }
        Logger.log("--- New Semesters sended. ---");
    }

    public Config getServConfig () {return this.config;}
    public ClientSocketList getClients () {return this.allClient;}

    /*FONCTION POUR LES TEST*/
    public CourseDataBase getCourseDataBase() {
        return courseDataBase;
    }

    public SemesterDataBase getSemesterDataBase() {
        return semesterDataBase;
    }

    public SharedCourseDataBase getSharedCourseDataBase() {
        return sharedCourseDataBase;
    }

    public TypeCourseDataBase getCourseTypeDataBase() {
        return courseTypeDataBase;
    }
}
