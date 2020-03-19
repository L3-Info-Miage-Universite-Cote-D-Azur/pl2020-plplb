package com.example.plplbproject.controleur;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.ApercuActivity;
import com.example.plplbproject.Vue.Vue;

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



}
