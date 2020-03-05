package serveur;

import metier.Categorie;
import metier.Semestre;
import metier.UE;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        assertEquals(false,directory.exists());

        dbManager = new DBManager("testFile");

        //Le repertoire est cree.
        assertEquals(true,directory.exists());
        //Le fichier n'est pas encore cree.
        assertEquals(false,dbManager.getFile().getFile().exists());
        //Le nom est correct
        assertEquals("db\\testFile",dbManager.getFile().getFile().toString());
    }

    @Test
    public void constructorTestWithEmptyName(){
        //Le repertoire n'existe pas
        assertEquals(false,directory.exists());

        dbManager = new DBManager();

        //Le repertoire est cree.
        assertEquals(true,directory.exists());
        //Le fichier n'est pas encore cree.
        assertEquals(false,dbManager.getFile().getFile().exists());
        //Le nom est correct (par defaut)
        assertEquals("db\\default.txt",dbManager.getFile().getFile().toString());
    }

    @Test
    public void constructorTestWithFile(){
        //Le repertoire n'existe pas
        assertEquals(false,directory.exists());

        File file = new File("monTest");
        dbManager = new DBManager(file);

        //Le fichier n'est pas encore cree.
        assertEquals(false,dbManager.getFile().getFile().exists());
        //Le nom est correct
        assertEquals("monTest",dbManager.getFile().getFile().toString());
    }

    @Test
    public void setFileTestString(){
        dbManager = new DBManager("autreFichier");

        //Le nom du file n'est pas le meme.
        assertEquals("db\\autreFichier", dbManager.getFile().getFile().toString());
        assertNotEquals("db\\monTest2", dbManager.getFile().getFile().toString());

        dbManager.setFile("monTest2");
        //Le fichier a changer et on a ajouter "db"
        assertEquals("db\\monTest2", dbManager.getFile().getFile().toString());

        //Le fichier n'existe pas
        assertEquals(false,dbManager.getFile().getFile().exists());

        //ON ECRIT DIRECTEMENT DB DANS LE NOM DU FICHIER :

        //le fichier n'as pas changer
        assertNotEquals("db\\monTest3", dbManager.getFile().getFile().toString());

        dbManager.setFile("db/monTest3");

        //le fichier a changer et "db" n'est pas ajouter deux fois.
        assertEquals("db\\monTest3", dbManager.getFile().getFile().toString());
        //Le fichier n'existe pas
        assertEquals(false,dbManager.getFile().getFile().exists());
    }

    @Test
    public void setFileTestWithFile(){
        dbManager = new DBManager("autreFichier");

        //Le nom du file n'est pas le meme.
        assertEquals("db\\autreFichier", dbManager.getFile().getFile().toString());
        assertNotEquals("db\\monTest2", dbManager.getFile().getFile().toString());

        File file = new File("monTest2");
        dbManager.setFile(file);

        //Le fichier a changer et on a ajouter "db"
        assertEquals("db\\monTest2", dbManager.getFile().getFile().toString());

        //Le fichier n'existe pas
        assertEquals(false,dbManager.getFile().getFile().exists());

        //ON ECRIT DIRECTEMENT DB DANS LE NOM DU FICHIER :

        //le fichier n'as pas changer
        assertNotEquals("db\\monTest3", dbManager.getFile().getFile().toString());

        file = new File("db/monTest3");
        dbManager.setFile(file);

        //le fichier a changer et "db" n'est pas ajouter deux fois.
        assertEquals("db\\monTest3", dbManager.getFile().getFile().toString());
        //Le fichier n'existe pas
        assertEquals(false,dbManager.getFile().getFile().exists());
    }

    @Test
    public void saveTestSemestreExistingFile() throws IOException {
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"Categorie test\"," +
                "\"listUE\":[{\"name\":\"MonTestName\",\"code\":\"MonTestCode\",\"checked\":false}]}]}";

        UE ue = new UE("MonTestName","MonTestCode");
        ArrayList<UE> a = new ArrayList<UE>();
        a.add(ue);

        Categorie categorie = new Categorie("Categorie test",a);
        ArrayList<Categorie> b = new ArrayList<Categorie>();
        b.add(categorie);

        Semestre semestre = new Semestre(1,b);

        dbManager = new DBManager("monFichierTest");

        try {
            dbManager.getFile().getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Le fichier existe
        assertEquals("db\\monFichierTest",dbManager.getFile().getFile().toString());
        assertEquals(true,dbManager.getFile().exists());

        //Le fichier est vide
        assertEquals(null,dbManager.getFile().getFileContent());

        dbManager.save(semestre);

        //Le fichier contient maintenant le semestre.
        assertEquals(expected,dbManager.getFile().getFileContent());


        //AUTRE TEXTE
        expected = "{\"number\":2,\"listCategorie\":[{\"name\":\"Categorie test\"," +
                "\"listUE\":[{\"name\":\"MonTestName\",\"code\":\"MonTestCode\",\"checked" +
                "\":false}]},{\"name\":\"Autre categorie test\",\"listUE\":[{\"name\":\"MonAutreTestName\"," +
                "\"code\":\"MonAutreTestCode\",\"checked\":false}]}]}";

        UE ue2 = new UE("MonAutreTestName","MonAutreTestCode");
        a.clear();
        a.add(ue2);

        Categorie categorie2 = new Categorie("Autre categorie test",a);
        b.add(categorie2);

        semestre = new Semestre(2,b);

        dbManager.save(semestre);

        //Le fichier contient l'autre semestre.
        assertEquals(expected,dbManager.getFile().getFileContent());
    }

    @Test
    public void saveTestSemestreWithNoFile() throws IOException {
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"Categorie test\"," +
                "\"listUE\":[{\"name\":\"MonTestName\",\"code\":\"MonTestCode\",\"checked\":false}]}]}";

        UE ue = new UE("MonTestName","MonTestCode");
        ArrayList<UE> a = new ArrayList<UE>();
        a.add(ue);

        Categorie categorie = new Categorie("Categorie test",a);
        ArrayList<Categorie> b = new ArrayList<Categorie>();
        b.add(categorie);

        Semestre semestre = new Semestre(1,b);

        dbManager = new DBManager("monFichierTest");

        //Le fichier n'existe pas
        assertEquals("db\\monFichierTest",dbManager.getFile().getFile().toString());
        assertEquals(false,dbManager.getFile().exists());

        dbManager.save(semestre);

        //Le fichier existe et contient le semestre
        assertEquals(true,dbManager.getFile().exists());
        assertEquals(expected,dbManager.getFile().getFileContent());
    }

    @Test
    public void loadTest(){
        dbManager = new DBManager("monFichierTest");

        //Le fichier n'existe pas.
        assertEquals("db\\monFichierTest",dbManager.getFile().getFile().toString());
        assertEquals(false,dbManager.getFile().exists());

        String res = dbManager.load();
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\"," +
                "\"listUE\":[{\"name\":\"Decouverte 1\",\"code\":\"SPUGDE10\",\"checked\":false}," +
                "{\"name\":\"Decouverte 2\",\"code\":\"SPUGDC10\",\"checked\":false}," +
                "{\"name\":\"Decouverte 1\",\"code\":\"SPUGDI10\",\"checked\":false}]}," +
                "{\"name\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Bases de l\\u0027informatique\"," +
                "\"code\":\"SPUF10\",\"checked\":false},{\"name\":\"Introduction a l\\u0027informatique par le web\"," +
                "\"code\":\"SPUF11\",\"checked\":false}]},{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":\"Fondements 1\"," +
                "\"code\":\"SPUM11\",\"checked\":false},{\"name\":\"Complements 1\",\"code\":\"SPUM13\",\"checked\":false}," +
                "{\"name\":\"Methodes - approche continue\",\"code\":\"SPUM12\",\"checked\":false}]}," +
                "{\"name\":\"SCIENCES DE LA VIE\",\"listUE\":[{\"name\":\"Genetique. evolution. origine vie \\u0026 biodiversite\"," +
                "\"code\":\"SPUV101\",\"checked\":false},{\"name\":\"Org et mecan. moleculaires - cellules eucaryotes\"," +
                "\"code\":\"SPUV100\",\"checked\":false},{\"name\":\"Structure microscopique de la Categorie\"," +
                "\"code\":\"SPUC10\",\"checked\":false}]},{\"name\":\"ELECTRONIQUE\"," +
                "\"listUE\":[{\"name\":\"Electronique numerique - Bases\",\"code\":\"SPUE10\",\"checked\":false}]}," +
                "{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion\",\"code\":\"SPUA10\",\"checked\":false}]}," +
                "{\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique 1\",\"code\":\"SPUP10\",\"checked\":false}]}," +
                "{\"name\":\"SCIENCES DE LA TERRE\",\"listUE\":[{\"name\":\"Decouverte des sciences de la terre\"," +
                "\"code\":\"SPUT10\",\"checked\":false}]},{\"name\":\"MATH ENJEUX\",\"listUE\":[{\"name\":\"Math enjeux 1\"," +
                "\"code\":\"SPUS10\",\"checked\":false}]},{\"name\":\"COMPETENCES TRANSVERSALE\"," +
                "\"listUE\":[{\"name\":\"Competences transversales\",\"code\":\"KCTTS1\",\"checked\":false}]}," +
                "{\"name\":\"FABLAB\",\"listUE\":[{\"name\":\"Fablab S1\",\"code\":\"SPUSF100\",\"checked\":false}]}]}";

        //On recupere s1 de semestreSample;
        assertEquals(res,expected);

        //ON CREER LE FICHIER QUI CONTIENT QUELQUE CHOSE.
        try {
            dbManager.getFile().getFile().createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        dbManager.getFile().write("MaPhraseTest");

        //Le fichier existe
        assertEquals("db\\monFichierTest",dbManager.getFile().getFile().toString());
        assertEquals(true,dbManager.getFile().exists());

        res = dbManager.load();
        //On recupere bien ce qu'il y a dans le fichier.
        assertEquals(res,"MaPhraseTest");

        //AUTRE CONTENT
        dbManager.getFile().write("MonAutrePhraseTest");
        res = dbManager.load();

        //On recupere bien ce qu'il y a dans le fichier.
        assertEquals(res,"MonAutrePhraseTest");
    }
}
