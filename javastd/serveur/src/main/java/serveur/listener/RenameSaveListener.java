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

import javax.swing.*;
import java.util.ArrayList;

import static constantes.NET.RENAMECOURSE;

public class RenameSaveListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final CourseDataBase courseDataBase;
    private final Gson gson = new GsonBuilder().create();

    public RenameSaveListener(LinkClientSocket linkClientSocket, CourseDataBase courseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.courseDataBase = courseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackRequest) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }
        //On reçoit les données
        ArrayList<String> receiveData = gson.fromJson(data,ArrayList.class);
        //On recupere l'ancient nom et le nouvea
        String oldName = receiveData.get(0);
        String newName = receiveData.get(1);
        if(newName==null || newName.trim().equals("")){
            sock.sendEvent(RENAMECOURSE,gson.toJson(false));
            Logger.log("Rename file "+oldName+ " can be rename to nothing");
            return;
        }
        //On renomme le fichier
        Boolean success = courseDataBase.renameSave(client.getStudent().getNom(),oldName,newName);

        //On envoie l'event au fichier
        Logger.log("Rename file "+oldName+ " to "+newName+" for student : "+client.getStudent().getNom()+" result : "+success);
        sock.sendEvent(RENAMECOURSE,gson.toJson(success));
    }
}
