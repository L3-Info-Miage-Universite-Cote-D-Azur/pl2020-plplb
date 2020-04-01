package com.example.plplbproject.controleur.creationMenu;

import android.view.View;

import com.example.plplbproject.Vue.creationMenu.CreationMenuActivity;
import com.example.plplbproject.controleur.creationMenu.CreationMenuModele;

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
                modele.setCourseName(courseName);
                vue.switchIntent();
            }
            else{//Sinon on affiche l'erreur
                vue.setTextError("Veuillez selectionner une parcours prédéfini.");
            }
        }
        else{//Sinon on affiche l'erreur
            vue.setTextError("Ce nom de parcours existe déjà");
        }
    }

}
