package com.example.plplbproject.Vue.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.mainMenu.MainMenuActivity;
import com.example.plplbproject.controleur.login.LoginClickListener;

import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Student;

import static constantes.NET.STUDENT;

/**
 * Page de login
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginButton;//Le button pour se connecter
    private EditText loginInput;//Le champ ou l'utilisateur ecrit
    private TextView textError;//Le champ d'erreur.

    private Student student;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        student = new Student("default");


        //On setup la connexion
        Connexion.CONNEXION.setup();
        Connexion.CONNEXION.connect();

    }

    @Override
    protected void onResume() {
        super.onResume();

        loginButton = findViewById(R.id.connexionButton);
        loginInput = findViewById(R.id.loginInput);
        textError = findViewById(R.id.connexionError);

        //On met le button sur ecoute.
        loginButton.setOnClickListener(new LoginClickListener(student,this));
        if(!Connexion.CONNEXION.isConnected()) {
            Connexion.CONNEXION.connect();
        }
    }



    /**
     * affiche le message text dans le champ de l'erreur
     * @param text : le texte a afficher.
     */
    public void setTextError(String text) {
        textError.setText(text);
        textError.setVisibility(View.VISIBLE);
    }

    /**
     * Recupere le champ du login.
     * @return le champ du login sous format string.
     */
    public String getLoginInput() {
        return loginInput.getText().toString();
    }

    /**
     * clearLoginInput reset le champ du login.
     */
    public void clearLoginInput(){
        loginInput.getText().clear();
    }


    /**
     * Permet de changer d'intent pour passer au menu principale
     */
    public void switchIntent(){

        //on enregistre l'etudiant qui c'est connecter dans la connection
        Gson gson = new GsonBuilder().create();
        Connexion.CONNEXION.setStudentLogin(student);
        Connexion.CONNEXION.send(STUDENT,gson.toJson(student));

        //on lance le prochain intent
        Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
        intent.putExtra("etudiant",student);
        startActivity(intent);
    }
}
