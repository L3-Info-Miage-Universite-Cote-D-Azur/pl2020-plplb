package metier.semestre;

import metier.UE;
import metier.semestre.SemestreManager;
import metier.semestre.SemestreRules;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SemestreManagerTest {

    @Spy
    SemestreManager manager;

    @Mock
    SemestreRules rules;

    @BeforeEach
    public void init(){
        rules = Mockito.mock(SemestreRules.class);
        manager = Mockito.spy(new SemestreManager(rules));
    }



    @Test
    public void checkTest(){
        //la verification si l'ue est deja dedans n'est pas gerer par cette classe mais par parcours

        UE ue = new UE("aaaa","bbb");
        ue.setCategorie("cat");
        //si l'ue est une ue obligatoire aucun traitement necessaire
        when(rules.isObligatoryUE(any())).thenReturn(true);
        manager.check(ue);
        //la fonction qui suit n'est donc pas appeler
        verify(rules,never()).isChooseUE(any());

        init();
        //si c'est une ue libre on ajoute l'ue
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(false);
        manager.check(ue);

        //l'ue est bien ajouter a la liste des ue libre et a ca categorie
        assertEquals(manager.getUeLibreSelected(),1);
        assertEquals(manager.getCountByCategory().get("cat"),1);

        init();
        //si c'est une ue a choix mais il n'y a pas de place dans les ue a choix l'ue est taité comme une ue normale
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(true);
        when(rules.getNumberChooseUE()).thenReturn(0); //il n'y a pas de place dans les ue a choix
        manager.check(ue);
        //traitement normale
        assertEquals(manager.getUeLibreSelected(),1);
        assertEquals(manager.getCountByCategory().get("cat"),1);
        //ajout au nombre d'ue a choix selectionner
        assertEquals(manager.getChooseUeSelected(),1);

        init();
        //si c'est une ue a choix et il y a de place dans les ue a choix l'ue n'est pas compter pour la categorie et non plus comme ue libre
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(true);
        when(rules.getNumberChooseUE()).thenReturn(1); //il y a de la place dans les ue a choix
        manager.check(ue);
        //traitement speciale
        assertEquals(manager.getUeLibreSelected(),0);
        assertEquals(manager.getCountByCategory().getOrDefault("cat",0),0);
        //ajout au nombre d'ue a choix selectionner
        assertEquals(manager.getChooseUeSelected(),1);
    }

    @Test
    public void uncheckTest(){
        //la verification si l'ue est deja dedans n'est pas gerer par cette classe mais par parcours

        //INIT=========================================================================
        UE ue = new UE("aaaa","bbb");
        ue.setCategorie("cat");
        //si l'ue est une ue obligatoire aucun traitement necessaire
        when(rules.isObligatoryUE(any())).thenReturn(true);
        //TEST===========================================================================
        manager.uncheck(ue);
        //la fonction qui suit n'est donc pas appeler
        verify(rules,never()).isChooseUE(any());

        //INIT=========================================================================
        init();
        //si c'est une ue libre
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(false);

        manager.check(ue);
        //l'ue est bien ajouter a la liste des ue libre et a ca categorie
        assertEquals(manager.getUeLibreSelected(),1);
        assertEquals(manager.getCountByCategory().get("cat"),1);
        //TEST===========================================================================
        //on la decoche
        manager.uncheck(ue);
        //on a bien tout qui est enlever
        assertEquals(manager.getUeLibreSelected(),0);
        assertEquals(manager.getCountByCategory().get("cat"),0);


        //TEST===========================================================================
        init();
        //si c'est une ue a choix mais il a pas de place dans les ue a choix l'ue est taité comme une ue normale
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(true);
        when(rules.getNumberChooseUE()).thenReturn(0); //il n'y a pas de place dans les ue a choix
        manager.check(ue);
        //traitement normale
        assertEquals(manager.getUeLibreSelected(),1);
        assertEquals(manager.getCountByCategory().get("cat"),1);
        //ajout au nombre d'ue a choix selectionner
        assertEquals(manager.getChooseUeSelected(),1);
        //TEST===========================================================================

        //on la decoche:
        manager.uncheck(ue);
        //on a bien tout qui est enlever
        assertEquals(manager.getUeLibreSelected(),0);
        assertEquals(manager.getCountByCategory().get("cat"),0);
        assertEquals(manager.getChooseUeSelected(),0);


        //INIT===========================================================================
        init();
        //si c'est une ue a choix et il y a de place dans les ue a choix l'ue n'est pas compter pour la categorie et non plus comme ue libre
        when(rules.isObligatoryUE(any())).thenReturn(false);
        when(rules.isChooseUE(any())).thenReturn(true);
        when(rules.getNumberChooseUE()).thenReturn(1); //il y a de la place dans les ue a choix
        manager.check(ue);
        //traitement speciale
        assertEquals(manager.getUeLibreSelected(),0);
        assertEquals(manager.getCountByCategory().getOrDefault("cat",0),0);
        //ajout au nombre d'ue a choix selectionner
        assertEquals(manager.getChooseUeSelected(),1);

        //TEST===========================================================================
        manager.uncheck(ue);
        //on a bien tout qui est enlever
        assertEquals(manager.getUeLibreSelected(),0); //a valeur n'est pas passer en negatif
        assertEquals(manager.getCountByCategory().getOrDefault("cat",0),0); //a valeur n'est pas passer en negatif
        assertEquals(manager.getChooseUeSelected(),0);

    }

    @Test
    public void canBeCheckTest() {
        manager.canBeCheck(null);
        //ca appelle bien la bonne fonction
        verify(rules,times(1)).canBeCheck(any(),any());
    }

    @Test
    public void canBeUncheckTest() {
        manager.canBeUncheck(null);
        //ca appelle bien la bonne fonction
        verify(rules,times(1)).canBeUncheck(any(),any());
    }

    @Test
    public void verifCompleteParcoursTest(){
        manager.verifCompleteParcours();
        //ca appelle bien la bonne fonction
        verify(rules,times(1)).verifCorrectSemestre(any());
    }
}
