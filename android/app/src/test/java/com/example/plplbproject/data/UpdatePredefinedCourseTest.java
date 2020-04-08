package com.example.plplbproject.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;

import metier.parcours.ParcoursType;

public class UpdatePredefinedCourseTest {

    UpdatePredefinedCourse updatePredefinedCourse = new UpdatePredefinedCourse();
    ArrayList<ParcoursType> parcoursTypeList;
    ArrayList<String> toSend;
    Gson gson = new GsonBuilder().create();

    @Before
    public void init(){
        ArrayList<ParcoursType> listParcoursType = new ArrayList<ParcoursType>();
        ArrayList<String> parcoursTypesName = new ArrayList<String>();

        //PARCOURS INFO
        HashMap<String,Integer> numberUeInfo = new HashMap<String,Integer>();
        numberUeInfo.put("INFORMATIQUE",6);
        numberUeInfo.put("MATHEMATIQUES",2);

        listParcoursType.add(new ParcoursType("Parcours Informatique",numberUeInfo,null));

        //PARCOURS MATHEMATIQUES
        ArrayList<String> obligatoryUeMath = new ArrayList<String>();
        obligatoryUeMath.add("SPUM11");
        obligatoryUeMath.add("SPUM13");

        obligatoryUeMath.add("SPUM21");
        obligatoryUeMath.add("SPUM23");

        obligatoryUeMath.add("SPUM30");
        obligatoryUeMath.add("SPUM32");
        obligatoryUeMath.add("SPUM31");

        obligatoryUeMath.add("SPUM40");
        obligatoryUeMath.add("SPUM42");
        obligatoryUeMath.add("SPUM41");
        obligatoryUeMath.add("SPUM43");

        listParcoursType.add(new ParcoursType("Parcours Mathématiques",null,obligatoryUeMath));

        //PARCOURS MAJEUR MATHEMATIQUES
        ArrayList<String> obligatoryUeMathMaj = new ArrayList<String>();
        obligatoryUeMathMaj.add("SPUM11");
        obligatoryUeMathMaj.add("SPUM13");

        obligatoryUeMathMaj.add("SPUM21");
        obligatoryUeMathMaj.add("SPUM23");

        obligatoryUeMathMaj.add("SPUM30");
        obligatoryUeMathMaj.add("SPUM32");
        obligatoryUeMathMaj.add("SPUM31");

        obligatoryUeMathMaj.add("SPUM40");
        obligatoryUeMathMaj.add("SPUM42");
        obligatoryUeMathMaj.add("SPUM41");
        //obligatoryUeMathMaj.add("SPUM43");

        listParcoursType.add(new ParcoursType("Parcours Majeur Mathématiques",null,obligatoryUeMathMaj));

        //PARCOURS ELECTRONIQUE
        ArrayList<String> obligatoryUeElect = new ArrayList<String>();
        obligatoryUeElect.add("SPUE10");
        obligatoryUeElect.add("SPUM12");

        obligatoryUeElect.add("SPUE21");
        obligatoryUeElect.add("SPUM22");

        obligatoryUeElect.add("SPUM33");
        obligatoryUeElect.add("SPUE31");
        obligatoryUeElect.add("SPUE32");
        obligatoryUeElect.add("SPUE30");

        obligatoryUeElect.add("SPUM44");
        obligatoryUeElect.add("SPUE42");
        obligatoryUeElect.add("SPUE41");
        obligatoryUeElect.add("SPUE40");

        listParcoursType.add(new ParcoursType("Parcours Electronique",null,obligatoryUeElect));

        //PARCOURS LIBRE
        listParcoursType.add(new ParcoursType("Parcours Libre",null,null));


        parcoursTypeList = listParcoursType;
        toSend = new ArrayList<String>();

        for(ParcoursType parcoursType : listParcoursType){
            toSend.add(gson.toJson(parcoursType));
        }

        //On remet a zero le singleton.
        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(null);

    }

    @Test
    public void UpdatePredefinedCourseTest(){

        //On n'a encore rien reçu la liste est donc vide.
        assertEquals(false,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());

        //On envoie des données
        updatePredefinedCourse.call(gson.toJson(toSend));

        //On a recu quelque chose
        assertEquals(true,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());

        //On recupere ce que l'on a
        ArrayList<ParcoursType> data = DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList();

        //On va tout parcourir
        for(int i = 0; i < parcoursTypeList.size();i++){
            ParcoursType parcoursType1 = parcoursTypeList.get(i);
            ParcoursType parcoursType2 = parcoursTypeList.get(i);

            //Egalité sur les noms
            assertEquals(parcoursType1.getName(),parcoursType2.getName());

            //Egalité sur leurs hashmap
            if(parcoursType1.getNumberUes() != null && parcoursType2.getNumberUes() != null){
                assertEquals(parcoursType1.getNumberUes().keySet(),parcoursType2.getNumberUes().keySet());
                assertEquals(parcoursType1.getNumberUes().values(),parcoursType2.getNumberUes().values());
            }

            if(parcoursType1.getObligatoryUes() != null && parcoursType2.getObligatoryUes() != null) {
                for(int j = 0; j < parcoursType1.getObligatoryUes().size();j++){
                    //Egalité sur les code ue
                    String code1 = parcoursType1.getObligatoryUes().get(j);
                    String code2 = parcoursType2.getObligatoryUes().get(j);
                    assertEquals(code1, code2);
                }
            }
        }
    }
}
