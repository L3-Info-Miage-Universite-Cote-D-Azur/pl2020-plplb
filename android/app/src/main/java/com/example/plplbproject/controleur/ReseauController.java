package com.example.plplbproject.controleur;



import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.MainModele;
import metier.parcours.Parcours;
import metier.semestre.Semestre;

import static constantes.NET.*;

/**
 * Controlleur de tout les evenement venant du reseau
 */
public class ReseauController{

    /*FIELDS*/
    private Vue vue;
    private MainModele modele;
    private final Gson gson = new GsonBuilder().create();

    /*CONSTRUCTOR*/
    public ReseauController(Vue vue, MainModele modele){

        this.vue = vue;
        this.modele = modele;
    }

    /**
     * gere la reception des message(textuel) du serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener receiveMessage(){
        return new EmitterListener(vue,modele) {
            @Override
            public void call(Object... args) {
                vue.toastMessage("Server sent you a message: "+((String) args[0]));
            }
        };
    }

    /**
     * gere la connexion au serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener connexionEvent(){
        return new EmitterListener(vue,modele) {
            @Override
            public void call(Object... args) {
                Etudiant etu = modele.getEtudiant();
                Connexion.CONNEXION.send(CONNEXION, gson.toJson(etu));
            }
        };
    }

    /**
     * gere la reception de la liste des ue
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener dataConnexion() {
        return new EmitterListener(vue,modele) {
            @Override
            public void call(Object... args) {
                ArrayList<String> semestres = gson.fromJson((String) args[0], ArrayList.class);
                System.out.println("data receive from server");
                //TODO Modifier le modele pour un meillieur traitement
                for (String s: semestres) {
                    modele.addSemestre(gson.fromJson(s,Semestre.class));
                }
                modele.getParcours().initParcoursSemestresManager();
                vue.notifyUeListView();
            }
        };
    }

    /**
     * Gère la reception de la la sauvegarde envoyée par le serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener receiveSave() {
        return new EmitterListener(vue,modele) {
            @Override
            public void call(Object... args) {
                int semestreCourant = modele.getSemestreCourant();
                ArrayList<String> ueCode = gson.fromJson((String) args[0], ArrayList.class);
                System.out.println("save receive from server");
                //TODO Modifier parcours
                modele.setParcours(new Parcours(modele.getSemestres(),ueCode));
                vue.notifyUeListView();
            }
        };
    }



}
