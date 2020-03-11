package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.UeClickListener;

import java.util.ArrayList;
import java.util.HashMap;

import metier.Categorie;
import metier.MainModele;
import metier.Parcours;
import metier.Semestre;
import metier.UE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private MainModele mainModele;
    private int semestreCourant;  // Pour lisibilité
    private ArrayList<Categorie> categorieArrayList;

    /**
     * L'adapteur doit savoir quel est le semestre qu'il doit adapter
     * @param context
     * @param mainModele
     */

    public ExpandableListAdapter(Context context, MainModele mainModele) {
        this.context = context;
        this.mainModele = mainModele;
        this.semestreCourant = mainModele.getSemestreCourant();
        this.categorieArrayList = mainModele.getSemestres().get(semestreCourant).getListCategorie(); // listDataHeader
    }

    @Override
    public int getGroupCount() {
        return categorieArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return categorieArrayList.get(groupPosition).getListUE().size();
    }

    @Override
    public Object getGroup(int i) {
        return categorieArrayList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return categorieArrayList.get(i).getListUE().get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    /**
     * Adapter du parent
     * @param groupPosition
     * @param isExpanded
     * @param convertView
     * @param parent
     * @return
     */

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Categorie c = (Categorie) getGroup(groupPosition);
        String headerTitle = c.getName();

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.category_element, null);
        }

        TextView categoryName = convertView.findViewById(R.id.catName);
        categoryName.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        UE ue = (UE) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.ue_element_main, null);
        }

        TextView ueName = (TextView) convertView.findViewById(R.id.ueName);
        TextView ueCode = (TextView) convertView.findViewById(R.id.codeUe);
        CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.checkboxUe);

        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());

        checkBox.setOnClickListener(new UeClickListener(checkBox,ue,mainModele.getParcours()));


        //Mise a jour de la case coche ou non.
        //if(parcours.isChecked(ue)){
        //    checkBox.setChecked(true);
        //}

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return true;
    }

    @Override
    public void notifyDataSetChanged(){
        mainModele.getSemestres().get(semestreCourant).getListCategorie();
        super.notifyDataSetChanged();
    }
}
