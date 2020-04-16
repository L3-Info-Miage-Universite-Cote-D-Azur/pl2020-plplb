package com.example.plplbproject.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;

public class UpdateSemesterTest {

    SemesterList semesterList;
    UpdateSemester updateSemester;
    Gson gson = new GsonBuilder().create();

    @Before
    public void init(){
        // ----------------- SEMESTRE 1
        ArrayList<Categorie> listCategorie1 = new ArrayList<Categorie>();

        //CATEGORIE 1 S1
        Categorie cat1S1 = new Categorie("CAT1S1");
        cat1S1.addUe(new UE("ue1","code1"));
        cat1S1.addUe(new UE("ue2","code2"));
        cat1S1.addUe(new UE("ue3","code3"));
        listCategorie1.add(cat1S1);

        //CATEGORIE 2 S1
        Categorie cat2S1 = new Categorie("CAT2S1");
        cat2S1.addUe(new UE("ue4","code4"));
        cat2S1.addUe(new UE("ue5","code5"));
        listCategorie1.add(cat2S1);

        Semestre semestre1 = new Semestre(1,listCategorie1,null);

        // ----------------- SEMESTRE 2
        ArrayList<Categorie> listCategorie2 = new ArrayList<Categorie>();

        //CATEGORIE 1 S2
        Categorie cat1S2 = new Categorie("CAT1S2");
        cat1S2.addUe(new UE("ue6","code6"));
        cat1S2.addUe(new UE("ue7","code7"));
        cat1S2.addUe(new UE("ue8","code8"));
        listCategorie2.add(cat1S2);

        //CATEGORIE 2 S2
        Categorie cat2S2 = new Categorie("CAT2S2");
        cat2S2.addUe(new UE("ue9","code9"));
        cat2S2.addUe(new UE("ue10","code10"));
        listCategorie2.add(cat2S2);

        Semestre semestre2 = new Semestre(2,listCategorie2,null);

        // ----------------- SEMESTRE 3
        ArrayList<Categorie> listCategorie3 = new ArrayList<Categorie>();

        //CATEGORIE 1 S3
        Categorie cat1S3 = new Categorie("CAT1S3");
        cat1S3.addUe(new UE("ue11","code11"));
        cat1S3.addUe(new UE("ue12","code12"));
        cat1S3.addUe(new UE("ue13","code13"));
        listCategorie3.add(cat1S3);

        //CATEGORIE 2 S2
        Categorie cat2S3 = new Categorie("CAT2S3");
        cat2S3.addUe(new UE("ue14","code14"));
        cat2S3.addUe(new UE("ue15","code15"));
        listCategorie2.add(cat2S3);

        Semestre semestre3 = new Semestre(3,listCategorie3,null);

        // ----------------- SEMESTRE 4
        ArrayList<Categorie> listCategorie4 = new ArrayList<Categorie>();

        //CATEGORIE 1 S4
        Categorie cat1S4 = new Categorie("CAT1S4");
        cat1S4.addUe(new UE("ue16","code16"));
        cat1S4.addUe(new UE("ue17","code17"));
        cat1S4.addUe(new UE("ue18","code18"));
        listCategorie4.add(cat1S4);

        //CATEGORIE 2 S4
        Categorie cat2S4 = new Categorie("CAT2S4");
        cat2S4.addUe(new UE("ue19","code19"));
        cat2S4.addUe(new UE("ue20","code20"));
        listCategorie4.add(cat2S4);

        Semestre semestre4 = new Semestre(4,listCategorie4,null);

        //------init du semestre list
        semesterList = new SemesterList();
        updateSemester = new UpdateSemester();

        semesterList.add(semestre1);
        semesterList.add(semestre2);
        semesterList.add(semestre3);
        semesterList.add(semestre4);



        //On remet a zero le singleton.
        DataSemester.SEMESTER.setSemesterList(null);
    }

    @Test
    public void callTest(){
        //On n'a encore rien reçu la liste est donc vide.
        assertEquals(false,DataSemester.SEMESTER.hasSemesterList());

        //On envoie des données
        updateSemester.call(gson.toJson(semesterList));

        //On a recu quelque chose
        assertEquals(true,DataSemester.SEMESTER.hasSemesterList());

        //On recupere ce que l'on a
        SemesterList data = DataSemester.SEMESTER.getSemesterList();

        //On va tout parcourir
        for(int i =0;i < semesterList.size(); i++){
            Semestre semestre = semesterList.get(i);//Le semestre voulu
            Semestre semestre2 = data.get(i);//Le semestre obtenu

            //Egalite sur le numero
            assertEquals(semestre.getNumber(),semestre2.getNumber());

            for(int j =0;j<semestre.getListCategorie().size();j++){
                Categorie categorie = semestre.getListCategorie().get(j);//La categorie voulue
                Categorie categorie2 = semestre2.getListCategorie().get(j);//La categorie obtenue

                //Egalite sur les nom de categorie
                assertEquals(categorie.getName(),categorie2.getName());

                for(int h =0; h<categorie.getListUE().size();h++){
                    UE ue = categorie.getListUE().get(h);//L'ue voulue
                    UE ue2 = categorie2.getListUE().get(h);//L'ue obtenue

                    //Egalite sur les noms des ues
                    assertEquals(ue.getUeName(),ue2.getUeName());
                    //Egalite sur les code des ues
                    assertEquals(ue.getUeCode(),ue2.getUeCode());
                }
            }
        }
    }

}
