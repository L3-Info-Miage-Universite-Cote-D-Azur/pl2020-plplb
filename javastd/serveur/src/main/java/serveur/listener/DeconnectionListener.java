package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;
import debug.Debug;

/**
 * Listener qui s'occupe de la deconnection
 */
public class DeconnectionListener implements DisconnectListener{

    private final LinkClientSocket linkClientSocket;

    public DeconnectionListener(LinkClientSocket linkClientSocket){
        this.linkClientSocket = linkClientSocket;
    }


    @Override
    public void onDisconnect(SocketIOClient sock) {
        if (linkClientSocket.numberClient() > 0)
        {
            Client client = linkClientSocket.getClient(sock);
            if(client!=null) {
                Debug.log(client.getStudent().getNom() + " leaved the server.");
                linkClientSocket.removeClient(sock);
            }
        }
    }
}
