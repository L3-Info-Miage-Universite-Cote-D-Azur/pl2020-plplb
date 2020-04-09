package com.example.plplbproject.controleur.UeVisualisation;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.UeVisualisation.UeVisualisationActivity;

import metier.UE;

/**
 * Le Click listener qui amene vers la visualisation détaillée d'une UE
 */
public class UeVisualisationClickListener implements View.OnClickListener {

    //Le context avec lequel on appelle la classe
    Context context;
    //L'ue qu'il faut afficher
    UE ue;

    public UeVisualisationClickListener(Context context, UE ue){
        this.context = context;
        this.ue = ue;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, UeVisualisationActivity.class);
        intent.putExtra("UE",ue);//On donne l'ue
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);//Afin de pouvoir start l'activity depuis un context
        context.startActivity(intent);
    }
}
