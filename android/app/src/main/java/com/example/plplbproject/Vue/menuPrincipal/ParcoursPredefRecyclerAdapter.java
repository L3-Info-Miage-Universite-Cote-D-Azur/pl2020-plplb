package com.example.plplbproject.Vue.menuPrincipal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

import java.util.ArrayList;

import metier.parcours.Parcours;


public class ParcoursPredefRecyclerAdapter extends RecyclerView.Adapter<ParcoursPredefViewHolder> {

    private Context context;
    private ArrayList<String> parcoursTypeName;

    public ParcoursPredefRecyclerAdapter(Context context, ArrayList<String> parcoursTypeName) {
        this.context = context;
        this.parcoursTypeName = parcoursTypeName;
    }

    @NonNull
    @Override
    public ParcoursPredefViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.parcours_predef_element, viewGroup, false);

        return new ParcoursPredefViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ParcoursPredefViewHolder holder, int position) {
        holder.parcoursPredefName.setText(parcoursTypeName.get(position));

        // TODO holder.setONClick...
    }

    @Override
    public int getItemCount() {
        return parcoursTypeName.size();
    }
}
