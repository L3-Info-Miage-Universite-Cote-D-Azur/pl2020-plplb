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

/**
 * CourseNameListener est la classe qui permet
 * d'envoyer au client sa liste de parcours
 * sauvegardes
 */
public class CourseNameListener implements DataListener<String> {
    /** Le convertisseur de JSON */
    private final Gson gson = new GsonBuilder().create();
    /** La liste des clients */
    private final LinkClientSocket linkClientSocket;
    /** La base de donnees qui gere les parcours */
    private final CourseDataBase courseDataBase;

    /**
     * Constructeur de base
     * @param linkClientSocket | La liste des clients
     * @param courseDataBase | La base de donnees pour les parcours
     */
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
