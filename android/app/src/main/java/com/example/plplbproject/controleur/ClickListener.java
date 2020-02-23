package com.example.plplbproject.controleur;

import android.view.View;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.model.MainModele;
import com.example.plplbproject.reseau.Connexion;

public abstract class ClickListener implements View.OnClickListener {

    private Vue vue;
    private Connexion connexion;
    private MainModele modele;

    public ClickListener(Vue vue, Connexion connexion, MainModele modele){
        this.vue = vue;
        this.connexion=connexion;
        this.modele = modele;
    }

    @Override
    public abstract void onClick(View view);
}
