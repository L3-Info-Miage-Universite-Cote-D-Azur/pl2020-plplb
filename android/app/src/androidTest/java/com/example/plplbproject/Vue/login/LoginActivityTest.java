package com.example.plplbproject.Vue.login;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.espresso.Espresso;
import androidx.test.rule.ActivityTestRule;

import com.example.plplbproject.R;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import io.socket.client.Socket;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class LoginActivityTest {


    @Mock
    Socket socket;

    private EditText loginInput;//Le champ ou l'utilisateur ecrit
    private TextView textError;//Le champ d'erreur.

    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule = new ActivityTestRule<>(LoginActivity.class, true, false);

    @Before
    public void init() {
        socket = Mockito.mock(Socket.class);
        when(socket.connected()).thenReturn(true);

        Intent startIntent = new Intent();

        mActivityRule.launchActivity(startIntent);

        //setup de bouton
        loginInput = mActivityRule.getActivity().findViewById(R.id.loginInput);
        textError = mActivityRule.getActivity().findViewById(R.id.connexionError);

    }




    @Test
    public void testInputBadLogin(){

        //on ne voit pas de message d'erreur au debut
        assertEquals(textError.getVisibility(),View.INVISIBLE);

        //on essaye de rentrer un input trop long
        onView(withId(R.id.loginInput)).perform(typeText("0123456789"), closeSoftKeyboard());

        //les dernier caracter on etait bloqué
        assertEquals(loginInput.getText().toString(),"01234567");

        //de plus le login n'est pas correcte il vas donc etre refuse un message d'erreur vas apparaitre
        onView(withId(R.id.connexionButton)).perform(click());

        //on ne voit pas de message d'erreur au debut
        assertEquals(textError.getVisibility(),View.VISIBLE);
        //le message d'erreur est "INE invalide (format : ab123456)"
        assertEquals(textError.getText().toString(),"Numéro étudiant invalide (format : ab123456)");
        //on a l'input qui est vider
        assertEquals(loginInput.getText().toString(),"");

        //on met un login accepter mais il a pas de connexion internet il a une nouvelle erreur
        when(socket.connected()).thenReturn(false);
        onView(withId(R.id.loginInput)).perform(typeText("ab123456"), closeSoftKeyboard());
        onView(withId(R.id.connexionButton)).perform(click());

        //on  voit  le message d'erreur
        assertEquals(textError.getVisibility(),View.VISIBLE);

        //le message d'erreur est "Il est impossible de ce connecter au serveur"
        assertEquals(textError.getText().toString(),"Il est impossible de ce connecter au serveur");

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
