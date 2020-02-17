package serveur;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.DataListener;
import metier.UserID;

import java.net.Inet6Address;

import static constantes.NET.*;


public class Serveur {


    private final SocketIOServer server;


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

        this.server.addEventListener(CONNEXION, UserID.class, new DataListener<UserID>() {
            @Override
            public void onData(SocketIOClient socketIOClient, UserID id, AckRequest ackRequest) throws Exception {
                clientConnect(socketIOClient, id);
            }
        });

        this.server.addEventListener(SENDMESSAGE, String.class, new DataListener<String>() {
            @Override
            public void onData(SocketIOClient socketIOClient, String s, AckRequest ackRequest) throws Exception {
                System.out.println("client send : "+ s);
            }
        });


    }

    protected void clientConnect(SocketIOClient socketIOClient, UserID id) {
        // map.put(id, socketIOClient);
        Debug.log("New client connected : "+id);
        socketIOClient.sendEvent(SENDMESSAGE ,"Hello client");
    }


    protected void startServer() {

        server.start();
        Debug.log("Server listening.");
    }


}
