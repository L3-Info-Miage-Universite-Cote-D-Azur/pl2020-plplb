package com.example.plplbproject.Vue.previewCourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import android.widget.Button;
import android.widget.TextView;

import com.example.plplbproject.R;
import com.example.plplbproject.controleur.UeVisualisation.UeVisualisationClickListener;


import java.util.ArrayList;

import metier.parcours.Parcours;


import metier.UE;

/**
 * Liste des ue selectionner a l'interrieur d'un semestre
 */
public class PreviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<UE> ues;

    public PreviewAdapter(Context context, Parcours course, int semestreCourant) {
        this.context = context;
        this.ues = new ArrayList<>();
        if(course!=null){
            for (UE ue: course.getParcoursSelect().values()
                ) {
                if(ue.getSemestreNumber() == semestreCourant){
                    ues.add(ue);
                }
            }}
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
        Button description = (Button) convertView.findViewById(R.id.descriptionButton);

        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());
        ueRect.setBackgroundColor(0xff51C5C4);
        description.setOnClickListener(new UeVisualisationClickListener(context,ue));

        return convertView;
    }
}
