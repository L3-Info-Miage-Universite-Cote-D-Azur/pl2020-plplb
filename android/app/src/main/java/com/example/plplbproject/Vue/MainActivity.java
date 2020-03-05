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
import com.example.plplbproject.reseau.Connexion;

import java.util.ArrayList;


import metier.Categorie;
import metier.MainModele;
import metier.UE;


public class MainActivity extends AppCompatActivity implements Vue {

    /* FIELDS */
    private ListView categoryListView;
    private Button save; //Le bouton de sauvegarde

    private UserController userController;
    private MainModele modele;
    private Connexion socket;


    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;
    private String ip = "0.0.0.0";
    private String port = "10101";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autoconnect = getIntent().getBooleanExtra(AUTOCONNECT, true);
        this.modele = new MainModele();

    }

    /**
     * Set connexion
     * @param connexion new connexion
     */
    protected void setConnexion(Connexion connexion){
        this.socket = connexion;
        socket.setup(ip,port);

    }

    protected MainModele getModele(){
        return modele;
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


    @Override
    protected void onPause() {
        super.onPause();
        if (socket != null) socket.disconnect();
    }




    @Override
    protected void onResume() {
        super.onResume();
        if (autoconnect) setConnexion(new Connexion((Vue)this,modele));

        userController = new UserController((Vue)this,socket,modele);
        save = findViewById(R.id.save);// Boutton de sauvegarde
        categoryListView = findViewById(R.id.catList);
        if(autoconnect) initVue();

    }

    /**
     * Initialisation de le vue
     */
    protected void initVue(){

        //###################### First adapt the list of categories ##################
        resetAdaptateurModele();

        //###################### Server connection #####################
        socket.connect();


        //##################### Controller for the user #####################
        save.setOnClickListener(userController.saveButton());
        needSave(false); //boutton non disponible avant d'avoir fait une modification


    }


    /**
     * Envoie a la liste des Ues un changement et change la vue en consequence.
     */


    @Override
    public void notifyUeListView(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                //UeDisplayAdapter adapter = (UeDisplayAdapter) ueListView.getAdapter();
                //adapter.notifyDataSetChanged();

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

    /**
     * recharge les adaptateur en fonction du modele
     */
    @Override
    public void resetAdaptateurModele(){
        CategoryAdapter categoryAdapter = new CategoryAdapter(this,modele.getSemestre().getListCategorie(),modele.getParcours());
        categoryListView.setAdapter(categoryAdapter);

    }




}
