package semester_manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import metier.parcours.manager.Parser;

public class
ParserTest 
{
	private Parser parser;
	
	@BeforeEach
	public void
	init ()
	{
		this.parser = new Parser();
	}
	
	@Test
	public void
	testParse ()
	{
		/**
		 * Test d'enlevement de \n, \t, \r et l'espace
		 */
		String before = "AA\nBB CC\tDD\rEE";
		String after = "AABBCCDDEE";
		assertEquals(after, this.parser.parse(before));
	}
}
