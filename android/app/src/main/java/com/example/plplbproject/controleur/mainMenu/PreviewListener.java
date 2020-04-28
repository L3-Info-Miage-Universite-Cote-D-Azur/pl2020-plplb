package com.example.plplbproject.controleur.mainMenu;

import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.mainMenu.MainMenuActivity;
import com.example.plplbproject.Vue.previewCourse.PreviewActivity;

public class PreviewListener implements View.OnClickListener {

    private String parcoursName;
    private MainMenuActivity activity;//Besoin de l'activité pour le result

    public PreviewListener(String parcoursName, MainMenuActivity activity){
        this.activity = activity;
        this.parcoursName = parcoursName;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(activity, PreviewActivity.class);
        intent.putExtra("className","MainMenuActivity");
        intent.putExtra("CourseName",parcoursName);
        activity.startActivityForResult(intent,2);
    }
}
