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

import static constantes.NET.LOADCOURSE;

/**
 * CourseLoaderListener est la classe qui permet
 * de charger et d'envoyer au client sa sauvegarde
 */
public class CourseLoaderListener implements DataListener<String> {
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
    public CourseLoaderListener(LinkClientSocket linkClientSocket, CourseDataBase courseDataBase){
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
        //On reçoit le nom du parcours a envoyer.
        String fileName = gson.fromJson(data,String.class);
        //Le json contenu dans le fichier.
        String content = courseDataBase.loadStudentSave(client.getStudent().getNom(),fileName);
        //on envoie l'event
        sock.sendEvent(LOADCOURSE,content);
    }
}
