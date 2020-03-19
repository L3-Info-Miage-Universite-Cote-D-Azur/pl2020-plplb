package com.example.plplbproject.Vue.apercusParcour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ExpandableListView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.semestreBuilder.ExpandableCategoryAdapter;

import java.util.ArrayList;

import metier.Categorie;
import metier.MainModele;
import metier.semestre.Semestre;


public class ApercuAdapter extends BaseAdapter {

    private Context context;
    private MainModele modele;
    private ArrayList<Semestre> semestres;

    public ApercuAdapter(Context context, MainModele modele) {
        this.context = context;
        this.modele = modele;
        this.semestres = modele.getSemestres();
    }

    @Override
    public int getCount() {
        return semestres.size();
    }

    @Override
    public ArrayList<Categorie> getItem(int i) {
        return semestres.get(i).getListCategorie();
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ue_element_main,parent,false);
        }

        ArrayList<Categorie> categories = getItem(position);

        ExpandableListView expandableListView = convertView.findViewById(R.id.catExpList);
        ExpandableCategoryAdapter expandableCategoryAdapter = new ExpandableCategoryAdapter(context,categories);
        expandableListView.setAdapter(expandableCategoryAdapter);

        // Pour qu'elles soient toutes déroulées de base
        for(int i=0; i < expandableCategoryAdapter.getGroupCount(); i++)
            expandableListView.expandGroup(i);

        return convertView;
    }
}
