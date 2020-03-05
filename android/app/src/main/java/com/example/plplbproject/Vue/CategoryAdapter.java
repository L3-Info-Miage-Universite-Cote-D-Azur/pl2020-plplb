package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.plplbproject.R;

import java.util.List;

import metier.Categorie;

public class CategoryAdapter extends ArrayAdapter<Categorie> {

    private Context context;

    private TextView categoryName;
    private ListView ueList;

    public CategoryAdapter(@NonNull Context context, @NonNull List<Categorie> objects){
        super(context, 0, objects);
        this.context = context;
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
        ueList = convertView.findViewById(R.id.catList);

        categoryName.setText(categorie.getName());

        //###################### Adapt the list of Ue to display #####################
        UeDisplayAdapter ueListViewAdaptateur = new UeDisplayAdapter(getContext(), categorie.getListUE());
        ueList.setAdapter(ueListViewAdaptateur);
        return convertView;

    }

}
