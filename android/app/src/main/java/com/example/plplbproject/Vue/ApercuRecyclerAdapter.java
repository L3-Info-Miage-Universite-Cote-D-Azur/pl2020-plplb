package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

import java.lang.reflect.Modifier;
import java.util.ArrayList;

import metier.MainModele;
import metier.semestre.Semestre;

public class ApercuRecyclerAdapter extends RecyclerView.Adapter<ApercuViewHolder>{

    private Context context;
    private MainModele modele;
    private ArrayList<Semestre> semestres;

    public ApercuRecyclerAdapter(Context context, MainModele modele) {
        this.context = context;
        this.modele = modele;
        this.semestres = modele.getSemestres();
    }

    @NonNull
    @Override
    public ApercuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.semestre_element, viewGroup, false);

        return new ApercuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ApercuViewHolder holder, int position) {

        int semestreNumber = this.semestres.get(position).getNumber();
        holder.semesterName.setText("Semestre " + semestreNumber);

        //Semestre semestre = this.semestres.get(position);
        holder.listView.setAdapter(new ApercuAdapter(context,modele,semestreNumber));

    }


    @Override
    public int getItemCount() {
        return semestres.size();
    }
}
