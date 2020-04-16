package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import log.Logger;
import metier.Student;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

public class ConnectionListener implements DataListener<String> {

    private final Gson gson = new GsonBuilder().create();
    private final LinkClientSocket linkClientSocket;

    public ConnectionListener(LinkClientSocket linkClientSocket){
        this.linkClientSocket = linkClientSocket;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = new Client(gson.fromJson(data, Student.class), sock);
        //TODO verif client et envoyer une confirmation au client
        acceptLogin(client);
    }

    /**
     * Si le login est accepter on le rajoute a notre structure de donner
     * @param client le client qui sait identifier
     */
    private void acceptLogin(Client client){
        Logger.log("New client connected : " + client.toString());
        linkClientSocket.addClient(client);
    }
}
