package serveur;


import org.junit.jupiter.api.*;

import files.FileManager;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    FileManager fileManager;
    String path = "testFile/";
    File directory = new File("testFile");

    @BeforeEach
    public void init(){
        fileManager = new FileManager(path+"testFile");
        directory.mkdir();
    }

    @Test
    public void existsTest(){
        //cas de base le fichier n'existe pas
        assertEquals(false,fileManager.exists());

        //creation d'un fichier avec un nom different
        File otherFile = new File(path+"testOtherFile");
        try {
            otherFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(false,fileManager.exists());

        //Si le fichier existe:
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertEquals(true,fileManager.exists());
    }



    @Test
    public void writeAndGetFileContentTest(){
        String content = "hello world";
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        fileManager.write(content);

        assertEquals(content,fileManager.getFileContent());
    }

    @Test
    public void clearFileTest(){
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //le fichier est vide par defaut
        assertEquals(null,fileManager.getFileContent());
        fileManager.clearFile();
        //il reste vide si il etait vide
        assertEquals(null,fileManager.getFileContent());

        //on ecrit dans le fichier
        fileManager.write("the file is not empty");
        assertEquals(true,fileManager.getFileContent().length()>0);

        //on vide le fichier
        fileManager.clearFile();
        assertEquals(null,fileManager.getFileContent());
    }

    @AfterEach
    public void deleteFile(){
        File[] files = directory.listFiles();

        for(int i = 0; i < files.length; i++) {
            files[i].delete();
        }
        directory.delete();
    }

}
