package file;


import log.Logger;
import org.junit.jupiter.api.*;


import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileManagerTest {
    FileManager fileManager;
    String path = "testFile/";
    File directory = new File("testFile");

    @BeforeEach
    public void init(){
        Logger.verbose = false;
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
        fileManager.create();
        fileManager.write(content);
        assertEquals(content,fileManager.getRaw());
    }

    @Test
    public void clearFileTest(){
        fileManager.create();
        //le fichier est vide par defaut
        assertEquals("",fileManager.getRaw());
        fileManager.clearFile();
        //il reste vide si il etait vide
        assertEquals("",fileManager.getRaw());

        //on ecrit dans le fichier
        fileManager.write("the file is not empty");
        assertEquals(true,fileManager.getRaw().length()>0);

        //on vide le fichier
        fileManager.clearFile();
        assertEquals("",fileManager.getRaw());
    }

    @Test
    public void getRawTest(){
        try {
            fileManager.getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Le fichier est vide
        assertEquals("",fileManager.getRaw());

        //On ecrit quelque chose dedans
        String text = "Bonjour j'ecrit quelque chose\nSur plusieurs lignes...\nEt voila.";
        fileManager.write(text);

        /* TODO: M'expliquer pourquoi setReadable false m'autorise toujours la lecture.
        //Le fichier n'est pas autorisé en lecture
        fileManager.getFile().setReadable(false);
        System.out.println(fileManager.getFile().canRead());
        assertEquals("",fileManager.getRaw());
         */

        //Le fichier est autorisé en lecture
        fileManager.getFile().setReadable(true);
        assertEquals(text,fileManager.getRaw());

        //Un autre texte
        String text2 = "Bonjour j'ecrit quelque chose sur une seule ligne";
        fileManager.write(text2);
        assertEquals(text2,fileManager.getRaw());
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
