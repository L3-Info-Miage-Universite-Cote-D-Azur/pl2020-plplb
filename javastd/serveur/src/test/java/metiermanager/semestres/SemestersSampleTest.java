package metiermanager.semestres;

import org.junit.jupiter.api.*;

import database.FileManager;
import metier.Categorie;
import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;
import metiermanager.Converter;
import metiermanager.semesters.SemestersSample;
import metiermanager.semesters.SemestreConsts;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;

public class
SemestersSampleTest 
{
	private String[] _fnames;
	private String _fdir;
	private long[] _fold;
	
	@BeforeEach
	public void
	__start ()
	{
		/* Old objects saving */
		_fnames = SemestreConsts.filenames;
		_fdir = SemestreConsts.dir;
		_fold = SemestreConsts.lastUpdate;
		
		SemestreConsts.dir = "testSemesterSample/";
		SemestreConsts.filenames = new String[] {"s1.txt","s1.txt","s1.txt","s1.txt"};
		SemestreConsts.lastUpdate = new long[] {0L, 0L, 0L, 0L};
	}
	
	/**
	 * Fonction qui permet la creation du semestre utile
	 * aux UTs.
	 * Le semestre se presente comme ceci:
	 * [num, cats:[UEs1, UEs2]]
	 * 
	 * @param num Numero du semestre
	 * @return un nouveau Semestre
	 */
	public Semestre
	createSemestre4UT (int num)
	{
		ArrayList<UE> UEs1 = new ArrayList<UE>();
		UEs1.add(new UE("UE1", "CODE1"));
		UEs1.add(new UE("UE2", "CODE2"));
		
		ArrayList<UE> UEs2 = new ArrayList<UE>();
		UEs2.add(new UE("UE3", "CODE3"));
		UEs2.add(new UE("UE4", "CODE4"));
		
		ArrayList<Categorie> cats = new ArrayList<Categorie>();
		cats.add(new Categorie("CAT1", UEs1));
		cats.add(new Categorie("CAT2", UEs2));
		
		SemestreRules sr = new SemestreRules(-1, -1, new ArrayList<String>());
		
		return new Semestre(num, cats, sr);
	}
	
    @Test
    public void
    testSemesters ()
    {
    	String expected = "{\"number\":42,\"listCategorie\":" +
    			"[{\"name\":\"CAT1\",\"listUE\":[{\"name\":\"" +
    			"UE1\",\"code\":\"CODE1\",\"semestreNumber\":" +
    			"42,\"categorie\":\"CAT1\"},{\"name\":\"UE2\","+
    			"\"code\":\"CODE2\",\"semestreNumber\":42,\"ca"+
    			"tegorie\":\"CAT1\"}]},{\"name\":\"CAT2\",\"li"+
    			"stUE\":[{\"name\":\"UE3\",\"code\":\"CODE3\","+
    			"\"semestreNumber\":42,\"categorie\":\"CAT2\"}"+
    			",{\"name\":\"UE4\",\"code\":\"CODE4\",\"semes"+
    			"treNumber\":42,\"categorie\":\"CAT2\"}]}],\"r"+
    			"ules\":{\"maxUELibre\":-1,\"maxByCategory\":-"+
    			"1,\"obligatoryUEList\":[],\"chooseUEList\":[]"+
    			",\"numberChooseUE\":0}}";
        FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
        Semestre sem = this.createSemestre4UT(42);
        /** Creation des semestres */
        new File(SemestreConsts.dir).mkdir();
        fm.create();
        fm.write(sem.getJson());
        
        SemestersSample.init();
        
        /**
         * Test sur les XXJsoned, on verifie bien
         * que le JSON est egal a notre string au dessus
         */
        assertTrue(SemestersSample.S1Jsoned.equals(expected));
        assertTrue(SemestersSample.S2Jsoned.equals(expected));
        assertTrue(SemestersSample.S3Jsoned.equals(expected));
        assertTrue(SemestersSample.S4Jsoned.equals(expected));
        
        Converter conv = new Converter();
        /** Semestre expecte */
        Semestre expectedSem = conv.stringToSemestre(expected);
        
        /**
         * UT Test sur la totale egalite
         * des 2 semestres 
		 */
        assertEquals(expectedSem.getNumber(), sem.getNumber());
        assertEquals(expectedSem.getListCategorie().size(), sem.getListCategorie().size());
        for (int ci = 0; ci < expectedSem.getListCategorie().size(); ci++)
        {
        	/**
        	 * Test de l'integralite des categories
        	 * dans l'ordres suivant:
        	 * -Nom
        	 * -Taille de la liste d'UE
        	 * -Listes d'UE
        	 */
        	 
        	assertTrue(expectedSem.getListCategorie().get(ci).getName()
        			.equals(sem.getListCategorie().get(ci).getName()));
        	
        	assertEquals(expectedSem.getListCategorie().get(ci).getListUE().size(),
        			sem.getListCategorie().get(ci).getListUE().size());
        	
        	for (int uei = 0; uei < expectedSem.getListCategorie().get(ci).getListUE().size(); uei++)
        	{
        		/**
        		 * Test de l'integralite de chaque UE de la categorie courante
        		 * dans l'ordre suivant:
        		 * -Nom
        		 * -Code
        		 * -Numero de semestre
        		 * -Nom de categorie
        		 */
        		
        		assertTrue(expectedSem.getListCategorie().get(ci).getListUE().get(uei).getUeName()
        				.equals(sem.getListCategorie().get(ci).getListUE().get(uei).getUeName()));
        		
        		assertTrue(expectedSem.getListCategorie().get(ci).getListUE().get(uei).getUeCode()
        				.equals(sem.getListCategorie().get(ci).getListUE().get(uei).getUeCode()));
        		
        		assertEquals(expectedSem.getListCategorie().get(ci).getListUE().get(uei).getSemestreNumber(),
        				sem.getListCategorie().get(ci).getListUE().get(uei).getSemestreNumber());
        		
        		assertTrue(expectedSem.getListCategorie().get(ci).getListUE().get(uei).getCategorie()
        				.equals(sem.getListCategorie().get(ci).getListUE().get(uei).getCategorie()));
        	}
        }
        
        fm.getFile().delete();
        new File(SemestreConsts.dir).delete();
    }
    
    @AfterEach
    public void
    __end ()
    {
    	/* Old objects reinitializing as expected */
    	SemestreConsts.dir = _fdir;
		SemestreConsts.filenames = _fnames;
		SemestreConsts.lastUpdate = _fold;
    }
}
