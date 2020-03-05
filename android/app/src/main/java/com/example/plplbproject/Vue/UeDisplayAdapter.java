package com.example.plplbproject.Vue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.plplbproject.R;

import java.util.List;

import metier.Parcours;
import metier.UE;

public class UeDisplayAdapter extends ArrayAdapter<UE> {

    /* FIELDS */
    private TextView ueName;
    private TextView ueCode;
    private CheckBox checkBox;
    private Parcours parcours;//Le parcours courant.
    private Vue vue;

    /* CONSTRUCTOR */
    public UeDisplayAdapter(@NonNull Context context,@NonNull List<UE> objects, @NonNull Parcours parcours) {
        //ressource
        super(context, 0, objects);
        this.vue = (Vue)context;
        this.parcours = parcours;
    }

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
                parcours.addUEParcours(ue);
                checkBox.setChecked(true);
            }
            else{
                parcours.delUEParcours(ue);
                checkBox.setChecked(false);
            }
            //Un changement a eu lieu.
            vue.needSave(true);

        }
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //Recupere les elements de la listeView.
        UE ue = getItem(position);
        // The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using.
        if (convertView == null){
            // Layoutinflater: Instantiates a layout XML file into its corresponding View objects.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ue_element_main,parent,false);
        }

        ueName = (TextView) convertView.findViewById(R.id.ueName);
        ueCode = (TextView) convertView.findViewById(R.id.codeUe);
        checkBox = (CheckBox) convertView.findViewById(R.id.checkboxUe);

        //Mise a jour du texte
        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());

        //Mise a jour de la case coche ou non.
        if(parcours.isChecked(ue)){
            checkBox.setChecked(true);
        }

        //########################## Add checkbox Listener ###############################
        checkBox.setOnClickListener(new MyUEClickListener(checkBox,ue,parcours,vue));

        return convertView;
    }
}
