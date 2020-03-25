package com.example.plplbproject.Vue.semestreBuilder;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import android.widget.Toast;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.Vue.apercusParcour.ApercuActivity;
import com.example.plplbproject.controleur.listener.ClickListener;
import com.example.plplbproject.controleur.semestreBuilder.ReseauController;
import com.example.plplbproject.controleur.semestreBuilder.UserController;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.io.Serializable;

import io.socket.client.Socket;
import metier.Etudiant;
import metier.MainModele;
import metier.semestre.Semestre;

import static constantes.NET.SENDCLIENTSAVE;
import static constantes.NET.SENDDATACONNEXION;
import static constantes.NET.SENDETUDIANTID;
import static constantes.NET.SENDMESSAGE;


public class MainActivity extends AppCompatActivity implements Vue {

    /* FIELDS */
    private ListView categoryListView;
    private Button nextButton; //Le bouton suivant
    private Button previousButton; //boutton precedent
    private Button extendButton;

    private UserController userController;
    private ReseauController reseauController;
    private MainModele modele;

    private Context context;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;


    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        this.modele = new MainModele();

        Serializable etu = getIntent().getSerializableExtra("etudiant");
        if(etu!=null) this.modele.setEtudiant((Etudiant) getIntent().getSerializableExtra("etudiant"));

        this.context = getApplicationContext();

    }


    protected MainModele getModele(){
        return modele;
    }

    /**
     * Gère le menu pour passer d'un semestre à l'autre
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    protected void onPause() {
        super.onPause();
    }




    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle("Semestre 1");
        userController = new UserController((Vue)this,modele,context);
        nextButton = findViewById(R.id.semestre_suivant);// Boutton suivant
        previousButton = findViewById(R.id.semestre_precedent);// Boutton suivant
        getSupportActionBar().setTitle("Semestre "+(modele.getSemestreCourant()+1));

        categoryListView = findViewById(R.id.catList);
        updateButton();

        if(autoconnect) initVue();
    }

    /**
     * Initialisation de le vue
     */
    protected void initVue(){

        expListView = (ExpandableListView) findViewById(R.id.catList);
        listAdapter = new ExpandableListAdapter(this, modele);
        expListView.setAdapter(listAdapter);

        //###################### Server connection #####################
        setupEventReseau();
        if(!Connexion.CONNEXION.isConnected()){
            Connexion.CONNEXION.connect();
        }
        if(modele.getSemestres().size()==0){
            Connexion.CONNEXION.send(SENDDATACONNEXION,"");
        }



        //##################### Controller for the user #####################
        nextButton.setOnClickListener(userController.nextButton());

        /*
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(modele.getParcours().verifiParcours()){
                    Intent intent = new Intent(context, ApercuActivity.class);
                    intent.putExtra("modele",modele);
                    startActivityForResult(intent,1);
                }
                else{
                    Toast toast = Toast. makeText(context, "La sauvegarde n'a pas pue etre effectuer car le parcours est incomplet (une page de renseignement serat ultérieurement mis en place)", Toast. LENGTH_SHORT);
                }
                            //TODO verification que le serveur a bien recus la sauvegarde
            }

        });

         */
        previousButton.setOnClickListener(userController.prevButton());


    }

    /**
     * setup les event necessaire pour cette activity
     */
    public void setupEventReseau(){
        reseauController = new ReseauController(this,modele);
        Connexion.CONNEXION.setEventListener(SENDMESSAGE, reseauController.receiveMessage());
        Connexion.CONNEXION.setEventListener(SENDDATACONNEXION, reseauController.dataConnexion());
        Connexion.CONNEXION.setEventListener(SENDCLIENTSAVE,reseauController.receiveSave());

    }


    /**
     * Envoie a la liste des Ues un changement et change la vue en consequence.
     */

    @Override
    public void notifyUeListView(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
                updateButton();
            }
        });
    }

    /**
     * Replis toutes les liste de categorie
     */
    @Override
    public void collapseList() {
        int count =  expListView.getCount();
        for (int i = 0; i <count ; i++)
            expListView.collapseGroup(i);
    }

    /**
     * notifie l'affichage que le semestre courant a changer
     */
    @Override
    public void notifySemestreChange() {
        getSupportActionBar().setTitle("Semestre "+(modele.getSemestreCourant()+1));
        notifyUeListView();
        collapseList();
        updateButton();
    }

    /**
     * Permet de mettre a jour les boutton suivant et precedent
     * (pour le moment uniquement le precedent a besoin d'etre mis a jour)
     */
    private void updateButton(){
        boolean prev = modele.hasPrevSemestre();
        if(prev){
            previousButton.setText(R.string.precedent);
        }
        else previousButton.setText(R.string.deconnexion);

        boolean next = modele.hasNextSemestre();
        if(next){
            nextButton.setText(R.string.suivant);
        }
        else{
            nextButton.setText(R.string.finaliser);
        }
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
                Toast.makeText(getApplicationContext(), receivedMessage,Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * permet de quité l'intent et passe a l'intent precedent
     */
    @Override
    public void exitIntent(){
        finish();
    }

    /**
     * Fonction à appeller lorsque l'utilisateur change de semestre
     */
    public void onChangeSemestre(int newSemesterIndex){
        if (newSemesterIndex == this.modele.getSemestreCourant()){
            return;
        }
        this.modele.changeSemestre(newSemesterIndex);
        notifyUeListView();
    }

    protected  void setModele(MainModele modele){
        this.modele = modele;
    }






}
