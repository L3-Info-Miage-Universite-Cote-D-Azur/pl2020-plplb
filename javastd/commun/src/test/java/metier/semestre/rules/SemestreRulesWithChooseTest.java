package metier.semestre.rules;

import metier.UE;
import metier.semestre.manager.BasicSemestreManager;
import metier.semestre.manager.SemestreChooseManager;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.internal.verification.Times;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SemestreRulesWithChooseTest {

    @Spy
    SemestreRulesWithChoose rules;

    @Mock
    SemestreChooseManager manager;

    @Test
    public void canBeCheckTest(){
        ArrayList<String> choose = new ArrayList<String>();
        choose.add("0");
        choose.add("1");
        choose.add("2");
        rules = Mockito.spy(new SemestreRulesWithChoose(0, 0, new ArrayList<>(),choose,2));
        for(int i = 0; i<4; i++) {
            manager = Mockito.mock(SemestreChooseManager.class);
            when(manager.getUeLibreSelected()).thenReturn(0);
            when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
            when(manager.getChooseUeSelected()).thenReturn(0);
            UE ue = new UE("name", "" + i);
            //c'est une ue a choix des place lui son resevé
            if (i <= 2) {
                assertEquals(rules.canBeCheck(ue, manager), true);
                verify(rules,never()).superCanBeCheck(any(),any()); //on appelle pas la fonction mere
            }
            //ce n'est pas une ue a choix aucune lui son resevé on regarde comme pour une ue normal (mais il a pas de place (0))
            else {
                assertEquals(rules.canBeCheck(ue, manager), false);
                verify(rules,times(1)).superCanBeCheck(any(),any()); //on appelle pas la fonction mere mais il a pas de place d'ue normale donc on peut pas l'ajouté
            }
        }

        //TEST on a le nombre requis d'ue a choix il faut regarder comme pour une UE normale
        rules = Mockito.spy(new SemestreRulesWithChoose(1, 10, new ArrayList<>(),choose,2));
        manager = Mockito.mock(SemestreChooseManager.class);
        when(manager.getUeLibreSelected()).thenReturn(0);
        when(manager.getCountByCategory()).thenReturn(new HashMap<String, Integer>());
        when(manager.getChooseUeSelected()).thenReturn(1); //1 place pour une ue a choix

        // il n'y a pas de place normale mais c'est une ue a choix elle est accepter car il manque 1 ue a choix
        assertEquals(rules.canBeCheck(new UE("name","1"), manager), true);
        verify(rules,never()).superCanBeCheck(any(),any()); //ue a choix on a pas appeler super

        when(manager.getUeLibreSelected()).thenReturn(1); //pas de place en ue libre
        when(manager.getChooseUeSelected()).thenReturn(2); //plus de place pour les eu a choix
        // il n'y a pas de place normale ni de place pour les ue a choix elle est refuser
        assertEquals(rules.canBeCheck(new UE("name","1"), manager), false);
        verify(rules,times(1)).superCanBeCheck(any(),any()); //ue a choix on a appeler super mais il a pas de place (false)

        when(manager.getUeLibreSelected()).thenReturn(0); //de la place en ue libre
        when(manager.getChooseUeSelected()).thenReturn(2); //plus de place pour les eu a choix
        // il y a une place normale mais pas de place reservé pour les ue a choix elle est donc considere comme une ue normale et est accepter
        assertEquals(rules.canBeCheck(new UE("name","1"), manager), true);
        verify(rules,times(2)).superCanBeCheck(any(),any()); //ue a choix on a appeler super mais cette fois il avait de la place
    }

    @Test
    public void isChooseUETest(){
        //TEST si une ue est obligatoire
        ArrayList<String> choose = new ArrayList<String>();
        choose.add("1");
        choose.add("2");
        choose.add("3");
        rules = new SemestreRulesWithChoose(0, 0, new ArrayList<String>(),choose,2);
        String chooseUE1 = "1";
        String chooseUE2 = "2";
        String chooseUE3 = "3";
        String notChooseUE = "0";
        assertEquals(rules.isChooseUE(chooseUE1),true);
        assertEquals(rules.isChooseUE(chooseUE2),true);
        assertEquals(rules.isChooseUE(chooseUE3),true);
        assertEquals(rules.isChooseUE(notChooseUE),false);
    }
}
