package com.example.plplbproject.controleur.creationMenu;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.creationMenu.CreationMenuActivity;
import com.example.plplbproject.data.DataPredefinedCourse;

import java.util.ArrayList;
import java.util.HashMap;

import metier.parcours.ParcoursType;

public class CreateNewCourseListener implements View.OnClickListener {
    CreationMenuActivity vue;
    CreationMenuModele modele;

    public CreateNewCourseListener(CreationMenuActivity vue, CreationMenuModele modele){
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void onClick(View view) {
        String courseName = vue.getCourseName();

        //Si le nom peut etre choisir
        if(modele.canBeChooseName(courseName)){
            if(modele.isSelectedPredefinedCourse()){//Si un parcours est selectionner.

                LayoutInflater li = LayoutInflater.from(vue);
                View promptsView = li.inflate(R.layout.prompt_name, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(vue);
                builder.setView(promptsView);

                ArrayAdapter<String> adapter = new ArrayAdapter<>(vue,R.layout.ue_obligatoire_element,dialogMessageBuilder(modele.getPredefinedCourseName()));


                builder
                        .setTitle("Vous allez créer un parcours " + modele.getPredefinedCourseName())
                        .setCancelable(false)
                        //.setMessage()
                        .setPositiveButton("Créer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                modele.setCourseName(vue.getCourseName());
                                vue.switchIntent();
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });

                builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
            else{//Sinon on affiche l'erreur
                vue.setTextError("Veuillez selectionner une parcours prédéfini.");
            }
        }
        else{//Sinon on affiche l'erreur
            vue.setTextError("Ce nom de parcours existe déjà");
        }
    }

    public ArrayList<String> dialogMessageBuilder(String parcoursName){

        ParcoursType parcoursType = DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse(parcoursName);
        HashMap<String, Integer> numberUes = parcoursType.getNumberUes();

        ArrayList<String> messagefinal = new ArrayList<>();

        String message = "";
        messagefinal.add("");
        messagefinal.add("      Pour valider votre parcours, vous devrez");
        messagefinal.add("      obligatoirement cocher les Ues ci-dessous:");


        if(numberUes != null){

            for (String s: numberUes.keySet()) {

                message = "         - " + numberUes.get(s) + " Ues de la catégorie " + s;
                messagefinal.add(message);
            }

        }

        messagefinal.add("");
        
        return messagefinal;
    }

}
