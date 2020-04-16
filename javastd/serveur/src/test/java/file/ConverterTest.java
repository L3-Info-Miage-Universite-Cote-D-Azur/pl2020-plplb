package file;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import metier.UE;
import org.junit.jupiter.api.*;

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
	testCsvToUe ()
	{
		/** null case */
		List<List<String>> before = new ArrayList<>();
		assertNull(this.conv.csvToUe(before));

		/** Before input */
		before = new ArrayList<List<String>>();
		before.add(new ArrayList<String>(Arrays.asList("Junk Row")));
		before.add(new ArrayList<String>(Arrays.asList("Code","Nom","-1","Cat","Desc")));

		/** After input */
		ArrayList<UE> output = this.conv.csvToUe(before);

		/** Asserts */
		assertEquals(output.size(), 1);
		assertEquals(output.get(0).getUeCode(), "Code");
		assertEquals(output.get(0).getUeName(), "Nom");
		assertEquals(output.get(0).getSemestreNumber(), -1);
		assertEquals(output.get(0).getCategorie(), "Cat");
		assertEquals(output.get(0).getUeDescription(), "Desc");
	}
}
