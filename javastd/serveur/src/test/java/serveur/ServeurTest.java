package serveur;

import com.corundumstudio.socketio.*;

import database.DBManager;
import database.FileManager;
import debug.Debug;
import metier.Categorie;
import metier.Student;
import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.SemestreRules;
import metiermanager.semesters.SemestreConsts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.io.File;
import java.net.SocketAddress;
import java.util.ArrayList;

import static constantes.NET.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;


/**
 * test de la class Serveur
 */
public class ServeurTest {

    Serveur serveur;

    @Mock
    SocketIOClient sockClient;
    @Mock
    SocketAddress socketAddress;
    @Mock
    Client c;
    @Mock
    Student etudiant;
    
    @BeforeEach
    public void init(){
        Debug.verbose = false;
        serveur = new Serveur("127.0.0.1",10113);
        sockClient = Mockito.mock(SocketIOClient.class);
        socketAddress = Mockito.mock(SocketAddress.class);
        etudiant = Mockito.spy(new Student("test"));
        c = Mockito.spy(new Client(etudiant, sockClient));
        
    }
    
    @Test
    public void clientOnConnectSendPredefinedCourse(){
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectSendPredefinedCourse(c);
        //quand un client ce connecte on lui envoie un message
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(PREDEFINEDCOURSE),any(String.class));
    }


    @Test
    public void clientOnConnectEventSendSemestersTest(){
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectEventSendSemesters(c);
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(SEMSTERDATA),any(String.class));
    }
    
    @Test
    public void 
    clientOnConnectSendCourses ()
    {
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectSendCourses(c);
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(COURSESNAMES),any(String.class));
    }

    @Test
    public void clientOnConnectSendSaveTest(){
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectSendSave(c, "aaa");
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(sockClient,new Times(0)).sendEvent(ArgumentMatchers.eq(SENDCLIENTSAVE),any(String.class));

        DBManager dbManager = new DBManager(etudiant.toString(), "parcours");
        new File("db/").mkdir();
        dbManager.getDir().getFile().mkdir();
        dbManager.getCourse().create();
        dbManager.getCourse().write("[\"TypeParcours\",\"UE1\",\"UE2\"]");

        serveur.clientOnConnectSendSave(c, "parcours");
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(LOADCOURSE),any(String.class));
    }
    
    /* ----------------------------------- */
    
	private String[] _fnames;
	private String _fdir;
	private long[] _fold;
	
	public void
	__start ()
	{
		/* Old objects saving */
		_fnames = SemestreConsts.filenames;
		_fdir = SemestreConsts.dir;
		_fold = SemestreConsts.lastUpdate;
		
		SemestreConsts.dir = "testServeur/";
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
    testUpdateSemestersOfClients ()
    {
    	/* -- Start --*/
    	this.__start();
    	Debug.verbose = false;
    	Semestre sem = this.createSemestre4UT(42);
    	FileManager fm = new FileManager(SemestreConsts.dir + SemestreConsts.filenames[0]);
    	new File(SemestreConsts.dir).mkdir();
    	fm.create();
    	fm.write(sem.getJson());
    	
    	// Ajout de 1 client
    	serveur.getClients().add(c);
    	
    	/* -- Traitement --*/
    	serveur.updateSemestersOfClients();
    	Mockito.verify(sockClient,new Times(serveur.getClients().size())).sendEvent(ArgumentMatchers.eq(CLIENTUPDATE),any(String.class));
    	
    	/* -- End -- */
    	SemestreConsts.dir = _fdir;
		SemestreConsts.filenames = _fnames;
		SemestreConsts.lastUpdate = _fold;
		fm.getFile().delete();
		new File(SemestreConsts.dir).delete();
    }
    
    /* ----------------------------------- */

    @AfterEach
    public void del(){
        File directory = new File("db");
        if(directory.exists()){
            File[] files = directory.listFiles();

            for(int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            directory.delete();
        }
    }
}
