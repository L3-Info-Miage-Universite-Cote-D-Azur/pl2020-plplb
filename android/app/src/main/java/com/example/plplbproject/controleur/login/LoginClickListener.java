package com.example.plplbproject.controleur.login;

import android.view.View;

import com.example.plplbproject.Vue.login.LoginActivity;
import com.example.plplbproject.reseau.Connexion;
import metier.Student;


public class LoginClickListener implements View.OnClickListener {

    LoginActivity vue;
    Student student;

    public LoginClickListener(Student student, LoginActivity vue){
        this.vue = vue;
        this.student = student;
    }

    @Override
    public void onClick(View view) {
        String ine = vue.getLoginInput();

        if(student.acceptINE(ine)){
            if(Connexion.CONNEXION.isConnected()){
                student.setNom(ine);
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
