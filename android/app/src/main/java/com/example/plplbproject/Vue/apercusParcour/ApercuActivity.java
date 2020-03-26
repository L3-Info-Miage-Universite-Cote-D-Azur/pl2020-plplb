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

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.MainModele;
import metier.parcours.Parcours;
import metier.semestre.Semestre;

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



        Button saveButton = findViewById(R.id.saveApercu);
        modele = new MainModele();
        String classCall = getIntent().getStringExtra("className");
        if(classCall == null) classCall ="";
        if (classCall.equals("MenuPrinc") ){
            String parcoursName = getIntent().getStringExtra("ParcoursName");
            Connexion.CONNEXION.setEventListener(SENDDATACONNEXION, receiveSemesters());
            Connexion.CONNEXION.send(SENDDATACONNEXION,"");

            Connexion.CONNEXION.setEventListener(SENDCLIENTSAVE, receiveParcours());
            Connexion.CONNEXION.send(SENDCOURSE,parcoursName);
            saveButton.setVisibility(View.GONE);
        }
        else {
            saveButton.setOnClickListener(saveButtonListener);
            modele = (MainModele) getIntent().getExtras().get("modele");
        }

        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new ApercuRecyclerAdapter(this,modele);
        apercuList.setAdapter(apercuAdapter);
        apercuList.setLayoutManager(new LinearLayoutManager(this));

    }


    public Emitter.Listener receiveParcours (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> ueCode = gson.fromJson((String) args[0], ArrayList.class);
                modele.setParcours(new Parcours(modele.getSemestres(),ueCode));
            }
        };
    }

    public Emitter.Listener receiveSemesters (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> semestres = gson.fromJson((String) args[0], ArrayList.class);
                for (String s: semestres) {
                    modele.addSemestre(gson.fromJson(s, Semestre.class));
                }
                if(modele.getParcours()!= null) {
                    modele.getParcours().updateSemestre(modele.getSemestres());
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        apercuAdapter.setModele(modele);
                        apercuAdapter.notifyDataSetChanged();

                    }
                });
            }
        };
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
