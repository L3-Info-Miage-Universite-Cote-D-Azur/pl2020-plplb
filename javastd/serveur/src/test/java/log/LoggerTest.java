package log;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import file.Config;
import file.FileManager;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

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

	@Mock
	Config config = Mockito.mock(Config.class);

	@Test
	public void
	testSaveLogs ()
	{
		// To except at the end
		Logger.logs = "Test";
		Mockito.when(config.getparentPath()).thenReturn(".");
		Mockito.when(config.getConfig("log_directory")).thenReturn("\\");
		/* Fichier */
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
		LocalDateTime now = LocalDateTime.now();
		FileManager fm = new FileManager("." + "\\" + dtf.format(now) + ".txt");

		/* Le nom du fichier n'est pas a tester puisqu'il est genere par des fonctions tiers */

		Logger.saveLogs(config);
		assertEquals(Logger.logs, "");
		assertEquals(fm.exists(), true);
		assertEquals(fm.getRaw(), "Test");

		fm.deleteFile();
	}
	
	@AfterEach
	public void
	exit ()
	{System.setOut(old);}
}
