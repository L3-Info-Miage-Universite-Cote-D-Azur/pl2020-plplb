package com.example.plplbproject.Vue.courseBuilder;


import android.content.Intent;

import android.widget.Button;


import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.BoundedMatcher;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;


import com.example.plplbproject.R;



import com.example.plplbproject.controleur.courseBuilder.CourseBuilderModele;

import com.example.plplbproject.data.DataSemester;

import org.hamcrest.Matcher;
import org.junit.Before;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import org.mockito.Spy;




import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.core.internal.deps.dagger.internal.Preconditions.checkNotNull;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isClickable;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity.AUTOINIT;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


import static androidx.test.espresso.assertion.ViewAssertions.matches;




/*
    TODO:refaire en adaptant a  la nouvelle connexion
 */
/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class CourseBuilderActivityTest {

    @Rule
    public ActivityTestRule<CourseBuilderActivity> mActivityRule = new ActivityTestRule<>(CourseBuilderActivity.class, true, false);



    @Mock
    Parcours course;


    @Spy
    CourseBuilderModele modele;



    /**
     * Permet d'initialiser la liste de semestre
     * dans cette classe la liste de semestre a deja etait charger on peut donc la charger avant les test
     */
    protected void initSemesterList(){
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
        info.addUe(new UE("decouverte de informatique","AZERTY11"));
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
        SemesterList semesters = new SemesterList();
        semesters.add(semestre1);
        semesters.add(semestre2);


        DataSemester.SEMESTER.setSemesterList(semesters);
    }






    @Before
    public void init() {

        initSemesterList();

        modele = Mockito.spy(new CourseBuilderModele(DataSemester.SEMESTER.getNumberSemesters()));
        course = Mockito.mock(Parcours.class);
        when(course.canBeCheckedUE(any(UE.class))).thenReturn(true);

        // Lazily start the Activity from the ActivityTestRule this time to inject the start Intent
        Intent startIntent = new Intent();

        startIntent.putExtra("PredefinedCourseName","none");
        startIntent.putExtra("CourseName","none");
        startIntent.putExtra("className","test");
        startIntent.putExtra(AUTOINIT,false);
        mActivityRule.launchActivity(startIntent);
        modele.setCourse(course);
        mActivityRule.getActivity().setModele(modele);

        final Object synch = new Object();

        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mActivityRule.getActivity().initVue();

                synchronized (synch){
                    synch.notify();
                }

            }
        });

        synchronized (synch){
            try {
                synch.wait(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //replis du clavier
        onView(withId(R.id.search)).perform(closeSoftKeyboard());



    }



/*
    public void initSpyParcoursRequest(final ReseauController reseauController, ArrayList<String> jsonCourseList) {

        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {


                reseauController.receiveSave().call();


                return null;
            }
        }).when(socket).emit(LOADCOURSE,(any(String.class)));

 */

    @Test
    public void loadTest(){

        assertEquals(2, DataSemester.SEMESTER.getNumberSemesters());
    }

    /**
     * Scénario de choix d'UE dans un semestre
     */
    @Test
    public void choisirUE(){


        verify(course, never()).checkUE(DataSemester.SEMESTER.getSemesterList().findUE("SPUGDE10"));
        verify(course, never()).checkUE(DataSemester.SEMESTER.getSemesterList().findUE("SPUGDC10"));

        //on deplie la liste
        onView(withText("GEOGRAPHIE")).perform(click());

        onView(withText("Decouverte 1"))
                .perform(click());

        onView(withText("Decouverte 2"))
                .perform(click());


        //on check l'ue dans le modele
        verify(course, times(1)).checkUE(DataSemester.SEMESTER.getSemesterList().findUE("SPUGDE10"));
        verify(course, times(1)).checkUE(DataSemester.SEMESTER.getSemesterList().findUE("SPUGDC10"));

        //on a pas clicker dessue on check pas
        verify(course, never()).checkUE(DataSemester.SEMESTER.getSemesterList().findUE("SPUGDI10"));




    }



    /**
     * Simulation d'un cas: passage d'un semestre à l'autre
     */

    @Test
    public void changeSemester(){

        //on est au semestre 1
        assertEquals(mActivityRule.getActivity().getSupportActionBar().getTitle(),"Semestre 1");
        //button precedent (il y a pas de semestre precedent)
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_precedent)).getText().toString(),mActivityRule.getActivity().getString(R.string.menuPrecedent));
        //button suivant
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_suivant)).getText().toString(),mActivityRule.getActivity().getString(R.string.suivant));


        // suivant
        onView(withText("Suivant"))
                .perform(click());


        assertEquals(modele.getIndexCurrentSemester(),1);

        //on est mtn au semestre 2
        assertEquals(mActivityRule.getActivity().getSupportActionBar().getTitle(),"Semestre 2");
        //button precedent (il y a un semestre precedent)
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_precedent)).getText().toString(),mActivityRule.getActivity().getString(R.string.precedent));
        //button suivant (il y a pas de semestre plus loin)
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_suivant)).getText().toString(),mActivityRule.getActivity().getString(R.string.finaliser));

        // suivant
        onView(withText("Precedent"))
                .perform(click());


        //on est de nouveau au semestre 1
        assertEquals(mActivityRule.getActivity().getSupportActionBar().getTitle(),"Semestre 1");
        //button precedent (il y a pas de semestre precedent)
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_precedent)).getText().toString(),mActivityRule.getActivity().getString(R.string.menuPrecedent));
        //button suivant
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_suivant)).getText().toString(),mActivityRule.getActivity().getString(R.string.suivant));

    }

    @Test
    public void menuPrecedentTest(){

        //on quite l'activity pour retourner a la precedente
        onView(withText(mActivityRule.getActivity().getString(R.string.menuPrecedent)))
                .perform(click());

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertTrue(mActivityRule.getActivity().isDestroyed());
    }

    @Test
    public void finaliserTest(){
        //on quite l'activity pour retourner a la precedente
        // suivant (semestre 2)
        onView(withText("Suivant"))
                .perform(click());
        //le bouton devien finaliser
        //button suivant (il y a pas de semestre plus loin)
        assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_suivant)).getText().toString(),mActivityRule.getActivity().getString(R.string.finaliser));



        when(course.verifiParcours()).thenReturn(false);//on a pas finit le parcours on peut pas continuer

        // suivant il a plus de semestre on essayer finalise
        //raison inconue n'est plus fonctionnel android animation faillure...

        onView(withText(mActivityRule.getActivity().getString(R.string.finaliser)))
                .check(ViewAssertions.matches(isDisplayed())) //on verifie que le bouton finaliser est bien afficher
                .check(ViewAssertions.matches(isClickable())) //il est bien clickable
                .perform(click());

        //on est encore sur l'activity car la verification n'est pas passer
         assertEquals(((Button) mActivityRule.getActivity().findViewById(R.id.semestre_suivant)).getText().toString(),mActivityRule.getActivity().getString(R.string.finaliser));

         //un message est afficher pour prevenir l'utilisateur:
        onView(withText("Votre parcours est incorrect")).inRoot(withDecorView(not(is(mActivityRule.getActivity().getWindow().getDecorView())))).check(matches(isDisplayed()));


        try {
            Thread.sleep(2000); //le temps de finir
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(mActivityRule.getActivity().isDestroyed());


        //cette fois on a un parcours complete
        when(course.verifiParcours()).thenReturn(true);

        //TODO verifier qu'une nouvelle intent ce lance

        // on a le droit de finaliser on change d'activity
        //onView(withText(mActivityRule.getActivity().getString(R.string.finaliser)))
        //        .perform(click());



    }


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

    @Test
    public void filterTest(){

        String textFind = "decouverte de informatique";

        onView(withId(R.id.search)).perform(typeText(textFind), closeSoftKeyboard());

        //on deplie la liste
        onView(withText("INFORMATIQUE")).perform(click());
        onData(allOf(is(instanceOf(UE.class)), withChildName("decouverte de informatique")))
                .inAdapterView(withId(R.id.catList))
                .check(matches(isDisplayed()));

        

    }
    


}
