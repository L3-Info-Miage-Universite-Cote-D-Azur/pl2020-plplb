package com.example.plplbproject.controleur.menuPrincipal;

import android.view.View;

import com.example.plplbproject.Vue.menuPrincipal.MenuInter;

import metier.MenuInterModele;

public class createNewParcoursListener implements View.OnClickListener {
    MenuInter vue;
    MenuInterModele modele;

    public createNewParcoursListener(MenuInter vue, MenuInterModele modele){
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void onClick(View view) {
        String parcoursName = vue.getParcoursName();

        //Si le nom peut etre choisir
        if(modele.canBeChooseName(parcoursName)){
            if(modele.isSelectedParcours()){//Si un parcours est selectionner.
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
