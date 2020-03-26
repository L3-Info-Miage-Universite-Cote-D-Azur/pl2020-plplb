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
                prepareModele(position);
                Intent intent = new Intent(menuPrinc, ApercuActivity.class);
                intent.putExtra("modele",modele);
                intent.putExtra("className","MenuPrinc");
                menuPrinc.startActivityForResult(intent,2);

            }
        });

        holder.modifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prepareModele(position);
                Intent intent = new Intent(menuPrinc, MainActivity.class);
                intent.putExtra("modele",modele);
                menuPrinc.startActivity(intent);
            }
        });
        //TODO Mettre les listeners sur les deux boutons
    }

    @Override
    public int getItemCount() {
        return parcoursNames.size();
    }


    // TODO modifier reseauControlleur

    public Emitter.Listener receiveParcours (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> ueCode = gson.fromJson((String) args[0], ArrayList.class);
                modele.setParcours(new Parcours(modele.getSemestres(),ueCode));
            }
        };
    }

    public Emitter.Listener receiveSemesters (){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> semestres = gson.fromJson((String) args[0], ArrayList.class);
                for (String s: semestres) {
                    modele.addSemestre(gson.fromJson(s, Semestre.class));
                }
                if(modele.getParcours()!= null) {
                    modele.getParcours().updateSemestre(modele.getSemestres());
                }
            }
        };
    }

    public void prepareModele(int position){

        this.modele = new MainModele();
        String parcoursChoisi = parcoursNames.get(position);

        Connexion.CONNEXION.send(SENDDATACONNEXION,"");
        Connexion.CONNEXION.setEventListener(SENDDATACONNEXION, receiveSemesters());

        Connexion.CONNEXION.setEventListener(SENDCLIENTSAVE, receiveParcours());
        Connexion.CONNEXION.send(SENDCOURSE,parcoursChoisi);
    }

}
