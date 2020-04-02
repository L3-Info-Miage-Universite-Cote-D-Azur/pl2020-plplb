package metier.parcours;

import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;
import metier.semestre.SemestreManager;
import metier.semestre.SemestreRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoSettings;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParcoursTest {

    Parcours parcours;

    @Mock
    ArrayList<SemestreManager> semestresManager = Mockito.mock(ArrayList.class);
    @Mock
    SemestreManager manager = Mockito.mock(SemestreManager.class);
    @Mock
    ParcoursType parcoursType = Mockito.mock(ParcoursType.class);
    @Mock
    ParcoursRules parcoursRules = Mockito.mock(ParcoursRules.class);
    @Mock
    SemesterList semesterList = Mockito.mock(SemesterList.class);
    @Mock
    Semestre semestre = Mockito.mock(Semestre.class);

    HashMap<String, UE> parcoursSelect = new HashMap<String, UE>();


    @BeforeEach
    public void init(){
        parcours = new Parcours(parcoursRules,"testName",semesterList,semestresManager,parcoursSelect);
    }

    @Test
    public void checkUENoVerifTest(){
        UE ue = new UE("test","testCode");
        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);

        parcours.checkUENoVerif(ue);
        Mockito.verify(semestresManager).get(anyInt());//La fonction est bien appelee.
        Mockito.verify(manager).check(ue);
        assertEquals(parcoursSelect.get(ue.getUeCode()),ue);//L'ue est bien ajoutee
    }

    @Test
    public void verifiParcoursTest(){

        ArrayList<HashMap<String,Integer>> hashList = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();

        Mockito.when(semestresManager.size()).thenReturn(10);
        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);
        Mockito.when(manager.getCountByCategory()).thenReturn(new HashMap<>());

        //verifCompleteParcours renvoie false.
        Mockito.when(parcoursRules.acceptParcours(any(),any())).thenReturn(false);
        assertEquals(false,parcours.verifiParcours());

        //verifCompleteParcours renvoie true.
        Mockito.when(parcoursRules.acceptParcours(any(),any())).thenReturn(true);
        Mockito.when(semestresManager.get(anyInt()).verifCompleteParcours()).thenReturn(true);
        assertEquals(true,parcours.verifiParcours());

        Mockito.when(semestresManager.get(anyInt()).verifCompleteParcours()).thenReturn(true);
        assertEquals(true,parcours.verifiParcours());

        Mockito.when(semestresManager.get(anyInt()).verifCompleteParcours()).thenReturn(false);
        assertEquals(false,parcours.verifiParcours());


    }

    @Test
    public void createListCodeUETest(){
        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");
        UE ue2 = new UE("test2","testCode2");
        UE ue3 = new UE("test3","testCode3");

        parcoursSelect.put(ue0.getUeCode(),ue0);
        parcoursSelect.put(ue1.getUeCode(),ue1);
        parcoursSelect.put(ue2.getUeCode(),ue2);
        parcoursSelect.put(ue3.getUeCode(),ue3);

        ArrayList<String> res = parcours.createListCodeUE();
        for(String s : res){
            //Les ues de res sont bien dans parcoursSelect
            assertEquals(true,parcoursSelect.get(s) != null);
            //les codes sont bien ceux des ues.
            assertEquals(parcoursSelect.get(s).getUeCode(),s);
        }
    }

    /**
     * Can be UncheckedUE vérifie si l'ue peut être décochée.
     * Pour notre test, nous avons deux ues cochées, et une d'entre elles est obligatoire (donc
     * ne peut être décochée)
     */

    @Test
    public void isCheckedTest(){
        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");
        UE ue2 = new UE("test2","testCode2");
        UE ue3 = new UE("test3","testCode3");

        UE ue4 = new UE("test3","testCode4");
        UE ue5 = new UE("test3","testCode5");

        parcoursSelect.put(ue0.getUeCode(),ue0);
        parcoursSelect.put(ue1.getUeCode(),ue1);
        parcoursSelect.put(ue2.getUeCode(),ue2);
        parcoursSelect.put(ue3.getUeCode(),ue3);

        //Les ues sont dans la hashmap
        assertEquals(true,parcours.isChecked(ue0));
        assertEquals(true,parcours.isChecked(ue1));
        assertEquals(true,parcours.isChecked(ue2));
        assertEquals(true,parcours.isChecked(ue3));

        //Les ues ne sont pas dans parcoursSelect
        assertEquals(false,parcours.isChecked(ue4));
        assertEquals(false,parcours.isChecked(ue5));
    }

    @Test
    public void canBeUncheckedUETest(){

        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);


        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");

        UE ue2 = new UE("test2","testCode2");
        UE ue3 = new UE("test3","testCode3");

        parcoursSelect.put(ue0.getUeCode(),ue0);
        parcoursSelect.put(ue1.getUeCode(),ue1);

        //Le semestre manager renvoie true.
        Mockito.when(manager.canBeUncheck(any(UE.class))).thenReturn(true);
        Mockito.when(parcoursRules.getParcoursType()).thenReturn(parcoursType);

        ArrayList<String> ueArrayList = new ArrayList<>();
        ueArrayList.add("testCode0");

        Mockito.when(parcoursType.getObligatoryUes()).thenReturn(ueArrayList);


        assertEquals(false,parcours.canBeUncheckedUE(ue0));
        assertEquals(true,parcours.canBeUncheckedUE(ue1));

        //Le semestre manager renvoie false.
        Mockito.when(manager.canBeUncheck(any(UE.class))).thenReturn(false);
        assertEquals(false,parcours.canBeUncheckedUE(ue0));
        assertEquals(false,parcours.canBeUncheckedUE(ue1));

        //Les ues ne sont pas check
        assertEquals(false,parcours.canBeUncheckedUE(ue2));
        assertEquals(false,parcours.canBeUncheckedUE(ue3));

    }

    /**
     * canBeCheckedUe vérifie s'il est possible de cocher l'ue
     */

    @Test
    public void canBeCheckedUETest(){
        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);


        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");

        UE ue2 = new UE("test2","testCode2");
        UE ue3 = new UE("test3","testCode3");

        parcoursSelect.put(ue0.getUeCode(),ue0);
        parcoursSelect.put(ue1.getUeCode(),ue1);

        //Le semestre manager renvoie true.
        Mockito.when(manager.canBeCheck(any(UE.class))).thenReturn(true);

        //Les ues sont check
        assertEquals(false,parcours.canBeCheckedUE(ue0));
        assertEquals(false,parcours.canBeCheckedUE(ue1));

        //Les ues ne sont pas check
        assertEquals(true,parcours.canBeCheckedUE(ue2));
        assertEquals(true,parcours.canBeCheckedUE(ue3));


        //Le semestre manager renvoie false.
        Mockito.when(manager.canBeCheck(any(UE.class))).thenReturn(false);
        assertEquals(false,parcours.canBeCheckedUE(ue0));
        assertEquals(false,parcours.canBeCheckedUE(ue1));

        //Les ues ne sont pas check
        assertEquals(false,parcours.canBeCheckedUE(ue2));
        assertEquals(false,parcours.canBeCheckedUE(ue3));

    }

    /**
     * Décoche l'ue selectionnée
     */

    @Test
    public void uncheckUETest(){


        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");

        UE ue2 = new UE("test0","testCode0");
        UE ue3 = new UE("test1","testCode1");

        parcoursSelect.put(ue0.getUeCode(),ue0);
        parcoursSelect.put(ue1.getUeCode(),ue1);

        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);
        Mockito.when(parcoursRules.getParcoursType()).thenReturn(parcoursType);

        //canBeUncheck retourne true;
        Mockito.when(manager.canBeUncheck(any(UE.class))).thenReturn(true);

        parcours.uncheckUE(ue0);
        assertEquals(null,parcoursSelect.get(ue0));
        // inutile ? Mockito.verify(semestresManager,times(2)).get(anyInt());//La fonction est bien appelee.
        Mockito.verify(manager).uncheck(ue0);

        parcours.uncheckUE(ue1);
        assertEquals(null,parcoursSelect.get(ue1));
        Mockito.verify(semestresManager,times(4)).get(anyInt());//La fonction est bien appelee.
        Mockito.verify(manager).uncheck(ue1);

        parcours.uncheckUE(ue2);
        parcours.uncheckUE(ue3);
        //Rien n'est retiré et rien n'est appelé car absent de l'hashmap
        //Mockito.verify(semestresManager,times(4)).get(anyInt());
        Mockito.verify(manager,times(0)).uncheck(ue2);
        Mockito.verify(manager,times(0)).uncheck(ue3);

    }

    @Test
    public void checkUETest(){
        Mockito.when(semestresManager.get(anyInt())).thenReturn(manager);
        UE ue0 = new UE("test0","testCode0");
        UE ue1 = new UE("test1","testCode1");

        //Renvoie vrai
        Mockito.when(manager.canBeCheck(any(UE.class))).thenReturn(true);

        parcours.checkUE(ue0);
        Mockito.verify(semestresManager,times(2)).get(anyInt());//La fonction est bien appelee.
        Mockito.verify(manager).check(ue0);
        Mockito.verify(manager).canBeCheck(ue0);
        assertEquals(parcoursSelect.get(ue0.getUeCode()),ue0);//L'ue est bien ajoutee

        parcours.checkUE(ue1);
        Mockito.verify(semestresManager,times(4)).get(anyInt());//La fonction est bien appelee.
        Mockito.verify(manager).check(ue1);
        Mockito.verify(manager).canBeCheck(ue1);
        assertEquals(parcoursSelect.get(ue1.getUeCode()),ue1);//L'ue est bien ajoutee

        //Renvoie faux rien ne doit plus bouger
        Mockito.when(manager.canBeCheck(any(UE.class))).thenReturn(false);

        UE ue2 = new UE("test2","testCode2");
        UE ue3 = new UE("test3","testCode3");
        parcours.checkUE(ue2);
        parcours.checkUE(ue3);

        //Le deux appels de canBeCheckUe
        Mockito.verify(semestresManager,times(6)).get(anyInt());
        Mockito.verify(manager,times(0)).check(ue2);
        Mockito.verify(manager).canBeCheck(ue2);
        Mockito.verify(manager,times(0)).check(ue3);
        Mockito.verify(manager).canBeCheck(ue3);
    }

    /**
     * Apparement pas à tester
     */
    @Test
    public void updateSemestreTest() {


    }

    /**
     * Quel est l'interêt de tester cette fonction?
     */
    
    @Test
    void initParcoursSemestresManagerTest() {

        Mockito.when(semesterList.size()).thenReturn(1);
        Mockito.when(semestre.getRules()).thenReturn(new SemestreRules(-1,-1,null));
        Mockito.when(semesterList.get(anyInt())).thenReturn(semestre);

        parcours.initParcoursSemestresManager();

        System.out.println(semestre.getRules());
        Mockito.verify(semestre,times(2)).getRules();
        
        assertNotNull(semestre.getRules());
    }

}
