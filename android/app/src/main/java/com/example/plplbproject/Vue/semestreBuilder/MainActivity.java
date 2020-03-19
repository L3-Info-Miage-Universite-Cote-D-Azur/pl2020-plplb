package com.example.plplbproject.Vue.semestreBuilder;


import android.content.Context;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;

import android.widget.Toast;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.controleur.semestreBuilder.ReseauController;
import com.example.plplbproject.controleur.semestreBuilder.UserController;
import com.example.plplbproject.reseau.Connexion;


import java.io.Serializable;

import io.socket.client.Socket;
import metier.Etudiant;
import metier.MainModele;

import static constantes.NET.SENDCLIENTSAVE;
import static constantes.NET.SENDDATACONNEXION;
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.semestre1:
                onChangeSemestre(0);
                getSupportActionBar().setTitle("Semestre 1");
                //System.out.println(id);
                break;
            case R.id.semestre2:
                onChangeSemestre(1);
                getSupportActionBar().setTitle("Semestre 2");
                break;
        }
        collapseList();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Connexion.CONNEXION.disconnect();
    }




    @Override
    protected void onResume() {
        super.onResume();

        getSupportActionBar().setTitle("Semestre 1");
        userController = new UserController((Vue)this,modele,context);
        nextButton = findViewById(R.id.semestre_suivant);// Boutton suivant
        previousButton = findViewById(R.id.semestre_precedent);// Boutton suivant
        categoryListView = findViewById(R.id.catList);
        updateButton();

        if(autoconnect) initVue();
    }

    /**
     * Initialisation de le vue
     */
    protected void initVue(){

        /*
        // Pour tester en local, à enlever! TODO
        // Il faut mettre à jour les méthodes des classes contenues dans controlleur
        UE ue1 = new UE("Maths","0000");
        UE ue2 = new UE("Anglais","0001");
        UE ue3 = new UE("Francais","0002");
        UE ue4 = new UE("Algo","0003");
        UE ue5 = new UE("OFI","0004");
        UE ue6 = new UE("POO","0005");

        ArrayList<UE> arr1= new ArrayList<UE>();
        ArrayList<UE> arr2= new ArrayList<UE>();

        arr1.add(ue1);arr1.add(ue2);arr1.add(ue3);
        arr2.add(ue4);arr2.add(ue5);arr2.add(ue6);

        Categorie generalCat = new Categorie("general",arr1);
        Categorie infoCat = new Categorie("info",arr2);

        categoryList = new ArrayList<>();
        categoryList.add(generalCat);categoryList.add(infoCat);


         */
        expListView = (ExpandableListView) findViewById(R.id.catList);
        listAdapter = new ExpandableListAdapter(this, modele);
        expListView.setAdapter(listAdapter);

        //###################### Server connection #####################
        setupEventReseau();
        if(!Connexion.CONNEXION.isConnected()){
            Connexion.CONNEXION.connect();
        }
        Connexion.CONNEXION.send(SENDDATACONNEXION,"");


        //##################### Controller for the user #####################
        nextButton.setOnClickListener(userController.nextButton());
        previousButton.setOnClickListener(userController.prevButton());


    }

    /**
     * setup les event necessaire pour cette activity
     */
    public void setupEventReseau(){
        reseauController = new ReseauController(this,modele);
        Connexion.CONNEXION.setEventListener(Socket.EVENT_CONNECT,reseauController.connexionEvent());
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
