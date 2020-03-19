package com.example.plplbproject.controleur.semestreBuilder;

import android.view.View;
import android.widget.CheckBox;

import com.example.plplbproject.Vue.Vue;

import metier.UE;
import metier.parcours.Parcours;

public class UeClickListener implements View.OnClickListener{

    private UE ue;
    private CheckBox checkBox;
    private Vue vue;
    private Parcours parcours;

    public UeClickListener(CheckBox checkBox, UE ue,Parcours parcours) {
        this.ue = ue;
        this.checkBox = checkBox;
        this.vue = vue;
        this.parcours = parcours;
    }

    @Override
    public void onClick(View view) {

         if(!parcours.isChecked(ue) && parcours.canBeCheckedUE(ue)){ //Change le check de l'ue en consequence.
             parcours.checkUE(ue);
             checkBox.setChecked(true);
         }
         else if(parcours.canBeUncheckedUE(ue)){
             parcours.uncheckUE(ue);
             checkBox.setChecked(false);
         }
         else{
             checkBox.setChecked(true);
         }
         //Un changement a eu lieu.
         //vue.needSave(true);
    }
}

