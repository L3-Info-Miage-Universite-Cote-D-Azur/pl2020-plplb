package metier.semestre;

import metier.Categorie;
import metier.UE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestreTest {

    Semestre semestre;
    ArrayList<Categorie> categoriesList;

    @BeforeEach
    public void init(){
        ArrayList<Categorie> listCat = new ArrayList<Categorie>();
        Categorie cat;
        UE ue;
        ArrayList<UE> ueList;
        String codeUE;
        //on cree un semestre avec i categorie et j ue pour chacun avec un codeUE unique pour chaque ue
        for(int i = 0; i<5; i++){
            ueList = new ArrayList<UE>();
            for(int j = 0; j<100; j++){
                codeUE = ""+j+(i*100);
                ueList.add(new UE(""+j,codeUE));
            }
            cat = new Categorie(""+i,ueList);
            listCat.add(cat);
        }
        categoriesList = listCat;
        semestre = new Semestre(1,listCat,null);
    }

    @Test
    public void findUETest(){
        UE ue;
        String codeUE;
        for(int i = 0; i<5; i++) {
            for (int j = 0; j < 100; j++) {
                codeUE = ""+j+(i*100);

                //tout les ue doivent etre trouver
                ue = semestre.findUE(codeUE);
                assertEquals(ue != null,true);

                //toutes les ue doivent avoir un code correspondant a celui rechercher:
                assertEquals(ue.getUeCode(),codeUE);
            }
        }
    }

    @Test
    public void setListCategorieTest(){
        //Creation de base
        semestre = new Semestre(1,categoriesList,null);

        //Tout les numero de semestre des ue est 1.
        for(Categorie c : semestre.getListCategorie()){
            for(UE ue : c.getListUE()){
                assertEquals(1,ue.getSemestreNumber());
            }
        }

        //test de setListCategorie en changeant le numero de semestre a chaque fois.
        for(int i = 2; i<200;i++){
            semestre.setNumber(i);
            semestre.setListCategorie(categoriesList);

            //Tout les numeros de semestre des ues doivent changer.
            for(Categorie c : semestre.getListCategorie()){
                for(UE ue : c.getListUE()){
                    assertEquals(i,ue.getSemestreNumber());
                }
            }
        }
    }

    @Test
    public void getJsonTest(){

        //-------CREATION D'UNE LISTE DE CATEGORIE MOINS GRANDE ---------------
        ArrayList<Categorie> listCat = new ArrayList<Categorie>();
        Categorie cat;
        UE ue;
        ArrayList<UE> ueList;
        String codeUE;

        //on cree un semestre avec i categorie et j ue pour chacun avec un codeUE unique pour chaque ue
        for(int i = 0; i<5; i++){
            ueList = new ArrayList<UE>();
            for(int j = 0; j<5; j++){
                codeUE = ""+j+i;
                ueList.add(new UE(""+j,codeUE));
            }
            cat = new Categorie(""+i,ueList);
            listCat.add(cat);
        }

        //On set la nouvelle liste
        semestre.setListCategorie(listCat);

        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"0\",\"listUE\":[{\"name\":\"0\",\"code\":\"00" +
                "\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"1\",\"code\":\"10\",\"semestreNumber\":1,\"categorie" +
                "\":\"0\"},{\"name\":\"2\",\"code\":\"20\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"3\",\"code\":" +
                "\"30\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"4\",\"code\":\"40\",\"semestreNumber\":1," +
                "\"categorie\":\"0\"}]},{\"name\":\"1\",\"listUE\":[{\"name\":\"0\",\"code\":\"01\",\"semestreNumber\":1," +
                "\"categorie\":\"1\"},{\"name\":\"1\",\"code\":\"11\",\"semestreNumber\":1,\"categorie\":\"1\"},{\"name\":" +
                "\"2\",\"code\":\"21\",\"semestreNumber\":1,\"categorie\":\"1\"},{\"name\":\"3\",\"code\":\"31\",\"semestreNumber" +
                "\":1,\"categorie\":\"1\"},{\"name\":\"4\",\"code\":\"41\",\"semestreNumber\":1,\"categorie\":\"1\"}]},{" +
                "\"name\":\"2\",\"listUE\":[{\"name\":\"0\",\"code\":\"02\",\"semestreNumber\":1,\"categorie\":\"2\"},{\"name" +
                "\":\"1\",\"code\":\"12\",\"semestreNumber\":1,\"categorie\":\"2\"},{\"name\":\"2\",\"code\":\"22\"," +
                "\"semestreNumber\":1,\"categorie\":\"2\"},{\"name\":\"3\",\"code\":\"32\",\"semestreNumber\":1,\"categorie" +
                "\":\"2\"},{\"name\":\"4\",\"code\":\"42\",\"semestreNumber\":1,\"categorie\":\"2\"}]},{\"name\":\"3\"," +
                "\"listUE\":[{\"name\":\"0\",\"code\":\"03\",\"semestreNumber\":1,\"categorie\":\"3\"},{\"name\":\"1\"," +
                "\"code\":\"13\",\"semestreNumber\":1,\"categorie\":\"3\"},{\"name\":\"2\",\"code\":\"23\",\"semestreNumber" +
                "\":1,\"categorie\":\"3\"},{\"name\":\"3\",\"code\":\"33\",\"semestreNumber\":1,\"categorie\":\"3\"},{" +
                "\"name\":\"4\",\"code\":\"43\",\"semestreNumber\":1,\"categorie\":\"3\"}]},{\"name\":\"4\",\"listUE\":[{" +
                "\"name\":\"0\",\"code\":\"04\",\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"1\",\"code\":\"14\"," +
                "\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"2\",\"code\":\"24\",\"semestreNumber\":1,\"categorie" +
                "\":\"4\"},{\"name\":\"3\",\"code\":\"34\",\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"4\",\"code" +
                "\":\"44\",\"semestreNumber\":1,\"categorie\":\"4\"}]}]}";

        String result = semestre.getJson();

        //On obtient le json de la classe.
        assertEquals(expected,result);

        String expected2 = "{\"number\":99,\"listCategorie\":[{\"name\":\"0\",\"listUE\":[{\"name\":\"0\",\"code\":\"00" +
                "\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"1\",\"code\":\"10\",\"semestreNumber\":1,\"categorie" +
                "\":\"0\"},{\"name\":\"2\",\"code\":\"20\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"3\",\"code\":" +
                "\"30\",\"semestreNumber\":1,\"categorie\":\"0\"},{\"name\":\"4\",\"code\":\"40\",\"semestreNumber\":1," +
                "\"categorie\":\"0\"}]},{\"name\":\"1\",\"listUE\":[{\"name\":\"0\",\"code\":\"01\",\"semestreNumber\":1," +
                "\"categorie\":\"1\"},{\"name\":\"1\",\"code\":\"11\",\"semestreNumber\":1,\"categorie\":\"1\"},{\"name\":" +
                "\"2\",\"code\":\"21\",\"semestreNumber\":1,\"categorie\":\"1\"},{\"name\":\"3\",\"code\":\"31\",\"semestreNumber" +
                "\":1,\"categorie\":\"1\"},{\"name\":\"4\",\"code\":\"41\",\"semestreNumber\":1,\"categorie\":\"1\"}]},{" +
                "\"name\":\"2\",\"listUE\":[{\"name\":\"0\",\"code\":\"02\",\"semestreNumber\":1,\"categorie\":\"2\"},{\"name" +
                "\":\"1\",\"code\":\"12\",\"semestreNumber\":1,\"categorie\":\"2\"},{\"name\":\"2\",\"code\":\"22\"," +
                "\"semestreNumber\":1,\"categorie\":\"2\"},{\"name\":\"3\",\"code\":\"32\",\"semestreNumber\":1,\"categorie" +
                "\":\"2\"},{\"name\":\"4\",\"code\":\"42\",\"semestreNumber\":1,\"categorie\":\"2\"}]},{\"name\":\"3\"," +
                "\"listUE\":[{\"name\":\"0\",\"code\":\"03\",\"semestreNumber\":1,\"categorie\":\"3\"},{\"name\":\"1\"," +
                "\"code\":\"13\",\"semestreNumber\":1,\"categorie\":\"3\"},{\"name\":\"2\",\"code\":\"23\",\"semestreNumber" +
                "\":1,\"categorie\":\"3\"},{\"name\":\"3\",\"code\":\"33\",\"semestreNumber\":1,\"categorie\":\"3\"},{" +
                "\"name\":\"4\",\"code\":\"43\",\"semestreNumber\":1,\"categorie\":\"3\"}]},{\"name\":\"4\",\"listUE\":[{" +
                "\"name\":\"0\",\"code\":\"04\",\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"1\",\"code\":\"14\"," +
                "\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"2\",\"code\":\"24\",\"semestreNumber\":1,\"categorie" +
                "\":\"4\"},{\"name\":\"3\",\"code\":\"34\",\"semestreNumber\":1,\"categorie\":\"4\"},{\"name\":\"4\",\"code" +
                "\":\"44\",\"semestreNumber\":1,\"categorie\":\"4\"}]}]}";

        //Si on change le number du semestre
        semestre.setNumber(99);

        String result2 = semestre.getJson();
        //Le numero change bien dans le json
        assertEquals(expected2,result2);

        //On remet le numero a 1
        semestre.setNumber(1);
        //Si la liste de categorie est nulle
        semestre.setListCategorie(null);
        assertEquals("{\"number\":1}",semestre.getJson());

        //Si on change le numero le changement est pris en compte;
        semestre.setNumber(99);
        assertEquals("{\"number\":99}",semestre.getJson());
    }
}
