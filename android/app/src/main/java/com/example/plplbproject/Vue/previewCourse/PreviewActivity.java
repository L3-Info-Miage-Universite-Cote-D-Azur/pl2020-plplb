package com.example.plplbproject.Vue.previewCourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import static constantes.NET.*;


import com.example.plplbproject.R;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.Parcours;

public class PreviewActivity extends AppCompatActivity {


    private long backPressedTime = 0;    // used by onBackPressed()

    private RecyclerView apercuList;
    private PreviewRecyclerAdapter apercuAdapter;
    private Parcours course;

    private Button saveButton;

    private final Gson gson = new GsonBuilder().create();

    public static final String AUTOINIT = "AUTOINIT";
    private boolean autoInit =  true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu_activity);

        autoInit = getIntent().getBooleanExtra(AUTOINIT,true);
        saveButton = findViewById(R.id.saveApercu);

        String classCall = getIntent().getStringExtra("className");
        if(classCall == null)
            classCall ="";
        if (classCall.equals("MainMenuActivity") ){
            onCreatePreviews();
        }
        else if(classCall.equals("CourseBuilderActivity")){
            onCreateEndCourseBuilder();
        }
        else {
            finish(); //aucune autre classe ne doit mener a cette classe
        }

    }

    protected void onCreateEndCourseBuilder(){
        course = (Parcours) getIntent().getSerializableExtra("Course");
        if(autoInit) initPreviewsCourse();
        saveButton.setOnClickListener(saveButtonListener);
    }

    protected  void onCreatePreviews(){
        String courseName = getIntent().getStringExtra("CourseName");
        Connexion.CONNEXION.setEventListener(LOADCOURSE,receiveCourse());
        Connexion.CONNEXION.send(LOADCOURSE,courseName);
        saveButton.setVisibility(View.GONE);
    }

    protected void initPreviewsCourse(){
        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new PreviewRecyclerAdapter(this,course);
        apercuList.setAdapter(apercuAdapter);
        apercuList.setLayoutManager(new LinearLayoutManager(this));
    }


    /**
     * Controller qui permet de gerer la reception du parcours
     * @return le controller
     */
    public Emitter.Listener receiveCourse(){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                ArrayList<String> ueCode = gson.fromJson((String) args[0], ArrayList.class);
                course = new Parcours(DataSemester.SEMESTER.getSemesterList(),ueCode, DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList());
                initPreviewsCourse();
            }
        };
    }




    private View.OnClickListener saveButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Connexion.CONNEXION.send(SENDCLIENTSAVE,gson.toJson(course.createSaveList()));
            Toast toast = Toast.makeText(getApplicationContext(), "Parcours sauvegardé", Toast.LENGTH_SHORT);
            toast.show();

            Intent intent=new Intent();
            setResult(2,intent);
            finish();
        }
    };

    @Override
    public void onBackPressed(){
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Appuyer une nouvelle fois pour quitter",
                    Toast.LENGTH_SHORT).show();
        } else {
            //super.onBackPressed();
            Intent intent=new Intent();
            setResult(1,intent);
            finish();
        }
    }

    //SETTER for test
    protected void setCourse(Parcours course){
        this.course = course;
    }


}
