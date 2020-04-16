package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dataBase.SemesterDataBase;
import log.Logger;
import metier.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

public class SemesterDataListenerTest {
    private SemesterDataListener semesterDataListener;

    @Mock
    private LinkClientSocket linkClientSocket = Mockito.mock(LinkClientSocket.class);
    @Mock
    private Client client = Mockito.mock(Client.class);
    @Mock
    private SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);
    @Mock
    private Student student = Mockito.mock(Student.class);
    @Mock
    private SemesterDataBase semesterDataBase = Mockito.mock(SemesterDataBase.class);

    @BeforeEach
    public void init(){
        Logger.verbose = false;
        semesterDataListener = new SemesterDataListener(linkClientSocket,semesterDataBase);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mocks
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");

        //On appelle la fonction
        semesterDataListener.onData(socketIOClient,"",null);

        //On verifie que tout est appeler
        Mockito.verify(linkClientSocket,times(1)).getClient(any(SocketIOClient.class));
        Mockito.verify(socketIOClient,times(1)).sendEvent(anyString(),anyString());
        Mockito.verify(semesterDataBase,times(1)).getSemesterList();
    }
}
