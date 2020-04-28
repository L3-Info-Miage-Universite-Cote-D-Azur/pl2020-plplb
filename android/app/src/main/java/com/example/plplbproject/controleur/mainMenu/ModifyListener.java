package com.example.plplbproject.controleur.mainMenu;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;

public class ModifyListener implements View.OnClickListener {

    private String parcoursName;
    private Context context;

    public ModifyListener(String parcoursName, Context context){
        this.context = context;
        this.parcoursName = parcoursName;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(context, CourseBuilderActivity.class);
        intent.putExtra("className","MainMenuActivity");
        intent.putExtra("CourseName",parcoursName);
        context.startActivity(intent);
    }
}
