package metiermanager.semestres;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import database.FileManager;
import debug.Debug;
import metiermanager.semesters.SemesterManager;
import metiermanager.semesters.SemesterThread;
import metiermanager.semesters.SemestersSample;
import metiermanager.semesters.SemestreConsts;
import serveur.Serveur;

public class
SemesterThreadTest 
{
	private SemesterThread st;
	private SemesterManager sm;
	private Serveur serv;
	
	private String[] _fnames;
	private String _fdir;
	private long[] _fold;
	
	@BeforeEach
	public void
	constructSS ()
	{
		/* Old objects saving */
		_fnames = SemestreConsts.filenames;
		_fdir = SemestreConsts.dir;
		_fold = SemestreConsts.lastUpdate;
		
		SemestreConsts.dir = "testST/";
		SemestreConsts.filenames = new String[] {"s1.txt","s1.txt","s1.txt","s1.txt"};
		SemestreConsts.lastUpdate = new long[] {0L, 0L, 0L, 0L};
	}
	
	@AfterEach
	public void
	destroySS ()
	{
    	/* Old objects reinitializing as expected */
    	SemestreConsts.dir = _fdir;
		SemestreConsts.filenames = _fnames;
		SemestreConsts.lastUpdate = _fold;
		
		/* Delete files & dirs */
		FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
		fm.getFile().delete();
		new File(SemestreConsts.dir).delete();
    }
	long time;
	@BeforeEach
	public void
	testConstructor ()
	{
		Debug.verbose = false;
		this.serv = new Serveur("127.0.0.1", 1234);
		this.sm = new SemesterManager();
		
		new File(SemestreConsts.dir).mkdir();
		FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
		fm.create();
		fm.write("{\"number\":42,\"listCategorie\":" +
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
    			",\"numberChooseUE\":0}}");
		this.sm.updateConsts();
		time = SemestreConsts.lastUpdate[0];
		
		SemestersSample.init();
		this.st = new SemesterThread(this.serv, this.sm, true);
	}
	
	@Test
	public void
	runTest ()
	{
		/**
		 * Cas ou il n'y a pas de modification
		 */
		/* Thread lance */
		Thread th = new Thread(this.st);
		th.start();
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		th.interrupt();
				
		/* SemestreConsts.lastUpdated ne change pas
		 * car aucune modification */
		for (long l : SemestreConsts.lastUpdate)
			assertEquals(l, time);
		/* Quit function run() */
		
		/**
		 * Cas ou il y a modification
		 */
		FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
		assertTrue(fm.exists());
		
		/* Thread lance */
		th = new Thread(this.st);
		th.start();
		
		/* attendre 50 millisecondes*/
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// On change 42 par 43 dans le numero du semestre
		fm.write("{\"number\":43,\"listCategorie\":" +
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
    			",\"numberChooseUE\":0}}");
		this.sm.updateConsts();
		try {
			TimeUnit.MILLISECONDS.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		th.interrupt();
		/* SemestreConsts.lastUpdated change
		 * car modification */
		for (long l : SemestreConsts.lastUpdate)
			assertTrue(l > time);
	}
}
