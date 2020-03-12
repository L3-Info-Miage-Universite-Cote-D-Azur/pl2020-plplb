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
    private Connexion connexion;
    private MainModele modele;
    private final Gson gson = new GsonBuilder().create();

    /*CONSTRUCTOR*/
    public UserController(Vue vue, Connexion connexion, MainModele modele){
        this.vue = vue;
        this.connexion=connexion;
        this.modele = modele;
    }

    /**
     * Gere l'appui sur le bouton de sauvegarde
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public View.OnClickListener saveButton(){
        return new ClickListener(vue,connexion,modele) {
            @Override
            public void onClick(View view) {
                if(modele.getParcours().verifiParcours()) connexion.send(SENDCLIENTSAVE,gson.toJson(modele.getParcours().createListCodeUE()));
                else vue.toastMessage("La sauvegarde n'a pas pue etre effectuer car le parcours est incomplet (une page de renseignement serat ultérieurement mis en place)");
                //TODO verification que le serveur a bien recus la sauvegarde
            }
        };
    }



}
