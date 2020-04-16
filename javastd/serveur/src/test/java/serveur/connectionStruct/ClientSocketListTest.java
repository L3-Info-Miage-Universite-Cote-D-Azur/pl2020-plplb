package serveur.connectionStruct;

import com.corundumstudio.socketio.SocketIOClient;
import metier.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ClientSocketListTest {

    ClientSocketList clientList;
    @Mock
    SocketIOClient client1Sock;

    @Mock
    SocketIOClient client2Sock;

    @Mock
    SocketIOClient client3Sock;

    @BeforeEach
    public void init(){
        clientList = new ClientSocketList();
        client1Sock = Mockito.mock(SocketIOClient.class);
        client2Sock = Mockito.mock(SocketIOClient.class);
        client3Sock = Mockito.mock(SocketIOClient.class);


    }

    @Test
    public void getClientTest(){
        Client client1 = new Client(new Student("name1"),client1Sock);
        Client client2 = new Client(new Student("name2"),client2Sock);
        Client clientNotInList = new Client(new Student("other"),client3Sock);

        clientList.addClient(client1);
        clientList.addClient(client2);

        //on a bien les 2 client dans la liste
        assertEquals(clientList.size(),2);

        //on verifie que l'on peut les recuperer avec le socket ou le nom de l'etudiant
        //client 1:
        assertEquals(clientList.getClient(client1.getSock()),client1);
        assertEquals(clientList.getClient(client1.getStudent()),client1);

        //client 2:
        assertEquals(clientList.getClient(client2.getSock()),client2);
        assertEquals(clientList.getClient(client2.getStudent()),client2);

        //un client pas ajouter on ne le trouve pas:
        assertEquals(clientList.getClient(clientNotInList.getSock()),null);
        assertEquals(clientList.getClient(clientNotInList.getStudent()),null);




    }




    @Test
    public void removeClientTest() {
        Client client1 = new Client(new Student("name1"),client1Sock);
        Client client2 = new Client(new Student("name2"),client2Sock);
        Client clientNotInList = new Client(new Student("other"),client3Sock);
        clientList.addClient(client1);
        clientList.addClient(client2);
        //on a bien les 2 client dans la liste
        assertEquals(clientList.size(),2);

        //on suprime un client qui n'est pas dans la list rien ne ce passe
        clientList.removeClient(clientNotInList.getSock());
        assertEquals(clientList.size(),2);

        //on supprime le client 1 avec le sock
        clientList.removeClient(client1.getSock());
        //il y a plus que le client 2
        assertEquals(clientList.size(),1);
        assertEquals(client2,clientList.getClient(client2.getSock()));

        //on supprime le client 2 avec son nom
        clientList.removeClient(client2.getStudent());
        //il y a plus personne
        assertEquals(clientList.size(),0);



    }



    @Test
    public void addClientTest(){
        Client client1 = new Client(new Student("name1"),client1Sock);
        Client client2 = new Client(new Student("name2"),client2Sock);
        Client client3 = new Client(new Student("name2"),client1Sock);

        //on a aucun client au debut
        assertEquals(clientList.size(),0);
        clientList.addClient(client1);

        //on a bien le client qui c'est ajouter
        assertEquals(clientList.size(),1);

        //on en ajoute un deusieme:
        clientList.addClient(client2);

        //on a bien le client qui c'est ajouter
        assertEquals(clientList.size(),2);

        //on ajoute de nouveau le premier il y a pas de duplication
        clientList.addClient(client1);
        //aucun changement
        assertEquals(clientList.size(),2);
        //ce sont bien les bon client
        assertEquals(client1,clientList.getClient(client1.getSock()));
        assertEquals(client2,clientList.getClient(client2.getSock()));


        //si un nouveau client ce connect avec les meme socket alors que l'ancien n'est pas enlever ca le remplace
        clientList.addClient(client3);
        //aucun changement
        assertEquals(clientList.size(),2);
        assertEquals(client3,clientList.getClient(client3.getSock()));
        assertEquals(client2,clientList.getClient(client2.getSock()));
    }

    @Test
    public void numberClientTest(){
        Client client1 = new Client(new Student("name1"),client1Sock);
        Client client2 = new Client(new Student("name2"),client2Sock);

        //on a aucun client au debut
        assertEquals(clientList.size(),0);
        clientList.addClient(client1);

        //on a bien le client qui c'est ajouter
        assertEquals(clientList.size(),1);

        //on en ajoute un deusieme:
        clientList.addClient(client2);

        //on a bien le client qui c'est ajouter
        assertEquals(clientList.size(),2);

    }




}
