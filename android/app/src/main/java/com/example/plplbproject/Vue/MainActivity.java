package com.example.plplbproject.Vue;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.UserController;
import com.example.plplbproject.model.MainModele;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


import metier.UE;


public class MainActivity extends AppCompatActivity implements Vue {

    /* FIELDS */
    private ArrayList<UE> ueList;

    private ListView ueListView;
    private Button save; //Le bouton de sauvegarde

    private UserController userController;
    private MainModele modele;
    private Connexion socket;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.modele = new MainModele();


        //###################### Adapt the list of Ue to display #####################
        UeDisplayAdapter ueListViewAdaptateur = new UeDisplayAdapter(this, modele.getAllUE());
        ueListView = findViewById(R.id.ueListView);
        ueListView.setAdapter(ueListViewAdaptateur);


        //###################### Server connection #####################
        socket= new Connexion(this,modele);
        socket.setup("10.0.2.2","10101");
        socket.connect();


        //##################### Controller for the user #####################
        userController = new UserController((Vue)this,socket,modele);

        save = findViewById(R.id.save);// Boutton de sauvegarde
        save.setOnClickListener(userController.saveButton());
        needSave(false); //boutton non disponible avant d'avoir fait une modification


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Envoie a la liste des Ues un changement et change la vue en consequence.
     */
    @Override
    public void notifyUeListView(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                UeDisplayAdapter adapter = (UeDisplayAdapter) ueListView.getAdapter();
                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * needSave rend visible ou invisible le bouton de sauvegarde des donnees.
     * @param needSave vaut true si un changement doit etre sauvegarder
     */
    @Override
    public void needSave(Boolean needSave) {
        if(needSave) save.setVisibility(View.VISIBLE);
        else save.setVisibility(View.INVISIBLE);
    }

    /**
     * Affiche un message dans un toast sur la vue actuelle.
     * @param msg message a afficher.
     */
    @Override
    public void toastMessage(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String receivedMessage = msg;

                // add the message to view todo
                //just show the message in a toast
                Toast.makeText(getApplicationContext(),"Server sent you a message: "+ receivedMessage,Toast.LENGTH_LONG).show();
            }
        });
    }




}
