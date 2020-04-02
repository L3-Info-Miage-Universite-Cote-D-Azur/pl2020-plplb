package com.example.plplbproject.data;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;

import metier.parcours.ParcoursType;

import static org.junit.Assert.assertEquals;

public class DataPredefinedCourseTest {

    @Before
    public void init() {
        ArrayList<ParcoursType> listParcoursType = new ArrayList<ParcoursType>();

        //PARCOURS INFO
        HashMap<String, Integer> numberUeInfo = new HashMap<String, Integer>();
        numberUeInfo.put("INFORMATIQUE", 6);
        numberUeInfo.put("MATHEMATIQUES", 2);

        listParcoursType.add(new ParcoursType("Parcours Informatique", numberUeInfo, null));

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

        listParcoursType.add(new ParcoursType("Parcours Mathématiques", null, obligatoryUeMath));

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

        listParcoursType.add(new ParcoursType("Parcours Majeur Mathématiques", null, obligatoryUeMathMaj));

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

        listParcoursType.add(new ParcoursType("Parcours Electronique", null, obligatoryUeElect));

        //PARCOURS LIBRE
        listParcoursType.add(new ParcoursType("Parcours Libre", null, null));

        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(listParcoursType);
    }

    @Test
    public void hasPredefinedCourseListTest(){
        //Le test renvoie true car on a reçu des chose avec l'init()
        assertEquals(true,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());

        //On réinitialise
        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(null);

        //On n'a plus de liste
        assertEquals(false,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());
    }

    @Test
    public void getPredefinedCourseNameTest(){
        //Le test renvoie true car on a reçu des chose avec l'init()
        assertEquals(true,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());

        //Ce que l'on attend
        ArrayList<String> expected = new ArrayList<>();
        expected.add("Parcours Informatique");
        expected.add("Parcours Mathématiques");
        expected.add("Parcours Majeur Mathématiques");
        expected.add("Parcours Electronique");
        expected.add("Parcours Libre");

        //Ce que l'on a.
        ArrayList<String> result = DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseName();

        //Les deux liste on la meme taille
        assertEquals(expected.size(),result.size());

        //On parcours les listes
        for(int i=0;i<expected.size();i++){
            //Chaque noms des listes sont les memes.
            assertEquals(expected.get(i),result.get(i));
        }
    }

    @Test
    public void getPredefinedCourseTest(){
        //Le test renvoie true car on a reçu des chose avec l'init()
        assertEquals(true,DataPredefinedCourse.PREDEFINEDCOURSE.hasPredefinedCourseList());

        //On va reconstruire la liste de parcoursType avec getPredefinedCourse
        ArrayList<ParcoursType> tab = new ArrayList<>();
        tab.add(DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse("Parcours Informatique"));
        tab.add(DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse("Parcours Mathématiques"));
        tab.add(DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse("Parcours Majeur Mathématiques"));
        tab.add(DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse("Parcours Electronique"));
        tab.add(DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourse("Parcours Libre"));

        //Le nom des parcours des données
        ArrayList<String> names = DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseName();

        for(ParcoursType p : tab){
            //Les donnees contient le nom du parcours
            assertEquals(true,names.contains(p.getName()));

            //On trouve le parcoursType qui correspond
            for(ParcoursType p2 : DataPredefinedCourse.PREDEFINEDCOURSE.getPredefinedCourseList()){

                //On le trouvera forcement (test d'en haut)
                if(p.getName().equals(p2.getName())){

                    //Si l'hashmap n'est pas null chez les deux
                    if(p.getNumberUes() != null && p2.getNumberUes()!=null){

                        //On verifie l'egalité chez les deux
                        assertEquals(p.getNumberUes().values(),p2.getNumberUes().values());
                        assertEquals(p.getNumberUes().keySet(),p2.getNumberUes().keySet());
                    }
                    else{
                        //Ils sont donc null (les deux)
                        assertEquals(null,p.getNumberUes());
                        assertEquals(null,p2.getNumberUes());
                    }

                    //Si la liste des ues obligatoire n'est pas nulle chez les deux
                    if(p.getObligatoryUes() != null && p2.getObligatoryUes() != null){

                        //On verifie l'égalite de taille
                        assertEquals(p.getObligatoryUes().size(),p2.getObligatoryUes().size());

                        for(int i =0;i<p.getObligatoryUes().size();i++){
                            //Chaque element de la liste sont egaux
                            assertEquals(p.getObligatoryUes().get(i),p2.getObligatoryUes().get(i));
                        }
                    }
                    else{
                        //Ils sont donc tout les deux null
                        assertEquals(null,p.getObligatoryUes());
                        assertEquals(null,p2.getObligatoryUes());
                    }
                }
            }
        }

    }
}
