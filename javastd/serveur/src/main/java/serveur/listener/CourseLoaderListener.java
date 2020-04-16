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

public class CourseLoaderListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final CourseDataBase courseDataBase;
    private final Gson gson = new GsonBuilder().create();

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
