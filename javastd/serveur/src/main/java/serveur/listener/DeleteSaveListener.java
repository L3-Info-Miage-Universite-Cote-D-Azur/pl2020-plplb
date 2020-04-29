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

import static constantes.NET.DELETECOURSE;

/**
 * DeleteSaveListener est la classe qui permet
 * la suppression d'une sauvegarde sur le serveur
 * sur demande du client
 */
public class DeleteSaveListener implements DataListener<String> {
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
    public DeleteSaveListener(LinkClientSocket linkClientSocket, CourseDataBase courseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.courseDataBase = courseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackRequest) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if (client == null) {
            Logger.error("No such client logged.");
            return;
        }
        //On reçoit les données
        String fileName = gson.fromJson(data,String.class);
        //On supprime le fichier
        Boolean success = courseDataBase.deleteSave(client.getStudent().getNom(),fileName);
        //On envoie le resultat au client
        Logger.log("Delete file "+fileName+" for client : "+client.getStudent().getNom()+" result : "+success);
        sock.sendEvent(DELETECOURSE,gson.toJson(success));
    }
}
