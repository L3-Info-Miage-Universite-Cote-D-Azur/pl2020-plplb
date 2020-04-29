package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DisconnectListener;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;
import log.Logger;

/**
 * DeconnectionListener est la classe qui permet
 * de s'occuper de la deconnexion
 */
public class DeconnectionListener implements DisconnectListener{

    /** La liste des clients */
    private final LinkClientSocket linkClientSocket;

    /**
     * Constructeur de base
     * @param linkClientSocket | La liste des clients
     */
    public DeconnectionListener(LinkClientSocket linkClientSocket){
        this.linkClientSocket = linkClientSocket;
    }

    @Override
    public void onDisconnect(SocketIOClient sock) {
        if (linkClientSocket.numberClient() > 0)
        {
            Client client = linkClientSocket.getClient(sock);
            if(client!=null) {
                Logger.log(client.getStudent().getNom() + " leaved the server.");
                linkClientSocket.removeClient(sock);
            }
        }
    }
}
