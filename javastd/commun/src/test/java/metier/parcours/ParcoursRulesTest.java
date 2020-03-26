package metier.parcours;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParcoursRulesTest {

    ParcoursRules parcoursRules;
    ArrayList<String> parcoursType_ObligatoryUe;
    HashMap<String,Integer> parcoursType_NumberUes;

    ArrayList<HashMap<String,Integer>> listHashmap;
    ArrayList<String> listSelectUe;

    public void init1(){
        /* INITIALISATION DU PARCOURS RULE */
        parcoursType_ObligatoryUe = new ArrayList<String>();
        parcoursType_ObligatoryUe.add("Moncode1");
        parcoursType_ObligatoryUe.add("Moncode3");
        parcoursType_ObligatoryUe.add("Moncode7");
        parcoursType_ObligatoryUe.add("Moncode11");

        parcoursType_NumberUes = new HashMap<String, Integer>();
        parcoursType_NumberUes.put("INFO",6);
        parcoursType_NumberUes.put("MATH",2);

        parcoursRules = new ParcoursRules(new ParcoursType("test",parcoursType_NumberUes,parcoursType_ObligatoryUe));

        /* INITIALISATION DU PARCOURS A VERIFIER */
        HashMap<String,Integer> hashMaps1 = new HashMap<String, Integer>();
        hashMaps1.put("ECO",1);
        hashMaps1.put("INFO",1);
        hashMaps1.put("MATH",1);

        HashMap<String,Integer> hashMaps2 = new HashMap<String, Integer>();
        hashMaps2.put("INFO",2);
        hashMaps2.put("ECO",1);

        HashMap<String,Integer> hashMaps3 = new HashMap<String, Integer>();
        hashMaps3.put("MATH",2);
        hashMaps3.put("INFO",2);

        HashMap<String,Integer> hashMaps4 = new HashMap<String, Integer>();
        hashMaps4.put("INFO",1);

        listHashmap = new ArrayList<HashMap<String, Integer>>();
        listHashmap.add(hashMaps1);
        listHashmap.add(hashMaps2);
        listHashmap.add(hashMaps3);
        listHashmap.add(hashMaps4);

        listSelectUe = new ArrayList<String>();

        listSelectUe.add("Moncode1");
        listSelectUe.add("Moncode3");
        listSelectUe.add("Moncode7");
        listSelectUe.add("Moncode11");
        listSelectUe.add("Moncode2");
        listSelectUe.add("Moncode4");
    }

    public void init2(){
        //CREATION D'UN PARCOURS TYPE SANS RESTRICTION.
        parcoursRules = new ParcoursRules(new ParcoursType("Test0",null,null));

        /* INITIALISATION DU PARCOURS A VERIFIER */
        HashMap<String,Integer> hashMaps1 = new HashMap<String, Integer>();
        hashMaps1.put("ECO",1);
        hashMaps1.put("INFO",1);
        hashMaps1.put("MATH",1);

        HashMap<String,Integer> hashMaps2 = new HashMap<String, Integer>();
        hashMaps2.put("INFO",2);
        hashMaps2.put("ECO",1);

        HashMap<String,Integer> hashMaps3 = new HashMap<String, Integer>();
        hashMaps3.put("MATH",2);
        hashMaps3.put("INFO",2);

        HashMap<String,Integer> hashMaps4 = new HashMap<String, Integer>();
        hashMaps4.put("INFO",1);

        listHashmap = new ArrayList<HashMap<String, Integer>>();
        listHashmap.add(hashMaps1);
        listHashmap.add(hashMaps2);
        listHashmap.add(hashMaps3);
        listHashmap.add(hashMaps4);

        listSelectUe = new ArrayList<String>();

        listSelectUe.add("Moncode1");
        listSelectUe.add("Moncode3");
        listSelectUe.add("Moncode7");
        listSelectUe.add("Moncode11");
        listSelectUe.add("Moncode2");
        listSelectUe.add("Moncode4");
    }

    @Test
    public void acceptParcoursTest(){
        init1();
        //Le parcours donné est correct.
        assertEquals(true,parcoursRules.acceptParcours(listHashmap,listSelectUe));

        parcoursType_NumberUes.put("INFO",5);
        parcoursRules = new ParcoursRules(new ParcoursType("TEST",parcoursType_NumberUes,parcoursType_ObligatoryUe));
        //On retire des conditions, le parcours reste correct
        assertEquals(true,parcoursRules.acceptParcours(listHashmap,listSelectUe));

        parcoursType_ObligatoryUe.remove("Moncode1");
        parcoursRules = new ParcoursRules(new ParcoursType("TEST",parcoursType_NumberUes,parcoursType_ObligatoryUe));
        //On retire des conditions, le parcours reste correct
        assertEquals(true,parcoursRules.acceptParcours(listHashmap,listSelectUe));

        //On retire des ue d'info.
        listHashmap.get(1).put("INFO",1);
        listHashmap.get(2).put("INFO",1);
        //Les conditions ne sont plus respectée.
        assertEquals(false,parcoursRules.acceptParcours(listHashmap,listSelectUe));

        //RAZ
        init1();
        listSelectUe.remove("Moncode1");
        //Les conditions ne sont plus respectée.
        assertEquals(false,parcoursRules.acceptParcours(listHashmap,listSelectUe));


        //Parcours type sans restriction
        init2();
        //Le parcours est accepte tout le temps.
        assertEquals(true,parcoursRules.acceptParcours(listHashmap,listSelectUe));

        listHashmap.get(0).put("INFO",0);
        listHashmap.get(2).put("INFO",0);
        listSelectUe.remove(2);
        listSelectUe.remove(3);

        //Le parcours est accepte tout le temps.
        assertEquals(true,parcoursRules.acceptParcours(listHashmap,listSelectUe));
    }
}
