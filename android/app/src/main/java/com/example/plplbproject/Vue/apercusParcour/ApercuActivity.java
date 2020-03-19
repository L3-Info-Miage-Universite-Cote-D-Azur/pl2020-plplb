package com.example.plplbproject.Vue.apercusParcour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static constantes.NET.*;


import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuRecyclerAdapter;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.MainModele;
import metier.parcours.Parcours;

public class ApercuActivity extends AppCompatActivity {

    private MainModele modele;
    private Parcours parcours;

    private RecyclerView apercuList;
    private ApercuRecyclerAdapter apercuAdapter;

    private final Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu_activity);

        modele = (MainModele) getIntent().getExtras().get("modele");
        parcours = modele.getParcours();

        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new ApercuRecyclerAdapter(this,modele);
        apercuList.setAdapter(apercuAdapter);
        apercuList.setLayoutManager(new LinearLayoutManager(this));

        Button saveButton = findViewById(R.id.saveApercu);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Gson gson = new GsonBuilder().create();
                Connexion.CONNEXION.send(SENDETUDIANTID, gson.toJson(modele.getEtudiant()));
                Connexion.CONNEXION.send(SENDCLIENTSAVE,gson.toJson(modele.getParcours().createListCodeUE()));
                Toast toast = Toast.makeText(getApplicationContext(), "Parcours sauvegardé", Toast.LENGTH_SHORT);
                toast.show();
                finish();
            }
        });

    }
}
