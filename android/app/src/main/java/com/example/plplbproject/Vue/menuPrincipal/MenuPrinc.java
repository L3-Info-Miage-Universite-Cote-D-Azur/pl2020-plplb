package com.example.plplbproject.Vue.menuPrincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuRecyclerAdapter;
import com.example.plplbproject.Vue.semestreBuilder.MainActivity;

import java.io.Serializable;
import java.util.ArrayList;

import metier.Etudiant;
import metier.MainModele;
import metier.parcours.Parcours;


public class MenuPrinc extends AppCompatActivity {

    private MainModele modele;
    private Context context;
    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    private Etudiant etudiant;

    private Button deconnexion;
    private Button nouveauParcours;

    private RecyclerView parcoursRecyclerView;
    private ApercuRecyclerAdapter parcoursAdapter;
    private ArrayList<Parcours> parcoursList;

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

        //this.parcoursList = TODO

        // ################## Définition des listeners #####################""

        deconnexion = findViewById(R.id.deconnexion);
        nouveauParcours = findViewById(R.id.nouveauParcours);

        nouveauParcours.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuPrinc.this , MenuInter.class);
                intent.putExtra("etudiant",etudiant);
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

        // #################### Mise en place de l'adapter de parcours

        parcoursRecyclerView = findViewById(R.id.parcoursList);
    }
}
