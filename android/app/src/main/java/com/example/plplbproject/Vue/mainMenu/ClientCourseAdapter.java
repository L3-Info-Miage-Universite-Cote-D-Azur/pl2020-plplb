package com.example.plplbproject.Vue.mainMenu;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.plplbproject.R;
import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.Vue.previewCourse.PreviewActivity;


import java.util.ArrayList;

/**
 * L'adapteur pour la liste des parcours du client
 */
public class ClientCourseAdapter extends RecyclerView.Adapter<ClientCourseViewHolder> {

    private ArrayList<String> coursesNames;//La liste de nom des parcours
    private MainMenuActivity mainMenuActivity;

    public ClientCourseAdapter(ArrayList<String> coursesNames, MainMenuActivity mainMenuActivity) {
        this.coursesNames = coursesNames;
        this.mainMenuActivity = mainMenuActivity;
    }

    /**
     * permet de set la liste des nom de parcours
     * @param parcoursNames la liste a set
     */
    public void setParcoursNames(ArrayList<String> parcoursNames){
        this.coursesNames = parcoursNames;
    }

    @NonNull
    @Override
    public ClientCourseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view;
        view = LayoutInflater.from(mainMenuActivity.getApplicationContext()).inflate(R.layout.parcours_element, viewGroup, false);
        return new ClientCourseViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ClientCourseViewHolder holder, final int position) {
        holder.parcoursName.setText(coursesNames.get(position));

        holder.visualiserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainMenuActivity, PreviewActivity.class);
                intent.putExtra("className","MainMenuActivity");
                intent.putExtra("CourseName",coursesNames.get(position));
                mainMenuActivity.startActivityForResult(intent,2);

            }
        });

        holder.modifButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mainMenuActivity, CourseBuilderActivity.class);
                intent.putExtra("className","MainMenuActivity");
                intent.putExtra("CourseName",coursesNames.get(position));
                mainMenuActivity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return coursesNames.size();
    }
}
