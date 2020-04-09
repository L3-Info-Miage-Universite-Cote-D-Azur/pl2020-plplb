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
    public void saveButton(){
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
                    saveButton();
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

    /**
     * Permet de generer un message d'erreur
     * @return le message d'erreur
     */
    public String errorMessage(){

        // Une erreur dans
        if(modele.getCourse().getParcoursRules()!=null &&
                modele.getCourse().getParcoursRules().getCurrentErrorMessage() != ""){
            return modele.getCourse().getParcoursRules().getCurrentErrorMessage();
        }
        else{
            String errorMessage = modele.getCourse().getLastVerifErrorMessage();
            if(errorMessage == null || errorMessage.trim().equals("")) {
                //si il a pas de message generer on ne connais pas l'erreur
                return "Votre parcours est incorrect"; //cas normalement improbable
            }
            return errorMessage;
        }
    }



}
