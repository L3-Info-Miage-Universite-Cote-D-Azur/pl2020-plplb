package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dataBase.SharedCourseDataBase;
import log.Logger;
import metier.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import serveur.connectionStruct.Client;
import serveur.connectionStruct.LinkClientSocket;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

public class AskCodeListenerTest {

    private AskCodeListener askCodeListener;

    @Mock
    private SharedCourseDataBase sharedCourseDataBase = Mockito.mock(SharedCourseDataBase.class);
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
        askCodeListener = new AskCodeListener(linkClientSocket,sharedCourseDataBase);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mock
        Mockito.when(sharedCourseDataBase.generateSharedCourseCode()).thenReturn("code");
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");

        //On appelle la fonction
        askCodeListener.onData(socketIOClient,"[\"Code\"]",null);
        //On verifie que tout est appeler
        Mockito.verify(sharedCourseDataBase,times(1)).generateSharedCourseCode();
        Mockito.verify(linkClientSocket,times(1)).getClient(any(SocketIOClient.class));
        Mockito.verify(sharedCourseDataBase,times(1)).addShareCourse(anyString(),anyString());
        Mockito.verify(socketIOClient,times(1)).sendEvent(anyString(),anyString());

    }
}
