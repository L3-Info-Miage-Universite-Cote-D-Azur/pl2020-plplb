package com.example.plplbproject.controleur.creationMenu;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import static org.junit.Assert.*;

public class CreationMenuModeleTest {

    CreationMenuModele creationMenuModele;

    @Before
    public void init(){
        ArrayList<String> listPredefinedCourse = new ArrayList<String>();
        ArrayList<String> listCourseName = new ArrayList<String>();

        listPredefinedCourse.add("PredefinedCourse1");
        listPredefinedCourse.add("PredefinedCourse2");
        listPredefinedCourse.add("PredefinedCourse3");

        listCourseName.add("UnParcours1");
        listCourseName.add("UnParcours2");
        listCourseName.add("UnParcours3");
        listCourseName.add("UnParcours4");

        creationMenuModele = new CreationMenuModele(listPredefinedCourse,listCourseName);
    }

    @Test
    public void canBeChooseNameTest(){
        //Le vide est refusé
        assertEquals(false,creationMenuModele.canBeChooseName(""));
        assertEquals(false,creationMenuModele.canBeChooseName(" "));
        assertEquals(false,creationMenuModele.canBeChooseName("  "));

        //Les noms deja pris sont refusé
        assertEquals(false,creationMenuModele.canBeChooseName("UnParcours1"));
        assertEquals(false,creationMenuModele.canBeChooseName("UnParcours2"));
        assertEquals(false,creationMenuModele.canBeChooseName("UnParcours3"));
        assertEquals(false,creationMenuModele.canBeChooseName("UnParcours4"));

        //Les noms qui ne sont pas pris sont accepté.

        assertEquals(true,creationMenuModele.canBeChooseName("UnAutreParcours"));
        assertEquals(true,creationMenuModele.canBeChooseName("UnParcours5"));
        assertEquals(true,creationMenuModele.canBeChooseName("EncoreUnAutreParcours"));
    }

    @Test
    public void isSelectedPredefinedCourseTest(){
        //Rien n'est selectionné
        assertEquals(false,creationMenuModele.isSelectedPredefinedCourse());

        //Le nom attribué est normalement un nom de parcours predefinie.
        creationMenuModele.setPredefinedCourseName("OnAChoisiQuelqueChose");
        //Quelque chose est selectionné
        assertEquals(true,creationMenuModele.isSelectedPredefinedCourse());

        //On a toujours quelque chose de selectionné
        creationMenuModele.setPredefinedCourseName("AutreChose");
        assertEquals(true,creationMenuModele.isSelectedPredefinedCourse());

        creationMenuModele.setPredefinedCourseName(null);
        //On n'a plus rien de selectionné
        assertEquals(false,creationMenuModele.isSelectedPredefinedCourse());
    }
}
