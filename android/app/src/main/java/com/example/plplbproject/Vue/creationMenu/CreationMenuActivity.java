package com.example.plplbproject.Vue.creationMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.controleur.creationMenu.CreateNewCourseListener;
import com.example.plplbproject.controleur.creationMenu.CreationMenuModele;
import com.example.plplbproject.data.DataPredefinedCourse;

import java.util.ArrayList;


/**
 * Activity de la page de creation d'un nouveau menu
 */
public class CreationMenuActivity extends AppCompatActivity {
    private CreationMenuModele modele;

    private RecyclerView courseRecyclerView;
    private PredefinedCourseAdapter adapterPredefinedCourse;

    private Button deconnexion;//Le bouton de deconnexion
    private Button createNewCourse;//Le bouton pour creer le nouveau parcours
    private EditText editCourseName;//Le champs rempli avec le nom du nouveau parcours
    private TextView errorMessage;//L'affiche d'erreur

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inter);

        ArrayList<String> coursesNames = (ArrayList<String>) getIntent().getSerializableExtra("clientCourses");

        ArrayList<String> predefinedCourseName = DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseName();

        modele = new CreationMenuModele(predefinedCourseName,coursesNames);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Chargement des elements graphiques
        courseRecyclerView = findViewById(R.id.parcoursPredefList);
        deconnexion = findViewById(R.id.deconnexion);
        createNewCourse = findViewById(R.id.nouveauParcours);
        editCourseName = findViewById(R.id.editParcoursName);
        errorMessage = findViewById(R.id.errorMessage);

        //Mise en place de l'adapter.
        adapterPredefinedCourse = new PredefinedCourseAdapter(this,modele);
        courseRecyclerView.setAdapter(adapterPredefinedCourse);
        courseRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        //Mise en place des listener
        createNewCourse.setOnClickListener(new CreateNewCourseListener(this,modele));

        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==2)
        {
            finish();
        }
    }

    /**
     * Recupere le texte dans l'edittext
     * @return le nom du parcours à creer.
     */
    public String getCourseName(){
        return editCourseName.getText().toString();
    }

    /**
     * Permet de changer d'activité
     */
    public void switchIntent(){
        Intent intent = new Intent(CreationMenuActivity.this, CourseBuilderActivity.class);
        intent.putExtra("PredefinedCourseName",modele.getPredefinedCourseName());
        intent.putExtra("CourseName",modele.getCourseName());
        intent.putExtra("className","CreationMenuActivity");
        startActivityForResult(intent,2);
    }

    public void notifyParcoursTypeSelected(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adapterPredefinedCourse.notifyDataSetChanged();
            }
        });
    }

    /**
     * affiche le message text dans le champ de l'erreur
     * @param text : le texte a afficher.
     */
    public void setTextError(String text) {
        errorMessage.setText(text);
        errorMessage.setVisibility(View.VISIBLE);
    }

    /**
     * permet de recuperer le modele (utile pour les test)
     * @return le modele de lactivity
     */
    protected CreationMenuModele getModele(){
        return modele;
    }

    /**
     * permet de set le modele (utile pour les test
     * @param modele le modele que l'on veut set
     */
    protected void setModele(CreationMenuModele modele){
        this.modele = modele;
    }
}
