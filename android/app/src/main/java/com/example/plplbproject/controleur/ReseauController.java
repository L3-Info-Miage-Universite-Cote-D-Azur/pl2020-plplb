package com.example.plplbproject.controleur;



import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.model.MainModele;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.Semestre;

import static constantes.NET.*;

public class ReseauController{

    private Vue vue;
    private Connexion connexion;
    private MainModele modele;
    private final Gson gson = new GsonBuilder().create();

    public ReseauController(Vue vue, Connexion connexion, MainModele modele){

        this.vue = vue;
        this.connexion = connexion;
        this.modele = modele;
    }


    public Emitter.Listener receiveMessage(){
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                vue.toastMessage((String) args[0]);
            }
        };
    }


    public Emitter.Listener connexionEvent(){
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                Etudiant etu = modele.getEtudiant();
                connexion.send(CONNEXION, gson.toJson(etu));
            }
        };
    }
    public Emitter.Listener dataConnexion() {
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                Semestre semestre = gson.fromJson((String) args[0], Semestre.class);
                System.out.println("data receive from server");
                //TODO Modifier le modele pour un meillieur traitement
                modele.addToAllUE(semestre.getListUE());
                vue.notifyUeListView();
            }
        };
    }



}
