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

    private ArrayList<String> clientCourses;//La liste de nom des parcours du client.

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



        if(clientCourses == null){
            clientCourses = new ArrayList<String>();
        }

        // #################### Mise en place de l'adapter de parcours ################################"

        clientCourseRecyclerView = findViewById(R.id.parcoursList);
        clientCourseAdapter = new ClientCourseAdapter(clientCourses,this);
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
     * setup et appel les event necessaire pour cette activity
     */
    public void setupCoursesList(){
        Connexion.CONNEXION.setEventListener(COURSESNAMES, receiveParcoursName());
        Connexion.CONNEXION.send(COURSESNAMES,"");
    }

    public void setClientCourses(ArrayList<String> clientCourses){
        this.clientCourses = clientCourses;
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
                setClientCourses(coursesNames);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clientCourseAdapter.setParcoursNames(coursesNames);
                        clientCourseAdapter.notifyDataSetChanged();
                    }
                });
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
                intent.putExtra("clientCourses",clientCourses);
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
                                    if(!Connexion.CONNEXION.isCodeListenerSet()){
                                        Connexion.CONNEXION.setEventListener(COURSECODE,receiveCourseWithCode());
                                        Connexion.CONNEXION.setCodeListenerSet(true);
                                    }
                                    Connexion.CONNEXION.send(COURSECODE,gson.toJson(code));
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
                    
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Parcours ajouté", Toast.LENGTH_SHORT).show();

                            clientCourses.add(code);
                            clientCourseAdapter.notifyDataSetChanged();
                        }
                    });
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Veuillez entrer un code valide", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        };
    }


    public void delete(Boolean deleted, String s){

        final String courseName = s;

        if(deleted){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    clientCourses.remove(courseName);
                    clientCourseAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Parcours supprimé", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Le parcours n'a pas pu être supprimé auprès du serveur", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void rename(Boolean renamed, String s, String news){

        final String courseName = s;


        final String newCourseName = news;

        if(renamed){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    clientCourses.remove(courseName);
                    clientCourses.add(newCourseName);
                    clientCourseAdapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "Parcours renommé", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getApplicationContext(), "Le parcours n'a pas pu être renommé auprès du serveur", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public Button getNewCourse() {
        return newCourse;
    }

    public void reSetNewCourse(){

        newCourse.setText("Nouveau Parcours");
        newCourse.setOnClickListener(createNewCourse());

    }

    public void askConfirm(String parcoursName){

        this.actualParcoursName = parcoursName;

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainMenuActivity.this);

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.delete_confirmation_dialog, null);

        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_delete_forever_24px);

        final AlertDialog dialog = alertDialog.create();

        Button oui = dialogView.findViewById(R.id.ouiBoutton);
        Button non = dialogView.findViewById(R.id.nonBoutton);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Connexion.CONNEXION.setEventListener(DELETECOURSE,delete());
                Connexion.CONNEXION.send(DELETECOURSE,actualParcoursName);
                dialog.dismiss();
            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    /**
     * Supprime le parcours voulu en appellant la méthode delete de mainMenuActivity
     */
    public Emitter.Listener delete(){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Boolean deleted = gson.fromJson((String) args[0], Boolean.class);
                delete(deleted,actualParcoursName);
            }
        };
    }

}
