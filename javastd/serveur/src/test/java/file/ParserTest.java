package file;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;

import file.Parser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class
ParserTest
{
    private Parser parser;

    /** Initialisation pour chaque UT (Unit Test) */
    @BeforeEach
    public void
    init ()
    {
        this.parser = new Parser();
    }

    @Test
    public void
    testParseLine ()
    {
        /** Payloads */
        ArrayList<String> befores = new ArrayList<String>(Arrays.asList(
                "a\nb\nc\nd",
                "linefeed\ntab\t\ncarriage return\r\n",
                " \n\n "));

        /** Expected output */
        ArrayList<String>[] afters = new ArrayList[] {
                new ArrayList<String>(Arrays.asList("a","b","c","d")),
                new ArrayList<String>(Arrays.asList("linefeed","tab","carriage return")),
                new ArrayList<String>(Arrays.asList("","",""))};

        /** Asserts */
        for (int i = 0; i < befores.size(); i++)
        {
            assertEquals(this.parser.parseLine(befores.get(i)).equals(afters[i]), true);
        }
    }

    @Test
    public void
    testParseCSV ()
    {
        /** Payload */
        String CSVContent = "Tab1;    Tab2\n" + 	// espaces
                "Col1;Col2;\n" +  		// pas d'espaces
                ";;1;           2";		// pas de ; a la fin

        /** Expected output */
        ArrayList<ArrayList<String>> expected = new ArrayList<ArrayList<String>>();
        expected.add(new ArrayList<String>(Arrays.asList("Tab1","Tab2")));
        expected.add(new ArrayList<String>(Arrays.asList("Col1", "Col2")));
        expected.add(new ArrayList<String>(Arrays.asList("","", "1", "2")));

        /** Real output */
        List<List<String>> after = this.parser.parseCsv(CSVContent);


        /** Asserts */
        for (int i = 0; i < after.size(); i++)
        {
            for (int k = 0; k < after.get(i).size(); k++)
            {
                assertEquals(expected.get(i).get(k), after.get(i).get(k));
            }
        }
    }
}
