package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import log.Logger;
import metier.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class DeconnectionListenerTest {
    private DeconnectionListener deconnectionListener;

    @Mock
    private LinkClientSocket linkClientSocket = Mockito.mock(LinkClientSocket.class);
    @Mock
    private Client client = Mockito.mock(Client.class);
    @Mock
    private SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);
    @Mock
    private Student student = Mockito.mock(Student.class);

    @BeforeEach
    public void init(){
        Logger.verbose = false;
        deconnectionListener = new DeconnectionListener(linkClientSocket);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mocks
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");
        Mockito.when(linkClientSocket.numberClient()).thenReturn(99);

        //On appelle la fonction
        deconnectionListener.onDisconnect(socketIOClient);

        //On verifie que tout est appeler
        Mockito.verify(linkClientSocket,times(1)).getClient(any(SocketIOClient.class));
        Mockito.verify(linkClientSocket,times(1)).numberClient();
        Mockito.verify(linkClientSocket,times(1)).removeClient(any(SocketIOClient.class));
    }
}
