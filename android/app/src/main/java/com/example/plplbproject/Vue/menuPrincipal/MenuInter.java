package com.example.plplbproject.Vue.menuPrincipal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuRecyclerAdapter;
import com.example.plplbproject.Vue.semestreBuilder.MainActivity;
import com.example.plplbproject.controleur.menuPrincipal.createNewParcoursListener;

import java.io.Serializable;
import java.util.ArrayList;

import metier.Etudiant;
import metier.MainModele;
import metier.MenuInterModele;
import metier.parcours.Parcours;
import metier.parcours.ParcoursSample;
import metier.parcours.ParcoursType;

public class MenuInter extends AppCompatActivity {

    private Context context;
    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    private MenuInterModele modele;

    private RecyclerView parcoursRecyclerView;
    private ParcoursPredefRecyclerAdapter adapterParcoursType;

    private Button deconnexion;//Le bouton de deconnexion
    private Button createNewParcours;//Le bouton pour creer le nouveau parcours
    private EditText editParcoursName;//Le champs rempli avec le nom du nouveau parcours
    private TextView errorMessage;//L'affiche d'erreur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inter);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        this.context = getApplicationContext();

        ArrayList<String> parcoursName = (ArrayList<String>) getIntent().getSerializableExtra("parcoursList");
        ParcoursSample.init();
        modele = new MenuInterModele(ParcoursSample.parcoursTypesName,parcoursName);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Chargement des elements graphiques
        parcoursRecyclerView = findViewById(R.id.parcoursList);
        deconnexion = findViewById(R.id.deconnexion);
        createNewParcours = findViewById(R.id.nouveauParcours);
        editParcoursName = findViewById(R.id.editParcoursName);
        errorMessage = findViewById(R.id.errorMessage);

        //Mise en place de l'adapter.
        adapterParcoursType = new ParcoursPredefRecyclerAdapter(this,modele.getListParcoursPredef());
        parcoursRecyclerView.setAdapter(adapterParcoursType);
        parcoursRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Mise en place des listener
        createNewParcours.setOnClickListener(new createNewParcoursListener(this,modele));

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Recupere le texte dans l'edittext
     * @return le nom du parcours à creer.
     */
    public String getParcoursName(){
        return editParcoursName.getText().toString();
    }

    /**
     * Permet de changer d'activité
     */
    public void switchIntent(){
        Intent intent = new Intent(MenuInter.this, MainActivity.class);
        intent.putExtra(AUTOCONNECT,autoconnect);
        intent.putExtra("ParcoursTypeName",modele.getParcoursTypeName());
        intent.putExtra("ParcoursName",modele.getParcoursName());
        startActivity(intent);
    }

    /**
     * affiche le message text dans le champ de l'erreur
     * @param text : le texte a afficher.
     */
    public void setTextError(String text) {
        errorMessage.setText(text);
        errorMessage.setVisibility(View.VISIBLE);
    }
}
