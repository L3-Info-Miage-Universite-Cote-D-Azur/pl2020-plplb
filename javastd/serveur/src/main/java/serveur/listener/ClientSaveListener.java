package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.CourseDataBase;
import log.Logger;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import java.util.ArrayList;

public class ClientSaveListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final CourseDataBase courseDataBase;
    private final Gson gson = new GsonBuilder().create();

    public ClientSaveListener(LinkClientSocket linkClientSocket, CourseDataBase courseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.courseDataBase = courseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }
        //On reçoit les données
        ArrayList<String> receiveData = gson.fromJson(data,ArrayList.class);
        //On recupere le nom du fichier
        String fileName = receiveData.get(0);
        //On convertit en json et on lui envoie les donnees.
        String content = gson.toJson(receiveData);
        courseDataBase.writeStudentSave(client.getStudent().getNom(),fileName,content);
    }
}
