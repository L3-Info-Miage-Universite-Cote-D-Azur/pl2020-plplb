package com.example.plplbproject.Vue.mainMenu;

import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.rule.ActivityTestRule;

import com.example.plplbproject.MyViewAction;
import com.example.plplbproject.R;

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


import io.socket.client.Socket;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static constantes.NET.COURSECODE;
import static constantes.NET.COURSESNAMES;
import static constantes.NET.DELETECOURSE;
import static constantes.NET.RENAMECOURSE;
import static constantes.NET.SEMSTERDATA;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;


import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;




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



    @Test
    public void testSuppButtonOfParcours(){

        // on recois la liste qui est demander par le client
        mActivityRule.getActivity().receiveParcoursName().call(gson.toJson(listParcour));

        onView(withId(R.id.parcoursList)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.supprParcours)));

        //on a bien la popup qui est visible
        onView(withText("OUI")).check(matches(isDisplayed()));
        onView(withText("Etes vous sur de vouloir supprimer le parcours?")).check(matches(isDisplayed()));
        onView(withText("NON")).check(matches(isDisplayed()));

        //on supprime pas
        onView(withText("NON")).perform(click());


        //on a pas fait de suppresion
        verify(socket,never()).emit(eq(DELETECOURSE),anyString());

        //on ouvre de nouveau la popup
        onView(withId(R.id.parcoursList)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.supprParcours)));

        //on a bien la popup qui est visible
        onView(withText("OUI")).check(matches(isDisplayed()));
        onView(withText("Etes vous sur de vouloir supprimer le parcours?")).check(matches(isDisplayed()));
        onView(withText("NON")).check(matches(isDisplayed()));

        //on supprime
        onView(withText("OUI")).perform(click());

        //on demande au serveur si la suppression est valide
        verify(socket,times(1)).emit(eq(DELETECOURSE),eq("aaa")); //on envoie la suppresion au serveur

    }



    @Test
    public void testRenameButtonOfParcours(){

        // on recois la liste qui est demander par le client
        mActivityRule.getActivity().receiveParcoursName().call(gson.toJson(listParcour));

        onView(withId(R.id.parcoursList)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.renomParcours)));

        //on a bien la popup qui est visible
        onView(withText("CONFIRMER")).check(matches(isDisplayed()));
        onView(withText("aaa")).check(matches(isDisplayed())); //on a bien l'ancien nom d'afficher
        onView(withText("ANNULER")).check(matches(isDisplayed()));

        //on supprime pas
        onView(withText("ANNULER")).perform(click());


        //on a pas fait de renomage
        verify(socket,never()).emit(eq(RENAMECOURSE),anyString());

        //on ouvre de nouveau la popup
        onView(withId(R.id.parcoursList)).perform(
                RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.renomParcours)));

        //on a bien la popup qui est visible
        onView(withText("CONFIRMER")).check(matches(isDisplayed()));
        onView(withText("aaa")).check(matches(isDisplayed())); //on a bien l'ancien nom d'afficher
        onView(withText("ANNULER")).check(matches(isDisplayed()));

        //on rentre un nom
        onView(withId(R.id.newNameInput)).perform(typeText("newName"),closeSoftKeyboard());

        //on supprime
        onView(withText("CONFIRMER")).perform(click());

        //on demande au serveur si la suppression est valide
        verify(socket,times(1)).emit(eq(RENAMECOURSE),eq("[\"aaa\",\"newName\"]")); //on envoie la suppresion au serveur

    }


    @Test
    public void testShareButton(){
        // on recois la liste qui est demander par le client
        mActivityRule.getActivity().receiveParcoursName().call(gson.toJson(listParcour));

        onView(withId(R.id.addImageButton)).perform(click());

        //on a bien la popup d'ouvert
        onView(withText("Ajout par code")).check(matches(isDisplayed()));
        onView(withText("Entrer un code")).check(matches(isDisplayed()));
        onView(withText("ANNULER")).check(matches(isDisplayed()));
        onView(withText("AJOUTER")).check(matches(isDisplayed()));

        //on appuis sur annuler il ce passe rien
        onView(withText("ANNULER")).perform(click());

        //rien ne ce passe
        verify(socket,never()).emit(eq(COURSECODE),anyString());


        onView(withId(R.id.addImageButton)).perform(click());

        //on a bien la popup ouvert de nouveau
        onView(withText("Ajout par code")).check(matches(isDisplayed()));
        onView(withText("Entrer un code")).check(matches(isDisplayed()));
        onView(withText("ANNULER")).check(matches(isDisplayed()));
        onView(withText("AJOUTER")).check(matches(isDisplayed()));

        onView(withId(R.id.codeInput)).perform(typeText("12345"),closeSoftKeyboard());

        //on confirme
        onView(withText("AJOUTER")).perform(click());

        //une requete est envoyer au serveur pour le partage
        verify(socket,times(1)).emit(eq(COURSECODE),eq("\"12345\""));
    }
    

}
