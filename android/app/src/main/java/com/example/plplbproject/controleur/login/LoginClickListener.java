package com.example.plplbproject.controleur.login;

import android.view.View;

import com.example.plplbproject.Vue.login.LoginActivity;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Etudiant;
import metier.LoginModele;

import static constantes.NET.SENDETUDIANTID;

public class LoginClickListener implements View.OnClickListener {

    LoginActivity vue;
    LoginModele modele;

    public LoginClickListener(LoginModele modele, LoginActivity vue){
        this.vue = vue;
        this.modele = modele;
    }

    @Override
    public void onClick(View view) {
        String ine = vue.getLoginInput();

        if(modele.acceptINE(ine)){
            if(Connexion.CONNEXION.isConnected()){
                Gson gson = new GsonBuilder().create();
                Connexion.CONNEXION.send(SENDETUDIANTID, gson.toJson(new Etudiant(ine)));
                vue.createEtudiant(ine);
                vue.switchIntent();
            }
            else{
                vue.setTextError("Il est impossible de ce connecter au serveur");
            }

        }else{
            vue.setTextError("INE invalide (format : ab123456)");
            vue.clearLoginInput();
        }
    }
}
