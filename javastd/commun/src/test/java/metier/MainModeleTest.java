package metier;

import metier.semestre.Semestre;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MainModeleTest {

    MainModele mainModele;

    @BeforeEach
    public void init(){
        ArrayList<Categorie> listCat1 = new ArrayList<Categorie>();
        Categorie cat;
        ArrayList<UE> ueList1;
        String codeUE;
        //on cree un semestre avec i categorie et j ue pour chacun avec un codeUE unique pour chaque ue
        for(int i = 0; i<5; i++){
            ueList1 = new ArrayList<UE>();
            for(int j = 0; j<100; j++){
                codeUE = ""+j+(i*100);
                ueList1.add(new UE(""+j,codeUE));
            }
            cat = new Categorie(""+i,ueList1);
            listCat1.add(cat);
        }
        Semestre semestre1 = new Semestre(1,listCat1,null);

        ArrayList<Categorie> listCat2 = new ArrayList<Categorie>();
        ArrayList<UE> ueList2;
        //on cree un semestre avec i categorie et j ue pour chacun avec un codeUE unique pour chaque ue
        for(int i = 0; i<5; i++){
            ueList2 = new ArrayList<UE>();
            for(int j = 0; j<100; j++){
                codeUE = ""+j+(i*200);
                ueList2.add(new UE(""+j,codeUE));
            }
            cat = new Categorie(""+i,ueList2);
            listCat2.add(cat);
        }
        Semestre semestre2 = new Semestre(2,listCat2,null);

        mainModele = new MainModele();
        mainModele.addSemestre(semestre1);
        mainModele.addSemestre(semestre2);
    }

    @Test
    public void findUETest(){
        UE ue;
        String codeUE;
        //On profite que les deux semestre aient les meme categories.
        for(int i = 0; i<5; i++) {
            //PREMIER SEMESTRE
            for (int j = 0; j < 100; j++) {
                codeUE = ""+j+(i*100);

                //tout les ue doivent etre trouver
                ue = mainModele.getSemestres().findUE(codeUE);
                assertEquals(ue != null,true);

                //toutes les ue doivent avoir un code correspondant a celui rechercher:
                assertEquals(ue.getUeCode(),codeUE);
            }
            //DEUXIEME SEMESTRE
            for (int j = 0; j < 100; j++) {
                codeUE = ""+j+(i*200);

                //tout les ue doivent etre trouver
                ue = mainModele.getSemestres().findUE(codeUE);
                assertEquals(ue != null,true);

                //toutes les ue doivent avoir un code correspondant a celui rechercher:
                assertEquals(ue.getUeCode(),codeUE);
            }
        }
    }

}
