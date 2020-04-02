package metiermanager.courses;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.FileManager;
import metier.Categorie;
import metier.UE;
import metier.parcours.ParcoursType;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;
import metiermanager.Converter;
import metiermanager.semesters.SemestersSample;
import metiermanager.semesters.SemestreConsts;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

public class
ParcoursSampleTest
{
	private String[] _fnames;
	private String _fdir;
	
	@BeforeEach
	public void
	__start ()
	{
		/* Old objects saving */
		_fnames = ParcoursConsts.filenames;
		_fdir = ParcoursConsts.dir;
		
		ParcoursConsts.dir = "testPS/";
		ParcoursConsts.filenames = new String[] {"p1.txt","p1.txt","p1.txt","p1.txt","p1.txt"};
	}
	
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
	
    @Test
    public void
    testCourseType ()
    {
    	String expected = "{\"name\":\"Parcours Electronique\","+"\"obligatoryUes\":["+
    			"\"SPUE10\",\"SPUM12\",\"SPUE21\",\"SPUM22\",\"SPUM33\","+
    			"\"SPUE31\",\"SPUE32\",\"SPUE30\",\"SPUM44\",\"SPUE42\","+
    			"\"SPUE41\",\"SPUE40\"]}";
        FileManager fm = new FileManager(ParcoursConsts.dir + ParcoursConsts.filenames[0]);
        Gson gson = new GsonBuilder().create();
        ParcoursType pt = this.createParcoursType4UT();
        /** Creation des semestres */
        new File(ParcoursConsts.dir).mkdir();
        fm.create();
        fm.write(gson.toJson(pt));
        
        ParcoursSample.init();
        
        /**
         * Test sur les XXJsoned, on verifie bien
         * que le JSON est egal a notre string au dessus
         */
        assertTrue(ParcoursSample.JPI.equals(expected));
        assertTrue(ParcoursSample.JPM.equals(expected));
        assertTrue(ParcoursSample.JPMM.equals(expected));
        assertTrue(ParcoursSample.JPE.equals(expected));
        assertTrue(ParcoursSample.JPF.equals(expected));
        
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
