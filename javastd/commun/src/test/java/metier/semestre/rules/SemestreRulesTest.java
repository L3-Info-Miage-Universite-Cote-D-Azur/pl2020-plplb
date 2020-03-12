package metier.semestre.rules;

import metier.UE;
import metier.semestre.SemestreManager;
import metier.semestre.SemestreRules;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SemestreRulesTest {

    SemestreRules rules;

    @Mock
    SemestreManager manager;


    @Test
    public void canBeCheckTest(){

        //===================================
        //TEST NOMBRES UE LIBRE MAX
        //===================================
        for(int i = 1; i<6; i++){
            //on test le nombre max d'ue libre au totale
            rules = new SemestreRules(i,100,new ArrayList<String>());

            manager = Mockito.mock(SemestreManager.class);
            //TEST SELECTION VALIDE
            // une hashmap vide soit on a selectionner 0 ue par categorie
            when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());
            //je considere que je suis a i-1 ue selectionner je dois donc pouvoir tout le temps selectionner une nouvelle ue
            when(manager.getUeLibreSelected()).thenReturn(i-1);
            //je peut tout le temps selectionner
            assertEquals(rules.canBeCheck(new UE("123","abc"),manager),true);

            //TEST SELECTION INVALIDE
            manager = Mockito.mock(SemestreManager.class);
            // une hashmap vide soit on a selectionner 0 ue par categorie
            when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());
            //je considere que je suis au nombre max d'ue selectionner ce semestre
            when(manager.getUeLibreSelected()).thenReturn(i);
            //je peut pas selectionner
            assertEquals(rules.canBeCheck(new UE("123","abc"),manager),false);

        }

        //===================================
        //TEST NOMBRES UE LIBRE MAX (nombre constant mais nb ue deja selectionner varie)
        //===================================
        int maxUE = 4;
        for(int i = 1; i<6; i++) {
            //on test le nombre max d'ue libre au totale
            rules = new SemestreRules(maxUE, 100, new ArrayList<String>());

            //TEST SELECTION VALIDE
            if (i < maxUE) {
                manager = Mockito.mock(SemestreManager.class);
                // une hashmap vide soit on a selectionner 0 ue par categorie
                when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
                //i< maxUE donc je peut encore selectionner dans ce semestre
                when(manager.getUeLibreSelected()).thenReturn(i);
                //je peut selectionner
                assertEquals(rules.canBeCheck(new UE("123", "abc"), manager), true);
            }
            //TEST SELECTION INVALIDE
            else {
                manager = Mockito.mock(SemestreManager.class);
                // une hashmap vide soit on a selectionner 0 ue par categorie
                when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
                //comme i >= maxUE il a plus de place
                when(manager.getUeLibreSelected()).thenReturn(i);
                //je peut pas selectionner
                assertEquals(rules.canBeCheck(new UE("123", "abc"), manager), false);

            }
        }


        //===================================
        //TEST NB UE PAR CATEGORIE
        //===================================
        HashMap<String,Integer> nbByCat = new HashMap<String,Integer>();
        nbByCat.put("0",0); //il reste de la place
        nbByCat.put("1",1); //il reste de la place
        nbByCat.put("2",2); //on est deja au max on peut plus ajouter
        manager = Mockito.mock(SemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0); //on fait comme si on a pas d'ue
        when(manager.getCountByCategory()).thenReturn(nbByCat); //on lui donne notre hashmap
        int nbMaxCat = 1;
        //on cree une regle avec pour nombre max par cat 1
        rules = new SemestreRules(100, nbMaxCat, new ArrayList<String>());
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


        //===================================
        //TEST UE OBLIGATOIRE on peut tout le temps la cocher (normalement ces ue ne doivent pas etre decocher )
        //===================================

        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        rules = new SemestreRules(0, 0, obligatoire); //on ne peut rien mettre dedans sauf les obligatoire
        UE obligatoireUE = new UE("name","1");
        UE notObligatoireUE = new UE("name","0");
        manager = Mockito.mock(SemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0);
        when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());
        //on peut cocher l'ue obligatoire
        assertEquals(rules.canBeCheck(obligatoireUE,manager),true);
        //on peut cocher l'ue libre
        assertEquals(rules.canBeCheck(notObligatoireUE,manager),false);


        //===================================
        //TEST UE A CHOIX
        //===================================
        ArrayList<String> choose = new ArrayList<String>();
        choose.add("0");
        choose.add("1");
        choose.add("2");
        rules = Mockito.spy(new SemestreRules(0, 0, new ArrayList<>(), choose, 2));
        for (int i = 0; i < 4; i++) {
            manager = Mockito.mock(SemestreManager.class);
            when(manager.getUeLibreSelected()).thenReturn(0);
            when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
            when(manager.getChooseUeSelected()).thenReturn(0);
            UE ue = new UE("name", "" + i);
            //c'est une ue a choix des place lui son resevé
            if (i <= 2) {
                assertEquals(rules.canBeCheck(ue, manager), true);
                //on appelle pas la fonction pour les ue libre car une place est reserver pour les ue au choix
                verify(rules,never()).canBeCheckLibreUE(any(),any());
            }
            //ce n'est pas une ue a choix aucune lui son resevé on regarde comme pour une ue normal (mais il a pas de place (0))
            else {
                assertEquals(rules.canBeCheck(ue, manager), false);
                //on appelle pas la fonction pour les ue libre mais il ne reste pas de place
                verify(rules,times(1)).canBeCheckLibreUE(any(),any());
            }
        }
    }


    @Test
    public void canBeUncheckTest(){
        //TEST cas ou c'est une ue obligatoire on peut pas la decocher
        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        rules = new SemestreRules(0, 0, obligatoire);
        UE obligatoireUE = new UE("name","1");
        UE notObligatoireUE = new UE("name","0");
        manager = Mockito.mock(SemestreManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0);
        when(manager.getCountByCategory()).thenReturn(new HashMap<String,Integer>());

        //on peut pas decocher une ue obligatoire
        assertEquals(rules.canBeUncheck(obligatoireUE,manager),false);

        //on peut decocher les autre ue
        assertEquals(rules.canBeUncheck(notObligatoireUE,manager),true);
    }


    @Test
    public void isObligatoryUETest(){
        //TEST si une ue est obligatoire
        ArrayList<String> obligatoire = new ArrayList<String>();
        obligatoire.add("1");
        obligatoire.add("2");
        obligatoire.add("3");
        rules = new SemestreRules(0, 0, obligatoire);
        String obligatoireUE1 = "1";
        String obligatoireUE2 = "2";
        String obligatoireUE3 = "3";
        String notObligatoireUE = "0";
        //les ue obligatoire sont bien considere comme obligatoire
        assertEquals(rules.isObligatoryUE(obligatoireUE1),true);
        assertEquals(rules.isObligatoryUE(obligatoireUE2),true);
        assertEquals(rules.isObligatoryUE(obligatoireUE3),true);

        //les ue non obligatoire sont bien considere comme non obligatoire
        assertEquals(rules.isObligatoryUE(notObligatoireUE),false);
    }

    @Test
    public void isChooseUETest(){
        //TEST si une ue est obligatoire
        ArrayList<String> choose = new ArrayList<String>();
        choose.add("1");
        choose.add("2");
        choose.add("3");
        rules = new SemestreRules(0, 0, new ArrayList<String>(),choose,2);
        String chooseUE1 = "1";
        String chooseUE2 = "2";
        String chooseUE3 = "3";
        String notChooseUE = "0";

        //les ue parmis les choix sont bien considere comme ue a choix
        assertEquals(rules.isChooseUE(chooseUE1),true);
        assertEquals(rules.isChooseUE(chooseUE2),true);
        assertEquals(rules.isChooseUE(chooseUE3),true);

        //les ue qui sont pas parmis les choix ne sont pas considere comme ue a choix
        assertEquals(rules.isChooseUE(notChooseUE),false);
    }



}

