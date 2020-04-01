package com.example.plplbproject.controleur.courseBuilder;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CourseBuilderModeleTest {

    CourseBuilderModele courseBuilderModele;

    @Test
    public void changeSemesterTest(){
        courseBuilderModele = new CourseBuilderModele(50);
        //L'index est a 0 par defaut.
        assertEquals(0,courseBuilderModele.getIndexCurrentSemester());

        //On change l'index de 0 à 50.
        for(int i = 0; i < 50; i++){
            courseBuilderModele.changeSemester(i);
            assertEquals(i,courseBuilderModele.getIndexCurrentSemester());
        }
    }

    @Test
    public void nextSemesterTest_And_PrevSemestreTest(){
        courseBuilderModele = new CourseBuilderModele(50);
        //L'index est a 0 par defaut.
        assertEquals(0,courseBuilderModele.getIndexCurrentSemester());

        //On incremente 49 fois
        for(int i = 1; i<50; i++){
            courseBuilderModele.nextSemester();
            //On a bien incrementer
            assertEquals(i,courseBuilderModele.getIndexCurrentSemester());

        }
        //On ne peut pas aller plus loin.
        assertEquals(49,courseBuilderModele.getIndexCurrentSemester());
        for(int i=0; i<500;i++){
            courseBuilderModele.nextSemester();
            assertEquals(49,courseBuilderModele.getIndexCurrentSemester());
        }

        //On decremente 49 fois
        for(int i = 1; i<50; i++){
            courseBuilderModele.prevSemestre();
            //On a bien incrementer
            assertEquals(49-i,courseBuilderModele.getIndexCurrentSemester());

        }
        //On ne peut pas aller plus loin.
        assertEquals(0,courseBuilderModele.getIndexCurrentSemester());
        for(int i = 0; i<500;i++){
            courseBuilderModele.prevSemestre();
            assertEquals(0,courseBuilderModele.getIndexCurrentSemester());
        }

    }

    @Test
    public void hasNextSemestreTest(){
        courseBuilderModele = new CourseBuilderModele(50);
        //L'index est a 0 par defaut.
        assertEquals(0,courseBuilderModele.getIndexCurrentSemester());

        for(int i = 0 ; i<50;i++){
            //On peut incrementer 49 fois
            assertEquals(true,courseBuilderModele.hasNextSemestre());
            //On change le semestre.
            courseBuilderModele.changeSemester(i);
        }
        //On ne peut plus incrementer
        assertEquals(false,courseBuilderModele.hasNextSemestre());
    }

    @Test
    public void hasPrevSemestreTest(){
        courseBuilderModele = new CourseBuilderModele(50);
        //L'index est a 0 par defaut.
        assertEquals(0,courseBuilderModele.getIndexCurrentSemester());

        //On commence au maximum
        courseBuilderModele.changeSemester(49);

        for(int i = 49 ; i>=0;i--){
            //On peut decrementer 49 fois
            assertEquals(true,courseBuilderModele.hasPrevSemestre());
            //On change le semestre.
            courseBuilderModele.changeSemester(i);
        }
        //On ne peut plus decrementer
        assertEquals(false,courseBuilderModele.hasPrevSemestre());
    }
}
