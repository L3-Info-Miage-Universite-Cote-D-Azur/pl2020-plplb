package serveur;


import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.Etudiant;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.DBManager;
import debug.Debug;
import metier.parcours.Parcours;
import metier.semestre.Semestre;
import semester_manager.SemesterThread;
import semester_manager.SemestersSample;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static constantes.NET.*;

/**
 * classe du serveur, gere les differentes connexion et event venant de celle-ci.
 */
public class Serveur {

    /*FIELDS*/
    private final Gson gson = new GsonBuilder().create();

    private final SocketIOServer server;
    private DBManager dbManager;
    private Etudiant etudiant;

    /*CONSTRUCTOR*/
    public Serveur(String ip, int port) {

        Debug.log("Creating server..");

        // config  com.corundumstudio.socketio.Configuration;
        Debug.log("Creating server configuration..");
        Configuration config = new Configuration();
        config.setHostname(ip);
        config.setPort(port);
        Debug.log("Server configuration created.");

        // creation du serveur
        this.server = new SocketIOServer(config);
        Debug.log("Server created.");
        initEventListener();

    }

    /**
     * Initialisation des Event Listener
     */
    protected void initEventListener(){
        this.server.addEventListener(CONNEXION, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                Etudiant etu = gson.fromJson(json,Etudiant.class);
                clientConnect(socketIOClient, etu);
                clientConnectData(socketIOClient, etu);
                clientSendData(socketIOClient,etu);
            }
        });

        this.server.addEventListener(SENDCLIENTSAVE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String json, AckRequest ackRequest) throws Exception {
                ArrayList<String> data = gson.fromJson(json, ArrayList.class);
                dbManager.save(data);
                //TODO : envoyer un liste contenant le semestre et l'etudiant si ce n'est pas un thread.
                Debug.log("Save data for "+etudiant.getNom());
            }
        });
    }



    /**
     * demarage du serveur
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
     * Arret de l'ecoute du serveur
     */
    protected void stopServeur(){
        server.stop();
        Debug.log("Server shutdown.");
    }

    /**
     * get etudiant
     * @return etudiant
     */
    public Etudiant getEtudiant(){
        return etudiant;
    }


    /**
     * Gestion de la connexion d'un client
     * @param socketIOClient le socketIO du client
     * @param etu L'etudiant qui ce connecte.
     */
    protected void clientConnect(SocketIOClient socketIOClient, Etudiant etu) {
        //TODO Liaison entre le socket et l'etudiant avec une hashmap pour avoir plusier etudiant

        // map.put(id, socketIOClient);
        etudiant = etu;
        Debug.log("New client connected : "+etu);
        socketIOClient.sendEvent(SENDMESSAGE ,"Connected to server");
    }

    /**
     * Gestion des donner du client qui vient de se connecter
     * @param socketIOClient le socketIO du client
     * @param etu L'etudiant qui ce connecte.
     */
    protected void clientConnectData(SocketIOClient socketIOClient, Etudiant etu){
        //TODO enlevement du parametre etu recuperer dans la future hashmap
        Debug.log("Send Semesters to : "+etu);

        ArrayList<String> semestresJsonned = new ArrayList<String>();
        semestresJsonned.add(SemestersSample.S1Jsoned);
        semestresJsonned.add(SemestersSample.S2Jsoned);

        socketIOClient.sendEvent(SENDDATACONNEXION,gson.toJson(semestresJsonned));
    }

    protected void clientSendData(SocketIOClient socketIOClient, Etudiant etu){
        //TODO enlevement du parametre etu recuperer dans la future hashmap

        dbManager = new DBManager(etu.toString());
        if(dbManager.getFile().exists()) {
            Debug.log("Send data to : " + etu);
            socketIOClient.sendEvent(SENDCLIENTSAVE, gson.toJson(dbManager.load()));
        }

    }

    public void
    sendEvent ()
    {
    	this.server.addEventListener(SENDCLIENTUPDATE, String.class, listener);
    }
}
