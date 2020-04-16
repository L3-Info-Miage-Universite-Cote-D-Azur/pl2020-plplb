package dataBase;

import file.FileManager;
import log.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TypeCourseDataBaseTest {

    String path = "testTypeCourseDB";
    File directory;

    @BeforeEach
    public void init() {
        Logger.verbose = false;
        this.directory = new File(path);
        this.directory.mkdir();
    }

    @Test
    public void isInitTest(){
        TypeCourseDataBase typeCourseDataBase = new TypeCourseDataBase(directory);
        //le parcours n'est pas init
        assertEquals(typeCourseDataBase.isInit(),false);

        //on init mais il a pas de fichier dans le directory il n'est donc toujour pas init
        typeCourseDataBase.initParcoursType();
        assertEquals(typeCourseDataBase.isInit(),false);

        //on initialise avec un dossier non vide
        FileManager parcoursT1 = new FileManager(new File(directory,"parcoursT1"));
        parcoursT1.create();
        typeCourseDataBase.initParcoursType();
        //le parcours type est bien charger
        assertEquals(typeCourseDataBase.isInit(),true);
    }

    @Test
    public void initParcoursTypeTest(){
        TypeCourseDataBase typeCourseDataBase = new TypeCourseDataBase(directory);
        boolean canInit = typeCourseDataBase.initParcoursType();
        //le parcours ne peut pas s'initialise rsans avoir au moins un parcours type:
        assertEquals(canInit,false);
        assertEquals(typeCourseDataBase.isInit(),false);

        //on rajoute un fichier(ou plusieur)
        FileManager parcoursT1 = new FileManager(new File(directory,"parcoursT1"));
        parcoursT1.create();

        canInit = typeCourseDataBase.initParcoursType();
        //on a bien pue initialiser
        assertEquals(canInit,true);
        assertEquals(typeCourseDataBase.isInit(),true);

    }

    @Test
    public void loadCourseTypeJsonTest(){
        TypeCourseDataBase typeCourseDataBase = new TypeCourseDataBase(directory);
        ArrayList<String> typecourse = typeCourseDataBase.loadTypeCourseJson();
        //il a pas de fichier on a donc une array list vide
        assertTrue(typecourse==null||typecourse.size()==0);

        //on crée 2 parcours
        FileManager parcoursT1 = new FileManager(new File(directory,"parcoursT1"));
        parcoursT1.create();
        FileManager parcoursT2 = new FileManager(new File(directory,"parcoursT2"));
        parcoursT2.create();

        typecourse = typeCourseDataBase.loadTypeCourseJson();
        //on a bien les 2 parcours de charger
        assertEquals(typecourse.size(),2);



    }



    @AfterEach
    public void clearFile(){
        for(File file :directory.listFiles()){
            file.delete();
        }
        directory.delete();
    }
}
