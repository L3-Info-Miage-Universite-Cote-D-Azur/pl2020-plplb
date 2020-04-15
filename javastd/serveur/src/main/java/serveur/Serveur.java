package serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.*;
import serveur.connectionStruct.ClientSocketList;
import serveur.connectionStruct.LinkClientSocket;
import debug.Debug;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

import java.io.File;

public class Serveur {

    /*CONFIG*/
    private Config config;

    /* FIELDS */
    /** Est l'objet qui permet de traduire du JSON */
    private final Gson gson = new GsonBuilder().create();

    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;

    /** Contient la liste de tous les clients actuellement connectes au serveur */
    private final LinkClientSocket allClient = new ClientSocketList();

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
        Debug.log("Creating server..");

        Debug.log("Creating server configuration..");
        Configuration configuation = new Configuration();
        configuation.setHostname(config.getConfig("ip"));
        configuation.setPort(Integer.parseInt(config.getConfig("port")));

        Debug.log("Server configuration created.");


        // creation du serveur
        this.server = new SocketIOServer(configuation);
    }

    /**
     * Permet d'initialiser la base de donner contenant les semestre/ue/rule
     */
    protected void initSemesterDataBase(){
        String directoryRelativePath = config.getConfig("semestre_directory");
        int numberSemester = Integer.parseInt(config.getConfig("number_semester"));

        File directory = new File(config.getparentPath(),directoryRelativePath);
        if(!directory.exists() || !directory.isDirectory()){
            Debug.error("file not found: "+directory.getAbsolutePath());
        }

        semesterDataBase = new SemesterDataBase(directory,numberSemester);
        semesterDataBase.initSemesterList();

    }

    /**
     * Permet d'initialiser la base de donner qui gere les sauvergarde des parcours
     */
    protected void initCourseDataBase(){
        String directoryRelativePath = config.getConfig("save_directory");
        File directory = new File(config.getparentPath(),directoryRelativePath);

        //le fichier n'existe pas on le crée
        if(!directory.exists()){
            directory.mkdir();
        }
        courseDataBase = new CourseDataBase(directory);
    }

    /**
     * Permet d'initialiser la base de donner qui gere les partage
     */
    protected void initSharedCourseDataBase(){
        String directoryRelativePath = config.getConfig("share_directory");
        File directory = new File(config.getparentPath(),directoryRelativePath);
        //le fichier n'existe pas on le crée
        if(!directory.exists()){
            directory.mkdir();
        }
        courseDataBase = new CourseDataBase(directory);
    }

    /**
     * Permet d'initialiser la base de donner qui gere les parcours type
     */
    protected  void initCourseTypeDataBas(){
        String directoryRelativePath = config.getConfig("courseType_directory");
        File directory = new File(config.getparentPath(),directoryRelativePath);

        courseTypeDataBase = new TypeCourseDataBase(directory);
        courseTypeDataBase.initParcoursType();
    }






    /**
     * Permet au serveur de commencer a listen des clients
     */
    public void startServer () {
        server.start();
        Debug.log("Server listening.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void stopServeur () {
        Debug.log("The application is about to shutdown..");
        server.stop();
        Debug.log("Shutdown.");
    }


}
