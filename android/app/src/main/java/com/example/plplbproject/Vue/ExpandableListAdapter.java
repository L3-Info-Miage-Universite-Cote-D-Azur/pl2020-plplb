package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.plplbproject.R;

import java.util.ArrayList;
import java.util.HashMap;

import metier.Categorie;
import metier.UE;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<Categorie> categorieArrayList;
    private HashMap<Categorie , ArrayList<UE>> ueMap;

    public ExpandableListAdapter(Context context, ArrayList<Categorie> catList) {
        this.context = context;
        this.categorieArrayList = catList; // listDataHeader
        this.ueMap = new HashMap(); // listChildData

        for (Categorie c: categorieArrayList
             ) {
            ArrayList<UE> uelist = new ArrayList<>();
            for (UE ue: c.getListUE()
                 ) {
                uelist.add(ue);
            }
            ueMap.put(c,uelist);
        }

    }

    @Override
    public int getGroupCount() {
        return categorieArrayList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ueMap.get(this.categorieArrayList.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int i) {
        return categorieArrayList.get(i);
    }

    @Override
    public Object getChild(int i, int i1) {
        return ueMap.get(this.categorieArrayList.get(i)).get(i1);
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
}
