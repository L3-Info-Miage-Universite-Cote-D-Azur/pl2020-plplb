package log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.*;

public class
LoggerTest
{
	/**
	 * -- UT Tests --
	 * On redirige System.out (stdout) vers un ByteArrayOutputStream
	 * On verifie le contenu de output a chaque test*()
	 */
	
	ByteArrayOutputStream output;
	PrintStream ps;
	PrintStream old;
	
	@BeforeEach
	public void 
	start ()
	{
		output = new ByteArrayOutputStream();
		ps = new PrintStream(output);
		old = System.out;
		System.setOut(ps);
		Logger.verbose = true;
	}
	
	@AfterEach
	public void
	ae ()
	{System.out.flush();}
	
	@Test
	public void
	testError ()
	{
		Logger.error("Test");
		assertTrue(output.toString().equals("[E] Test" + System.lineSeparator()));
	}
	
	@Test
	public void
	testLog ()
	{
		Logger.log("Test");
		assertTrue(output.toString().equals("[*] Test" + System.lineSeparator()));
	}
	
	@Test
	public void
	testPut ()
	{
		/* test valide de debug */
		Logger.verbose = true;
		Logger.put("Test");
		assertTrue(output.toString().equals("Test\r\n"));
		System.out.flush();
		
		/* flusher System.out ne flush par l'output */
		Logger.verbose = false;
		Logger.put("Test");
		assertTrue(output.toString().equals("Test\r\n"));
		
		/* flush de l'output, ca ne vide pas le buffer */
		try {
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		assertTrue(output.toString().equals("Test\r\n"));
	}
	
	@AfterEach
	public void
	exit ()
	{System.setOut(old);}
}
