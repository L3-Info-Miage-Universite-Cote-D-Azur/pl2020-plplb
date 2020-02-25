package serveur;

import com.corundumstudio.socketio.*;

import metier.Etudiant;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.Times;

import static constantes.NET.SENDDATACONNEXION;
import static constantes.NET.SENDMESSAGE;
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
        serveur = new Serveur("127.0.0.1",10113);
        client = Mockito.mock(SocketIOClient.class);
        etudiant = Mockito.spy(new Etudiant("test"));
        Debug.verbose = false;

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


}
