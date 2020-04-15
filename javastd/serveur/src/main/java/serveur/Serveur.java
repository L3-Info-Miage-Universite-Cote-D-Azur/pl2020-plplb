package serveur;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import serveur.connectionStruct.ClientSocketList;
import serveur.connectionStruct.LinkClientSocket;
import debug.Debug;
import dataBase.Config;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOServer;

public class Serveur {

    Config config;

    /* FIELDS */
    /** Est l'objet qui permet de traduire du JSON */
    private final Gson gson = new GsonBuilder().create();

    /** Est l'objet qui represente la socket du serveur, c'est a elle que les clients communiquent */
    private final SocketIOServer server;

    /** Contient la liste de tous les clients actuellement connectes au serveur */
    private final LinkClientSocket allClient = new ClientSocketList();


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
