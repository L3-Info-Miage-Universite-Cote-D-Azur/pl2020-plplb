package com.example.plplbproject.Vue.mainMenu;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

/**
 * ViewHolder d'un parcours deja existant du menu principale
 */
public class ClientCourseViewHolder extends RecyclerView.ViewHolder {

    protected TextView parcoursName;
    protected Button modifButton;
    protected Button visualiserButton;

    public ClientCourseViewHolder(@NonNull View itemView) {
        super(itemView);
        parcoursName = itemView.findViewById(R.id.parcoursName);
        modifButton = itemView.findViewById(R.id.modifier);
        visualiserButton = itemView.findViewById(R.id.visualiser);
        }
}
