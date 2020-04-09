package com.example.plplbproject.controleur.courseBuilder;

import android.content.Intent;
import android.view.View;


import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import metier.parcours.Parcours;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserControllerTest {

    @Mock
    CourseBuilderActivity courseBuilderActivity = Mockito.mock(CourseBuilderActivity.class);
    @Mock
    CourseBuilderModele courseBuilderModele = Mockito.mock(CourseBuilderModele.class);
    @Mock
    Parcours parcours = Mockito.mock(Parcours.class);

    @Mock
    View view = Mockito.mock(View.class);

    UserController userController;

    @Before
    public void init(){
        userController = new UserController(courseBuilderActivity,courseBuilderModele);
        userController = Mockito.spy(userController);
    }


    @Test
    public void saveButtonTest(){
        //Jamais appeler avant
        Mockito.verify(courseBuilderActivity,never()).startActivityForResult(any(Intent.class),anyInt());
        Mockito.verify(courseBuilderActivity,never()).toastMessage(anyString());


        //VERIFIPARCOURS RENVOIE TRUE.
        Mockito.when(courseBuilderModele.getCourse()).thenReturn(parcours);
        Mockito.when(parcours.verifiParcours()).thenReturn(true);


        userController.saveButton();
        //On appel bien une fois la fonction.
        Mockito.verify(courseBuilderActivity,times(1)).startActivityForResult(any(Intent.class),anyInt());

        //VERIFIPARCOURS RENVOIE FALSE.
        Mockito.when(parcours.verifiParcours()).thenReturn(false);

        userController.saveButton();
        //On appel bien une fois la fonction.
        Mockito.verify(courseBuilderActivity,times(1)).toastMessage(anyString());

    }

    @Test
    public void nextButtonTest(){
        //Jamais appeler avant
        Mockito.verify(courseBuilderActivity,never()).notifySemestreChange();
        Mockito.verify(courseBuilderModele,never()).nextSemester();

        //Mise en place du bouton.
        View.OnClickListener nextButton = userController.nextButton();

        //HasnextSemestre renvoie true.
        Mockito.when(courseBuilderModele.hasNextSemestre()).thenReturn(true);

        //On clique sur le button
        nextButton.onClick(view);
        //On verifie que les fonctions sont appelés.
        Mockito.verify(courseBuilderActivity,times(1)).notifySemestreChange();
        Mockito.verify(courseBuilderModele,times(1)).nextSemester();

        //HasnextSemestre renvoie false.
        Mockito.when(courseBuilderModele.hasNextSemestre()).thenReturn(false);

        //VERIFIPARCOURS RENVOIE FALSE.
        Mockito.when(courseBuilderModele.getCourse()).thenReturn(parcours);
        Mockito.when(parcours.verifiParcours()).thenReturn(false);

        //On clique sur le button
        nextButton.onClick(view);
        //On verifier que tout est bien appele.
        verify(userController,times(1)).saveButton();
    }

    @Test
    public void prevButtonTest(){
        //Jamais appeler avant
        Mockito.verify(courseBuilderActivity,never()).notifySemestreChange();
        Mockito.verify(courseBuilderModele,never()).prevSemestre();
        Mockito.verify(courseBuilderActivity,never()).exitIntent();

        //Mise en place du bouton.
        View.OnClickListener prevButton = userController.prevButton();

        //HasnextSemestre renvoie true.
        Mockito.when(courseBuilderModele.hasPrevSemestre()).thenReturn(true);

        //On clique sur le button
        prevButton.onClick(view);
        //On verifie que les fonctions sont appeles.
        Mockito.verify(courseBuilderActivity,times(1)).notifySemestreChange();
        Mockito.verify(courseBuilderModele,times(1)).prevSemestre();

        //HasnextSemestre renvoie false
        Mockito.when(courseBuilderModele.hasPrevSemestre()).thenReturn(false);

        //On clique sur le button
        prevButton.onClick(view);

        //Rien n'a bouger
        Mockito.verify(courseBuilderActivity,times(1)).notifySemestreChange();
        Mockito.verify(courseBuilderModele,times(1)).prevSemestre();
        //On verifie que la fonction est appele.
        Mockito.verify(courseBuilderActivity,times(1)).exitIntent();
    }
}
