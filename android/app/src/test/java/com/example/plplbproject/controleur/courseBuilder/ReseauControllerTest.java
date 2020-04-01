package com.example.plplbproject.controleur.courseBuilder;

import com.example.plplbproject.Vue.courseBuilder.CourseBuilderActivity;
import com.example.plplbproject.data.DataPredefinedCourse;
import com.example.plplbproject.data.DataSemester;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.Parcours;
import metier.parcours.ParcoursType;
import metier.semestre.SemesterList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class ReseauControllerTest {
    @Mock
    CourseBuilderActivity courseBuilderActivity = Mockito.mock(CourseBuilderActivity.class);
    @Mock
    CourseBuilderModele courseBuilderModele = Mockito.mock(CourseBuilderModele.class);
    @Mock
    ParcoursType parcoursType = Mockito.mock(ParcoursType.class);

    ReseauController reseauController;
    Gson gson;

    @Before
    public void init(){
        reseauController = new ReseauController(courseBuilderActivity,courseBuilderModele);
        gson = new Gson();
    }

    @Test
    public void receiveSaveTest(){
        //Jamais appeler avant
        Mockito.verify(courseBuilderActivity,never()).notifyUeListView();
        Mockito.verify(courseBuilderModele,never()).setCourse(any(Parcours.class));

        //Mise en place du listener.
        Emitter.Listener receiveSave = reseauController.receiveSave();

        //Mise en place.
        ArrayList<ParcoursType> tab = new ArrayList<ParcoursType>();
        //On ajout un mock
        tab.add(parcoursType);
        //Le nom doit correspondre au nom donné dans l'envoie.
        //Sinon crash du au parcoursRule (dans parcours) qui sera null.
        Mockito.when(parcoursType.getName()).thenReturn("nomParcoursType");

        //On simule la reception de donnée
        DataSemester.SEMESTER.setSemesterList(new SemesterList());
        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(tab);

        //Creation de l'envoie
        ArrayList<String> toSend = new ArrayList<>();
        toSend.add("nom");
        toSend.add("nomParcoursType");
        toSend.add("UnCodeD'UE");

        //On appelle le listener.
        receiveSave.call(gson.toJson(toSend));

        //Les fonctions ont été appelée.
        Mockito.verify(courseBuilderActivity,times(1)).notifyUeListView();
        Mockito.verify(courseBuilderModele,times(1)).setCourse(any(Parcours.class));
    }
}
