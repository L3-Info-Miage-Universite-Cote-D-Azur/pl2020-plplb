package dataBase;

import file.FileManager;
import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class SharedCourseDataBaseTest {

    private SharedCourseDataBase sharedCourseDataBase;
    private File directory;
    private FileManager fileManager;

    @BeforeEach
    public void init(){
        //On mute l'envoie du serveur
        Logger.verbose = false;

        //Creation du dossier principal
        directory = new File("TestSharedDirectory");
        directory.mkdir();

        //Creation de save
        File save1 = new File(directory,"00000");
        fileManager = new FileManager(save1);
        fileManager.write("content1");
        File save2 = new File(directory,"11111");
        fileManager = new FileManager(save2);
        fileManager.write("content2");
        File save3 = new File(directory,"22222");
        fileManager = new FileManager(save3);
        fileManager.write("content3");

        //INIT
        sharedCourseDataBase = new SharedCourseDataBase(directory);
    }

    @Test
    public void getAllSharedCourseNameTest(){
        //On recupere tout les noms des parcours partagé (tout les codes)
        ArrayList<String> allCode = sharedCourseDataBase.getAllSharedCourseName();

        //Les deux listes ont la meme taille
        assertEquals(directory.listFiles().length,allCode.size());

        for(File file : directory.listFiles()){
            //Les noms sont bien ceux des fichiers
            assertEquals(true,allCode.contains(file.getName()));
        }
    }

    @Test
    public void generateSharedCourseCodeTest(){

        ArrayList<String> existingCode= sharedCourseDataBase.getAllSharedCourseName();
        String code;
        File file;
        //La taille de la liste au debut.
        int startSize = existingCode.size();

        //On regarde que le code est toujours unique
        for(int i=0;i<500;i++){
            //On recupere les codes existant
            existingCode = sharedCourseDataBase.getAllSharedCourseName();
            //On genere le code
            code = sharedCourseDataBase.generateSharedCourseCode();
            //Le code est unique
            assertEquals(false,existingCode.contains(code));

            //On l'ajoute pour l'iteration suivante
            file = new File(directory,code);
            fileManager = new FileManager(file);
            //Ici on le creer
            fileManager.write("something");
        }
        //On a bien ajouter 500 codes
        existingCode= sharedCourseDataBase.getAllSharedCourseName();
        assertEquals(startSize+500,existingCode.size());
    }

    @Test
    public void createSharedCourseFileTest(){
        File file;

        for(int i=0;i<100;i++){
            String fileName = "coucou"+i;
            //On recupere l'objet File creer
            file = sharedCourseDataBase.createSharedCourseFile(fileName);
            //Le nom est le meme
            assertEquals(fileName,file.getName());
            //Il est dans le bon dossier
            assertEquals(directory.getName(),file.getParentFile().getName());
        }
    }

    @Test
    public void addShareCourseTest(){
        String code;
        String content;
        File file;
        FileManager fileManager;

        for(int i = 0; i<100;i++){
            code = ""+i;
            content = "content "+i;
            //On creer le fichier
            sharedCourseDataBase.addShareCourse(code,content);
            //On recupere le fichier
            file = sharedCourseDataBase.findShareCourseFile(code);
            //Il existe
            assertEquals(true,file.exists());
            //son nom est correct
            assertEquals(code,file.getName());
            //Son contenu est correct
            fileManager = new FileManager(file);
            assertEquals(content,fileManager.getRaw());
            //Il est creer au bon endroit.
            assertEquals(directory.getName(),file.getParentFile().getName());
        }
    }

    @Test
    public void findShareCourseFileTest(){
        //On retrouver des fichiers existant
        File file1 = sharedCourseDataBase.findShareCourseFile("00000");
        //C'est fichier avec un nom correct
        assertEquals(true,file1.isFile());
        assertEquals("00000",file1.getName());
        //Le contenu et le repertoire parent est bon
        FileManager fileManager = new FileManager(file1);
        assertEquals("content1",fileManager.getRaw());
        assertEquals(directory.getName(),file1.getParentFile().getName());

        //On retrouver des fichiers existant
        File file2 = sharedCourseDataBase.findShareCourseFile("11111");
        //C'est fichier avec un nom correct
        assertEquals(true,file2.isFile());
        assertEquals("11111",file2.getName());
        //Le contenu et le repertoire parent est bon
        fileManager = new FileManager(file2);
        assertEquals("content2",fileManager.getRaw());
        assertEquals(directory.getName(),file2.getParentFile().getName());

        //On retrouver des fichiers existant
        File file3 = sharedCourseDataBase.findShareCourseFile("22222");
        //C'est fichier avec un nom correct
        assertEquals(true,file3.isFile());
        assertEquals("22222",file3.getName());
        //Le contenu et le repertoire parent est bon
        fileManager = new FileManager(file3);
        assertEquals("content3",fileManager.getRaw());
        assertEquals(directory.getName(),file3.getParentFile().getName());

        //On cherche un fichier inexistant
        File error = sharedCourseDataBase.findShareCourseFile("12345");
        assertEquals(null,error);
    }

    @Test
    public void loadShareCourseTest(){
        //on recupere le contenu des fichiers
        String content1 = sharedCourseDataBase.loadShareCourse("00000");
        assertEquals("content1",content1);

        String content2 = sharedCourseDataBase.loadShareCourse("11111");
        assertEquals("content2",content2);

        String content3 = sharedCourseDataBase.loadShareCourse("22222");
        assertEquals("content3",content3);

        //On recupere le contenu de fichier inexistant.
        String error = sharedCourseDataBase.loadShareCourse("12345");
        assertEquals(null,error);
    }

    @Test
    public void verifyCodeTest(){
        //Le code existe dans la DB
        assertEquals(true,sharedCourseDataBase.verifyCode("00000"));
        //Le code existe dans la DB
        assertEquals(true,sharedCourseDataBase.verifyCode("11111"));
        //Le code existe dans la DB
        assertEquals(true,sharedCourseDataBase.verifyCode("22222"));

        for(int i=0;i<100;i++){
            //Code qui n'existe pas encore
            String code = sharedCourseDataBase.generateSharedCourseCode();
            //Il n'est donc pas dans la DB
            assertEquals(false,sharedCourseDataBase.verifyCode(code));
            //On l'ajoute
            sharedCourseDataBase.addShareCourse(code,"smthg");
            //Le code est dans la DB maintenant
            assertEquals(true,sharedCourseDataBase.verifyCode(code));
        }
    }


    @AfterEach
    public void delete(){
        for(File file : directory.listFiles()){
            file.delete();
        }
        directory.delete();
    }


}
