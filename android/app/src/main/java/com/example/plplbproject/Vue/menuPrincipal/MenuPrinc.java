package com.example.plplbproject.Vue.menuPrincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.plplbproject.R;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.Etudiant;


import static constantes.NET.SENDCLIENTLISTCOURSE;



public class MenuPrinc extends AppCompatActivity{

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

        Serializable etu = getIntent().getSerializableExtra("etudiant");


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

        if(parcoursList == null){
            parcoursList= new ArrayList<>();
        }

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
        Connexion.CONNEXION.send(SENDCLIENTLISTCOURSE,"");

        // #################### Mise en place de l'adapter de parcours ################################"

        parcoursRecyclerView = findViewById(R.id.parcoursList);
        parcoursAdapter = new ParcoursRecyclerAdapter(this,this.parcoursList,this);
        parcoursRecyclerView.setAdapter(parcoursAdapter);
        parcoursRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * setup les event necessaire pour cette activity
     */
    public void setupEventReseau(){
        Connexion.CONNEXION.setEventListener(SENDCLIENTLISTCOURSE, receiveParcoursName());
    }

    public void setParcoursList(ArrayList<String> parcoursNames){
        this.parcoursList = parcoursNames;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            finish();
        }
        else{
            finish();
        }
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
                final ArrayList<String> parcoursName = gson.fromJson((String) args[0], ArrayList.class);
                setParcoursList(parcoursName);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parcoursAdapter.setParcoursNames(parcoursName);
                        parcoursAdapter.notifyDataSetChanged();
                    }
                });
            }
        };
    }

}