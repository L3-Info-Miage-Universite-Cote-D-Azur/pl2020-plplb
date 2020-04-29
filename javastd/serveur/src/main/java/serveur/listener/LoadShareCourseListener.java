package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dataBase.CourseDataBase;
import dataBase.SharedCourseDataBase;
import log.Logger;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static constantes.NET.COURSECODE;

/**
 * LoadShareCourseListener est la classe qui permet
 * de charger un parcours partage, ce qui a pour effet
 * de sauvegarder ce parcours dans la sauvegarde des parcours
 * du client.
 */
public class LoadShareCourseListener implements DataListener<String> {
    /** La base de donnees qui gere les parcours partages */
    private final SharedCourseDataBase sharedCourseDataBase;
    /** Le convertisseur de JSON */
    private final Gson gson = new GsonBuilder().create();
    /** La liste des clients */
    private final LinkClientSocket linkClientSocket;
    /** La base de donnees qui gere les parcours */
    private final CourseDataBase courseDataBase;

    /**
     * Constructeur de base
     * @param linkClientSocket | La liste des clients
     * @param sharedCourseDataBase | La base de donnees qui gere les parcours partages
     * @param courseDataBase | La base de donnees pour les parcours
     */
    public LoadShareCourseListener(LinkClientSocket linkClientSocket,
                                   SharedCourseDataBase sharedCourseDataBase,
                                   CourseDataBase courseDataBase){
        this.linkClientSocket = linkClientSocket;
        this.sharedCourseDataBase = sharedCourseDataBase;
        this.courseDataBase = courseDataBase;
    }

    @Override
    public void onData(SocketIOClient sock, String data, AckRequest ackSender) throws Exception {
        Client client = linkClientSocket.getClient(sock);
        boolean isCorrect = false;

        //Si le client est null.
        if(client == null) {
            Logger.error("No such client logged.");
            return;
        }

        //On recupere le code
        String code = gson.fromJson(data,String.class);

        //Si le code existe.
        if(sharedCourseDataBase.verifyCode(code)){
            //Le code est correct
            isCorrect = true;
            //On recupere le contenu du fichier
            String content = sharedCourseDataBase.loadShareCourse(code);
            //On le copie dans les sauvegarde du client
            courseDataBase.writeStudentSave(client.getStudent().getNom(),code,content);
        }
        //On transforme en json et on envoie au client
        String json = gson.toJson(isCorrect);
        Logger.log("Client "+client.getStudent().getNom()+" send code : "+code+" -> accepted : "+isCorrect);
        sock.sendEvent(COURSECODE,json);
    }
}
