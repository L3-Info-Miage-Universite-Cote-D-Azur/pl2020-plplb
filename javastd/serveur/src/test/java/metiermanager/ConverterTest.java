package metiermanager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Categorie;
import metier.UE;
import metier.parcours.ParcoursType;
import metiermanager.Converter;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;

public class
ConverterTest
{
	private Converter conv;
	
	@BeforeEach
	public void
	init ()
	{
		this.conv = new Converter();
	}
	
	@Test
	public void
	testStringToSemester ()
	{
		//creation des ue d'un semestre:
		//code;nom;semestre;categorie;description
		String ueInString = "code;nom;semestre;categorie;description\n"+ //(la conversion ignore la premiere ligne elle permet d'aider a remplir le reste)
				"CODE1;NAME1;1;CAT1;None\n" +
				"CODE2;NAME2;1;CAT1;None\n"+
				"CODE3;NAME3;1;CAT2;None\n";


		Semestre received = this.conv.stringToSemestre(ueInString);

		//on verifie on dois avoir 2 categorie cat1 et cat2
		assertEquals(received.getListCategorie().size(),2);

		//on a bien les 2 bon nom de categorie
		assertEquals(received.getListCategorie().get(0).getName(),"CAT1");
		assertEquals(received.getListCategorie().get(1).getName(),"CAT2");

		//on a bien 2 ue dans cat1
		assertEquals(received.getListCategorie().get(0).getListUE().size(),2);

		//on a bien 1 ue dans cat2
		assertEquals(received.getListCategorie().get(1).getListUE().size(),1);
	}
	
	@Test
	public void
	testStringToCourse ()
	{
		String expected = "{" + 
				"\"name\":\"MATH\"," + 
				"\"obligatoryUes\":[" + 
				"\"SPUM11\"," + 
				"\"SPUM13\"," + 
				"\"SPUM21\"," + 
				"\"SPUM23\"," + 
				"\"SPUM30\"," + 
				"\"SPUM32\"," + 
				"\"SPUM31\"," + 
				"\"SPUM40\"," + 
				"\"SPUM42\"," + 
				"\"SPUM41\"," + 
				"\"SPUM43\"" + 
				"]" + 
				"}";
		
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
        ParcoursType pt = new ParcoursType("MATH", null, obligatoryUeMath);
		
		Gson gson = new GsonBuilder().create();
		String before = gson.toJson(pt);

		assertTrue(before.equals(expected));
		
		ParcoursType received = this.conv.stringToCourse(before);
		String after = gson.toJson(received);
		
		assertTrue(before.equals(after));
	}
}
