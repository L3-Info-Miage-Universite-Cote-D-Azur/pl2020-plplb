package com.example.plplbproject.Vue.UeVisualisation;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import metier.UE;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class UeVisualisationActivityTest {

    @Rule
    public ActivityTestRule<UeVisualisationActivity> mActivityRule = new ActivityTestRule<>(UeVisualisationActivity.class, true, false);

    UE ue;

    @Before
    public void init() {
        ue = new UE("XXXXX","YYYYY");
        ue.setSemestreNumber(1);
        ue.setCategorie("ma categorie");
        ue.setDescription("ca description");

        Intent startIntent = new Intent();
        startIntent.putExtra("UE",ue);
        mActivityRule.launchActivity(startIntent);
    }

    @Test
    public void testAffichage(){

        //on a bien le code ue d'afficher:
        onView(withText("XXXXX")).check(matches(isDisplayed()));

        //on a bien le nom d'afficher:
        onView(withText("YYYYY")).check(matches(isDisplayed()));

        //on a bien la categorie d'afficher:
        onView(withText("ma categorie")).check(matches(isDisplayed()));

        //on a bien la description d'afficher:
        onView(withText("ca description")).check(matches(isDisplayed()));

        //on a bien le bouton quiter:
        onView(withText("QUITTER")).check(matches(isDisplayed()));
    }

    @Test
    public void testQuitter(){

        //on a bien l'activiter active
        assertFalse(mActivityRule.getActivity().isDestroyed());

        //on essaye de quiter:
        onView(withText("QUITTER")).check(matches(isDisplayed())).perform(click());

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
