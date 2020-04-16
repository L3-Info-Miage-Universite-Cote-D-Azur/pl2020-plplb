package serveur.listener;

import com.corundumstudio.socketio.SocketIOClient;
import dataBase.CourseDataBase;
import dataBase.SharedCourseDataBase;
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

public class LoadShareCourseListenerTest {
    private LoadShareCourseListener loadShareCourseListener;

    @Mock
    private LinkClientSocket linkClientSocket = Mockito.mock(LinkClientSocket.class);
    @Mock
    private Client client = Mockito.mock(Client.class);
    @Mock
    private SocketIOClient socketIOClient = Mockito.mock(SocketIOClient.class);
    @Mock
    private Student student = Mockito.mock(Student.class);
    @Mock
    private SharedCourseDataBase sharedCourseDataBase = Mockito.mock(SharedCourseDataBase.class);
    @Mock
    private CourseDataBase courseDataBase = Mockito.mock(CourseDataBase.class);

    @BeforeEach
    public void init(){
        Logger.verbose = false;
        loadShareCourseListener = new LoadShareCourseListener(linkClientSocket,sharedCourseDataBase,courseDataBase);
    }

    @Test
    public void onDataTest() throws Exception {
        //Mise en place des mocks
        Mockito.when(linkClientSocket.getClient(any(SocketIOClient.class))).thenReturn(client);
        Mockito.when(sharedCourseDataBase.verifyCode(anyString())).thenReturn(true);
        Mockito.when(client.getStudent()).thenReturn(student);
        Mockito.when(student.getNom()).thenReturn("nom");
        Mockito.when(sharedCourseDataBase.loadShareCourse(anyString())).thenReturn("content");

        //On appelle la fonction
        loadShareCourseListener.onData(socketIOClient,"code",null);

        //On verifie que tout est appeler
        Mockito.verify(linkClientSocket,times(1)).getClient(any(SocketIOClient.class));
        Mockito.verify(socketIOClient,times(1)).sendEvent(anyString(),anyString());
        Mockito.verify(sharedCourseDataBase,times(1)).verifyCode(anyString());
        Mockito.verify(sharedCourseDataBase,times(1)).loadShareCourse(anyString());
        Mockito.verify(courseDataBase,times(1)).writeStudentSave(anyString(),anyString(),anyString());
    }
}
