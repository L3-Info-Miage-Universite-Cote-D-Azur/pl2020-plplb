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

/**
 * AskCodeListener est un ecouteur qui attend de la part du client
 * le nom du fichier d'un parcours partage.
 */
public class AskCodeListener implements DataListener<String> {

    /** La liste des clients */
    private final LinkClientSocket linkClientSocket;
    /** La base de donnees des parcours partages */
    private final SharedCourseDataBase sharedCourseDataBase;
    /** Le convertisseur de JSON */
    private final Gson gson = new GsonBuilder().create();

    /**
     * Constructeur de base
     * @param linkClientSocket | La liste des clients
     * @param sharedCourseDataBase | La base de donnees
     */
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
