package serveur;

import com.corundumstudio.socketio.*;

import database.DBManager;
import debug.Debug;
import metier.Etudiant;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import java.io.File;
import java.net.SocketAddress;

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
    Etudiant etudiant;
    
    @BeforeEach
    public void init(){
        Debug.verbose = false;
        serveur = new Serveur("127.0.0.1",10113);
        sockClient = Mockito.mock(SocketIOClient.class);
        socketAddress = Mockito.mock(SocketAddress.class);
        etudiant = Mockito.spy(new Etudiant("test"));
        c = Mockito.spy(new Client(etudiant, sockClient));
        
    }
    @Test
    public void clientOnConnectEventTest(){
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectEvent(c);
        //quand un client ce connecte on lui envoie un message
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDMESSAGE),any(String.class));
    }


    @Test
    public void clientOnConnectEventSendSemestersTest(){
        Mockito.when(c.getSock()).thenReturn(sockClient);
        Mockito.when(sockClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("test");

        serveur.clientOnConnectEventSendSemesters(c);
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDDATACONNEXION),any(String.class));
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
        Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDCLIENTLISTCOURSE),any(String.class));
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
        dbManager.getDir().getFile().mkdir();
        dbManager.getCourse().create();

        //TODO reparer ce test
        //serveur.clientOnConnectSendSave(c, "parcours");
        //Mockito.verify(sockClient,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDCLIENTSAVE),any(String.class));
    }

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
