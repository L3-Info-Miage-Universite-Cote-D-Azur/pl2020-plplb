package com.example.plplbproject.Vue.menuPrincipal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.apercusParcour.ApercuActivity;
import com.example.plplbproject.Vue.semestreBuilder.MainActivity;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.MainModele;
import metier.parcours.Parcours;
import metier.semestre.Semestre;

import static constantes.NET.SENDCLIENTSAVE;
import static constantes.NET.SENDCOURSE;
import static constantes.NET.SENDDATACONNEXION;

public class ParcoursRecyclerAdapter extends RecyclerView.Adapter<ParcoursViewHolder> {

    private Context context;
    private ArrayList<String> parcoursNames;
    private MainModele modele;
    private MenuPrinc menuPrinc;
    private final Gson gson = new GsonBuilder().create();


    public ParcoursRecyclerAdapter(Context context, ArrayList<String> parcoursNames,MenuPrinc menuPrinc) {
        this.context = context;
        this.parcoursNames = parcoursNames;
        this.modele = null;
        this.menuPrinc = menuPrinc;
    }

    /**
     * permet de set la liste des nom de parcours
     * @param parcoursNames la liste a set
     */
    public void setParcoursNames(ArrayList<String> parcoursNames){
        this.parcoursNames = parcoursNames;
    }

    @NonNull
    @Override
    public ParcoursViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view;
        view = LayoutInflater.from(context).inflate(R.layout.parcours_element, viewGroup, false);

        return new ParcoursViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ParcoursViewHolder holder, final int position) {
        holder.parcoursName.setText(parcoursNames.get(position));

        holder.visualiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menuPrinc, ApercuActivity.class);
                intent.putExtra("className","MenuPrinc");
                intent.putExtra("parcourName",parcoursNames.get(position));
                menuPrinc.startActivityForResult(intent,2);

            }
        });

        holder.modifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(menuPrinc, MainActivity.class);
                intent.putExtra("className","MenuPrinc");
                intent.putExtra("parcourName",parcoursNames.get(position));
                menuPrinc.startActivity(intent);
            }
        });
        //TODO Mettre les listeners sur les deux boutons
    }

    @Override
    public int getItemCount() {
        return parcoursNames.size();
    }


}
