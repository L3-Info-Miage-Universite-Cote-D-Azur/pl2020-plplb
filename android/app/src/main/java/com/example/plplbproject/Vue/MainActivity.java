package com.example.plplbproject.Vue;


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
import com.example.plplbproject.controleur.UserController;
import com.example.plplbproject.reseau.Connexion;


import metier.MainModele;


public class MainActivity extends AppCompatActivity implements Vue {

    /* FIELDS */
    private ListView categoryListView;
    private Button save; //Le bouton de sauvegarde
    private Button extendButton;

    private UserController userController;
    private MainModele modele;
    private Connexion socket;

    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;


    public static final String AUTOCONNECT = "AUTOCONNECT";
    private boolean autoconnect =  true;
    private String ip = "192.168.0.17";
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

        getSupportActionBar().setTitle("Semestre 1");
        userController = new UserController((Vue)this,socket,modele);
        save = findViewById(R.id.save);// Boutton de sauvegarde
        categoryListView = findViewById(R.id.catList);
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
                listAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * needSave rend visible ou invisible le bouton de sauvegarde des donnees.
     * @param needSave vaut true si un changement doit etre sauvegarder
     */
    @Override
    public void needSave(Boolean needSave) {
        //preverification du client
        if(needSave && modele.getParcours().verifiParcours()) save.setVisibility(View.VISIBLE);
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
     * Fonction à appeller lorsque l'utilisateur change de semestre
     */
    public void onChangeSemestre(int newSemesterIndex){
        if (newSemesterIndex == this.modele.getSemestreCourant()){
            return;
        }
        this.modele.changeSemestre(newSemesterIndex);
        notifyUeListView();
    }

    /**
     * recharge les adaptateur en fonction du modele
     */
    @Override
    public void resetAdaptateurModele(){
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                listAdapter.notifyDataSetChanged();
            }
        });
    }

}
