package com.example.plplbproject.reseau;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import metier.Student;

public enum Connexion {
    CONNEXION;

    /* FIELDS */
    private Socket mSocket;
    private Student studentLogin;

    private final String ip = "10.0.2.2";
    private final String port = "10101";

    private Boolean isCodeListenerSet = false;
    private Boolean isRenameListenerSet = false;


    /**
     * setup permet de preparer la connection au serveur avec l'ip et le port.
     * Met en place la reception des events.
     */
    public void setup() {
        try {
            String url = "http://"+ip+":"+port;
            mSocket = IO.socket(url);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    /**
     * permet d'aplliquer un traitement a un event
     * @param event l'event qui ce produit
     * @param listener le listener qui doit gerer l'event
     */
    public void setEventListener(String event, Emitter.Listener listener){
        mSocket.on(event, listener);
    }

    /**
     * connect permet de se connecter au serveur.
     */
    public void connect() {
        mSocket.connect();
    }

    /**
     * disconnect permet de se deconnecter du serveur.
     */
    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    /**
     * send envoie un message au serveur.
     * @param event le nom de l'evenement.s
     * @param json le json a envoye.
     */
    public void send(String event, String json){
        mSocket.emit(event,json);
    }

    /**
     * Permet de voir si on est actuellement connecter au serveur
     * @return true = connecter; false deconnecter.
     */
    public boolean isConnected(){
        return mSocket.connected();
    }


    public void setStudentLogin(Student etudiant){
        this.studentLogin = etudiant;
    }

    public Student getStudentLogin(){
        return  this.studentLogin;
    }

    //pour les test
    public void setSocket(Socket socket){
        this.mSocket =socket;
    }

    public void setCodeListenerSet(Boolean codeListenerSet) {
        isCodeListenerSet = codeListenerSet;
    }

    public void setRenameListenerSet(Boolean renameListenerSet) {
        isRenameListenerSet = renameListenerSet;
    }

    public Boolean isCodeListenerSet(){
        return this.isCodeListenerSet;
    }

    public Boolean isRenameListenerSet(){
        return this.isRenameListenerSet;
    }
}
