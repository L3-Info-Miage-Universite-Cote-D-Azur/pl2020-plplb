package com.example.plplbproject.controleur.courseBuilder;



import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.Parcours;


/**
 * Controlleur de tout les evenement venant du reseau
 */
public class ReseauController{

    /*FIELDS*/
    private CourseBuilderActivity vue;
    private CourseBuilderModele modele;
    private final Gson gson = new GsonBuilder().create();

    /*CONSTRUCTOR*/
    public ReseauController(CourseBuilderActivity vue, CourseBuilderModele modele){
        this.vue = vue;
        this.modele = modele;
    }


    /**
     * Gère la reception de la la sauvegarde envoyée par le serveur
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public Emitter.Listener receiveSave() {
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> ueCode = gson.fromJson((String) args[0], ArrayList.class);
                System.out.println("save receive from server");
                //TODO Modifier parcours
                modele.setCourse(new Parcours(DataSemester.SEMESTER.getSemesterList(),ueCode, DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList()));
                vue.notifyUeListView();
                vue.updateInformationCourse();
            }
        };
    }

}
