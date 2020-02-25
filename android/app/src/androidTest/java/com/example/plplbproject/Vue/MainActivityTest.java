package com.example.plplbproject.Vue;

import android.content.Context;
import android.content.Intent;

import android.widget.ListView;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.plplbproject.R;

import com.example.plplbproject.controleur.ReseauController;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.util.ArrayList;

import metier.Semestre;
import metier.UE;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static constantes.NET.CONNEXION;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;


/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    private final Gson gson = new GsonBuilder().create();

    ReseauController reseauController;
    @Mock
    Connexion connexion;

    @Before
    public void initConnexion() {


        connexion = Mockito.mock(Connexion.class);
        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(MainActivity.AUTOCONNECT, false);
        mActivityRule.launchActivity(startIntent);

        reseauController = new ReseauController(mActivityRule.getActivity(),connexion,mActivityRule.getActivity().getModele());
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //le serveur renvoie une ue
                ArrayList<UE> array = new ArrayList<UE>();
                array.add(new UE("UE1","codeue1"));
                Semestre semesetre = new Semestre(1,array);

                //le controlleur gere cette UE
                reseauController.dataConnexion().call(gson.toJson(semesetre));
                //de plus le serveur envoie un message helloClient qui na pas de test pour le moment

                return null;
            }
        }).when(connexion).send(any(String.class), any(String.class));


        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityRule.getActivity().setConnexion(connexion);
                mActivityRule.getActivity().initVue();
            }
        });



    }

    @Test
    public void loadUETest(){
        //avant la connexion on a une liste view de 0 UE
        assertEquals(0, mActivityRule.getActivity().getModele().getAllUE().size());
        //quand le client ce connecte au serveur, le serveur recois la liste des UE dans ce cas 1 UE
        connexion.send(CONNEXION,"user");

        assertEquals(1, mActivityRule.getActivity().getModele().getAllUE().size());
        //TODO test affichage

    }

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();

        assertEquals("com.example.plplbproject", appContext.getPackageName());
    }

}
