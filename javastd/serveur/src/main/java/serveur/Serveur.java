package serveur;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import constantes.NET;
import database.DBManager;
import debug.Debug;
import metier.Student;
import metier.parcours.Parcours;
import metiermanager.courses.ParcoursSample;
import metiermanager.semesters.SemesterThread;
import metiermanager.semesters.SemestersSample;

import java.util.ArrayList;

import static constantes.NET.*;

/**
 * La class representant le serveur.<br>
 * Elle permet de gerer l'application en communiquant 
 * avec tous les clients.
 */
public class 
Serveur 
{
    /* FIELDS */
	/** Est l'objet qui permet de traduire du JSON */
    private final Gson gson;
    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;
    /** Est l'objet qui permet de gerer la base de donnee du serveur avec les clients */
    private DBManager dbManager;
    /** Contient la liste de tous les clients actuellement connectes au serveur */
    private ArrayList<Client> listOfClients;

    /* CONSTRUCTOR */
    /**
     * Constructeur de Serveur
     * @param ip Une ip de la forme IPv4, exemple: 42.13.37.69
     * @param port Le port sur lequel les clients vont se connecter
     */
    public 
    Serveur (String ip, int port) 
    {
        Debug.log("Creating server..");
        this.gson = new GsonBuilder().create();
        // config  com.corundumstudio.socketio.Configuration;
        Debug.log("Creating server configuration..");
        Configuration config = new Configuration();
        config.setHostname(ip);
        config.setPort(port);
        Debug.log("Server configuration created.");
        
        this.listOfClients = new ArrayList<Client>();
        
        // creation du serveur
        this.server = new SocketIOServer(config);
        Debug.log("Server created.");
        initEventListener();
    }

    /**
     * Initialisation des listeners
     */
    protected void 
    initEventListener ()
    {
        //le client viens de ce connecter
        this.server.addEventListener(STUDENT, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
            	Client c = new Client(gson.fromJson(json, Student.class), socketIOClient);
            	listOfClients.add(c);
            }
        });

        //le client envoie ces donner
        this.server.addEventListener(STUDENT,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client c = new Client(gson.fromJson(json,Student.class), socketIOClient);
                listOfClients.add(c);
                Debug.log("New client connected : " + c.getStudent().getNom());
            }
        });

        //le client demande les donnees de connexion
        this.server.addEventListener(SEMSTERDATA,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                clientOnConnectEventSendSemesters(client);
            }
        });

        //Le client demande la liste des parcours predefinis
        this.server.addEventListener(PREDEFINEDCOURSE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                clientOnConnectSendPredefinedCourse(client);
            }
        });
        
        /*  */
        this.server.addEventListener(COURSESNAMES,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                clientOnConnectSendCourses(client);
            }
        });
        
        /* Le client envoie un parcours a charger */
        this.server.addEventListener(LOADCOURSE,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                String fname = gson.fromJson(json, String.class);
                clientOnConnectSendSave(client, fname);
            }
        });

        //le client envoie une sauvegarde
        this.server.addEventListener(SENDCLIENTSAVE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                @SuppressWarnings("unchecked")
				ArrayList<String> data = gson.fromJson(json, ArrayList.class);
                Client currentClient = ServerUtility.getClientFromSocketOnList(socketIOClient, listOfClients);
                dbManager.setCurrentDir(currentClient.getStudent().toString());
                dbManager.save(data);
                Debug.log("Save data for " + currentClient.getStudent().getNom());
            }
        });

        //le client ce deconnecte
        this.server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				Debug.log(ServerUtility.getClientFromSocketOnList(client, listOfClients).getStudent().getNom() + " leaved the server.");
				listOfClients.remove(ServerUtility.getClientFromSocketOnList(client, listOfClients));
			}
		});

        //Le client veut partager son parcours.
        this.server.addEventListener(ASKCODE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                ArrayList<String> shareCourse = gson.fromJson(json, ArrayList.class);
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                clientCreateShareCourse(shareCourse,client);
            }
        });


        //Le client veut charger un parcours partage, le serveur lui envoie.
        this.server.addEventListener(COURSECODE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                //On recupere le code du parcours partage.
                String code = gson.fromJson(json, String.class);

                //TODO : charger le parcours depuis le code.
                client.getSock().sendEvent(COURSECODE,gson.toJson(null));
                //TODO Envoie le parcours, ou direct si c'est deja un json
            }
        });
    }

    /**
     * Permet au serveur de commencer a listen des clients
     */
    public void
    startServer () 
    {
    	Thread daemonThread = new Thread(new SemesterThread(this));
    	daemonThread.setDaemon(true);
    	daemonThread.start();
    	
        server.start();
        Debug.log("Server listening.");
    }

    /**
     * Permet au serveur d'arreter de listen et de se fermer
     */
    public void 
    stopServeur ()
    {
    	Debug.log("The application is about to shutdown..");
        server.stop();
        Debug.log("Shutdown.");
    }

    /**
     * Gestion des donner du client qui vient de se connecter
     * @param c La representation du Client
     */
    protected void 
    clientOnConnectEventSendSemesters (Client c)
    {
        Debug.log("Send Semesters to : " + c.getStudent().getNom());

        String semesterList = ServerUtility.getListOfSemestersJSONed();

        c.getSock().sendEvent(SEMSTERDATA, semesterList);
    }

    protected void
    clientOnConnectSendPredefinedCourse(Client c)
    {
        Debug.log("Send predefined course to : "+c.getStudent().getNom());
        c.getSock().sendEvent(PREDEFINEDCOURSE, ServerUtility.getListOfCourseTypeJSONed());
    }

    protected void
    clientOnConnectSendCourses (Client c)
    {
    	dbManager = new DBManager(c.getStudent().toString(), "");
    	ArrayList<String> filenames = dbManager.getAllCourses();
    	Debug.log("Sending course\'s list " + filenames.toString() + " to " + c.getStudent().toString());
    	c.getSock().sendEvent(COURSESNAMES, gson.toJson(filenames));
    }

    /**
     * Permet d'envoyer au client sa derniere sauvegarde
     * @param c La representation du Client
     */
    protected void
    clientOnConnectSendSave (Client c, String filename)
    {
        dbManager = new DBManager(c.getStudent().toString(), filename);
        if (dbManager.getCourse().exists()) 
        {
            Debug.log("Send data to : " + c.getStudent().getNom());
            if (dbManager.getAllCourses().contains(filename))
            	c.getSock().sendEvent(LOADCOURSE, gson.toJson(dbManager.load(filename)));
            else
            {
            	Debug.error(c.getStudent().toString() + " sent an impossible course: " + filename);
            	Debug.error(filename + " not in " + dbManager.getAllCourses().toString());
            }
        }
    }

    /**
     * Enregistre shareCourse puis renvoie le code généré.
     * @param shareCourse : le parcours partage a enregistrer
     * @return le Code généré.
     */
    protected void clientCreateShareCourse(ArrayList<String> shareCourse, Client client){
        //La liste des codes existant deja (le nom des fichiers sont les codes)
        ArrayList<String> existingName = null;//TODO: Donner les noms de parcours partager existant (code)
        //On genere le code
        String code = ServerUtility.generateCourseCode(existingName);

        //TODO enregistrer le parcours partager.
        Debug.log("Save share course and send code "+code+" to "+client.getStudent().getNom());
        client.getSock().sendEvent(ASKCODE,gson.toJson(code));
    }

    /**
     * Permet de mettre a jour les semestres et de les envoyer aux clients
     */
    public void
    updateSemestersOfClients ()
    {
    	SemestersSample.init();
    	if (this.listOfClients.size() == 0)
    		return;
    	Debug.log("--- Sending new semesters to clients... ---");
    	// Creation du message
    	String msg = ServerUtility.getListOfSemestersJSONed();
        // Pour chaque client, on lui envoie le message
    	for (Client c : this.listOfClients)
    	{
    		Debug.log("Sending to " + c.getStudent().getNom());
    		c.getSock().sendEvent(NET.CLIENTUPDATE, msg);
    	}
    	Debug.log("--- New Semesters sended. ---");
    }

    /** Retourne la liste des clients */
    public ArrayList<Client>
    getClients ()
    {return this.listOfClients;}
}
