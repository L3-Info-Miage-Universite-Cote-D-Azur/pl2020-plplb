package com.example.plplbproject;

import com.example.plplbproject.model.MainModele;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.verification.Times;

import java.util.ArrayList;

import metier.UE;

import static org.junit.Assert.assertEquals;

public class MainModeleTest {

    @Spy
    MainModele mainModele;

    @Before
    public void init(){
        mainModele = Mockito.spy(MainModele.class);
    }

    @After
    public void clean(){
        mainModele.getAllUE().clear();
    }

    @Test
    public void setAllUETest() {
        ArrayList<UE> arrayList = new ArrayList<UE>();
        UE ue = new UE("TestName1", "TestCode1");
        UE ue2 = new UE("TestName2", "TestCode2");

        arrayList.add(ue);
        arrayList.add(ue2);

        //MainModele n'a pas encore d'UE
        mainModele.setAllUE(arrayList);

        //On verifie que addToAllUE est appele
        Mockito.verify(mainModele, new Times(1)).addToAllUE(arrayList);

        //On verifie que c'est bien ce qu'on a donner en argument.
        assertEquals(mainModele.getAllUE().size(), arrayList.size());
        for (int i = 0; i < arrayList.size(); i++) {
            assertEquals(mainModele.getAllUE().get(i), arrayList.get(i));
        }

        //AJOUT D'UNE AUTRE LISTE
        ArrayList<UE> arrayList2 = new ArrayList<UE>();
        UE ue3 = new UE("TestName3", "TestCode3");
        UE ue4 = new UE("TestName4", "TestCode4");
        UE ue5 = new UE("TestName5", "TestCode5");
        arrayList2.add(ue3);
        arrayList2.add(ue4);
        arrayList2.add(ue5);

        mainModele.setAllUE(arrayList2);

        //On verifie que addToAllUE est appele
        Mockito.verify(mainModele, new Times(1)).addToAllUE(arrayList);

        //On verifie que c'est bien ce qu'on a donner en argument.
        assertEquals(mainModele.getAllUE().size(), arrayList2.size());
        for (int i = 0; i < arrayList2.size(); i++) {
            assertEquals(mainModele.getAllUE().get(i), arrayList2.get(i));
        }
    }


    @Test
    public void addAllUEOneUETest(){
        UE ue = new UE("TestName1","TestCode1");
        UE ue2 = new UE("TestName2","TestCode2");

        //MainModele ne contient pas d'ue
        assertEquals(0,mainModele.getAllUE().size());

        mainModele.addToAllUE(ue);

        //Une UE est ajoutee.
        assertEquals(1,mainModele.getAllUE().size());
        assertEquals(ue,mainModele.getAllUE().get(0));

        mainModele.addToAllUE(ue2);

        //Une autre UE est ajoutee.
        assertEquals(2,mainModele.getAllUE().size());
        assertEquals(ue,mainModele.getAllUE().get(0));
        assertEquals(ue2,mainModele.getAllUE().get(1));

        //Ajout de beaucoup d'ue
        for(int i=1; i<100;i++){
            UE ue_i = new UE(""+i,""+i);
            mainModele.addToAllUE(ue_i);
            //l'ue est ajoute.
            assertEquals(2+i,mainModele.getAllUE().size());
            assertEquals(ue_i,mainModele.getAllUE().get(i+1));
        }

    }

    @Test
    public void addAllUEListUETest(){
        ArrayList<UE> arrayList = new ArrayList<UE>();
        UE ue = new UE("TestName1", "TestCode1");
        UE ue2 = new UE("TestName2", "TestCode2");
        UE ue3 = new UE("TestName3", "TestCode3");
        UE ue4 = new UE("TestName4", "TestCode4");

        arrayList.add(ue);
        arrayList.add(ue2);

        //Il n'y a pas d'ue
        assertEquals(0,mainModele.getAllUE().size());

        mainModele.addToAllUE(arrayList);

        //on ajoute deux ue
        assertEquals(2,mainModele.getAllUE().size());
        assertEquals(ue,mainModele.getAllUE().get(0));
        assertEquals(ue2,mainModele.getAllUE().get(1));

        arrayList.clear();
        arrayList.add(ue3);
        arrayList.add(ue4);
        mainModele.addToAllUE(arrayList);

        //on ajoute aux ue deja existantes deux nouvelles ues.
        assertEquals(4,mainModele.getAllUE().size());
        assertEquals(ue,mainModele.getAllUE().get(0));
        assertEquals(ue2,mainModele.getAllUE().get(1));
        assertEquals(ue3,mainModele.getAllUE().get(2));
        assertEquals(ue4,mainModele.getAllUE().get(3));
    }
}
