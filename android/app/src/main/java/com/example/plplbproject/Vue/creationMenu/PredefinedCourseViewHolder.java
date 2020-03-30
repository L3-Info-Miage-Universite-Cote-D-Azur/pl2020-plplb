package com.example.plplbproject.Vue.creationMenu;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;


public class PredefinedCourseViewHolder extends RecyclerView.ViewHolder {

    protected TextView predefinedCourseName;


    public PredefinedCourseViewHolder(@NonNull View itemView) {
        super(itemView);
        predefinedCourseName = itemView.findViewById(R.id.parcoursPredefName);
    }

    public void setSelected(){
        itemView.setBackgroundColor(0xff51C5C4);
    }

    public void setUnselected(){
        itemView.setBackgroundColor(0xffffffff);
    }
}
