package com.example.plplbproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import java.util.List;

public class UeDisplayAdapter extends ArrayAdapter<Ue> {

    private TextView ueName;
    private TextView ueCode;
    private CheckBox checkBox;


    public UeDisplayAdapter(@NonNull Context context,@NonNull List<Ue> objects) {
        //ressource
        super(context, 0, objects);
    }

    public class MyUEClickListener implements View.OnClickListener{

        private Ue ue;
        private CheckBox checkBox;

        public MyUEClickListener(CheckBox checkBox, Ue ue) {
            this.ue = ue;
            this.checkBox = checkBox;
        }

        @Override
        public void onClick(View view) {
            if(checkBox.isChecked()){
                ue.setChecked(true);
            }
            else{
                ue.setChecked(false);
            }
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // get data from list view
        Ue ue = getItem(position);
        // The old view to reuse, if possible. Note: You should check that this view is non-null and of an appropriate type before using.
        if (convertView == null){
            // Layoutinflater: Instantiates a layout XML file into its corresponding View objects.
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.ue_element_main,parent,false);
        }

        ueName = (TextView) convertView.findViewById(R.id.ueName);
        ueCode = (TextView) convertView.findViewById(R.id.codeUe);
        checkBox = (CheckBox) convertView.findViewById(R.id.checkboxUe);

        ueName.setText(ue.getUeName());
        ueCode.setText(ue.getUeCode());
        // get back the previous checked Ue of the user
        if(ue.getChecked()){
            checkBox.setChecked(true);
        }

        //########################## Add checkbox Listener ###############################
        checkBox.setOnClickListener(new MyUEClickListener(checkBox,ue));

        return convertView;
    }
}
