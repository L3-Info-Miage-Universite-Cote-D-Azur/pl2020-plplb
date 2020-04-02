package serveur;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.corundumstudio.socketio.AckCallback;
import com.corundumstudio.socketio.HandshakeData;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIONamespace;
import com.corundumstudio.socketio.Transport;
import com.corundumstudio.socketio.protocol.Packet;

import database.FileManager;
import debug.Debug;
import metier.Student;
import metiermanager.semesters.SemestersSample;
import metiermanager.semesters.SemestreConsts;

public class 
ServerUtilityTest 
{
	private ArrayList<Client> list;
	private Client client;
	private Client clientInvalid;
	
	private Student etu;
	private Student etuInvalid;
	private SocketIOClient csock;
	private SocketIOClient csockInvalid;
	
	public SocketIOClient makeCSock ()
	{
		return new SocketIOClient() {
			@Override
			public void set(String key, Object val) {}
			@Override
			public boolean has(String key) {return false;}
			@Override
			public <T> T get(String key) {return null;}
			@Override
			public void del(String key) {}
			@Override
			public void sendEvent(String name, Object... data) {}
			@Override
			public void send(Packet packet) {}
			@Override
			public void disconnect() {}
			@Override
			public void sendEvent(String name, AckCallback<?> ackCallback, Object... data) {}
			@Override
			public void send(Packet packet, AckCallback<?> ackCallback) {}
			@Override
			public void leaveRoom(String room) {}
			@Override
			public void joinRoom(String room) {}
			@Override
			public boolean isChannelOpen() {return false;}
			@Override
			public Transport getTransport() {return null;}
			@Override
			public UUID getSessionId() {return null;}
			@Override
			public SocketAddress getRemoteAddress() {return null;}
			@Override
			public SocketIONamespace getNamespace() {return null;}
			@Override
			public HandshakeData getHandshakeData() {return null;}
			@Override
			public Set<String> getAllRooms() {return null;}
		};
	}
	
	@BeforeEach
	public void
	init ()
	{
		list = new ArrayList<Client>();
		
		etu = new Student("etu1");
		etuInvalid = new Student("etu2");
		
		csock = makeCSock();
		csockInvalid = makeCSock();
		
		client = new Client(etu, csock);
		clientInvalid = new Client(etuInvalid, csockInvalid);
		list.add(client);
	}
	
	@Test
	public void
	testGetStudentFromList ()
	{
		Student etu = ServerUtility.getStudentFromList(csock, list);
		assertEquals(etu.getNom(), "etu1");
		
		etu = ServerUtility.getStudentFromList(csockInvalid, list);
		assertEquals(etu, null);
	}
	
	@Test
	public void
	testGetSocketFromList ()
	{
		SocketIOClient c = ServerUtility.getSocketFromList(etu, list);
		assertTrue(csock.equals(c));
		
		c = ServerUtility.getSocketFromList(etuInvalid, list);
		assertFalse(csock.equals(c));
	}
	
	@Test
	public void
	testGetClientFromSocketOnList ()
	{
		Client c = ServerUtility.getClientFromSocketOnList(csock, list);
		assertTrue(c.equals(client));
		assertFalse(c.equals(clientInvalid));
		assertNull(ServerUtility.getClientFromSocketOnList(csockInvalid, list));
	}
	
	@Test
	public void
	testGetListOfSemestersJSONed ()
	{
		/* Old objects saving */
		String[] _fnames = SemestreConsts.filenames;
		String _fdir = SemestreConsts.dir;
		long[] _fold = SemestreConsts.lastUpdate;
		
		String toWrite = "{\"number\":42,\"listCategorie\":" +
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
		
		String before = "[\"{\\\"number\\\":42,\\\"listCategorie\\\":[{\\\"name\\\":\\\"CAT1\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE1\\\",\\\"code\\\":\\\"CODE1\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"},{\\\"name\\\":\\\"UE2\\\",\\\"code\\\":\\\"CODE2\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"}]},{\\\"name\\\":\\\"CAT2\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE3\\\",\\\"code\\\":\\\"CODE3\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"},{\\\"name\\\":\\\"UE4\\\",\\\"code\\\":\\\"CODE4\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"}]}],\\\"rules\\\":{\\\"maxUELibre\\\":-1,\\\"maxByCategory\\\":-1,\\\"obligatoryUEList\\\":[],\\\"chooseUEList\\\":[],\\\"numberChooseUE\\\":0}}\",\"{\\\"number\\\":42,\\\"listCategorie\\\":[{\\\"name\\\":\\\"CAT1\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE1\\\",\\\"code\\\":\\\"CODE1\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"},{\\\"name\\\":\\\"UE2\\\",\\\"code\\\":\\\"CODE2\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"}]},{\\\"name\\\":\\\"CAT2\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE3\\\",\\\"code\\\":\\\"CODE3\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"},{\\\"name\\\":\\\"UE4\\\",\\\"code\\\":\\\"CODE4\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"}]}],\\\"rules\\\":{\\\"maxUELibre\\\":-1,\\\"maxByCategory\\\":-1,\\\"obligatoryUEList\\\":[],\\\"chooseUEList\\\":[],\\\"numberChooseUE\\\":0}}\",\"{\\\"number\\\":42,\\\"listCategorie\\\":[{\\\"name\\\":\\\"CAT1\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE1\\\",\\\"code\\\":\\\"CODE1\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"},{\\\"name\\\":\\\"UE2\\\",\\\"code\\\":\\\"CODE2\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"}]},{\\\"name\\\":\\\"CAT2\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE3\\\",\\\"code\\\":\\\"CODE3\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"},{\\\"name\\\":\\\"UE4\\\",\\\"code\\\":\\\"CODE4\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"}]}],\\\"rules\\\":{\\\"maxUELibre\\\":-1,\\\"maxByCategory\\\":-1,\\\"obligatoryUEList\\\":[],\\\"chooseUEList\\\":[],\\\"numberChooseUE\\\":0}}\",\"{\\\"number\\\":42,\\\"listCategorie\\\":[{\\\"name\\\":\\\"CAT1\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE1\\\",\\\"code\\\":\\\"CODE1\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"},{\\\"name\\\":\\\"UE2\\\",\\\"code\\\":\\\"CODE2\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT1\\\"}]},{\\\"name\\\":\\\"CAT2\\\",\\\"listUE\\\":[{\\\"name\\\":\\\"UE3\\\",\\\"code\\\":\\\"CODE3\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"},{\\\"name\\\":\\\"UE4\\\",\\\"code\\\":\\\"CODE4\\\",\\\"semestreNumber\\\":42,\\\"categorie\\\":\\\"CAT2\\\"}]}],\\\"rules\\\":{\\\"maxUELibre\\\":-1,\\\"maxByCategory\\\":-1,\\\"obligatoryUEList\\\":[],\\\"chooseUEList\\\":[],\\\"numberChooseUE\\\":0}}\"]";
		
		SemestreConsts.dir = "testSU/";
		SemestreConsts.filenames = new String[] {"s1.txt","s1.txt","s1.txt","s1.txt"};
		SemestreConsts.lastUpdate = new long[] {0L, 0L, 0L, 0L};
		FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
		new File(SemestreConsts.dir).mkdir();
		fm.create();
		fm.write(toWrite);

		assertTrue(before.equals(ServerUtility.getListOfSemestersJSONed()));
		
		/* Old objects reinitializing as expected */
    	SemestreConsts.dir = _fdir;
		SemestreConsts.filenames = _fnames;
		SemestreConsts.lastUpdate = _fold;
		fm.getFile().delete();
		new File(SemestreConsts.dir).delete();
	}
	
	@Test
	public void
	testGetListOfCourseTypeJSONed ()
	{
		String expected = "[null,null,null,null,null]";
		assertTrue(expected.equals(ServerUtility.getListOfCourseTypeJSONed()));
	}
}
