package com.example.plplbproject.Vue.menuPrincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuRecyclerAdapter;

import java.io.Serializable;
import java.util.ArrayList;

import metier.Etudiant;
import metier.MainModele;
import metier.parcours.Parcours;

public class MenuInter extends AppCompatActivity {

    private MainModele modele;
    private Context context;
    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    private Etudiant etudiant;

    private RecyclerView parcoursRecyclerView;
    private ApercuRecyclerAdapter parcoursAdapter;
    private ArrayList<Parcours> parcoursList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inter);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        this.modele = new MainModele();

        Serializable etu = getIntent().getSerializableExtra("etudiant");
        if(etu!=null){
            etudiant = (Etudiant) getIntent().getSerializableExtra("etudiant");
            this.modele.setEtudiant(etudiant);
        }

        this.context = getApplicationContext();

    }
}
