package com.example.plplbproject.Vue.menuPrincipal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

public class ParcoursViewHolder extends RecyclerView.ViewHolder {

    protected TextView parcoursName;

    public ParcoursViewHolder(@NonNull View itemView) {
        super(itemView);
        parcoursName = itemView.findViewById(R.id.parcoursName);
        }
}
