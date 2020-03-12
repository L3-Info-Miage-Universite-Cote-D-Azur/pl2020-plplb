package metier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.*;

public class
CategorieTest 
{
	private String name;
	private ArrayList<UE> listUE;
	
	@BeforeEach
	public void
	init ()
	{
		this.name = "CatTest";
		this.listUE = new ArrayList<UE>();
		this.listUE.add(new UE("UE1", "CODE1"));
	}
	
	@Test
	public void
	testConstructor ()
	{
		/**
		 * Test pour savoir si toutes les assignations lors
		 * de la construction sont correctes
		 */
		Categorie cat1 = new Categorie(this.name, this.listUE);
		assertNotNull(cat1);
		assertEquals(cat1.getName().equals(this.name), true);
		assertEquals(cat1.getListUE().equals(this.listUE), true);
		assertEquals(cat1.getClass(), Categorie.class);
		
		Categorie cat2 = new Categorie(this.name);
		cat2.setListUE(this.listUE);
		assertNotNull(cat2);
		assertEquals(cat2.getName().equals(this.name), true);
		assertEquals(cat2.getListUE().equals(this.listUE), true);
		assertEquals(cat2.getClass(), Categorie.class);
		
		assertEquals(cat1.getListUE() == cat2.getListUE(), false);
	}
	
	@Test
	public void
	testFindUE ()
	{
		/**
		 * Test pour 1 element
		 * Si ca marche pour 1, ca marche pour n
		 */
		Categorie cat = new Categorie(this.name, this.listUE);
		assertEquals("CODE1".equals(cat.findUE("CODE1").getUeCode()), true);
		
		/**
		 * Test pour 0 element
		 */
		cat = new Categorie(this.name);
		assertEquals(null, cat.findUE("CODE1"));
	}
	
	@Test
	public void
	testAddUE ()
	{
		/**
		 * Test pour k elements
		 * Si ca marche pour k, ca marche pour n
		 */
		Categorie cat = new Categorie(this.name);
		assertEquals(cat.getListUE().equals(new ArrayList<UE>()), true);
		cat.addUe(new UE("U", "C"));
		
		ArrayList<UE> expected = new ArrayList<UE>(Arrays.asList(new UE("U", "C")));
		ArrayList<UE> catUE = cat.getListUE();
		
		/**
		 * Check des tailles
		 */
		assertEquals(expected.size(), catUE.size());
		
		/**
		 * Check pour chaque element
		 */
		for (int idx = 0; idx < expected.size(); idx++)
		{
			UE u1 = expected.get(idx);
			UE u2 = catUE.get(idx);
			
			assertEquals(u1.getUeCode().equals(u2.getUeCode()), true);
			assertEquals(u1.getUeName().equals(u2.getUeName()), true);
		}
	}
	
	
}
