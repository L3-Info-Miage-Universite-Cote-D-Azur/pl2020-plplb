package com.example.plplbproject.Vue;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.example.plplbproject.R;

import com.example.plplbproject.Vue.semestreBuilder.MainActivity;
import com.example.plplbproject.controleur.courseBuilder.ReseauController;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;


import java.util.ArrayList;

import metier.Categorie;
import metier.MainModele;
import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.Semestre;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static constantes.NET.CONNEXION;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

/*
    TODO:refaire en adaptant a  la nouvelle connexion
 */
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

    @Mock
    ReseauController reseauController;

    @Mock
    Parcours parcours;

    @Mock
    Connexion connexion;

    Semestre semestre1;
    Semestre semestre2;

    @Spy
    MainModele modele;


    public static Matcher<Object> withChildName(String name) {
        checkNotNull(name);
        return withChildName(equalTo(name));
    }

    public static Matcher<Object> withChildName(final Matcher<String> name) {
        checkNotNull(name);
        return new BoundedMatcher<Object, UE>(UE.class){

            @Override
            public void describeTo(org.hamcrest.Description description) {
                name.describeTo(description);

            }

            @Override
            public boolean matchesSafely (final UE childStruct){
                return name.matches(childStruct.getUeName());
            }
        } ;
    }

    @Before
    public void initConnexion() {


        connexion = Mockito.mock(Connexion.class);
        modele = Mockito.spy(new MainModele());
        parcours = Mockito.mock(Parcours.class);

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();
        startIntent.putExtra(MainActivity.AUTOCONNECT, false);
        mActivityRule.launchActivity(startIntent);
        mActivityRule.getActivity().setModele(modele);
        modele.setParcours(parcours);

        reseauController = new ReseauController(mActivityRule.getActivity(),connexion,modele);

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                //le serveur renvoie deux semestres avec deux matières chacuns:

                ArrayList<Categorie> listCategorie = new ArrayList<Categorie>();

                //GEOGRAPHIE
                Categorie geo = new Categorie("GEOGRAPHIE");
                geo.addUe(new UE("Decouverte 1","SPUGDE10"));
                geo.addUe(new UE("Decouverte 2","SPUGDC10"));
                geo.addUe(new UE("Disciplinaire 1","SPUGDI10"));
                listCategorie.add(geo);

                //INFORMATIQUE
                Categorie info = new Categorie("INFORMATIQUE");
                info.addUe(new UE("Bases de l'informatique","SPUF10"));
                info.addUe(new UE("Introduction a l'informatique par le web","SPUF11"));
                listCategorie.add(info);

                Semestre semestre1 = new Semestre(1,listCategorie,null);

                // ------------------- Semestre 2 -----------------------------

                ArrayList<Categorie> listCategorie2 = new ArrayList<Categorie>();

                //MATHEMATIQUES (pas toutes dispo selon le choix en math obligatoire)
                Categorie math = new Categorie("MATHEMATIQUES");
                math.addUe(new UE("Fondements 2","SPUM21"));
                math.addUe(new UE("Complements 2","SPUM23"));
                math.addUe(new UE("Methodes Maths-Approche discrete","SPUM22"));
                listCategorie2.add(math);

                //SCIENCES DE LA VIE
                Categorie sv = new Categorie("SCIENCES DE LA VIE");
                sv.addUe(new UE("Diversite du vivant","SPUV201"));
                sv.addUe(new UE("Physiologie - neurologie - enzymologie","SPUV200"));
                listCategorie2.add(sv);

                Semestre semestre2 = new Semestre(2,listCategorie,null);

                //le controlleur gere cette UE


                ArrayList<String> list = new ArrayList<>();
                list.add(gson.toJson(semestre1)); list.add(gson.toJson(semestre2));

                reseauController.dataConnexion().call(gson.toJson(list));

                //de plus le serveur envoie un message helloClient qui na pas de test pour le moment TODO

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
    public void loadCategoryTest(){
        //avant la connexion on a une liste view de 0 UE
        for (Semestre s: mActivityRule.getActivity().getModele().getSemestres()
             ) {
            assertEquals(0,s.getListCategorie().size());
        }

        //quand le client ce connecte au serveur, le serveur recois la liste des UE dans ce cas 1 UE
        connexion.send(CONNEXION,"user");

        ArrayList<Semestre> s = mActivityRule.getActivity().getModele().getSemestres();

        assertEquals(2, s.size());
    }

    /**
     * Scénario de choix d'UE dans un semestre
     */
    @Test
    public void choisirUE(){


        connexion.send(CONNEXION,"user");
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityRule.getActivity().expListView.expandGroup(0);
                mActivityRule.getActivity().expListView.expandGroup(1);
            }});


                onData(allOf(is(instanceOf(UE.class)), withChildName("Decouverte 1")))
                        .inAdapterView(withId(R.id.catList))
                        .check(matches(isDisplayed()))
                        .perform(click());
                verify(parcours, atLeast(1)).isChecked(modele.getSemestre(0).findUE("SPUGDE10"));


    }



    /**
     * Simulation d'un cas: passage d'un semestre à l'autre
     */

    @Test
    public void changeSemestre(){


        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mActivityRule = Mockito.spy(mActivityRule);


        Espresso.openActionBarOverflowOrOptionsMenu(appContext);

        // Click the item.
        onView(withText("Semestre 2"))
                .perform(click());

        assertEquals(modele.getSemestreCourant(),1);

        assertEquals(mActivityRule.getActivity().getSupportActionBar().getTitle(),"Semestre 2");


    }




    //TODO tester le bouton SAUVEGARDER

}
