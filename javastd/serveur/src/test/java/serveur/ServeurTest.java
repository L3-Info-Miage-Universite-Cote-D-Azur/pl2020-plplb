package serveur;

import file.Config;
import file.FileManager;
import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ServeurTest {
    @Mock
    Config config;

    String path = "testServeur";
    File directory;

    Serveur serveur;

    @BeforeEach
    public void init(){
        Logger.verbose=false;
        directory = new File(path);
        directory.mkdir();

        config = Mockito.mock(Config.class);
        when(config.getConfig("ip")).thenReturn("0.0.0.0");
        when(config.getConfig("port")).thenReturn("12345");
        when(config.getConfig("semestre_directory")).thenReturn("semestre");
        when(config.getConfig("courseType_directory")).thenReturn("courseType");
        when(config.getConfig("save_directory")).thenReturn("sauvegarde");
        when(config.getConfig("share_directory")).thenReturn("share");
        when(config.getConfig("number_semester")).thenReturn("0");

        when(config.getparentPath()).thenReturn(directory.getAbsolutePath());
        serveur = new Serveur(config,null);

    }

    @Test
    public void initSemesterListTest(){
        assertEquals(serveur.getSemesterDataBase(),null);
        //le fichier existe pas l'initialisation vas donc echouer
        assertFalse(serveur.initSemesterDataBase());
        assertEquals(serveur.getSemesterDataBase(),null);
        //on crée le fichier
        File semestreDirectory = new File(directory,"semestre");
        semestreDirectory.mkdir();

        //maintenant on peut l'initialiser et il est bien intialiser
        assertTrue(serveur.initSemesterDataBase());

        assertEquals(serveur.getSemesterDataBase()!=null,true);


    }

    @Test
    public void initTypeCourseDBTest(){
        assertEquals(serveur.getCourseTypeDataBase(),null);
        //le fichier existe pas l'initialisation vas donc echouer
        assertFalse(serveur.initSemesterDataBase());
        assertEquals(serveur.getCourseTypeDataBase(),null);
        //on crée le fichier
        File semestreDirectory = new File(directory,"courseType");
        semestreDirectory.mkdir();

        //maintenant on peut l'initialiser et il est bien intialiser
        assertTrue(serveur.initCourseTypeDataBas());

        assertEquals(serveur.getCourseTypeDataBase()!=null,true);
    }

    @Test
    public void intCourseDBTest(){
        assertEquals(serveur.getCourseDataBase(),null);
        File dbDirectory = new File(directory,"sauvegarde");
        //le dossier existe pas
        assertFalse(dbDirectory.exists());

        //on init
        serveur.initCourseDataBase();

        //il est bien intialiser
        assertEquals(serveur.getCourseDataBase()!=null,true);

        //le repertoire est créer
        assertTrue(dbDirectory.exists());

        //on a le repertoire et un fichier créer avec des donner a l'interrieur
        FileManager file = new FileManager(new File(dbDirectory,"unfichier"));
        file.write("hello");
        serveur = new Serveur(config,null);
        //n'est plus initialiser
        assertEquals(serveur.getCourseDataBase(),null);

        //on init
        serveur.initCourseDataBase();

        //on a bien garder les donner a l'interrieur
        //elle n'on pas etait ecraser
        assertEquals(file.getRaw(),"hello");

        //il est bien intialiser
        assertEquals(serveur.getCourseDataBase()!=null,true);

    }

    @Test
    public void intShareDBTest(){
        assertEquals(serveur.getSharedCourseDataBase(),null);
        File dbDirectory = new File(directory,"share");
        //le dossier existe pas
        assertFalse(dbDirectory.exists());

        //on init
        serveur.initSharedCourseDataBase();

        //il est bien intialiser
        assertEquals(serveur.getSharedCourseDataBase()!=null,true);

        //le repertoire est créer
        assertTrue(dbDirectory.exists());

        //on a le repertoire et un fichier créer avec des donner a l'interrieur
        FileManager file = new FileManager(new File(dbDirectory,"unfichier"));
        file.write("hello");
        serveur = new Serveur(config,null);
        //n'est plus initialiser
        assertEquals(serveur.getSharedCourseDataBase(),null);

        //on init
        serveur.initSharedCourseDataBase();

        //on a bien garder les donner a l'interrieur
        //elle n'on pas etait ecraser
        assertEquals(file.getRaw(),"hello");

        //il est bien intialiser
        assertEquals(serveur.getSharedCourseDataBase()!=null,true);

    }


    @AfterEach
    public void clearAllFile(){
        clearFile(directory);
    }

    public void clearFile(File directory){
        for(File file :directory.listFiles()){
            if(file.isDirectory()){
                clearFile(file); //on clear en profondeur
            }
            file.delete();
        }
        directory.delete();
    }
}
