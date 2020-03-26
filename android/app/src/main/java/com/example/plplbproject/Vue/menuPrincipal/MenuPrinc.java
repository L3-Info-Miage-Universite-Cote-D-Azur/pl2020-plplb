package com.example.plplbproject.Vue.menuPrincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.EmbossMaskFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuRecyclerAdapter;
import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.Vue.semestreBuilder.MainActivity;
import com.example.plplbproject.controleur.listener.EmitterListener;
import com.example.plplbproject.controleur.semestreBuilder.ReseauController;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.Etudiant;
import metier.MainModele;
import metier.parcours.Parcours;

import static constantes.NET.SENDCLIENTSAVE;
import static constantes.NET.SENDDATACONNEXION;
import static constantes.NET.SENDMESSAGE;


public class MenuPrinc extends AppCompatActivity{

    private MainModele modele;
    private Context context;
    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    private Etudiant etudiant;
    private final Gson gson = new GsonBuilder().create();

    private Button deconnexion;
    private Button nouveauParcours;

    private RecyclerView parcoursRecyclerView;
    private ParcoursRecyclerAdapter parcoursAdapter;
    private ArrayList<String> parcoursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_princ);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        this.modele = new MainModele();

        Serializable etu = getIntent().getSerializableExtra("etudiant");
        if(etu!=null){
            etudiant = (Etudiant) getIntent().getSerializableExtra("etudiant");
            this.modele.setEtudiant(etudiant);
        }

        this.context = getApplicationContext();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // ################## Définition des listeners #####################

        deconnexion = findViewById(R.id.deconnexion);
        nouveauParcours = findViewById(R.id.nouveauParcours);

        nouveauParcours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrinc.this , MenuInter.class);
                intent.putExtra("etudiant",etudiant);
                intent.putExtra("parcoursList",parcoursList);
                intent.putExtra(AUTOCONNECT,autoconnect);
                startActivity(intent);
            }
        });

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // ################### récupération des parcours sauvegardés ######################################

        setupEventReseau();
        Connexion.CONNEXION.send(SENDDATACONNEXION,"");

        // #################### Mise en place de l'adapter de parcours ################################"

        parcoursRecyclerView = findViewById(R.id.parcoursList);
        parcoursAdapter = new ParcoursRecyclerAdapter(this,this.parcoursList);
        parcoursRecyclerView.setAdapter(parcoursAdapter);
        parcoursRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * setup les event necessaire pour cette activity
     */
    public void setupEventReseau(){
        Connexion.CONNEXION.setEventListener(SENDDATACONNEXION, receiveParcoursName());
    }

    public void setParcoursList(ArrayList<String> parcoursNames){
        this.parcoursList = parcoursNames;
    }


    /**
     * Gère la reception des parcours sauvegardés envoyé par le serveur
     * Le serveur envoie les noms de parcours uniquement
     * @return traitement à affectuer
     */

    public Emitter.Listener receiveParcoursName (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> parcoursName = gson.fromJson((String) args[0], ArrayList.class);
                setParcoursList(parcoursName);
            }
        };
    }

    /*
    TODO : modifier reseauController pour qu'il accepte autre chose qu'une vue en constructeur
     */
}
