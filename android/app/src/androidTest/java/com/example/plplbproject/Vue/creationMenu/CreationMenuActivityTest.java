package com.example.plplbproject.Vue.creationMenu;

import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.plplbproject.R;
import com.example.plplbproject.data.DataPredefinedCourse;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import metier.parcours.ParcoursType;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


@RunWith(AndroidJUnit4.class)
public class CreationMenuActivityTest {

    @Rule
    public ActivityTestRule<CreationMenuActivity> mActivityRule = new ActivityTestRule<>(CreationMenuActivity.class, true, false);

    @Before
    public void initIntent(){
        Intent startIntent = new Intent();
        ArrayList<String> parcoursName = new ArrayList<String>();
        parcoursName.add("old1");
        parcoursName.add("old2");
        parcoursName.add("old3");
        parcoursName.add("old4");
        startIntent.putExtra("clientCourses",parcoursName);

        ArrayList<ParcoursType> predefine = new ArrayList<ParcoursType>();
        for(int i = 0; i<10; i++){
            predefine.add(new ParcoursType("course"+i,null,null));
        }
        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(predefine);

        mActivityRule.launchActivity(startIntent);



        //on ferme le clavier pour eviter certaint bug entre 2 test
        onView(withId(R.id.parcoursPredefList)).perform(closeSoftKeyboard());

    }

    @Test
    public void testInit(){

        //on recupere l'adaptateur
        RecyclerView.Adapter<PredefinedCourseViewHolder> adapter = ((RecyclerView)mActivityRule.getActivity().findViewById(R.id.parcoursPredefList)).getAdapter();


        //on regarde que l'on a bien tout les parcours type dans la liste (une bonne initialisation)
        assertEquals(adapter.getItemCount(),DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList().size());
    }


    @Test
    public void testErrorMessage(){


        //au debut le text d'erreur est invisible
        assertEquals(mActivityRule.getActivity().findViewById(R.id.errorMessage).getVisibility(),4);



        onView(withId(R.id.editParcoursName)).perform(typeText("old1"), closeSoftKeyboard());
        onView(withId(R.id.nouveauParcours)).perform(click());

        //le nom de parcours existe deja il faut donc le changer
        //le message d'erreur devien visible
        assertEquals(mActivityRule.getActivity().findViewById(R.id.errorMessage).getVisibility(), 0);
        //le message d'erreur correspond a la premiere erreur
        assertEquals(((TextView) mActivityRule.getActivity().findViewById(R.id.errorMessage)).getText(), "Ce nom de parcours existe déjà");
        viderText();

        //on rentre un nouveau nom qui n'est pas existant
        //mais on a pas selectionner de
        onView(withId(R.id.editParcoursName)).perform(typeText("new"), closeSoftKeyboard());
        onView(withId(R.id.nouveauParcours)).perform(click());
        assertEquals(((TextView) mActivityRule.getActivity().findViewById(R.id.errorMessage)).getText(), "Veuillez selectionner une parcours prédéfini.");
    }

    @Test
    public void testClickOnParcoursType(){
        for(int i=0; i<DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList().size();i++) {

            //on rentre de bonne valeur
            onView(withId(R.id.parcoursPredefList)).perform(actionOnItemAtPosition(i, click()));

            // on verifie que tout les element peuve etre selectionner 1 a 1
            assertEquals(mActivityRule.getActivity().getModele().getPredefinedCourseName(), DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseName().get(i));

        }

    }

    public void viderText(){
        mActivityRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //on vide le text
                ((EditText)mActivityRule.getActivity().findViewById(R.id.editParcoursName)).setText("");
            }});
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
