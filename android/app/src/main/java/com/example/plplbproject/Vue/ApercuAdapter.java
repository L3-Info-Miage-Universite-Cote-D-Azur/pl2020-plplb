package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.plplbproject.R;

import java.util.ArrayList;

import metier.Categorie;
import metier.MainModele;
import metier.UE;

public class ApercuAdapter extends BaseAdapter {

    private Context context;
    private MainModele modele;
    private ArrayList<UE> ues;

    public ApercuAdapter(Context context, MainModele modele, int semestreCourant) {
        this.context = context;
        this.modele = modele;
        this.ues = new ArrayList<>();
        System.out.println(modele == null);
        for (Categorie cat: this.modele.getSemestre(semestreCourant -1).getListCategorie()
             ) {
            for (UE ue: cat.getListUE()
                 ) {
                ues.add(ue);
            }
        }
    }

    @Override
    public int getCount() {
        return ues.size();
    }

    @Override
    public UE getItem(int i) {
        return ues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {

        UE ue = getItem(i);

        if (convertView == null) {
            convertView = LayoutInflater.from(context).
                    inflate(R.layout.ue_element_main, parent, false);
        }

        TextView ueName = (TextView) convertView.findViewById(R.id.ueName);
        TextView ueCode = (TextView) convertView.findViewById(R.id.codeUe);
        View ueRect = (View) convertView.findViewById(R.id.rectangleUe);

        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());
        ueRect.setBackgroundColor(0xff51C5C4);

        return convertView;
    }
}
