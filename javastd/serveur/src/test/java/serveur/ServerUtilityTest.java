package serveur;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Any;

import com.corundumstudio.socketio.SocketIOClient;

import metier.Etudiant;

public class 
ServerUtilityTest 
{
	private ArrayList<Client> list;
	private Client client;
	
	@Mock
	private Etudiant etu;
	@Mock
	private SocketIOClient csock; 
	
	@BeforeEach
	public void
	init ()
	{
		list = new ArrayList<Client>();
		
		etu = Mockito.mock(Etudiant.class);
		Mockito.when(etu.getNom()).thenReturn("etu1");
		
		csock = Mockito.mock(SocketIOClient.class);
		
		client = new Client(etu, csock);
		list.add(client);
	}
	
	@Test
	public void
	testGetStudentFromList ()
	{
		Etudiant etu = ServerUtility.getStudentFromList(csock, list);
		assertEquals(etu.getNom(), "etu1");
	}
	
	@Test
	public void
	testGetSocketFromList ()
	{
		SocketIOClient c = ServerUtility.getSocketFromList(etu, list);
		assertTrue(csock.equals(c));
	}
	
	@Test
	public void
	testGetClientFromSocketOnList ()
	{
		Client c = ServerUtility.getClientFromSocketOnList(csock, list);
		assertTrue(c.equals(client));
	}
}
