package com.example.plplbproject.Vue.mainMenu;


import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.Vue.previewCourse.PreviewActivity;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.Parcours;

import static constantes.NET.DELETECOURSE;
import static constantes.NET.RENAMECOURSE;

/**
 * L'adapteur pour la liste des parcours du client
 */
public class ClientCourseAdapter extends RecyclerView.Adapter<ClientCourseViewHolder> {

    private ArrayList<String> coursesNames;//La liste de nom des parcours
    private MainMenuActivity mainMenuActivity;
    private final Gson gson = new GsonBuilder().create();
    private String actualParcoursName;
    private String newParcoursName;
    private InputMethodManager imm;


    public ClientCourseAdapter(ArrayList<String> coursesNames, MainMenuActivity mainMenuActivity) {
        this.coursesNames = coursesNames;
        this.mainMenuActivity = mainMenuActivity;
        this.actualParcoursName = "";
    }

    /**
     * permet de set la liste des nom de parcours
     * @param parcoursNames la liste a set
     */
    public void setParcoursNames(ArrayList<String> parcoursNames){
        this.coursesNames = parcoursNames;
    }

    @NonNull
    @Override
    public ClientCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(mainMenuActivity.getApplicationContext()).inflate(R.layout.parcours_element, viewGroup, false);
        return new ClientCourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ClientCourseViewHolder holder, final int position) {
        holder.parcoursName.setText(coursesNames.get(position));


        holder.supprButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualParcoursName = coursesNames.get(position);
                mainMenuActivity.askConfirm(actualParcoursName);
            }
        });

        holder.renomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.parcoursName.setFocusable(true);
                holder.parcoursName.setFocusableInTouchMode(true);
                holder.parcoursName.requestFocus();

                imm = (InputMethodManager) mainMenuActivity.getSystemService(mainMenuActivity.getApplicationContext().INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.parcoursName, InputMethodManager.SHOW_FORCED);

                mainMenuActivity.getNewCourse().setText("Terminer le renommage");
                mainMenuActivity.getNewCourse().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                        actualParcoursName = coursesNames.get(position);

                        if (holder.parcoursName.getText() != null) {
                            newParcoursName = holder.parcoursName.getText().toString();
                        }
                        else{
                            newParcoursName = "";
                        }
                        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                        mainMenuActivity.reSetNewCourse();
                        holder.parcoursName.setFocusable(false);
                        holder.parcoursName.setFocusableInTouchMode(false);

                        ArrayList<String> nomsAEnvoyer = new ArrayList<>();
                        nomsAEnvoyer.add(actualParcoursName);
                        nomsAEnvoyer.add(newParcoursName);


                        if(!Connexion.CONNEXION.isRenameListenerSet()){
                            Connexion.CONNEXION.setEventListener(RENAMECOURSE,rename());
                            Connexion.CONNEXION.setRenameListenerSet(true);
                        }
                        Connexion.CONNEXION.send(RENAMECOURSE,gson.toJson(nomsAEnvoyer));

                    }
                });

            }
        });


        holder.visualiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                actualParcoursName = coursesNames.get(position);
                Intent intent = new Intent(mainMenuActivity, PreviewActivity.class);
                intent.putExtra("className","MainMenuActivity");
                intent.putExtra("CourseName",coursesNames.get(position));
                mainMenuActivity.startActivityForResult(intent,2);

            }
        });

        holder.modifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actualParcoursName = coursesNames.get(position);
                Intent intent = new Intent(mainMenuActivity, CourseBuilderActivity.class);
                intent.putExtra("className","MainMenuActivity");
                intent.putExtra("CourseName",coursesNames.get(position));
                mainMenuActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursesNames.size();
    }


    /**
     * Supprime le parcours voulu en appellant la méthode delete de mainMenuActivity
     */
    public Emitter.Listener rename(){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                Boolean renamed = gson.fromJson((String) args[0], Boolean.class);
                mainMenuActivity.rename(renamed,actualParcoursName,newParcoursName);
            }
        };
    }



}
