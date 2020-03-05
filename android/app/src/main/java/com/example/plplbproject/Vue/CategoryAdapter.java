package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plplbproject.R;
import com.example.plplbproject.model.MainModele;

import java.util.List;

import metier.Categorie;
import metier.UE;
import metier.Parcours;

public class CategoryAdapter extends ArrayAdapter<Categorie> {

    private Context context;
    private TextView categoryName;
    private ListView ueList;
    private Parcours parcours;

    public CategoryAdapter(@NonNull Context context, @NonNull List<Categorie> objects, Parcours parcours){
        super(context, 0, objects);
        this.parcours = parcours;
        this.context = context;
        this.parcours = parcours;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Recupere les elements de la listeView.
        Categorie categorie = getItem(position);
        // The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using.
        if (convertView == null) {
            // Layoutinflater: Instantiates a layout XML file into its corresponding View objects.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.category_element, parent, false);
        }

        categoryName = convertView.findViewById(R.id.catName);
        ueList = convertView.findViewById(R.id.ueList);

        categoryName.setText(categorie.getName());

        //###################### Adapt the list of Ue to display #####################
        UeDisplayAdapter ueListViewAdaptateur = new UeDisplayAdapter(getContext(), categorie.getListUE(), parcours);
        ueList.setAdapter(ueListViewAdaptateur);


        // TODO findView.id renvoie toujours null (pour le bouton et textView, divider etc..): pourquoi??
        Button extendButton = convertView.findViewById(R.id.extendIcon);
        extendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //ueList.setVisibility(View.INVISIBLE);
            }
        });

        return convertView;

    }

}
