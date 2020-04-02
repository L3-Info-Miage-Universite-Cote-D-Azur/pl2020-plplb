package metiermanager.courses;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.FileManager;
import metier.parcours.ParcoursType;
import metiermanager.semesters.SemestreConsts;

public class
ParcoursManagerTest
{
	private ParcoursManager pm;
	private String[] _fnames;
	private String _fdir;
	
	public ParcoursType
	createParcoursType4UT ()
	{
		ArrayList<String> codes = new ArrayList<String>();
		codes.add("SPUE10");
		codes.add("SPUM12");
		codes.add("SPUE21");
		codes.add("SPUM22");
		codes.add("SPUM33");
		codes.add("SPUE31");
		codes.add("SPUE32");
		codes.add("SPUE30");
		codes.add("SPUM44");
		codes.add("SPUE42");
		codes.add("SPUE41");
		codes.add("SPUE40");
		return new ParcoursType("Parcours Electronique", null, codes);
	}
	
	@BeforeEach
	public void
	__start ()
	{
		this.pm = new ParcoursManager();
		/* Old objects saving */
		_fnames = ParcoursConsts.filenames;
		_fdir = ParcoursConsts.dir;
		
		ParcoursConsts.dir = "testPMT/";
		ParcoursConsts.filenames = new String[] {"p1.txt","p1.txt","p1.txt","p1.txt","p1.txt"};
	}
	
	@Test
	public void
	testGetJson ()
	{
		FileManager fm = new FileManager(ParcoursConsts.dir + ParcoursConsts.filenames[0]);
		new File(ParcoursConsts.dir).mkdir();
		fm.create();
		String toWrite = "{\r\n" + 
				"    \"name\":\"Parcours Electronique\",\r\n" + 
				"    \"obligatoryUes\":[\r\n" + 
				"        \"SPUE10\",\r\n" + 
				"        \"SPUM12\",\r\n" + 
				"        \"SPUE21\",\r\n" + 
				"        \"SPUM22\",\r\n" + 
				"        \"SPUM33\",\r\n" + 
				"        \"SPUE31\",\r\n" + 
				"        \"SPUE32\",\r\n" + 
				"        \"SPUE30\",\r\n" + 
				"        \"SPUM44\",\r\n" + 
				"        \"SPUE42\",\r\n" + 
				"        \"SPUE41\",\r\n" + 
				"        \"SPUE40\"\r\n" + 
				"    ]\r\n" + 
				"}\r\n";
		
		String expected = "{\"name\":\"Parcours Electronique\","+"\"obligatoryUes\":["+
    			"\"SPUE10\",\"SPUM12\",\"SPUE21\",\"SPUM22\",\"SPUM33\","+
    			"\"SPUE31\",\"SPUE32\",\"SPUE30\",\"SPUM44\",\"SPUE42\","+
    			"\"SPUE41\",\"SPUE40\"]}";
		fm.write(toWrite);
		
		String res = this.pm.getJson(ParcoursConsts.dir + ParcoursConsts.filenames[0]);
		assertEquals(expected, res);
		
		fm.getFile().delete();
		new File(ParcoursConsts.dir).delete();
	}
	
	@Test
	public void
	testGet ()
	{
		ParcoursType p = this.createParcoursType4UT();
		FileManager fm = new FileManager(ParcoursConsts.dir + ParcoursConsts.filenames[0]);
		new File(ParcoursConsts.dir).mkdir();
		fm.create();
		String toWrite = "{\r\n" + 
				"    \"name\":\"Parcours Electronique\",\r\n" + 
				"    \"obligatoryUes\":[\r\n" + 
				"        \"SPUE10\",\r\n" + 
				"        \"SPUM12\",\r\n" + 
				"        \"SPUE21\",\r\n" + 
				"        \"SPUM22\",\r\n" + 
				"        \"SPUM33\",\r\n" + 
				"        \"SPUE31\",\r\n" + 
				"        \"SPUE32\",\r\n" + 
				"        \"SPUE30\",\r\n" + 
				"        \"SPUM44\",\r\n" + 
				"        \"SPUE42\",\r\n" + 
				"        \"SPUE41\",\r\n" + 
				"        \"SPUE40\"\r\n" + 
				"    ]\r\n" + 
				"}\r\n";
		fm.write(toWrite);
		ParcoursType p2 = this.pm.get(ParcoursConsts.dir + ParcoursConsts.filenames[0]);
		Gson gson = new GsonBuilder().create();
		
		assertEquals(gson.toJson(p2), gson.toJson(p));
		
		fm.getFile().delete();
		new File(ParcoursConsts.dir).delete();
	}
    
    @AfterEach
    public void
    __end ()
    {
    	/* Old objects reinitializing as expected */
    	SemestreConsts.dir = _fdir;
		SemestreConsts.filenames = _fnames;
    }
}
