package com.example.plplbproject.data;

import org.junit.Test;

import metier.semestre.SemesterList;
import metier.semestre.Semestre;

import static org.junit.Assert.assertEquals;

public class DataSemesterTest {

    @Test
    public void hasSemesterListTest(){
        //On n'a encore rien reçu.
        assertEquals(false,DataSemester.SEMESTER.hasSemesterList());

        //On simule la reception de données
        SemesterList semesterList = new SemesterList();
        semesterList.add(new Semestre());

        DataSemester.SEMESTER.setSemesterList(semesterList);

        //On a à présent reçu quelque chose
        assertEquals(true,DataSemester.SEMESTER.hasSemesterList());
    }

    @Test
    public void getNumberSemestersTest(){
        //On n'a rien reçu, par defaut la valeur est zero.
        assertEquals(0,DataSemester.SEMESTER.getNumberSemesters());

        //On simule la reception de données
        SemesterList semesterList = new SemesterList();
        semesterList.add(new Semestre());

        DataSemester.SEMESTER.setSemesterList(semesterList);

        //La longueur est de 1
        assertEquals(1,DataSemester.SEMESTER.getNumberSemesters());

        //On simule plusieurs reception
        SemesterList semesterList1 = new SemesterList();
        for(int i=0;i<100;i++){
            //On ajoute un semestre
            semesterList1.add(new Semestre());
            //On l'affecte a nouveau
            DataSemester.SEMESTER.setSemesterList(semesterList1);
            //Le nombre de semestres dans semestresList est i+1
            assertEquals(i+1,DataSemester.SEMESTER.getNumberSemesters());
        }

        //On réinitialise, c'est bien 0
        DataSemester.SEMESTER.setSemesterList(null);
        assertEquals(0,DataSemester.SEMESTER.getNumberSemesters());
    }
}
