package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.SemesterDataBase;
import dataBase.TypeCourseDataBase;
import log.Logger;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static constantes.NET.PREDEFINEDCOURSE;

/**
 * SendCoursTypeListener est la classe qui permet
 * d'envoyer les parcours predefinis
 */
public class SendCoursTypeListener  implements DataListener<String> {
    /** Le convertisseur de JSON */
    private final Gson gson = new GsonBuilder().create();
    /** La liste des clients */
    private final LinkClientSocket linkClientSocket;
    /** La base de donnees qui gere les parcours types */
    private final TypeCourseDataBase typeCourseDataBase;

    /**
     * Constructeur de base
     * @param linkClientSocket | La liste des clients
     * @param typeCourseDataBase | La base de donnees pour les parcours types
     */
    public SendCoursTypeListener(LinkClientSocket linkClientSocket, TypeCourseDataBase typeCourseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.typeCourseDataBase = typeCourseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }
        Logger.log("Send Course Type to : " + client.getStudent().getNom());

        //On charge les parcours type.
        String json = gson.toJson(typeCourseDataBase.loadTypeCourseJson());
        //On envoie l'event au client.
        sock.sendEvent(PREDEFINEDCOURSE, json);
    }
}
