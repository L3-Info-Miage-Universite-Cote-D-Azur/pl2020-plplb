package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dataBase.CourseDataBase;
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

public class ClientSaveListenerTest {

    private ClientSaveListener clientSaveListener;

    @Mock
    private LinkClientSocket linkClientSocket = Mockito.mock(LinkClientSocket.class);
    @Mock
    private Client client = Mockito.mock(Client.class);
    @Mock
    private SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);
    @Mock
    private Student student = Mockito.mock(Student.class);
    @Mock
    private CourseDataBase courseDataBase = Mockito.mock(CourseDataBase.class);

    @BeforeEach
    public void init(){
        Logger.verbose = false;
        clientSaveListener = new ClientSaveListener(linkClientSocket,courseDataBase);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mocks
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");

        //On appelle la fonction
        clientSaveListener.onData(socketIOClient,"[\"nom\"]",null);

        //On verifie que tout est appeler
        Mockito.verify(courseDataBase,times(1)).writeStudentSave(anyString(),anyString(),anyString());
    }
}
