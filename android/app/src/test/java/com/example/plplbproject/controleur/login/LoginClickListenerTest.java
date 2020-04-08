package com.example.plplbproject.controleur.login;

import android.view.View;

import com.example.plplbproject.Vue.login.LoginActivity;
import com.example.plplbproject.reseau.Connexion;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;


import io.socket.client.Socket;
import metier.Student;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class LoginClickListenerTest {

    @Mock
    LoginActivity vue = Mockito.mock(LoginActivity.class);
    @Mock
    Student student = Mockito.mock(Student.class);
    @Mock
    View view = Mockito.mock(View.class);
    @Mock
    Socket socket = Mockito.mock(Socket.class);

    LoginClickListener loginClickListener;



    @Before
    public void init(){
        Connexion.CONNEXION.setSocket(socket);
        loginClickListener = new LoginClickListener(student,vue);
    }

    @Test
    public void onClickTest(){
        //Jamais appeler avant
        Mockito.verify(student,never()).setNom(anyString());
        Mockito.verify(vue,never()).switchIntent();
        Mockito.verify(vue,never()).setTextError("Il est impossible de ce connecter au serveur");
        Mockito.verify(vue,never()).setTextError("INE invalide (format : ab123456)");
        Mockito.verify(vue,never()).clearLoginInput();

        //Mise en place des mocks
        Mockito.when(vue.getLoginInput()).thenReturn("unINE");
        //Ine accepte
        Mockito.when(student.acceptINE(anyString())).thenReturn(true);
        //on est connecte
        Mockito.when(socket.connected()).thenReturn(true);

        //On clique
        loginClickListener.onClick(view);

        //Les fonctions ont été appelées
        Mockito.verify(student,times(1)).setNom(anyString());
        Mockito.verify(vue,times(1)).switchIntent();


        //on est n'est plus connecte
        Mockito.when(socket.connected()).thenReturn(false);

        //On clique
        loginClickListener.onClick(view);

        //Les fonctions ont été appelées et les autres n'ont pas bouger
        Mockito.verify(student,times(1)).setNom(anyString());

        Mockito.verify(vue,times(1)).switchIntent();
        Mockito.verify(vue,times(1)).setTextError("Il est impossible de ce connecter au serveur");

        //L'ine n'est plus accepter
        Mockito.when(student.acceptINE(anyString())).thenReturn(false);

        //On clique
        loginClickListener.onClick(view);

        //Les fonctions ont été appelées et les autres n'ont pas bouger
        Mockito.verify(vue,times(1)).setTextError("Numéro étudiant invalide (format : ab123456)");
        Mockito.verify(vue,times(1)).clearLoginInput();

        Mockito.verify(student,times(1)).setNom(anyString());
        Mockito.verify(vue,times(1)).switchIntent();
        Mockito.verify(vue,times(1)).setTextError("Il est impossible de ce connecter au serveur");
    }
}