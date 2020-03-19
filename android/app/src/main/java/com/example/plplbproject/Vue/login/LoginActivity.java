package com.example.plplbproject.Vue.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.semestreBuilder.MainActivity;
import com.example.plplbproject.controleur.login.LoginClickListener;
import com.example.plplbproject.controleur.semestreBuilder.ReseauController;
import com.example.plplbproject.reseau.Connexion;

import io.socket.client.Socket;
import metier.Etudiant;
import metier.LoginModele;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;//Le button pour se connecter
    private EditText loginInput;//Le champ ou l'utilisateur ecrit
    private TextView textError;//Le champ d'erreur.

    //Le modele du login.
    private LoginModele modele;

    private Etudiant etudiant;

    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        this.modele = new LoginModele();
        //On setup la connexion
        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        if(autoconnect) Connexion.CONNEXION.setup();
    }

    @Override
    protected void onResume() {
        super.onResume();

        loginButton = findViewById(R.id.connexionButton);
        loginInput = findViewById(R.id.loginInput);
        textError = findViewById(R.id.connexionError);

        //On met le button sur ecoute.
        loginButton.setOnClickListener(new LoginClickListener(modele,this));
        if(autoconnect && !Connexion.CONNEXION.isConnected()) {
            //TODO send event connexion to the server
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
     * creer un etudiant avec le nom name.
     * @param name : le nom.
     */
    public void createEtudiant(String name){
        etudiant = new Etudiant(name);
    }

    public void switchIntent(){
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("etudiant",etudiant);
        intent.putExtra(AUTOCONNECT,autoconnect);
        startActivity(intent);
    }
}
