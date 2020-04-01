package com.example.plplbproject.Vue.previewCourse;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;

/**
 * ViewHolder representant l'affichage du nom de smeestre
 */
public class SemesterViewHolder extends RecyclerView.ViewHolder {

    protected TextView semesterName;
    protected View divider;
    protected ListViewApercu listView;

    public SemesterViewHolder(@NonNull View itemView) {
        super(itemView);
        semesterName = itemView.findViewById(R.id.semName);
        divider = itemView.findViewById(R.id.divider1);
        listView = itemView.findViewById(R.id.apercuListView);
    }
}
