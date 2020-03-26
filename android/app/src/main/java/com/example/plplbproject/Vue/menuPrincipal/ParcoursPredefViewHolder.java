package com.example.plplbproject.Vue.menuPrincipal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

import metier.MainModele;
import metier.MenuInterModele;

public class ParcoursPredefViewHolder extends RecyclerView.ViewHolder {

    protected TextView parcoursPredefName;


    public ParcoursPredefViewHolder(@NonNull View itemView) {
        super(itemView);
        parcoursPredefName = itemView.findViewById(R.id.parcoursPredefName);
    }

    public void setSelected(){
        itemView.setBackgroundColor(0xff51C5C4);
    }

    public void setUnselected(){
        itemView.setBackgroundColor(0xffffffff);
    }

    public void setSelected(boolean select){
        if(select) this.setSelected();
        else this.setUnselected();
    }


}
