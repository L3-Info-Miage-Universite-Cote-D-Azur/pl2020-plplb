package com.example.plplbproject.Vue.menuPrincipal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.ApercuViewHolder;

import java.util.ArrayList;

import metier.parcours.Parcours;

public class ParcoursRecyclerAdapter extends RecyclerView.Adapter<ParcoursViewHolder> {

    private Context context;
    private ArrayList<String> parcours;

    public ParcoursRecyclerAdapter(Context context, ArrayList<String> parcours) {
        this.context = context;
        this.parcours = parcours;
    }

    @NonNull
    @Override
    public ParcoursViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.parcours_element, viewGroup, false);

        return new ParcoursViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ParcoursViewHolder holder, int position) {
        holder.parcoursName.setText(parcours.get(position));

        //TODO Mettre les listeners sur les deux boutons
    }

    @Override
    public int getItemCount() {
        return parcours.size();
    }
}
