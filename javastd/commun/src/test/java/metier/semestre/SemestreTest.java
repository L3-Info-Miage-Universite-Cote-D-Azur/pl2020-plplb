package metier.semestre;

import metier.Categorie;
import metier.UE;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestreTest {

    Semestre semestre;

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
}
