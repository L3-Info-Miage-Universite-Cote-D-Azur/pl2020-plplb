package com.example.plplbproject.reseau;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.controleur.ReseauController;
import com.example.plplbproject.model.MainModele;


import java.net.URISyntaxException;

import io.socket.client.IO;
import io.socket.client.Socket;

import static constantes.NET.*;

public class Connexion {

    private Socket mSocket;
    private Vue vue;
    private MainModele modele;

    public Connexion(Vue vue, MainModele modele) {

        setVue(vue);
        this.modele = modele;

    }

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

    public void connect() {
        mSocket.connect();

    }

    public void setVue(Vue vue) {
        this.vue = vue;
    }

    public Vue getVue() {
        return vue;
    }

    public void disconnect() {
        if (mSocket != null) mSocket.disconnect();
    }

    public void send(String event, String json){
        mSocket.emit(event,json);
    }

}
