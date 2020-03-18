package com.example.plplbproject.Vue;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.example.plplbproject.R;

import metier.MainModele;

public class ApercuActivity extends AppCompatActivity {

    private MainModele modele;

    private ListView apercuList;
    private ApercuAdapter apercuAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu_activity);

        modele = (MainModele) getIntent().getExtras().get("modele");

        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new ApercuAdapter(this,modele);
        apercuList.setAdapter(apercuAdapter);




    }
}
