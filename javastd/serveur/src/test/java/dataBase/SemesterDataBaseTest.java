package dataBase;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import file.FileManager;
import log.Logger;
import metier.semestre.SemestreRules;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class SemesterDataBaseTest {

    String path = "testSemesterDB";
    File directory;
    Gson gson = new GsonBuilder().create();

    SemesterDataBase semesterDataBase;

    FileManager ruleS1;
    FileManager ruleS2;

    @BeforeEach
    public void init(){
        Logger.verbose = false;

        this.directory = new File(path);
        this.directory.mkdir();

        FileManager ueFileManager = new FileManager(new File(this.directory,"ue.csv"));
        ueFileManager.create();


        ruleS1 = new FileManager(new File(this.directory,"s1rule"));
        ruleS1.write(gson.toJson(new SemestreRules(1,1,null)));

        ruleS2 = new FileManager(new File(this.directory,"s2rule"));
        ruleS2.write(gson.toJson(new SemestreRules(2,2,null)));
    }

    @Test
    public void isInitTest(){
        semesterDataBase = new SemesterDataBase(directory,2);
        //pas initialiser au debut
        assertEquals(semesterDataBase.isInit(),false);

        semesterDataBase = new SemesterDataBase(directory,0);
        //si on a une liste de semestre vide ce n'est pas initialiser non plus
        semesterDataBase.initSemesterList();
        assertEquals(semesterDataBase.isInit(),false);

        //on initialise bien
        semesterDataBase = new SemesterDataBase(directory,2);
        semesterDataBase.initSemesterList();
        assertEquals(semesterDataBase.isInit(),true);


    }


    @Test
    public void correctInitSemesterListTest(){
        semesterDataBase = new SemesterDataBase(directory,2);
        Boolean canInit = semesterDataBase.initSemesterList();

        //l'initialisation a bien aboutie
        assertTrue(canInit);
        //on verifie que l'on a bien nos 2 semestre
        assertEquals(semesterDataBase.getSemesterList().size(),2);
    }

    @Test
    public void badInitSemesterListTest(){
        semesterDataBase = new SemesterDataBase(directory,3);
        Boolean canInit = semesterDataBase.initSemesterList();

        //l'initialisation ne trouve pas le 3eme fichier des regle de semestre
        assertFalse(canInit);
        assertEquals(semesterDataBase.isInit(),false);

        //on initialise correctement mais un fichier semestre rule ne contient pas les bonne donner
        ruleS2.write(""); //on le vide il peut plus etre parse normalement
        semesterDataBase = new SemesterDataBase(directory,2);
        canInit = semesterDataBase.initSemesterList();

        //l'initialisation n'a pas abouti
        assertFalse(canInit);
        assertEquals(semesterDataBase.isInit(),false);

    }

    @Test
    public void reinitSemesterList(){
        //on initialise correctement
        semesterDataBase = new SemesterDataBase(directory,2);
        Boolean canInit = semesterDataBase.initSemesterList();
        assertTrue(canInit);
        assertEquals(semesterDataBase.isInit(),true);

        //on supprime un fichier de regle:
        ruleS2.deleteFile();

        //on reinitialise:
        canInit = semesterDataBase.initSemesterList();

        //l'initialisation a echouer
        assertFalse(canInit);
        //on a garder les ancienne donner valide
        assertEquals(semesterDataBase.isInit(),true);

    }

    @Test
    public void loadFileTest(){
        semesterDataBase = new SemesterDataBase(directory,2);
        //il charge le fichier si il est pas null
        assertTrue(semesterDataBase.loadFile("ue.csv")!=null);

        //si le fichier n'existe pas:
        assertTrue(semesterDataBase.loadFile("fdqsd")==null);
    }

    @AfterEach
    public void clearFile(){
        for(File file :directory.listFiles()){
            file.delete();
        }
        directory.delete();
    }

}
