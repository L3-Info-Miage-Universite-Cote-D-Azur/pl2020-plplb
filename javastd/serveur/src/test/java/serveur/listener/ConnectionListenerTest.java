package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import log.Logger;
import metier.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import java.net.SocketAddress;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class ConnectionListenerTest {

    private ConnectionListener connectionListener;
    private Gson gson = new GsonBuilder().create();

    private Student student;

    @Mock
    private SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);
    @Mock
    private SocketAddress socketAddress = Mockito.mock(SocketAddress.class);
    @Mock
    private LinkClientSocket linkClientSocket = Mockito.mock(LinkClientSocket.class);

    @BeforeEach
    public void init(){
        Logger.verbose = false;
        connectionListener = new ConnectionListener(linkClientSocket);
        student = new Student("nom");
    }

    @Test
    public void onDataTest() throws Exception {
        Mockito.when(socketIOClient.getRemoteAddress()).thenReturn(socketAddress);
        Mockito.when(socketAddress.toString()).thenReturn("");

        //On appelle la fonction
        connectionListener.onData(socketIOClient,gson.toJson(student),null);
        //On verifie
        Mockito.verify(linkClientSocket,times(1)).addClient(any(Client.class));
    }
}
