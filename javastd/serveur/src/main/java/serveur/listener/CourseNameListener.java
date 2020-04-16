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

import static constantes.NET.COURSESNAMES;

public class CourseNameListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final CourseDataBase courseDataBase;
    private final Gson gson = new GsonBuilder().create();

    public CourseNameListener(LinkClientSocket linkClientSocket, CourseDataBase courseDataBase){
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
        //On charge les noms de sauvegarde du client
        ArrayList<String> studentSaveName = courseDataBase.getStudentSaveNames(client.getStudent().getNom());
        if(studentSaveName==null){
            Logger.log("save not found for client : " +client.getStudent().toString());
        }
        else {
            //On transforme en json et on envoie l'event.
            String json = gson.toJson(studentSaveName);
            Logger.log("Send courses name " + studentSaveName.toString() + " to " + client.getStudent().toString());
            sock.sendEvent(COURSESNAMES, json);
        }
    }
}
