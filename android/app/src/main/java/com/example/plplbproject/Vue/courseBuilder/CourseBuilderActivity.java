package com.example.plplbproject.Vue.courseBuilder;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;



import android.view.Menu;

import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.plplbproject.R;
import com.example.plplbproject.controleur.courseBuilder.CourseBuilderModele;
import com.example.plplbproject.controleur.courseBuilder.ReseauController;
import com.example.plplbproject.controleur.courseBuilder.UserController;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;



import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import metier.parcours.Parcours;
import metier.parcours.ParcoursType;

import static constantes.NET.LOADCOURSE;


/**
 * Activity de la page de creation de parcour
 */
public class CourseBuilderActivity extends AppCompatActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    private Button nextButton; //Le bouton suivant
    private Button previousButton; //boutton precedent
    private SearchView searchView;
    private TextView information;

    private UserController userController;
    private ReseauController reseauController;
    private CourseBuilderModele modele;

    public static final String AUTOINIT = "AUTOINIT";
    private boolean autoInit =  true;

    ListUEAdaptater listAdapter;
    ExpandableListView expListView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        autoInit = getIntent().getBooleanExtra(AUTOINIT,true);

        modele = new CourseBuilderModele(DataSemester.SEMESTER.getNumberSemesters());
        reseauController = new ReseauController(this,modele);
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

        ArrayList<ParcoursType> predefinedCourse =  DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList();

        ParcoursType selected = null;
        for(ParcoursType parcoursType : predefinedCourse){
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
        getSupportActionBar().setTitle("Semestre "+(modele.getIndexCurrentSemester()+1));



        nextButton = findViewById(R.id.semestre_suivant);// Boutton suivant
        previousButton = findViewById(R.id.semestre_precedent);// Boutton suivant
        information = findViewById(R.id.informationUe);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = findViewById(R.id.search);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);

        updateButton();


        if(autoInit) initVue();
    }

    /**
     * Initialisation de le vue
     */
    protected void initVue(){
        userController = new UserController(this,modele);
        updateButton();
        expListView = (ExpandableListView) findViewById(R.id.catList);
        listAdapter = new ListUEAdaptater(this, modele);
        expListView.setAdapter(listAdapter);


        //##################### Controller for the user #####################
        nextButton.setOnClickListener(userController.nextButton());
        previousButton.setOnClickListener(userController.prevButton());

        updateInformationCourse();
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
     * Replie toutes les liste de categories, sauf celles qui contiennent des UEs cochées
     */

    public void collapseList() {
        Categorie cat = null;
        UE ue = null;
        int flag = 0; // Pour éviter d'expand plusieurs fois quand une catégorie contient + d'une UE cochée
        int count = listAdapter.getGroupCount();

        for (int i = 0; i <count ; i++) {
            cat = (Categorie) listAdapter.getGroup(i);
            for (int y = 0; y <listAdapter.getChildrenCount(i); y++) {
                ue = (UE) listAdapter.getChild(i,y);
                if (modele.getCourse().getParcoursSelect().containsKey(ue.getUeCode()) && flag == 0){
                    expListView.expandGroup(i);
                    flag = 1;
                }
                if (flag == 0){
                    expListView.collapseGroup(i);
                }
            }
            flag = 0;
        }
    }


    /**
     * notifie l'affichage que le semestre courant a changer
     */

    public void notifySemestreChange() {
        getSupportActionBar().setTitle("Semestre "+(modele.getIndexCurrentSemester()+1));
        notifyUeListView();
        collapseList();
        updateButton();
        updateInformationCourse();
    }


    /**
     * Permet de mettre a jour les information de parcours du semestre
     */
    public void updateInformationCourse(){
        if(modele ==null || modele.getCourse()==null) {
            information.setText("Chargement");
            return;
        }
        int ueToChosse = modele.getCourse().ueNeedToCompleteSemester(modele.getIndexCurrentSemester());
        information.setText("UE a choisir pour ce semestre: "+ueToChosse);

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
        else previousButton.setText(R.string.menuPrecedent);

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

                // add the message to view
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


    @Override
    public boolean onClose() {
        listAdapter.filterData("");
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        listAdapter.filterData(s);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        listAdapter.filterData(s);
        return false;
    }

    /*SETTER uniquement pour les test */
    protected void setModele(CourseBuilderModele modele){
        this.modele = modele;
    }
    protected void setUserController(UserController userController){
        this.userController = userController;
    }
}
