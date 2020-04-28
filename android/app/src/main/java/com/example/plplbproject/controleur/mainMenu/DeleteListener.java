package com.example.plplbproject.controleur.mainMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.mainMenu.MainMenuActivity;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import io.socket.emitter.Emitter;

import static constantes.NET.DELETECOURSE;


public class DeleteListener implements View.OnClickListener {

    private MainMenuActivity mainMenuActivity;
    private String parcoursName;
    private CourseNamesList courseNamesList;

    public DeleteListener(String parcoursName, MainMenuActivity mainMenuActivity, CourseNamesList courseNamesList){
        this.parcoursName = parcoursName;
        this.mainMenuActivity = mainMenuActivity;
        this.courseNamesList = courseNamesList;
    }

    @Override
    public void onClick(View view) {
        askConfirm();
    }

    public void askConfirm(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainMenuActivity);

        LayoutInflater inflater = (LayoutInflater)mainMenuActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.delete_confirmation_dialog, null);

        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_delete_forever_24px);

        final AlertDialog dialog = alertDialog.create();

        Button oui = dialogView.findViewById(R.id.ouiBoutton);
        Button non = dialogView.findViewById(R.id.nonBoutton);

        oui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
                dialog.dismiss();
            }
        });
        non.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void confirm(){
        Connexion.CONNEXION.setEventListener(DELETECOURSE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Connexion.CONNEXION.removeEventListener(DELETECOURSE);

                Gson gson = new GsonBuilder().create();
                Boolean deleted = gson.fromJson((String) args[0], Boolean.class);

                if(deleted){
                    mainMenuActivity.toastMessage("Parcours supprimé");
                    courseNamesList.removeCourse(parcoursName);
                    mainMenuActivity.notifyCourseListChange();//On notifie des changements

                }
                else{
                    mainMenuActivity.toastMessage("Le parcours n'a pas pu être supprimé auprès du serveur");
                }
            }
        });
        Connexion.CONNEXION.send(DELETECOURSE,parcoursName);
    }

}
