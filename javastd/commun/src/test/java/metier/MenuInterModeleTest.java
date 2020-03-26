package metier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MenuInterModeleTest {

    MenuInterModele menuInterModele;

    @BeforeEach
    public void init(){
        ArrayList<String> listParcoursPredef = new ArrayList<String>();
        listParcoursPredef.add("Parcours type 1");
        listParcoursPredef.add("Parcours type 2");
        listParcoursPredef.add("Parcours type 3");

        ArrayList<String> listParcoursName = new ArrayList<String>();
        listParcoursName.add("Mon Parcours 1");
        listParcoursName.add("Mon Parcours 2");
        listParcoursName.add("Mon Parcours 3");
        listParcoursName.add("Mon Parcours 4");

        menuInterModele = new MenuInterModele(listParcoursPredef,listParcoursName);
    }

    @Test
    public void canBeChooseNameTest(){
        String name1 = "Mon nouveau parcours";
        String name2 = "Ou cela ?";
        String name3 = "Ou ceci ?";
        String name4 = "On peut dire que ça marche";

        //Le nom est dispo
        assertEquals(true,menuInterModele.canBeChooseName(name1));
        assertEquals(true,menuInterModele.canBeChooseName(name2));
        assertEquals(true,menuInterModele.canBeChooseName(name3));
        assertEquals(true,menuInterModele.canBeChooseName(name4));

        String wrongName1 = "Mon Parcours 1";
        String wrongName2 = "Mon Parcours 2";
        String wrongName3 = "Mon Parcours 3";
        String wrongName4 = "Mon Parcours 4";
        //Le nom n'est pas dispo.
        assertEquals(false,menuInterModele.canBeChooseName(wrongName1));
        assertEquals(false,menuInterModele.canBeChooseName(wrongName2));
        assertEquals(false,menuInterModele.canBeChooseName(wrongName3));
        assertEquals(false,menuInterModele.canBeChooseName(wrongName4));

    }

    @Test
    public void isSelectedParcoursTest(){
        //Le parcoursTypeName n'est pas initialise
        assertEquals(false,menuInterModele.isSelectedParcours());

        //On l'affecte.
        menuInterModele.setParcoursTypeName("Un nom de parcours type");
        //Le parcoursTypeName n'est pas vide.
        assertEquals(true,menuInterModele.isSelectedParcours());

        //On peut aussi le remettre a null.
        menuInterModele.setParcoursTypeName(null);
        //Le parcoursTypeName n'est plus initialise
        assertEquals(false,menuInterModele.isSelectedParcours());
    }
}
