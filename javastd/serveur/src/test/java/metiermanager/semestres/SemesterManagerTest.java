package metiermanager.semestres;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import database.FileManager;
import metier.semestre.Semestre;
import metiermanager.semesters.SemesterManager;
import metiermanager.semesters.SemestreConsts;

public class
SemesterManagerTest
{
	private SemesterManager sm;
	private File dir;
	private FileManager fm;
	
	@BeforeEach
	public void
	init ()
	{
		this.sm = new SemesterManager();
		dir = new File(SemestreConsts.dir);
		dir.mkdir();
		fm = new FileManager(SemestreConsts.dir + "s.txt");
		fm.create();
		fm.clearFile();
	}
	
	@AfterEach
	public void
	after ()
	{
		fm.clearFile();
		fm.getFile().delete();
		dir.delete();
	}

	/*
	@Test
	public void
	testGet ()
	{
		// CONTENU DE s1.txt
		String before = "{\"number\":1,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\",\"listUE\":[{\"name\":" +
                "\"Decouverte 1\",\"code\":\"SPUGDE10\",\"semestreNumber\":1,\"categorie\":\"GEOGRAPHIE\"},{" +
                "\"name\":\"Decouverte 2\",\"code\":\"SPUGDC10\",\"semestreNumber\":1,\"categorie\":\"GEOGRAPHIE" +
                "\"},{\"name\":\"Disciplinaire 1\",\"code\":\"SPUGDI10\",\"semestreNumber\":1,\"categorie\":" +
                "\"GEOGRAPHIE\"}]},{\"name\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Bases de l\\u0027informatique" +
                "\",\"code\":\"SPUF10\",\"semestreNumber\":1,\"categorie\":\"INFORMATIQUE\"},{\"name\":" +
                "\"Introduction a l\\u0027informatique par le web\",\"code\":\"SPUF11\",\"semestreNumber" +
                "\":1,\"categorie\":\"INFORMATIQUE\"}]},{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":" +
                "\"Fondements 1\",\"code\":\"SPUM11\",\"semestreNumber\":1,\"categorie\":\"MATHEMATIQUES\"},{" +
                "\"name\":\"Complements 1\",\"code\":\"SPUM13\",\"semestreNumber\":1,\"categorie\":\"MATHEMATIQUES" +
                "\"},{\"name\":\"Methodes - approche continue\",\"code\":\"SPUM12\",\"semestreNumber\":1," +
                "\"categorie\":\"MATHEMATIQUES\"}]},{\"name\":\"SCIENCES DE LA VIE\",\"listUE\":[{\"name\":" +
                "\"Genetique. evolution. origine vie \\u0026 biodiversite\",\"code\":\"SPUV101\",\"semestreNumber" +
                "\":1,\"categorie\":\"SCIENCES DE LA VIE\"},{\"name\":" +
                "\"Org et mecan. moleculaires - cellules eucaryotes\",\"code\":\"SPUV100\",\"semestreNumber" +
                "\":1,\"categorie\":\"SCIENCES DE LA VIE\"}]},{\"name\":\"CHIMIE\",\"listUE\":[{\"name\":" +
                "\"Structure microscopique de la matiere\",\"code\":\"SPUC10\",\"semestreNumber\":1," +
                "\"categorie\":\"CHIMIE\"}]},{\"name\":\"ELECTRONIQUE\",\"listUE\":[{\"name\":" +
                "\"Electronique numerique - Bases\",\"code\":\"SPUE10\",\"semestreNumber\":1,\"categorie\":" +
                "\"ELECTRONIQUE\"}]},{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion" +
                "\",\"code\":\"SPUA10\",\"semestreNumber\":1,\"categorie\":\"ECONOMIE - GESTION\"}]},{" +
                "\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique 1\",\"code\":\"SPUP10\"," +
                "\"semestreNumber\":1,\"categorie\":\"PHYSIQUE\"}]},{\"name\":\"SCIENCES DE LA TERRE" +
                "\",\"listUE\":[{\"name\":\"Decouverte des sciences de la terre\",\"code\":\"SPUT10\"," +
                "\"semestreNumber\":1,\"categorie\":\"SCIENCES DE LA TERRE\"}]},{\"name\":\"MATH ENJEUX" +
                "\",\"listUE\":[{\"name\":\"Math enjeux 1\",\"code\":\"SPUS10\",\"semestreNumber\":1," +
                "\"categorie\":\"MATH ENJEUX\"}]},{\"name\":\"COMPETENCES TRANSVERSALES\",\"listUE\":[{" +
                "\"name\":\"Competences transversales\",\"code\":\"KCTTS1\",\"semestreNumber\":1,\"categorie" +
                "\":\"COMPETENCES TRANSVERSALES\"}]},{\"name\":\"FABLAB\",\"listUE\":[{\"name\":\"Fablab S1\"," +
                "\"code\":\"SPUSF100\",\"semestreNumber\":1,\"categorie\":\"FABLAB\"}]}],\"rules\":{" +
                "\"maxUELibre\":3,\"maxByCategory\":1,\"obligatoryUEList\":[\"KCTTS1\",\"SPUS10\"]," +
                "\"chooseUEList\":[\"SPUM11\",\"SPUM12\"],\"numberChooseUE\":1}}";
		
		fm.write(before);
		Semestre s = this.sm.get("s.txt");
		Gson gson = new GsonBuilder().create();
		String after = gson.toJson(s);

		assertTrue(before.equals(after));
	}
	*/
	
	@Test
	public void
	testGetLastUpdateFile ()
	{
		String[] old = SemestreConsts.filenames;
		long[] old_ = SemestreConsts.lastUpdate;
		SemestreConsts.filenames = new String[] {this.fm.getFile().getName()};
		SemestreConsts.lastUpdate = new long[] {this.fm.getFile().lastModified()};
				
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		this.sm.updateConsts();
		
		long newTime = System.currentTimeMillis();
		
		// Il peut y avoir 1 seconde de decalage
		assertTrue(SemestreConsts.lastUpdate[0] <= newTime || newTime - 1 <= SemestreConsts.lastUpdate[0]);
		
		SemestreConsts.filenames = old;
		SemestreConsts.lastUpdate = old_;
	}
	
	@Test
	public void
	testAreSemestersUpdated ()
	{
		String[] old = SemestreConsts.filenames;
		long[] old_ = SemestreConsts.lastUpdate;
		SemestreConsts.filenames = new String[] {this.fm.getFile().getName()};
		SemestreConsts.lastUpdate = new long[] {this.fm.getFile().lastModified()};
		
		assertFalse(this.sm.areSemestersUpdated());
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.fm.write("Updated");
		assertTrue(this.sm.areSemestersUpdated());
		
		SemestreConsts.filenames = old;
		SemestreConsts.lastUpdate = old_;
	}
	
	@Test
	public void
	testHasBeenUpdated ()
	{
		String[] old = SemestreConsts.filenames;
		long[] old_ = SemestreConsts.lastUpdate;
		SemestreConsts.filenames = new String[] {this.fm.getFile().getName()};
		SemestreConsts.lastUpdate = new long[] {this.fm.getFile().lastModified()};
		
		assertFalse(this.sm.hasBeenUpdated(0));
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.fm.write("Updated");
		assertTrue(this.sm.hasBeenUpdated(0));
		
		SemestreConsts.filenames = old;
		SemestreConsts.lastUpdate = old_;
	}
}
