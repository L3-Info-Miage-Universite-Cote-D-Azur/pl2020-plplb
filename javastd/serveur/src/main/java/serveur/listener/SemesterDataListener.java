package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.SemesterDataBase;
import log.Logger;
import metier.semestre.SemesterList;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static constantes.NET.SEMSTERDATA;

public class SemesterDataListener implements DataListener<String> {

    private final LinkClientSocket linkClientSocket;
    private final SemesterDataBase semesterDataBase;
    private final Gson gson = new GsonBuilder().create();

    public SemesterDataListener(LinkClientSocket linkClientSocket, SemesterDataBase dataBase){
        this.linkClientSocket = linkClientSocket;
        this.semesterDataBase = dataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);

        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }
        Logger.log("Send Semesters to : " + client.getStudent().getNom());
        //On charge les semestres.
        SemesterList semesterList = semesterDataBase.getSemesterList();
        //On creer le json.
        String json = gson.toJson(semesterList);
        //On envoie l'event au client.
        sock.sendEvent(SEMSTERDATA, json);
    }
}
