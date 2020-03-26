package com.example.plplbproject.Vue.menuPrincipal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

import java.util.ArrayList;

import metier.MenuInterModele;
import metier.parcours.Parcours;


public class ParcoursPredefRecyclerAdapter extends RecyclerView.Adapter<ParcoursPredefViewHolder> {

    private Context context;
    private ArrayList<String> parcoursTypeName;
    private MenuInterModele modele;

    public ParcoursPredefRecyclerAdapter(Context context, MenuInterModele modele) {
        this.context = context;
        this.parcoursTypeName = modele.getListParcoursPredef();
        this.modele = modele;
    }

    @NonNull
    @Override
    public ParcoursPredefViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.parcours_predef_element, viewGroup, false);
        return new ParcoursPredefViewHolder(view);

    }

    @Override

    public void onBindViewHolder(@NonNull ParcoursPredefViewHolder holder, final int position) {
        final String name = parcoursTypeName.get(position);
        holder.parcoursPredefName.setText(name);
        if(modele.getParcoursTypeName()!= null && name.equals(modele.getParcoursTypeName())) holder.setSelected();
        else holder.setUnselected();
        holder.parcoursPredefName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modele.setParcoursTypeName(name);
                ((MenuInter)context).notifyParcoursTypeSelected();
            }
        });

    }

    @Override
    public int getItemCount() {
        return parcoursTypeName.size();
    }





}
