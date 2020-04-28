package com.example.plplbproject.Vue.mainMenu;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.mainMenu.CourseNamesList;
import com.example.plplbproject.controleur.mainMenu.DeleteListener;
import com.example.plplbproject.controleur.mainMenu.ModifyListener;
import com.example.plplbproject.controleur.mainMenu.PreviewListener;
import com.example.plplbproject.controleur.mainMenu.RenameListener;


/**
 * L'adapteur pour la liste des parcours du client
 */
public class ClientCourseAdapter extends RecyclerView.Adapter<ClientCourseViewHolder> {

    private CourseNamesList courseNamesList;//La liste de nom des parcours
    private MainMenuActivity mainMenuActivity;

    public ClientCourseAdapter(CourseNamesList courseNamesList, MainMenuActivity mainMenuActivity) {
        this.courseNamesList = courseNamesList;
        this.mainMenuActivity = mainMenuActivity;
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

        String currentCourse = courseNamesList.getCourse(position);

        holder.parcoursName.setText(currentCourse);


        holder.supprButton.setOnClickListener(new DeleteListener(
                currentCourse,
                mainMenuActivity,
                courseNamesList));

        holder.renomButton.setOnClickListener(new RenameListener(
                currentCourse,
                mainMenuActivity,
                courseNamesList));


        holder.visualiserButton.setOnClickListener(new PreviewListener(
                currentCourse,
                mainMenuActivity));

        holder.modifButton.setOnClickListener(new ModifyListener(
                currentCourse,
                mainMenuActivity));
    }

    @Override
    public int getItemCount() {
        return courseNamesList.getSize();
    }
}
