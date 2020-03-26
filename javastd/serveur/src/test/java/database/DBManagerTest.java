package database;

import metier.Categorie;
import metier.UE;
import metier.parcours.Parcours;
import metier.semestre.Semestre;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.DBManager;
import database.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class DBManagerTest {
    FileManager fileManager;
    DBManager dbManager;
    File directory = new File("db");

    @BeforeEach
    public void init(){
        File directory = new File("db");
        if(directory.exists()){
            File[] files = directory.listFiles();

            for(int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            directory.delete();
        }
    }

    @AfterEach
    public void init2(){
        if(directory.exists()){
            File[] files = directory.listFiles();

            for(int i = 0; i < files.length; i++) {
                files[i].delete();
            }
            directory.delete();
        }
    }

    @Test
    public void constructorTestString(){
        //Le repertoire n'existe pas
        assertEquals(true,directory.exists());

        dbManager = new DBManager("testDir","testFile");

        //Le repertoire est cree.
        assertEquals(true,directory.exists());
        //Le fichier n'est pas encore cree.
        assertEquals(false, dbManager.getCourse().getFile().exists());
        assertEquals(false, dbManager.getDir().getFile().exists());
        //Le nom est correct
        assertEquals("db\\testDir\\testFile.txt",dbManager.getCourse().getFile().toString());
    }

    @Test
    public void constructorTestWithEmptyName(){
        //Le repertoire n'existe pas
        assertEquals(true,directory.exists());

        dbManager = new DBManager();

        //Le repertoire est cree.
        assertEquals(true,directory.exists());
        //Le fichier n'est pas encore cree.
        assertEquals(false, dbManager.getCourse().getFile().exists());
        assertEquals(false, dbManager.getDir().getFile().exists());
        //Le nom est correct (par defaut)
        assertEquals("db\\defaultStudent\\defaultCourse.txt", dbManager.getCourse().getFile().toString());
    }

    @Test
    public void constructorTestWithFile(){
        //Le repertoire n'existe pas
        assertEquals(true,directory.exists());

        File dir = new File("monTestDir");
        File file = new File("monTestFile");
        dbManager = new DBManager(dir, file);

        //Le fichier n'est pas encore cree.
        assertEquals(false, dbManager.getCourse().getFile().exists());
        assertEquals(false, dbManager.getDir().getFile().exists());
        //Le nom est correct
        //assertEquals("db\\monTestDir\\monTestFile",dbManager.getCourse().getFile().toString());
        //assertEquals("db\\monTestDir",dbManager.getDir().getFile().toString());
    }

    @Test
    public void setCurrentDirTestString ()
    {
        dbManager = new DBManager("D1", "F1");

        //Le nom du file n'est pas le meme.
        assertEquals("db\\D1", dbManager.getDir().getFile().toString());
        assertNotEquals("db\\D2", dbManager.getDir().getFile().toString());
        assertEquals("db\\D1\\F1.txt", dbManager.getCourse().getFile().toString());
        assertNotEquals("db\\D1\\F2.txt", dbManager.getCourse().getFile().toString());

        dbManager.setCurrentDir("D2");
        //Le fichier a changer et on a ajouter "db"
        assertEquals("db\\D2", dbManager.getDir().getFile().toString());

        //Le fichier n'existe pas
        assertEquals(false,dbManager.getCourse().getFile().exists());

        //ON ECRIT DIRECTEMENT DB DANS LE NOM DU FICHIER :

        //le fichier n'as pas changer
        assertNotEquals("db\\D3", dbManager.getDir().getFile().toString());

        dbManager.setCurrentDir("db/D3");

        //le fichier a changer et "db" n'est pas ajouter deux fois.
        assertEquals("db\\D3", dbManager.getDir().getFile().toString());
        //Le fichier n'existe pas
        assertEquals(false,dbManager.getCourse().getFile().exists());
    }

    @Test
    public void setFileTestWithFile(){
        dbManager = new DBManager("dossier","autreFichier");

        //Le nom du file n'est pas le meme.        
        assertEquals("db\\dossier\\autreFichier.txt", dbManager.getCourse().getFile().toString());
        assertNotEquals("db\\dossier\\monTest2.txt", dbManager.getCourse().getFile().toString());

        File file = new File("monTest2");
        dbManager.setCourseFile(file);

        //Le fichier a changer et on a ajouter "db"
        assertEquals("db\\dossier\\monTest2", dbManager.getCourse().getFile().toString());

        //Le fichier n'existe pas
        assertEquals(false,dbManager.getCourse().getFile().exists());

        //ON ECRIT DIRECTEMENT DB DANS LE NOM DU FICHIER :

        //le fichier n'as pas changer
        assertNotEquals("db\\dossier\\monTest3.txt", dbManager.getCourse().getFile().toString());

        file = new File("db/dossier/monTest3");
        dbManager.setCourseFile(file);

        //le fichier a changer et "db" n'est pas ajouter deux fois.
        assertEquals("db\\dossier\\monTest3", dbManager.getCourse().getFile().toString());
        //Le fichier n'existe pas
        assertEquals(false,dbManager.getCourse().getFile().exists());
    }

    @Test
    public void saveTest() throws IOException {
        dbManager = new DBManager("NomEtudiant","NomParcours");

        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add("NomParcours");
        arrayList.add("TypeParcours");
        arrayList.add("UE1");
        arrayList.add("UE2");

        String expectedContent = "[\"TypeParcours\",\"UE1\",\"UE2\"]";
        String expectedFilename = "NomParcours.txt";
        String expectedDirname = "NomEtudiant";

        dbManager.save(arrayList);
        //Le fichier contient maintenant l'ue.
        assertEquals(expectedContent, dbManager.getCourse().getFileContent());
        assertEquals(expectedDirname, dbManager.getDir().getFile().getName());
        assertEquals(expectedFilename, dbManager.getCourse().getFile().getName());
    }

    @Test
    public void loadTest(){
        dbManager = new DBManager("dossier","monFichierTest");
        UE ue = new UE("MonTestName","MonTestCode");
        ArrayList<UE> a = new ArrayList<UE>();
        ArrayList<String> als = new ArrayList<String>();
        als.add("MonTestCode");
        a.add(ue);

        Categorie categorie = new Categorie("Categorie test",a);
        ArrayList<Categorie> b = new ArrayList<Categorie>();
        b.add(categorie);        

        //Le fichier n'existe pas.
        assertEquals("db\\dossier\\monFichierTest.txt", dbManager.getCourse().getFile().toString());
        assertEquals(true, dbManager.getCourse().exists());

        //ON CREER LE FICHIER QUI CONTIENT QUELQUE CHOSE.
        ArrayList<String> expected = new ArrayList<String>();
        expected.add("Informatique");
        expected.add("SPUGDE10");
        expected.add("SPUGDC10");
        expected.add("SPUGDI10");
        
        dbManager.getDir().getFile().mkdir();
        dbManager.getCourse().create();
        dbManager.getCourse().write("[\"Informatique\",\"SPUGDE10\",\"SPUGDC10\",\"SPUGDI10\"]");
        //Le fichier existe
        assertEquals("db\\dossier\\monFichierTest.txt",dbManager.getCourse().getFile().toString());
        assertEquals(true,dbManager.getCourse().exists());

        ArrayList<String> res = dbManager.load("monFichierTest");
        assertEquals(res.size(), expected.size());
        for (int i = 0; i < expected.size(); i++)
        {
        	assertEquals(res.contains(expected.get(i)), true);
        }
    }
}
