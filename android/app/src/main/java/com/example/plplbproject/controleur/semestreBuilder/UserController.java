package com.example.plplbproject.controleur.semestreBuilder;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.ApercuActivity;
import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.controleur.listener.ClickListener;
import com.example.plplbproject.reseau.Connexion;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.MainModele;


/**
 * Classe de gestion des event de l'utilisateur
 */
public class UserController {

    /*FIELDS*/
    private Vue vue;
    private MainModele modele;
    private Context context;

    /*CONSTRUCTOR*/
    public UserController(Vue vue, MainModele modele, Context context){
        this.vue = vue;
        this.modele = modele;
        this.context = context;
    }

    /**
     * Gere l'appui sur le bouton de sauvegarde
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public View.OnClickListener saveButton(){
        return new ClickListener(vue,modele) {
            @Override
            public void onClick(View view) {
                if(modele.getParcours().verifiParcours()){
                    Intent intent = new Intent(context, ApercuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("modele",modele);
                    context.startActivity(intent);
                }
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
