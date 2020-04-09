package com.example.plplbproject.Vue.mainMenu;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.plplbproject.Vue.previewCourse.PreviewActivity;
import com.example.plplbproject.data.DataSemester;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

import io.socket.client.Socket;
import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.plplbproject.Vue.previewCourse.PreviewActivity.AUTOINIT;
import static constantes.NET.COURSESNAMES;
import static constantes.NET.SEMSTERDATA;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MainMenuActivityTest {

    @Rule
    public ActivityTestRule<MainMenuActivity> mActivityRule = new ActivityTestRule<>(MainMenuActivity.class, true, false);

    ArrayList<String> listParcour;
    private final Gson gson = new GsonBuilder().create();

    @Mock
    Socket socket;

    @Before
    public void init(){
        socket = Mockito.mock(Socket.class);
        Connexion.CONNEXION.setSocket(socket);

        listParcour = new ArrayList<String>();
        listParcour.add("aaa");
        listParcour.add("bbb");
        listParcour.add("ccc");
        DataSemester.SEMESTER.setSemesterList(null);

        Intent startIntent = new Intent();
        mActivityRule.launchActivity(startIntent);

    }


    @Test
    public void testInit(){
        //on demande bien au serveur la liste des nom de cours
        verify(socket,times(1)).emit(eq(COURSESNAMES),any());

        //on demande au serveur si on a bien demander la liste des semestre
        verify(socket,times(1)).emit(eq(SEMSTERDATA),any());

        //on a bien tout les bouton
        onView(withText("NOUVEAU PARCOURS")).check(matches(isDisplayed()));
        onView(withText("DECONNEXION")).check(matches(isDisplayed()));

    }


    @Test
    public void testAffichageParcours(){
        // on recois la liste qui est demander par le client
        mActivityRule.getActivity().receiveParcoursName().call(gson.toJson(listParcour));

        onView(withText("aaa")).check(matches(isDisplayed()));
        onView(withText("bbb")).check(matches(isDisplayed()));
        onView(withText("ccc")).check(matches(isDisplayed()));
    }


    @Test
    public void testQuitter(){

        //on a bien l'activiter active
        assertFalse(mActivityRule.getActivity().isDestroyed());

        //on essaye de quiter:
        onView(withText("DECONNEXION")).check(matches(isDisplayed())).perform(click());

        try {
            Thread.sleep(2000); //le temps de finir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //l'activiter c'est bien finit
        assertTrue(mActivityRule.getActivity().isDestroyed());
    }

    @Test
    public void pressBackTest(){

        //on a bien l'activiter active
        assertFalse(mActivityRule.getActivity().isDestroyed());

        //on essaye de quiter:
        Espresso.pressBackUnconditionally();

        try {
            Thread.sleep(2000); //le temps de finir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //l'activiter c'est bien finit
        assertTrue(mActivityRule.getActivity().isDestroyed());
    }
}
