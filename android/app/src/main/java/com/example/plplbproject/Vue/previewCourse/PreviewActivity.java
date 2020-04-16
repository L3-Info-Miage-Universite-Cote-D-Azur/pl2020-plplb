package com.example.plplbproject.Vue.previewCourse;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
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
    private ImageButton shareButton;

    private final Gson gson = new GsonBuilder().create();

    private ClipboardManager clipboardManager;
    private ClipData clipData;

    TextView codeText;
    ImageButton sendMailButton;
    ImageButton sendGlobalButton;

    private String shareCode;

    public static final String AUTOINIT = "AUTOINIT";
    private boolean autoInit =  true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.apercu_activity);

        autoInit = getIntent().getBooleanExtra(AUTOINIT,true);
        saveButton = findViewById(R.id.saveApercu);
        shareButton = findViewById(R.id.shareButton);

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


    /**
     * Si on vient du créateur de nouveau parcours
     */
    protected void onCreateEndCourseBuilder(){
        course = (Parcours) getIntent().getSerializableExtra("Course");
        if(autoInit) initPreviewsCourse();
        saveButton.setOnClickListener(saveButtonListener);
        shareButton.setOnClickListener(shareButtonListener);
    }

    /**
     * Si on vient de l'apercu du menu principal
     */
    protected  void onCreatePreviews(){
        String courseName = getIntent().getStringExtra("CourseName");

        // Pour récupérer le parcours
        Connexion.CONNEXION.setEventListener(LOADCOURSE,receiveCourse());
        Connexion.CONNEXION.send(LOADCOURSE,courseName);
        saveButton.setVisibility(View.GONE);
        initPreviewsCourse();

        // Pour le bouton share
        shareButton.setOnClickListener(shareButtonListener);
    }

    /**
     * Qu'on vienne du menu principal ou du constructeur de parcours, on initialise la vue
     */
    protected void initPreviewsCourse(){
        apercuList = findViewById(R.id.apercuList);
        apercuAdapter = new PreviewRecyclerAdapter(this,course);
        apercuList.setAdapter(apercuAdapter);
        apercuList.setLayoutManager(new LinearLayoutManager(this));
        apercuAdapter.notifyDataSetChanged();
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
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        apercuAdapter.setCourse(course);
                        apercuAdapter.notifyDataSetChanged();
                    }
                });

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

    public Emitter.Listener receiveCode(){
        return new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                String code = gson.fromJson((String) args[0], String.class);
                shareCode = code;
                if(codeText != null){
                    codeText.setText(shareCode);
                }
            }
        };
    }

    private View.OnClickListener shareButtonListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);

            //Si le client n'a pas partager son code
            if(shareCode == null){
                //Si reclique sur le bouton, on lui redonnera donc son code déjà créer par le serveur
                Connexion.CONNEXION.setEventListener(ASKCODE,receiveCode());
                Connexion.CONNEXION.send(ASKCODE,gson.toJson(course.createSaveList()));
            }


            // inflate the layout of the popup window
            LayoutInflater inflater = (LayoutInflater)
                    getSystemService(LAYOUT_INFLATER_SERVICE);
            View popupView = inflater.inflate(R.layout.share_popup, null);

            // create the popup window
            int width = LinearLayout.LayoutParams.WRAP_CONTENT;
            int height = LinearLayout.LayoutParams.WRAP_CONTENT;
            boolean focusable = true; // lets taps outside the popup also dismiss it
            final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

            // show the popup window
            // which view you pass in doesn't matter, it is only used for the window tolken
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

            codeText = ((TextView)popupWindow.getContentView().findViewById(R.id.codeText));
            codeText.setText(shareCode);

            sendMailButton = popupWindow.getContentView().findViewById(R.id.sendMailButton);
            sendMailButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    String subject = "Partage du parcous \"" + course.getName() + " \"";

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    //i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"bonjourMonsieurRenevier@commentAllezVous.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, subject);
                    i.putExtra(Intent.EXTRA_TEXT   , shareCode);
                    try {
                        System.out.println("here1");
                        startActivity(Intent.createChooser(i, "Send code"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        System.out.println("here2");
                        Toast.makeText(getApplicationContext(), "Aucune application supportant des messages rfc822 detectée.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            sendGlobalButton = popupWindow.getContentView().findViewById(R.id.sendGlobalButton);
            sendGlobalButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_TEXT   , shareCode);
                    try {
                        startActivity(Intent.createChooser(i, "Send code"));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(getApplicationContext(), "Aucune application permettant de partager detectée.", Toast.LENGTH_SHORT).show();
                    }

                }
            });

            // dismiss the popup window when touched
            popupView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    clipData = ClipData.newPlainText("text",shareCode);
                    clipboardManager.setPrimaryClip(clipData);
                    Toast.makeText(getApplicationContext(),"Copié dans le presse papier", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });

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
