package com.example.plplbproject.controleur.login;

import android.view.View;

import com.example.plplbproject.Vue.login.LoginActivity;
import com.example.plplbproject.reseau.Connexion;

import metier.LoginModele;

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
