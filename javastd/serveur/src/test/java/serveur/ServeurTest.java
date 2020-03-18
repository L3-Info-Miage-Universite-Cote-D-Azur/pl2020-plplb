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

import static constantes.NET.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;



/**
 * test de la class Serveur
 */
public class ServeurTest {

    Serveur serveur;

    @Mock
    SocketIOClient client;
    @Mock
    Etudiant etudiant;

    @BeforeEach
    public void init(){
        Debug.verbose = false;
        serveur = new Serveur("127.0.0.1",10113);
        client = Mockito.mock(SocketIOClient.class);
        etudiant = Mockito.spy(new Etudiant("test"));

    }
    @Test
    public void clientConnectTest(){
        serveur.clientConnect(client,etudiant);
        //quand un client ce connecte on lui envoie un message
        Mockito.verify(client,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDMESSAGE),any(String.class));
        // le client est bien enregistrer sur le serveur
        assertEquals(true,etudiant.equals(serveur.getEtudiant()));

    }


    @Test
    public void clientConnectDataTest(){
        serveur.clientConnectData(client,etudiant);
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(client,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDDATACONNEXION),any(String.class));
    }

    @Test
    public void clientSendDataTest(){
        serveur.clientSendData(client,etudiant);
        //quand un client ce connecte on lui envoie bien les donner
        Mockito.verify(client,new Times(0)).sendEvent(ArgumentMatchers.eq(SENDCLIENTSAVE),any(String.class));

        DBManager dbManager = new DBManager(etudiant.toString());
        dbManager.getFile().create();
        serveur.clientSendData(client,etudiant);
        Mockito.verify(client,new Times(1)).sendEvent(ArgumentMatchers.eq(SENDCLIENTSAVE),any(String.class));
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
