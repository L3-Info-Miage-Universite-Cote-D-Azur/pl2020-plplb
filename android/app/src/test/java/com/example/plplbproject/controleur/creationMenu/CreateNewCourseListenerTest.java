package com.example.plplbproject.controleur.creationMenu;

import android.view.View;

import com.example.plplbproject.Vue.creationMenu.CreationMenuActivity;
import com.example.plplbproject.data.DataPredefinedCourse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;

import metier.parcours.ParcoursType;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

public class CreateNewCourseListenerTest {
    @Mock
    CreationMenuActivity vue = Mockito.mock(CreationMenuActivity.class);
    @Mock
    CreationMenuModele modele = Mockito.mock(CreationMenuModele.class);
    @Mock
    View view = Mockito.mock(View.class);

    CreateNewCourseListener createNewCourseListener;

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

        //PARCOURS TEST
        HashMap<String, Integer> hashMap1 = new HashMap<String, Integer>();
        hashMap1.put("GEOGRAPHIE", 7);
        hashMap1.put("ECONOMIE ET GESTION", 4);

        listParcoursType.add(new ParcoursType("ParcoursTest",hashMap1,null));

        //PARCOURS TEST2
        HashMap<String, Integer> hashMap2 = new HashMap<String, Integer>();
        hashMap2.put("SCIENCES ET VIE DE LA TERRE", 2);
        hashMap2.put("CHIMIE", 10);

        listParcoursType.add(new ParcoursType("ParcoursTest2",hashMap2,null));

        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(listParcoursType);

        //---------- creation de la classe ----------
        createNewCourseListener = new CreateNewCourseListener(vue,modele);
    }

    @Test
    public void onClickTest(){
        //Mise en place des mocks

        Mockito.when(modele.getPredefinedCourseName()).thenReturn("unParcoursPredef");
        Mockito.when(vue.getCourseName()).thenReturn("UnNomDeParcours");

        //Jamais appeler avant
        Mockito.verify(modele,never()).getPredefinedCourseName();
        Mockito.verify(modele,never()).setCourseName(anyString());
        Mockito.verify(vue,never()).switchIntent();
        Mockito.verify(vue,never()).setTextError("Veuillez selectionner une parcours prédéfini.");
        Mockito.verify(vue,never()).setTextError("Ce nom de parcours existe déjà");

        //On retourne faux.
        Mockito.when(modele.canBeChooseName(anyString())).thenReturn(false);

        //On clique
        createNewCourseListener.onClick(view);

        //On verifie que les fonctions sont appelées.
        Mockito.verify(vue,times(1)).setTextError("Ce nom de parcours existe déjà");
        //Le reste n'as pas bouger
        Mockito.verify(vue,never()).setTextError("Veuillez selectionner une parcours prédéfini.");
        Mockito.verify(modele,never()).getPredefinedCourseName();
        Mockito.verify(modele,never()).setCourseName(anyString());
        Mockito.verify(vue,never()).switchIntent();

        //On retourne vrai
        Mockito.when(modele.canBeChooseName(anyString())).thenReturn(true);
        //On retourne faux
        Mockito.when(modele.isSelectedPredefinedCourse()).thenReturn(false);

        //On clique
        createNewCourseListener.onClick(view);

        //On verifie que les fonctions sont appelées.
        Mockito.verify(vue,times(1)).setTextError("Veuillez selectionner une parcours prédéfini.");
        //Le reste n'as pas bouger
        Mockito.verify(vue,times(1)).setTextError("Ce nom de parcours existe déjà");
        Mockito.verify(modele,never()).getPredefinedCourseName();
        Mockito.verify(modele,never()).setCourseName(anyString());
        Mockito.verify(vue,never()).switchIntent();

        /* NE MARCHE PAS .. PLANTAGE SUR :
        //View promptsView = li.inflate(R.layout.prompt_name, null);

        //On retourne vrai.
        Mockito.when(modele.canBeChooseName(anyString())).thenReturn(true);
        Mockito.when(modele.isSelectedPredefinedCourse()).thenReturn(true);
        //On clique
        createNewCourseListener.onClick(view);

         */
    }

    @Test
    public void dialogMessageBuilderTest(){
        //Certain éléments sont constant
        String first = "";
        String second = "      Pour valider votre parcours, vous devrez";
        String third = "      obligatoirement cocher les Ues ci-dessous:";
        String last  = "";

        //Mise en place de ce qui est attendu
        ArrayList<String> expected1 = new ArrayList<>();
        expected1.add("         - 2 Ues de la catégorie MATHEMATIQUES");
        expected1.add("         - 6 Ues de la catégorie INFORMATIQUE");

        ArrayList<String> expected2 = new ArrayList<>();
        ArrayList<String> expected3 = new ArrayList<>();
        ArrayList<String> expected4 = new ArrayList<>();
        ArrayList<String> expected5 = new ArrayList<>();

        ArrayList<String> expected6 = new ArrayList<>();
        expected6.add("         - 7 Ues de la catégorie GEOGRAPHIE");
        expected6.add("         - 4 Ues de la catégorie ECONOMIE ET GESTION");

        ArrayList<String> expected7 = new ArrayList<>();
        expected7.add("         - 2 Ues de la catégorie SCIENCES ET VIE DE LA TERRE");
        expected7.add("         - 10 Ues de la catégorie CHIMIE");


        //La liste des tests
        HashMap<String,ArrayList<String>> testList = new HashMap<>();
        testList.put("Parcours Informatique",expected1);
        testList.put("Parcours Mathématiques",expected2);
        testList.put("Parcours Majeur Mathématiques",expected3);
        testList.put("Parcours Electronique",expected4);
        testList.put("Parcours Libre",expected5);
        testList.put("ParcoursTest",expected6);
        testList.put("ParcoursTest2",expected7);


        //Variables de la boucle
        ArrayList<String> result;
        ArrayList<String> expected;

        //Pour chaque parcours a tester
        for(String parcours : testList.keySet()) {
            //On recupere expectation et resultat
            result = createNewCourseListener.dialogMessageBuilder(parcours);
            expected = testList.get(parcours);

            //Ils doivent avoir la meme taille
            //On ajout 4 car les 4 éléments constants ne sont pas dans expected
            assertEquals(expected.size()+4, result.size());

            //Les 3 premiers sont constant
            assertEquals(first,result.get(0));
            assertEquals(second,result.get(1));
            assertEquals(third,result.get(2));

            //Du 4e au avant dernier élément.
            for (int i = 3; i < result.size() - 1; i++) {
                //On regarde chaque élément du message.
                assertEquals(expected.get(i-3), result.get(i));
            }

            //Le dernier est constant
            assertEquals(last,result.get(result.size()-1));
        }
    }
}
