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
		// Creation des UE
		ArrayList<UE> listUE = new ArrayList<UE>();
		listUE.add(new UE("NAME", "CODE"));
		// Creation des Categorie
		ArrayList<Categorie> cats = new ArrayList<Categorie>();
		cats.add(new Categorie("CAT", listUE));
		// Creation des regles du semestre
		SemestreRules rules = new SemestreRules(1, 1, new ArrayList<String>());
		// Creation du semestre
		Semestre expected = new Semestre(1, cats, rules);
		
		Gson gson = new GsonBuilder().create();
		String before = gson.toJson(expected);
		Semestre received = this.conv.stringToSemestre(before);
		String after = gson.toJson(received);
		
		assertTrue(before.equals(after));
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
