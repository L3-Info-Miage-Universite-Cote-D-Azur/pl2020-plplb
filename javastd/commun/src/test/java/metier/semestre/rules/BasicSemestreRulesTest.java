package metier.semestre.rules;

import metier.UE;
import metier.semestre.manager.BasicSemestreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BasicSemestreRulesTest {

    BasicSemestreRules rules;

    @Mock
    BasicSemestreManager manager;


    @Test
    public void canBeCheckTest(){
        for(int i = 1; i<6; i++){
            //on test le nombre max d'ue libre au totale
            rules = new BasicSemestreRules(i,100,new ArrayList<String>());

            manager = Mockito.mock(BasicSemestreManager.class);
            //TEST SELECTION VALIDE
            // une hashmap vide soit on a selectionner 0 ue par categorie
            when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());
            //je considere que je suis a i-1 ue selectionner je dois donc pouvoir tout le temps selectionner une nouvelle ue
            when(manager.getUeLibreSelected()).thenReturn(i-1);
            //je peut tout le temps selectionner
            assertEquals(rules.canBeCheck(new UE("123","abc"),manager),true);

            //TEST SELECTION INVALIDE
            manager = Mockito.mock(BasicSemestreManager.class);
            // une hashmap vide soit on a selectionner 0 ue par categorie
            when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());
            //je considere que je suis au nombre max d'ue selectionner ce semestre
            when(manager.getUeLibreSelected()).thenReturn(i);
            //je peut pas selectionner
            assertEquals(rules.canBeCheck(new UE("123","abc"),manager),false);

        }


        //TEST: a force de cocher des ue dans un semestre je dois ne plus pouvoir en cocher au bout d'un certaint nombre:
        int maxUE = 4;
        for(int i = 1; i<6; i++) {
            //on test le nombre max d'ue libre au totale
            rules = new BasicSemestreRules(maxUE, 100, new ArrayList<String>());

            //TEST SELECTION VALIDE
            if (i < maxUE) {
                manager = Mockito.mock(BasicSemestreManager.class);
                // une hashmap vide soit on a selectionner 0 ue par categorie
                when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
                //i< maxUE donc je peut encore selectionner dans ce semestre
                when(manager.getUeLibreSelected()).thenReturn(i);
                //je peut selectionner
                assertEquals(rules.canBeCheck(new UE("123", "abc"), manager), true);
            }
            //TEST SELECTION INVALIDE
            else {
                manager = Mockito.mock(BasicSemestreManager.class);
                // une hashmap vide soit on a selectionner 0 ue par categorie
                when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
                //comme i >= maxUE il a plus de place
                when(manager.getUeLibreSelected()).thenReturn(i);
                //je peut pas selectionner
                assertEquals(rules.canBeCheck(new UE("123", "abc"), manager), false);

            }
        }

        //TEST NB UE PAR CATEGORIE
        HashMap<String,Integer> nbByCat = new HashMap<String,Integer>();
        nbByCat.put("0",0); //il reste de la place
        nbByCat.put("1",1); //il reste de la place
        nbByCat.put("2",2); //on est deja au max on peut plus ajouter
        manager = Mockito.mock(BasicSemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0); //on fait comme si on a pas d'ue
        when(manager.getCountByCategory()).thenReturn(nbByCat); //on lui donne notre hashmap
        int nbMaxCat = 1;
        rules = new BasicSemestreRules(100, nbMaxCat, new ArrayList<String>());
        for(int i = 0; i<nbMaxCat*2; i++){
            UE ue = new UE(""+i,""+i);
            ue.setCategorie(""+i);
            if(i<nbMaxCat){
                assertEquals(rules.canBeCheck(ue,manager),true);
            }
            else{
                assertEquals(rules.canBeCheck(ue,manager),false);
            }
        }

        //TEST cas ou c'est une ue obligatoire on peut tout le temps la cocher
        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        rules = new BasicSemestreRules(0, 0, obligatoire); //on ne peut rien mettre dedans sauf les obligatoire
        UE obligatoireUE = new UE("name","1");
        UE notObligatoireUE = new UE("name","0");
        manager = Mockito.mock(BasicSemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0);
        when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());

        assertEquals(rules.canBeCheck(obligatoireUE,manager),true);
        assertEquals(rules.canBeCheck(notObligatoireUE,manager),false);
    }

    @Test
    public void canBeUncheckTest(){
        //TEST cas ou c'est une ue obligatoire on peut pas la decocher
        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        rules = new BasicSemestreRules(0, 0, obligatoire);
        UE obligatoireUE = new UE("name","1");
        UE notObligatoireUE = new UE("name","0");
        manager = Mockito.mock(BasicSemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0);
        when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());

        assertEquals(rules.canBeUncheck(obligatoireUE,manager),false);
        assertEquals(rules.canBeUncheck(notObligatoireUE,manager),true);
    }


    @Test
    public void isObligatoryUETest(){
        //TEST si une ue est obligatoire
        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        obligatoire.add("2");
        obligatoire.add("3");
        rules = new BasicSemestreRules(0, 0, obligatoire);
        String obligatoireUE1 = "1";
        String obligatoireUE2 = "2";
        String obligatoireUE3 = "3";
        String notObligatoireUE = "0";
        assertEquals(rules.isObligatoryUE(obligatoireUE1),true);
        assertEquals(rules.isObligatoryUE(obligatoireUE2),true);
        assertEquals(rules.isObligatoryUE(obligatoireUE3),true);
        assertEquals(rules.isObligatoryUE(notObligatoireUE),false);
    }



}
