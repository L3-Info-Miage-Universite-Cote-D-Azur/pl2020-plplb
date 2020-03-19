package com.example.plplbproject.reseau;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.controleur.ReseauController;

import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;
import metier.MainModele;

import static constantes.NET.*;

public enum Connexion {
    CONNEXION;

    /* FIELDS */
    private Socket mSocket;
    private final String ip = "192.168.0.17";
    private final String port = "10101";


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

    public void setupEvent(ReseauController controller){
        mSocket.on(Socket.EVENT_CONNECT, controller.connexionEvent());
        mSocket.on(SENDMESSAGE, controller.receiveMessage());
        mSocket.on(SENDDATACONNEXION,controller.dataConnexion());
        mSocket.on(SENDCLIENTSAVE,controller.receiveSave());
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

    

}
