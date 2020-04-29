package com.example.plplbproject.controleur.mainMenu;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.mainMenu.MainMenuActivity;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.ResourceBundle;

import io.socket.emitter.Emitter;

import static constantes.NET.DELETECOURSE;
import static constantes.NET.RENAMECOURSE;

public class RenameListener implements View.OnClickListener {

    private MainMenuActivity mainMenuActivity;
    private String oldParcoursName;
    private String newParcourName;
    private CourseNamesList courseNamesList;

    public RenameListener(String oldParcoursName,MainMenuActivity mainMenuActivity, CourseNamesList courseNamesList){
        this.oldParcoursName = oldParcoursName;
        this.mainMenuActivity = mainMenuActivity;
        this.courseNamesList = courseNamesList;
        this.newParcourName = "ERROR";
    }

    @Override
    public void onClick(View view) {
        askConfirm();
    }

    public void askConfirm(){

        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mainMenuActivity);

        final LayoutInflater inflater = (LayoutInflater)mainMenuActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.rename_popup, null);

        alertDialog.setView(dialogView);
        alertDialog.setIcon(R.drawable.ic_delete_forever_24px);

        final AlertDialog dialog = alertDialog.create();

        Button confirmButton = dialogView.findViewById(R.id.confirmRenameBoutton);
        Button cancelButton = dialogView.findViewById(R.id.cancelRenameBoutton);
        TextView oldNameFiled = dialogView.findViewById(R.id.oldname);
        final EditText inputField = dialogView.findViewById(R.id.newNameInput);

        oldNameFiled.setText(oldParcoursName);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newParcourName = inputField.getText().toString();

                if(newParcourName.trim().equals("") || !courseNamesList.canBeChoosed(newParcourName)){//Si il ne remplit rien ou est valide.
                    mainMenuActivity.toastMessage("Nom invalide.");
                }
                else {
                    confirm();
                }
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void confirm(){
        final Gson gson = new GsonBuilder().create();
        Connexion.CONNEXION.setEventListener(RENAMECOURSE, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Connexion.CONNEXION.removeEventListener(RENAMECOURSE);

                Boolean renamed = gson.fromJson((String) args[0], Boolean.class);

                if(renamed){
                    mainMenuActivity.toastMessage("Parcours renommé");
                    courseNamesList.removeCourse(oldParcoursName);
                    courseNamesList.addCourse(newParcourName);
                    mainMenuActivity.notifyCourseListChange();//On notifie des changements
                }
                else{
                    mainMenuActivity.toastMessage("Le parcours n'a pas pu être renommé auprès du serveur");
                }
            }
        });
        ArrayList<String> toSend = new ArrayList<String>();
        toSend.add(oldParcoursName);
        toSend.add(newParcourName);

        Connexion.CONNEXION.send(RENAMECOURSE,gson.toJson(toSend));
    }
}
