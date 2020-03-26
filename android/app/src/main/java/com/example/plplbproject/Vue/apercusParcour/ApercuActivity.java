package com.example.plplbproject.Vue.apercusParcour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
    private long backPressedTime = 0;    // used by onBackPressed()

    private RecyclerView apercuList;
    private ApercuRecyclerAdapter apercuAdapter;

    private final Gson gson = new GsonBuilder().create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu_activity);

        modele = (MainModele) getIntent().getExtras().get("modele");

        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new ApercuRecyclerAdapter(this,modele);
        apercuList.setAdapter(apercuAdapter);
        apercuList.setLayoutManager(new LinearLayoutManager(this));

        Button saveButton = findViewById(R.id.saveApercu);

        if (getCallingActivity().getClassName() == "MenuPrinc" ){
            saveButton.setVisibility(View.GONE);
        }
        else {
            saveButton.setOnClickListener(saveButtonListener);
        }
    }

    public View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Connexion.CONNEXION.send(SENDCLIENTSAVE,gson.toJson(modele.getParcours().createSaveList()));
            Toast toast = Toast.makeText(getApplicationContext(), "Parcours sauvegardé", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent=new Intent();
            setResult(2,intent);
            finish();
        }
    };

    @Override
    public void onBackPressed(){
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Appuyer une nouvelle fois pour modifier",
                    Toast.LENGTH_SHORT).show();
        } else {
            //super.onBackPressed();
            Intent intent=new Intent();
            setResult(1,intent);
            finish();
        }
    }
}
