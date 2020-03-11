package com.example.plplbproject.controleur;

import android.view.View;
import android.widget.CheckBox;

import com.example.plplbproject.Vue.Vue;

import metier.Parcours;
import metier.UE;

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
             parcours.addUEParcours(ue);
             checkBox.setChecked(true);
         }
         else if(parcours.canBeUncheckedUE(ue)){
             parcours.delUEParcours(ue);
             checkBox.setChecked(false);
         }
         else{
             checkBox.setChecked(true);
         }
         //Un changement a eu lieu.
         //vue.needSave(true);
    }
}

