package com.example.plplbproject.Vue.mainMenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.creationMenu.CreationMenuActivity;
import com.example.plplbproject.controleur.mainMenu.CourseNamesList;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.data.UpdatePredefinedCourse;
import com.example.plplbproject.data.UpdateSemester;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.Parcours;

import static constantes.NET.ASKCODE;
import static constantes.NET.COURSECODE;
import static constantes.NET.COURSESNAMES;
import static constantes.NET.DELETECOURSE;
import static constantes.NET.PREDEFINEDCOURSE;
import static constantes.NET.SEMSTERDATA;

/**
 * L'Activité du menu principal.
 */
public class MainMenuActivity extends AppCompatActivity{

    private Context context;
    private Button deconnexion;//Le bouton de deconnexion.
    private Button newCourse;//Le button pour creer un nouveau parcours

    private CourseNamesList courseNamesList;//La liste de nom des parcours du client.

    private RecyclerView clientCourseRecyclerView;
    private ClientCourseAdapter clientCourseAdapter;

    private final Gson gson = new GsonBuilder().create();

    String code;
    String actualParcoursName;
    private ImageButton addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_princ);
        this.context = getApplicationContext();
        courseNamesList = new CourseNamesList();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();


        //le client a reussi a se connecter et n'a pas la liste des semestre on lui envoie
        if(!DataSemester.SEMESTER.hasSemesterList()){
            Connexion.CONNEXION.setEventListener(SEMSTERDATA, new UpdateSemester());
            Connexion.CONNEXION.send(SEMSTERDATA, "");
        }

        //Le client a reussi a se connecter et n'a pas la liste des parcours prédéfinis. on lui envoie
        if(!DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList()){
            Connexion.CONNEXION.setEventListener(PREDEFINEDCOURSE, new UpdatePredefinedCourse());
            Connexion.CONNEXION.send(PREDEFINEDCOURSE,"");
        }

        deconnexion = findViewById(R.id.deconnexion);
        newCourse = findViewById(R.id.nouveauParcours);
        addButton = findViewById(R.id.addImageButton);


        // #################### Mise en place de l'adapter de parcours ################################"

        clientCourseRecyclerView = findViewById(R.id.parcoursList);
        clientCourseAdapter = new ClientCourseAdapter(courseNamesList,this);
        clientCourseRecyclerView.setAdapter(clientCourseAdapter);
        clientCourseRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // ################## Définition des listeners #####################

        //Le listener du bouton de nouveau parcours.
        newCourse.setOnClickListener(createNewCourse());

        //Le listener du bouton de deconnexion.
        deconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Listener pour ajout d'un parcours via Code
        addButton.setOnClickListener(addButtonListener());

        // ################### récupération des parcours sauvegardés #####################################
        setupCoursesList();

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


    public void notifyCourseListChange(){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                clientCourseAdapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * setup et appel les event necessaire pour cette activity
     */
    public void setupCoursesList(){
        Connexion.CONNEXION.setEventListener(COURSESNAMES, receiveParcoursName());
        Connexion.CONNEXION.send(COURSESNAMES,"");
    }

    public void setClientCourses(ArrayList<String> clientCourses){
        this.courseNamesList.addAll(clientCourses);
    }


    /**
     * Gère la reception des parcours sauvegardés envoyé par le serveur
     * Le serveur envoie les noms de parcours uniquement
     * @return traitement à affectuer
     */
    public Emitter.Listener receiveParcoursName (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Gson gson = new GsonBuilder().create();

                final ArrayList<String> coursesNames = gson.fromJson((String) args[0], ArrayList.class);

                if(coursesNames != null) {
                    setClientCourses(coursesNames);
                    notifyCourseListChange();
                }
            }
        };
    }

    /**
     * Le listener du bouton newCourse
     * Il switch d'intent pour aller vers le menu de creation de parcours.
     * @return le listener
     */
    public View.OnClickListener createNewCourse(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainMenuActivity.this , CreationMenuActivity.class);
                intent.putExtra("clientCourses",courseNamesList.getList());
                startActivity(intent);
            }
        };
    }

    public View.OnClickListener addButtonListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenuActivity.this);
                alertDialog.setTitle("Ajout par code");
                alertDialog.setMessage("Entrer un code");

                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.add_course_alertdialog, null);

                alertDialog.setView(dialogView);
                alertDialog.setIcon(R.drawable.ic_library_add_black_18dp);

                final EditText input = dialogView.findViewById(R.id.codeInput);

                alertDialog.setPositiveButton("Ajouter",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                code = input.getText().toString();
                                if (code != "") {
                                    if(!courseNamesList.isInList(code)){

                                        Connexion.CONNEXION.removeEventListener(COURSECODE);
                                        Connexion.CONNEXION.setEventListener(COURSECODE,receiveCourseWithCode());
                                        Connexion.CONNEXION.send(COURSECODE,gson.toJson(code));
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Ce parcours est déjà dans vos sauvegardes", Toast.LENGTH_SHORT).show();
                                    }

                                }
                                else {
                                    Toast.makeText(getApplicationContext(), "Veuillez entrer un code valide", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                alertDialog.setNegativeButton("Annuler",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
            }

        };
    }


    /**
     * Controller qui permet de gerer la reception du parcours
     * @return le controller
     */
    public Emitter.Listener receiveCourseWithCode(){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                final Boolean parcoursFound = gson.fromJson((String) args[0], Boolean.class);
                if(parcoursFound == true){

                    toastMessage("Parcours ajouté");
                    courseNamesList.addCourse(code);
                    notifyCourseListChange();
                }
                else{
                    toastMessage("Veuillez entrer un code valide");
                }
            }
        };
    }
}
