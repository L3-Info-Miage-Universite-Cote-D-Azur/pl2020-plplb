package com.example.plplbproject.controleur.courseBuilder;

import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.Vue.previewCourse.PreviewActivity;


/**
 * Classe de gestion des event de l'utilisateur
 */
public class UserController {

    /*FIELDS*/
    private CourseBuilderActivity vue;
    private CourseBuilderModele modele;

    /*CONSTRUCTOR*/
    public UserController(CourseBuilderActivity courseBuilderActivity, CourseBuilderModele modele){
        this.vue = courseBuilderActivity;
        this.modele = modele;
    }

    /**
     * Gere l'appui sur le bouton de sauvegarde
     * @return traitement a effectuer (sur le modele et la vue)
     */
    public View.OnClickListener saveButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modele.getCourse().verifiParcours()){
                    Intent intent = new Intent(vue.getApplicationContext(), PreviewActivity.class);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Course",modele.getCourse());
                    intent.putExtra("className","CourseBuilderActivity");
                    vue.startActivityForResult(intent,1);
                }
                else vue.toastMessage(errorMessage());
                //TODO verification que le serveur a bien recus la sauvegarde
            }
        };
    }

    /**
     * controller du boutton suivant
     * @return le controller
     */
    public View.OnClickListener nextButton(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean nextExist = modele.hasNextSemestre();
                if(nextExist){
                    modele.nextSemester();
                    vue.notifySemestreChange();
                }
                else{
                    saveButton().onClick(view);
                }
            }
        };
    }

    /**
     * controller du boutton precedent
     * @return le controller
     */
    public View.OnClickListener prevButton(){
        return new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                boolean prevExist = modele.hasPrevSemestre();
                if(prevExist){
                    modele.prevSemestre();
                    vue.notifySemestreChange();
                }
                else vue.exitIntent();
            }
        };
    }

    public String errorMessage(){

        // Une erreur dans
        if(modele.getCourse().getParcoursRules().getCurrentErrorMessage() != ""){
            return modele.getCourse().getParcoursRules().getCurrentErrorMessage();
        }
        else{
            return "Une UE libre n'a pas été cochée";
        }
    }



}
