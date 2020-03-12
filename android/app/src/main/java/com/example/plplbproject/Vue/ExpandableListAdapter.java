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
import metier.UE;
import metier.parcours.Parcours;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private MainModele mainModele;
    private int semestreCourant;  // Pour lisibilité
    private ArrayList<Categorie> categorieArrayList = new ArrayList<Categorie>();


    /* SOUS CLASSE CONTROLLEUR */
    public class MyUEClickListener implements View.OnClickListener{

        private UE ue;
        private CheckBox checkBox;
        private Vue vue;
        private Parcours parcours;

        public MyUEClickListener(CheckBox checkBox, UE ue,Parcours parcours,Vue vue) {
            this.ue = ue;
            this.checkBox = checkBox;
            this.vue = vue;
            this.parcours = parcours;
        }

        @Override
        public void onClick(View view) {

            if(!parcours.isChecked(ue) && parcours.canBeCheckedUE(ue)){ //Change le check de l'ue en consequence.
                parcours.checkUE(ue);
                checkBox.setChecked(true);
            }
            else if(parcours.canBeUncheckedUE(ue)){
                parcours.uncheckUE(ue);
                checkBox.setChecked(false);
            }
            else{
                checkBox.setChecked(true);
            }
            //Un changement a eu lieu.
            //vue.needSave(true);
            vue.notifyUeListView();

        }
    }

    /**
     * L'adapteur doit savoir quel est le semestre qu'il doit adapter
     * @param context
     * @param mainModele
     */

    public ExpandableListAdapter(Context context, MainModele mainModele) {
        this.context = context;
        this.mainModele = mainModele;
        this.semestreCourant = mainModele.getSemestreCourant();
        if(mainModele.getSemestres().size() > 0){
            this.categorieArrayList = mainModele.getSemestres().get(semestreCourant).getListCategorie(); // listDataHeader
        }
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
        View ueRect = (View) convertView.findViewById(R.id.rectangleUe);

        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());


        if(mainModele.getParcours().isChecked(ue)) ueRect.setBackgroundColor(0xff51C5C4);//bleu
        else if(!mainModele.getParcours().canBeCheckedUE(ue)) ueRect.setBackgroundColor(0xffD54F34);//rouge
        else ueRect.setBackgroundColor(0xff62C65B);//vert



        checkBox.setOnClickListener(new MyUEClickListener(checkBox,ue,mainModele.getParcours(),(Vue) context));


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
        this.semestreCourant = mainModele.getSemestreCourant();
        categorieArrayList= mainModele.getSemestres().get(semestreCourant).getListCategorie();
        super.notifyDataSetChanged();
    }
}
