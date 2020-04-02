package metier.parcours;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import metier.Categorie;
import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;

public class
ParcoursListTest
{
	private ParcoursList pl;
	
	@Mock
	private Parcours p1;
	@Mock
	private Parcours p2;
	
	
	@BeforeEach
	public void
	__start ()
	{
		this.p1 = Mockito.mock(Parcours.class);
		this.p2 = Mockito.mock(Parcours.class);
		
		Mockito.when(p1.getName()).thenReturn("p1");
		Mockito.when(p2.getName()).thenReturn("p2");
		Mockito.doNothing().when(p1).updateSemestre();
		Mockito.doNothing().when(p2).updateSemestre();
		
		this.pl = new ParcoursList();
		this.pl.add(p1);
		this.pl.add(p2);
	}
	
	@Test
	public void
	testGetParcoursByName ()
	{
		Parcours p = this.pl.getParcoursByName("p2");
		assertEquals(p.getName(), p2.getName());
		
		p = this.pl.getParcoursByName("p1");
		assertEquals(p.getName(), p1.getName());
		
		p = this.pl.getParcoursByName("p3");
		assertEquals(p, null);
	}
	
	@Test
	public void
	testGetCurrentParcours ()
	{
		Parcours p;
		
		p = this.pl.getCurrentParcours();
		assertEquals(p.getName(), p1.getName());
		
		this.pl.currentParcours = 5;
		p = this.pl.getCurrentParcours();
		assertNull(p);
		this.pl.currentParcours = 0;
	}
	
	@Test
	public void
	testUpdateSemestre ()
	{
		this.pl.updateSemestre();
		Mockito.verify(p1, new Times(1)).updateSemestre();
		Mockito.verify(p2, new Times(1)).updateSemestre();
	}
	
	@Test
	public void
	testGetAllParcoursName ()
	{
		ArrayList<String> allName = this.pl.getAllParcoursName();
		
		assertEquals(allName.size(), 2);
		assertEquals(allName.get(0), this.p1.getName());
		assertEquals(allName.get(1), this.p2.getName());
	}
}
