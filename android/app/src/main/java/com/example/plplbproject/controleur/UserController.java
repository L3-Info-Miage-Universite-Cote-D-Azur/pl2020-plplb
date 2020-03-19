package com.example.plplbproject.controleur;

import android.view.View;
import android.widget.CheckBox;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.reseau.Connexion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.MainModele;

import static constantes.NET.*;

/**
 * Classe de gestion des event de l'utilisateur
 */
public class UserController {

    /*FIELDS*/
    private Vue vue;
    private MainModele modele;
    private final Gson gson = new GsonBuilder().create();

    /*CONSTRUCTOR*/
    public UserController(Vue vue, MainModele modele){
        this.vue = vue;
        this.modele = modele;
    }

    /**
     * Gere l'appui sur le bouton de sauvegarde
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public View.OnClickListener saveButton(){
        return new ClickListener(vue,modele) {
            @Override
            public void onClick(View view) {
                if(modele.getParcours().verifiParcours()) Connexion.CONNEXION.send(SENDCLIENTSAVE,gson.toJson(modele.getParcours().createListCodeUE()));
                else vue.toastMessage("La sauvegarde n'a pas pue etre effectuer car le parcours est incomplet (une page de renseignement serat ultérieurement mis en place)");
                //TODO verification que le serveur a bien recus la sauvegarde
            }
        };
    }

    /**
     * controller du boutton suivant
     * @return le controller
     */
    public View.OnClickListener nextButton(){
        return new ClickListener(vue,modele) {
            @Override
            public void onClick(View view) {
                boolean nextExist = modele.hasNextSemestre();
                if(nextExist){
                    modele.nextSemestre();
                    vue.notifySemestreChange();
                }
            }
        };
    }

    /**
     * controller du boutton precedent
     * @return le controller
     */
    public View.OnClickListener prevButton(){
        return new ClickListener(vue,modele) {
            @Override
            public void onClick(View view) {
                boolean prevExist = modele.hasPrevSemestre();
                if(prevExist){
                    modele.prevSemestre();
                    vue.notifySemestreChange();
                }
                else vue.exitIntent();
            }
        };
    }



}
