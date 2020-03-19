package serveur;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;

import metier.Etudiant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import constantes.NET;
import database.DBManager;
import debug.Debug;
import semester_manager.SemesterThread;
import semester_manager.SemestersSample;

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
        this.server.addEventListener(CONNEXION, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Debug.log("new connection : "+socketIOClient.toString());
            	Client c = new Client(gson.fromJson(json,Etudiant.class), socketIOClient);
            	listOfClients.add(c);
            }
        });

        //le client envoie ces donner
        this.server.addEventListener(SENDETUDIANTID,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client c = new Client(gson.fromJson(json,Etudiant.class), socketIOClient);
                listOfClients.add(c);
                clientOnConnectEvent(c);
            }
        });

        //le client demande les donner de connection
        this.server.addEventListener(SENDDATACONNEXION,String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Client client = ServerUtility.getClientFromSocketOnList(socketIOClient,listOfClients);
                clientOnConnectEventSendSemesters(client);
                clientOnConnectSendSave(client);
            }
        });

        //le client envoie une sauvegarde
        this.server.addEventListener(SENDCLIENTSAVE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                @SuppressWarnings("unchecked")
				ArrayList<String> data = gson.fromJson(json, ArrayList.class);
                Client currentClient = ServerUtility.getClientFromSocketOnList(socketIOClient, listOfClients);
                dbManager.setFile(currentClient.getStudent().toString());
                dbManager.save(data);
                Debug.log("Save data for " + currentClient.toString());
            }
        });

        //le client ce deconnecte
        this.server.addDisconnectListener(new DisconnectListener() {
			@Override
			public void onDisconnect(SocketIOClient client) {
				Debug.log(client.getRemoteAddress().toString() + " leaved the server.");
				listOfClients.remove(ServerUtility.getClientFromSocketOnList(client, listOfClients));
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
    protected void 
    stopServeur ()
    {
    	Debug.log("The application is about to shutdown..");
        server.stop();
        Debug.log("Shutdown.");
    }

    /**
     * Gestion de la connexion d'un client
     * @param c La representation du Client
     */
    protected void 
    clientOnConnectEvent (Client c) 
    {
        Debug.log("New client connected : " + c.toString());
        c.getSock().sendEvent(SENDMESSAGE ,"Connected to server");
    }

    /**
     * Gestion des donner du client qui vient de se connecter
     * @param c La representation du Client
     */
    protected void 
    clientOnConnectEventSendSemesters (Client c)
    {
        Debug.log("Send Semesters to : " + c.getSock().getRemoteAddress().toString());

        String msg = ServerUtility.getListOfSemestersJSONed();

        c.getSock().sendEvent(SENDDATACONNEXION, msg);
    }

    /**
     * Permet d'envoyer au client sa derniere sauvegarde
     * @param c La representation du Client
     */
    protected void
    clientOnConnectSendSave (Client c)
    {
        dbManager = new DBManager(c.getStudent().toString());
        if (dbManager.getFile().exists()) 
        {
            Debug.log("Send data to : " + c.toString());
            c.getSock().sendEvent(SENDCLIENTSAVE, gson.toJson(dbManager.load()));
        }

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
    		Debug.log("Sending to " + c);
    		c.getSock().sendEvent(NET.SENDCLIENTUPDATE, msg);
    	}
    	Debug.log("--- New Semesters sended. ---");
    }

    /** Retourne la liste des clients */
    public ArrayList<Client>
    getClients ()
    {return this.listOfClients;}
}
