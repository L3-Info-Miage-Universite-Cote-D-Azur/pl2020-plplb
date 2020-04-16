package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.SharedCourseDataBase;
import log.Logger;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import java.util.ArrayList;

import static constantes.NET.ASKCODE;

public class AskCodeListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final SharedCourseDataBase sharedCourseDataBase;
    private final Gson gson = new GsonBuilder().create();

    public AskCodeListener(LinkClientSocket linkClientSocket, SharedCourseDataBase sharedCourseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.sharedCourseDataBase = sharedCourseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);
        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }

        //On recupere les donnes
        ArrayList<String> shareCourse = gson.fromJson(data,ArrayList.class);
        //On creer un code et on remplace le nom par celui-ci
        String code = sharedCourseDataBase.generateSharedCourseCode();
        shareCourse.set(0,code);

        //On ecrit le json dans le fichier
        String content = gson.toJson(shareCourse);
        sharedCourseDataBase.addShareCourse(code,content);
        //On renvoie un event au client avec le code.
        Logger.log("Save share course and send code "+code+" to "+client.getStudent().getNom());
        sock.sendEvent(ASKCODE,code);
    }
}
