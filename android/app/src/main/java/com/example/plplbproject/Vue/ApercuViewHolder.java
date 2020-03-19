package com.example.plplbproject.Vue;

import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

public class ApercuViewHolder extends RecyclerView.ViewHolder {

    protected TextView semesterName;
    protected View divider;
    protected ListViewApercu listView;

    public ApercuViewHolder(@NonNull View itemView) {
        super(itemView);
        semesterName = itemView.findViewById(R.id.semName);
        divider = itemView.findViewById(R.id.divider1);
        listView = itemView.findViewById(R.id.apercuListView);
    }
}
