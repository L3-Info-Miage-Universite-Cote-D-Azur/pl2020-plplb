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

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

public class RenameSaveListenerTest {
    private RenameSaveListener renameSaveListener;

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
        renameSaveListener = new RenameSaveListener(linkClientSocket,courseDataBase);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mocks
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");

        //On appelle la fonction
        renameSaveListener.onData(socketIOClient,"[\"toto\",\"tata\"]",null);

        //On verifie que tout est appeler
        Mockito.verify(linkClientSocket,times(1)).getClient(any(SocketIOClient.class));
        Mockito.verify(socketIOClient,times(1)).sendEvent(anyString(),anyString());
        Mockito.verify(courseDataBase,times(1)).renameSave(anyString(),anyString(),anyString());
    }

    @Test
    public void canBeChoosedTest(){
        //Nos test
        String true1 = "un nom classique";
        String true2 = "test";
        String true3 = "un_n@m_m4rr4nt";

        String false1 = "%appdata%";
        String false2 = "../../oups";
        String false3 = "..........";

        assertEquals(true, renameSaveListener.canBeChoosed(true1));
        assertEquals(true, renameSaveListener.canBeChoosed(true2));
        assertEquals(true, renameSaveListener.canBeChoosed(true3));

        assertEquals(false, renameSaveListener.canBeChoosed(false1));
        assertEquals(false, renameSaveListener.canBeChoosed(false2));
        assertEquals(false, renameSaveListener.canBeChoosed(false3));
    }
}
