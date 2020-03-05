package com.example.plplbproject.controleur;

import android.view.View;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.reseau.Connexion;

import metier.MainModele;

/**
 * Classe de gestion d'event de click
 */
public abstract class ClickListener implements View.OnClickListener {

    /*FIELDS*/
    private Vue vue;
    private Connexion connexion;
    private MainModele modele;

    /*CONSTRUCTOR*/
    public ClickListener(Vue vue, Connexion connexion, MainModele modele){
        this.vue = vue;
        this.connexion=connexion;
        this.modele = modele;
    }

    /**
     * gestion du click
     * @param view vue
     */
    @Override
    public abstract void onClick(View view);
}
