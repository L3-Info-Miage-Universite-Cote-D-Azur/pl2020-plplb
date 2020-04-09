package com.example.plplbproject.Vue.previewCourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.data.DataSemester;

import metier.parcours.Parcours;

/**
 * Liste de la liste des semestre contenant des ue
 */
public class PreviewRecyclerAdapter extends RecyclerView.Adapter<SemesterViewHolder>{

    private Context context;
    private Parcours course;

    public PreviewRecyclerAdapter(Context context, Parcours course) {
        this.context = context;
        this.course = course;
    }

    @NonNull
    @Override
    public SemesterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.semestre_element, viewGroup, false);

        return new SemesterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SemesterViewHolder holder, int position) {
        holder.semesterName.setText("Semestre " + (position + 1));//+1 car debute a 0 pour le semestre 1

        holder.listView.setAdapter(new PreviewAdapter(context,course,(position + 1)));

    }

    /**
     * nombre de semestre dans la liste
     * @return le nombre de semestre
     */
    @Override
    public int getItemCount() {
        return DataSemester.SEMESTER.getNumberSemesters();
    }

    public void setCourse(Parcours course) {
        this.course = course;
    }
}
