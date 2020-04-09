package com.example.plplbproject.Vue.UeVisualisation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.plplbproject.R;

import metier.UE;

public class UeVisualisationActivity extends AppCompatActivity {

    private UE ue;//L'ue qui est détaillée.

    TextView nameUE;//Le nom de l'ue
    TextView codeUE;//Le code de l'ue
    TextView categoryUE;//La catégorie de l'ue
    TextView descriptionUE;//La description de l'ue
    Button exit;//Le bouton pour quitter

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.visualisation_ue);

        //On recupere l'ue par intent.
        ue = (UE) getIntent().getSerializableExtra("UE");
    }


    @Override
    protected void onResume() {
        super.onResume();

        nameUE = findViewById(R.id.nameUEVisu);
        codeUE = findViewById(R.id.codeUEVisu);
        categoryUE = findViewById(R.id.categoryUEVisu);
        descriptionUE = findViewById(R.id.descriptionUEVisu);
        exit = findViewById(R.id.exitVisualisation);

        //On initialise les texts view avec les infos de l'ue.
        initUEFields();

        //Bouton pour quitter.
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    /**
     * Set tout les text views aux infos qui y correspondent.
     */
    public void initUEFields(){
        nameUE.setText(ue.getUeName());
        codeUE.setText(ue.getUeCode());
        categoryUE.setText(ue.getCategorie());
        descriptionUE.setText(ue.getUeDescription());
    }
}
