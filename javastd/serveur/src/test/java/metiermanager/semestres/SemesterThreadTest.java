package metiermanager.semestres;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import debug.Debug;
import metiermanager.semesters.SemesterManager;
import metiermanager.semesters.SemesterThread;
import serveur.Serveur;

public class
SemesterThreadTest 
{
	private SemesterThread st;
	@Mock
	private SemesterManager sm;
	@Mock
	private Serveur serv;
	
	@BeforeEach
	public void
	buildMockSM ()
	{
		this.sm = Mockito.mock(SemesterManager.class);
		Mockito.when(this.sm.areSemestersUpdated()).thenReturn(true);
		Mockito.doNothing().when(this.sm).updateConsts();
	}
	
	@BeforeEach
	public void
	buildMockSERV ()
	{
		this.serv = Mockito.mock(Serveur.class);
		Mockito.doNothing().when(this.serv).updateSemestersOfClients();
	}
	
	@BeforeEach
	public void
	testConstructor ()
	{
		Debug.verbose = false;
		this.st = new SemesterThread(this.serv, this.sm, true);
	}
	
	@Test
	public void
	runTest ()
	{
		/* Thread lance */
		this.st.run(); /* wait 10 sec, ... */
		assertEquals(1, this.st.UTget__UTtimes());
		/* Quit function run() */
	}
}
