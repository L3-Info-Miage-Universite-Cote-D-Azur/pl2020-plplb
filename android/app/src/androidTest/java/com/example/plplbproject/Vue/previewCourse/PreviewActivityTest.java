package com.example.plplbproject.Vue.previewCourse;

import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;


import com.example.plplbproject.R;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import io.socket.client.Socket;
import java.util.HashMap;


import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;


import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;

import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.plplbproject.Vue.previewCourse.PreviewActivity.AUTOINIT;

import static constantes.NET.SENDCLIENTSAVE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.never;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static androidx.test.espresso.Espresso.onView;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class PreviewActivityTest {
    @Rule
    public ActivityTestRule<PreviewActivity> mActivityRule = new ActivityTestRule<>(PreviewActivity.class, true, false);

    @Mock
    Parcours course;

    @Before
    public void init(){
        SemesterList semesters = new SemesterList();
        semesters.add(new Semestre());
        semesters.add(new Semestre());
        semesters.add(new Semestre());
        semesters.add(new Semestre());
        DataSemester.SEMESTER.setSemesterList(semesters);
        HashMap<String, UE> ueSelescted = new HashMap<String, UE>();
        UE ue;
        //on initialise la liste des ue selectionner du parcours
        for(int i=1;i<5;i++){
            for(int j=0;j<3;j++){
                ue = new UE("ue"+i+j,"code"+i+j);
                ue.setCategorie("none");
                ue.setSemestreNumber(i);
                ueSelescted.put(ue.getUeCode(),ue);
            }
        }

        course = Mockito.mock(Parcours.class);
        when(course.getParcoursSelect()).thenReturn(ueSelescted);

        Intent startIntent = new Intent();
        startIntent.putExtra(AUTOINIT,false);
        startIntent.putExtra("className","CourseBuilderActivity");
        mActivityRule.launchActivity(startIntent);
        mActivityRule.getActivity().setCourse(course);

        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityRule.getActivity().initPreviewsCourse();
            }
        });

    }

    @Test
    public void graphiqueTest(){
        //on verifie que tout les ue sont bien afficher
        for(int i=1;i<5;i++) {
            for (int j = 0; j < 3; j++) {

                onView(withText("ue" + i + j)).check(matches(isDisplayed()));
            }
            onView(withId(R.id.apercuList))
                    .perform(RecyclerViewActions.scrollToPosition(i));

        }
        //on verifie que le boutton save est bien visible
        onView(withId(R.id.saveApercu)).check(matches(isDisplayed()));
    }


    @Test
    public void saveButton(){
        Socket socket = Mockito.mock(Socket.class);
        Connexion.CONNEXION.setSocket(socket);

        //l'activity existe
        assertFalse(mActivityRule.getActivity().isDestroyed());

        //on a rien envoyer avant
        verify(socket,never()).emit(eq(SENDCLIENTSAVE),any());

        //on appuis sur le boutton save
        onView(withId(R.id.saveApercu)).perform(click());

        //on envoie la sauvegarde
        verify(socket,atLeast(1)).emit(eq(SENDCLIENTSAVE),any());

        try {
            Thread.sleep(2000); //le temps de finir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //l'activity esr fermer
        assertTrue(mActivityRule.getActivity().isDestroyed());
    }




}
