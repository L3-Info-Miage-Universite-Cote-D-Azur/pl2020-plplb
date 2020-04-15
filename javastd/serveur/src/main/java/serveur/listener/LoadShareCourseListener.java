package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.SharedCourseDataBase;
import debug.Debug;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static constantes.NET.COURSECODE;

public class LoadShareCourseListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final SharedCourseDataBase sharedCourseDataBase;
    private final Gson gson = new GsonBuilder().create();

    public LoadShareCourseListener(LinkClientSocket linkClientSocket, SharedCourseDataBase sharedCourseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.sharedCourseDataBase = sharedCourseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if(client == null) {
            Debug.error("No such client logged.");
            return;
        }

        //On recupere le code
        String code = gson.fromJson(data,String.class);
        //On recupere le contenu du fichier
        String content = sharedCourseDataBase.loadShareCourse(code);
        //On envoie le contenu au client
        Debug.log("Send share course "+code+" to "+client.getStudent().getNom());
        sock.sendEvent(COURSECODE,content);
    }
}
