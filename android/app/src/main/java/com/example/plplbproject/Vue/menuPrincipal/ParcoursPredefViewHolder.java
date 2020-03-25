package com.example.plplbproject.Vue.menuPrincipal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

public class ParcoursPredefViewHolder extends RecyclerView.ViewHolder {

    protected TextView parcoursPredefName;

    public ParcoursPredefViewHolder(@NonNull View itemView) {
        super(itemView);
        parcoursPredefName = itemView.findViewById(R.id.parcoursPredefName);
    }
}
