package semester_manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Categorie;
import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;
import semester_manager.Converter;

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
}
