package com.example.plplbproject.Vue.courseBuilder;


import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.view.Menu;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.example.plplbproject.R;
import com.example.plplbproject.controleur.courseBuilder.CourseBuilderModele;
import com.example.plplbproject.controleur.courseBuilder.ReseauController;
import com.example.plplbproject.controleur.courseBuilder.UserController;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;



import java.util.ArrayList;

import metier.parcours.Parcours;
import metier.parcours.ParcoursSample;
import metier.parcours.ParcoursType;

import static constantes.NET.LOADCOURSE;



public class CourseBuilderActivity extends AppCompatActivity {


    private Button nextButton; //Le bouton suivant
    private Button previousButton; //boutton precedent

    private UserController userController;
    private ReseauController reseauController;
    private CourseBuilderModele modele;


    ListUEAdaptater listAdapter;
    ExpandableListView expListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        modele = new CourseBuilderModele(DataSemester.SEMESTER.getNumberSemesters());

        String classCall = getIntent().getStringExtra("className");
        if (classCall.equals("CreationMenuActivity")) {
            onCreateNewCours();
        }
        else if(classCall.equals("MainMenuActivity") ){
           onCreateLoadCourse();
        }


    }

    /**
     * Accede a la page d'un nouveau parcour pour crée un nouveau parcour
     */
    protected void onCreateNewCours(){
        String predefinedCourseName = getIntent().getStringExtra("PredefinedCourseName");
        String courseName = getIntent().getStringExtra("CourseName");

        ParcoursSample.init();
        ArrayList<ParcoursType> parcoursTypes =  ParcoursSample.parcoursTypes;
        ParcoursType selected = null;
        for(ParcoursType parcoursType : parcoursTypes){
            if(parcoursType.getName().equals(predefinedCourseName)){
                selected = parcoursType;
            }
        }
        modele.setCourse(new Parcours(DataSemester.SEMESTER.getSemesterList(),selected,courseName));
    }

    /**
     * Charge la creation de parcour depuis un parcour deja existant pour le modifier
     */
    protected void onCreateLoadCourse(){
        String courseName = getIntent().getStringExtra("CourseName");
        Connexion.CONNEXION.setEventListener(LOADCOURSE,reseauController.receiveSave());
        Connexion.CONNEXION.send(LOADCOURSE,courseName);
    }


    protected CourseBuilderModele getModele(){
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
        //on met le bon numero de semestre
        getSupportActionBar().setTitle("Semestre "+modele.getIndexCurrentSemester()+1);

        userController = new UserController(this,modele);

        nextButton = findViewById(R.id.semestre_suivant);// Boutton suivant
        previousButton = findViewById(R.id.semestre_precedent);// Boutton suivant
        updateButton();

        initVue();
    }

    /**
     * Initialisation de le vue
     */
    protected void initVue(){

        expListView = (ExpandableListView) findViewById(R.id.catList);
        listAdapter = new ListUEAdaptater(this, modele);
        expListView.setAdapter(listAdapter);


        //##################### Controller for the user #####################
        nextButton.setOnClickListener(userController.nextButton());
        previousButton.setOnClickListener(userController.prevButton());


    }



    /**
     * Envoie a la liste des Ues un changement et change la vue en consequence.
     */


    public void notifyUeListView(){
        runOnUiThread(new Runnable() {
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

    public void collapseList() {
        int count =  expListView.getCount();
        for (int i = 0; i <count ; i++)
            expListView.collapseGroup(i);
    }

    /**
     * notifie l'affichage que le semestre courant a changer
     */

    public void notifySemestreChange() {
        getSupportActionBar().setTitle("Semestre "+(modele.getIndexCurrentSemester()+1));
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
        else previousButton.setText("Menu Precedent");

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
    public void toastMessage(final String msg) {
        runOnUiThread(new Runnable() {
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
    public void exitIntent(){
        finish();
    }

    /**
     * Fonction à appeller lorsque l'utilisateur change de semestre
     */
    public void onChangeSemestre(int newSemesterIndex){
        if (newSemesterIndex == this.modele.getIndexCurrentSemester()){
            return;
        }
        this.modele.changeSemester(newSemesterIndex);
        notifySemestreChange();
    }

    protected void setModele(CourseBuilderModele modele){
        this.modele = modele;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
        {
            Intent intent=new Intent();
            setResult(2,intent);
            finish();
        }
        else if (resultCode==1){
            onChangeSemestre(0);
        }
    }





}
