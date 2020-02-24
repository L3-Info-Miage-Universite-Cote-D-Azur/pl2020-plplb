package com.example.plplbproject.reseau;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.controleur.ReseauController;
import com.example.plplbproject.model.MainModele;


import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

import static constantes.NET.*;

public class Connexion {

    /* FIELDS */
    private Socket mSocket;
    private Vue vue;
    private MainModele modele;

    /* CONSTRUCTOR */
    public Connexion(Vue vue, MainModele modele) {

        setVue(vue);
        this.modele = modele;

    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }

    /**
     * setup permet de preparer la connection au serveur avec l'ip et le port.
     * Met en place la reception des events.
     * @param ip : l'ip du serveur.
     * @param port : le port du serveur.
     */
    public void setup(String ip,String port) {
        ReseauController controller=new ReseauController(vue,this,modele);
        try {
            String url = "http://"+ip+":"+port;
            mSocket = IO.socket(url);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.on(Socket.EVENT_CONNECT, controller.connexionEvent());
        mSocket.on(SENDMESSAGE, controller.receiveMessage());
        mSocket.on(SENDDATACONNEXION,controller.dataConnexion());
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
