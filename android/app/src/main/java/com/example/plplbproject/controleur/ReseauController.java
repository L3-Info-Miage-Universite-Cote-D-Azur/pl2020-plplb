package com.example.plplbproject.controleur;



import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.MainModele;
import metier.Semestre;

import static constantes.NET.*;

/**
 * Controlleur de tout les evenement venant du reseau
 */
public class ReseauController{

    /*FIELDS*/
    private Vue vue;
    private Connexion connexion;
    private MainModele modele;
    private final Gson gson = new GsonBuilder().create();

    /*CONSTRUCTOR*/
    public ReseauController(Vue vue, Connexion connexion, MainModele modele){

        this.vue = vue;
        this.connexion = connexion;
        this.modele = modele;
    }

    /**
     * gere la reception des message(textuel) du serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener receiveMessage(){
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                vue.toastMessage((String) args[0]);
            }
        };
    }

    /**
     * gere la connexion au serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener connexionEvent(){
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                Etudiant etu = modele.getEtudiant();
                connexion.send(CONNEXION, gson.toJson(etu));
            }
        };
    }

    /**
     * gere la reception de la liste des ue et la sauvegarde envoyer par le serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener dataConnexion() {
        return new EmitterListener(vue,connexion,modele) {
            @Override
            public void call(Object... args) {
                Semestre semestre = gson.fromJson((String) args[0], Semestre.class);
                System.out.println("data receive from server");
                //TODO Modifier le modele pour un meillieur traitement
                modele.setSemestre(semestre);
                vue.resetAdaptateurModele();
            }
        };
    }



}
