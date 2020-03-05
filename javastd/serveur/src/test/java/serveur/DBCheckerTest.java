package serveur;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import metier.Categorie;
import metier.Semestre;
import metier.UE;

public class 
DBCheckerTest 
{
	private DBChecker db;
	private Semestre sem;
	private ArrayList<String> codes;
	private ArrayList<String> oblig;
	private ArrayList<Categorie> cats;
	private ArrayList<UE> uesOblig;
	private ArrayList<UE> uesOpt;
	
	@BeforeEach
	public void
	initEach ()
	{
		// Code UE Obligatoires
		this.oblig = new ArrayList<String>();
		this.oblig.add("O1");
		this.oblig.add("O2");
		
		// Initialisation des codes
		this.codes = new ArrayList<String>();
		
		// UE Obligatoires
		this.uesOblig = new ArrayList<UE>();
		this.uesOblig.add(new UE("UEO1", "O1"));
		this.uesOblig.add(new UE("UEO2", "O2"));
		
		// 2 UE Optionelles d une meme categorie 
		this.uesOpt = new ArrayList<UE>();
		this.uesOpt.add(new UE("UE1", "CODE1"));
		this.uesOpt.add(new UE("UE2", "CODE2"));
		
		this.cats = new ArrayList<Categorie>();
		// Ajout de la categorie pour UE Obligatoire
		this.cats.add(new Categorie("CAT1", this.uesOblig));
		this.cats.add(new Categorie("CAT2", this.uesOpt));
		
		// 1 UE Optionnelle
		this.uesOpt.clear();
		this.uesOpt.add(new UE("UE3", "CODE3"));
		
		this.cats.add(new Categorie("CAT3", this.uesOpt));
		
		// 1 UE Optionnelle
		this.uesOpt.clear();
		this.uesOpt.add(new UE("UE4", "CODE4"));
		
		this.cats.add(new Categorie("CAT4", this.uesOpt));
		
		// Creation du semestre
		this.sem = new Semestre(1, this.cats, this.oblig);
	}
	
	@Test
	public void
	testCheckSaveValid1 ()
	{
		/**
		 * TEST 1:
		 * L utilisateur choisi quelque chose de valide:
		 * 1 UE Obligatoire
		 * 3 UE de Categorie differente
		 */
		this.codes.add("O1");
		this.codes.add("CODE1");
		this.codes.add("CODE3");
		this.codes.add("CODE4");
		
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(true, this.db.checkSave());
	}
	
	@Test
	public void
	testCheckSaveValid2 ()
	{
		/**
		 * TEST 2:
		 * L utilisateur choisi quelque chose de valide:
		 * 1 UE Obligatoire
		 * 1 UE de meme categorie que celle obligatoire
		 * 2 UE de Categorie differente
		 */
		this.codes.add("O1");
		this.codes.add("O2");
		this.codes.add("CODE3");
		this.codes.add("CODE4");
		
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(true, this.db.checkSave());
	}
	
	
	@Test
	public void
	testCheckSaveValid3 ()
	{
		/**
		 * TEST 3:
		 * L utilisateur choisi quelque chose d invalide:
		 * 1 UE Obligatoire
		 * 2 UE de Categorie differente
		 */
		
		this.codes.add("O2");
		this.codes.add("CODE3");
		this.codes.add("CODE1");
		
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(true, this.db.checkSave());
	}
	
	/* ---------------------------------------------- */
	
	@Test
	public void
	testCheckSaveInvalid1 ()
	{
		/**
		 * TEST 5:
		 * L utilisateur choisi quelque chose d invalide:
		 * 1 UE Obligatoire
		 * 1 UE
		 * 2 UEs de meme categories
		 */
		
		this.codes.add("O2");
		this.codes.add("CODE3");
		this.codes.add("CODE1");
		this.codes.add("CODE2");
		
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(false, this.db.checkSave());
	}
	
	@Test
	public void
	testCheckSaveInvalid2 ()
	{
		/**
		 * TEST 6:
		 * L utilisateur choisi quelque chose d invalide:
		 * 1 UE Obligatoire
		 * 1 UE de meme categorie que celle obligatoire
		 * 2 UE de meme categorie
		 */
		
		this.codes.add("O2");
		this.codes.add("O1");
		this.codes.add("CODE2");
		this.codes.add("CODE1");
		
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(false, this.db.checkSave());
	}

	@Test
	public void
	testCheckSaveInvalid3 ()
	{
		/**
		 * TEST 7:
		 * L utilisateur ne choisi rien
		 */
		this.db = new DBChecker(this.sem, this.codes);
		
		assertEquals(false, this.db.checkSave());
	}
	
	@Test
	public void
	testCheckSaveInvalid4 ()
	{
		/**
		 * TEST 8:
		 * L utilisateur envoie un code UE non repertorie
		 */
		this.codes.add("STRANGE");
		
		this.db = new DBChecker(this.sem, this.codes);
		System.out.println("zzzzzzzzzzzzzzzz");
		
		assertEquals(false, this.db.checkSave());
	}
}
