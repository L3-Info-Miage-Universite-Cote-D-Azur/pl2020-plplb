package com.example.plplbproject.Vue.creationMenu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.creationMenu.CreationMenuModele;

import java.util.ArrayList;

/**
 * Adapter qui permet de gerer la liste des parcours type existant
 */
public class PredefinedCourseAdapter extends RecyclerView.Adapter<PredefinedCourseViewHolder> {

    private CreationMenuActivity vue;
    private ArrayList<String> predefinedCoursesNames;
    private CreationMenuModele modele;

    public PredefinedCourseAdapter(CreationMenuActivity vue, CreationMenuModele modele) {
        this.vue = vue;
        this.predefinedCoursesNames = modele.getListPredefinedCourse();
        this.modele = modele;
    }

    @NonNull
    @Override
    public PredefinedCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(vue.getApplicationContext()).inflate(R.layout.parcours_predef_element, viewGroup, false);
        return new PredefinedCourseViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull PredefinedCourseViewHolder holder, final int position) {
        final String name = predefinedCoursesNames.get(position);
        holder.predefinedCourseName.setText(name);
        if(modele.getPredefinedCourseName()!= null && name.equals(modele.getPredefinedCourseName())) holder.setSelected();
        else holder.setUnselected();
        //application du listenener
        holder.predefinedCourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modele.setPredefinedCourseName(name);
                vue.notifyParcoursTypeSelected();
            }
        });

    }

    @Override
    public int getItemCount() {
        return predefinedCoursesNames.size();
    }





}
