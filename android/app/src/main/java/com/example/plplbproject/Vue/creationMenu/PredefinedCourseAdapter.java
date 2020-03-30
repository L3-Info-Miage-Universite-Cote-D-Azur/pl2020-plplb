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


public class PredefinedCourseAdapter extends RecyclerView.Adapter<PredefinedCourseViewHolder> {

    private Context context;
    private ArrayList<String> predefinedCoursesNames;
    private CreationMenuModele modele;

    public PredefinedCourseAdapter(Context context, CreationMenuModele modele) {
        this.context = context;
        this.predefinedCoursesNames = modele.getListPredefinedCourse();
        this.modele = modele;
    }

    @NonNull
    @Override
    public PredefinedCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.parcours_predef_element, viewGroup, false);
        return new PredefinedCourseViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull PredefinedCourseViewHolder holder, final int position) {
        final String name = predefinedCoursesNames.get(position);
        holder.predefinedCourseName.setText(name);
        if(modele.getPredefinedCourseName()!= null && name.equals(modele.getPredefinedCourseName())) holder.setSelected();
        else holder.setUnselected();
        holder.predefinedCourseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modele.setPredefinedCourseName(name);
                ((CreationMenuActivity)context).notifyParcoursTypeSelected();
            }
        });

    }

    @Override
    public int getItemCount() {
        return predefinedCoursesNames.size();
    }





}
