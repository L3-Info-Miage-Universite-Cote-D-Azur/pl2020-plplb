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

public class CourseDataBaseTest {

    private CourseDataBase courseDataBase;
    private File directory;
    private FileManager fileManager;

    @BeforeEach
    public void init(){
        //On mute l'envoie du serveur
        Logger.verbose = false;

        //Creation du dossier principal
        directory = new File("TestDirectory");
        //System.out.println(directory.getAbsolutePath());
        directory.mkdir();

        //Creation des dossiers etudiants
        File studentDirectory1 = new File(directory,"aa123456");
        studentDirectory1.mkdir();
        //Creation de save
        File save1 = new File(studentDirectory1,"save1");
        fileManager = new FileManager(save1);
        fileManager.write("content1");
        File save2 = new File(studentDirectory1,"save2");
        fileManager = new FileManager(save2);
        fileManager.write("content2");

        //Creation des dossiers etudiants
        File studentDirectory2 = new File(directory,"bb123456");
        studentDirectory2.mkdir();
        //Creation de save
        File save3 = new File(studentDirectory2,"save3");
        fileManager = new FileManager(save3);
        fileManager.write("content3");
        File save4 = new File(studentDirectory2,"save4");
        fileManager = new FileManager(save4);
        fileManager.write("content4");
        File save5 = new File(studentDirectory2,"save5");
        fileManager = new FileManager(save5);
        fileManager.write("content5");

        //Init
        courseDataBase = new CourseDataBase(directory);
    }

    public void recursiveDelete(File rootFile){
        //Pour chaque sous fichiers/dossier
        for(File sub_file : rootFile.listFiles()){
            //Si c'est un fichier on supprime
            if(sub_file.isFile()){
                sub_file.delete();
            }
            //Si c'est un dossier
            if(sub_file.isDirectory()){
                //Appel recursif
                recursiveDelete(sub_file);
                //Puis on supprime le dossier vide.
                sub_file.delete();
            }
        }
    }

    @AfterEach
    public void deleteAll(){
        //On supprime tout les fichiers
        recursiveDelete(directory);
        //On supprime la racine
        directory.delete();
    }

    @Test
    public void getStudentSaveNamesTest(){
        //Les noms de fichiers qu'on attend
        ArrayList<String> excepted1 = new ArrayList<>();
        excepted1.add("save1");
        excepted1.add("save2");

        ArrayList<String> excepted2 = new ArrayList<>();
        excepted2.add("save3");
        excepted2.add("save4");
        excepted2.add("save5");

        //On cherche un etudiant qui n'existe pas.
        ArrayList<String> result0 = courseDataBase.getStudentSaveNames("cc123456");
        assertEquals(null,result0);

        //On cherche le premier etudiant
        ArrayList<String> result1 = courseDataBase.getStudentSaveNames("aa123456");
        //La taille doit etre la meme
        assertEquals(excepted1.size(),result1.size());
        for(int i=0;i<excepted1.size();i++){
            //Ainsi que les elements
            assertEquals(excepted1.get(i),result1.get(i));
        }

        //On cherche le deuxieme etudiant
        ArrayList<String> result2 = courseDataBase.getStudentSaveNames("bb123456");
        //La taille doit etre la meme
        assertEquals(excepted2.size(),result2.size());
        for(int i=0;i<excepted2.size();i++){
            //Ainsi que les elements
            assertEquals(excepted2.get(i),result2.get(i));
        }
    }

    @Test
    public void getStudentSaveTest(){
        File file;//Le fichier donne par courseDataBase
        String Scontent;//Le contenu du fichier voulu
        String content;//Le contenu du fichier du resultat
        File student;//Le dossier etudiant du resultat
        File rootDirectory;//Le fichier racine du resultat

        //POUR CHAQUE DOSSIER ETUDIANT
        for(File Sdirectory : directory.listFiles()){
            //C'est un dossier
            assertEquals(true,Sdirectory.isDirectory());
            //POUR CHAQUE SAVE
            for(File Sfile : Sdirectory.listFiles()){
                //C'est un fichier
                assertEquals(true,Sfile.isFile());
                file = courseDataBase.getStudentSave(Sdirectory.getName(),Sfile.getName());

                //Le nom doit etre le meme.
                assertEquals(Sfile.getName(),file.getName());

                //Le contenu doit etre le meme
                fileManager = new FileManager(Sfile);
                Scontent = fileManager.getRaw();
                fileManager = new FileManager(file);
                content = fileManager.getRaw();
                assertEquals(Scontent,content);

                //Le dossier parent doit etre le meme.
                student = file.getParentFile();
                assertEquals(Sdirectory.getName(),student.getName());

                //Le dossier racine est bien le bon
                rootDirectory = student.getParentFile();
                assertEquals(directory.getName(),rootDirectory.getName());
            }
        }
        //Le fichier n'existe pas
        File result1 = courseDataBase.getStudentSave("aa123456","save99");
        assertEquals(null,result1);
        //Le dossier n'existe aps
        File result2 = courseDataBase.getStudentSave("cc123456","save1");
        assertEquals(null,result2);
    }

    @Test
    public void loadStudentSaveTest(){
        String Scontent;//expected
        String content;//result

        //On load un fichier qui n'existe pas
        String content0 = courseDataBase.loadStudentSave("aa123456","save99");
        assertEquals(null,content0);
        //On load dans un dossier qui n'existe pas
        String content1 = courseDataBase.loadStudentSave("cc123456","save1");
        assertEquals(null,content1);

        //POUR CHAQUE DOSSIER ETUDIANT
        for(File Sdirectory : directory.listFiles()) {
            //C'est un dossier
            assertEquals(true, Sdirectory.isDirectory());
            //POUR CHAQUE SAVE
            for (File Sfile : Sdirectory.listFiles()) {
                //C'est un fichier
                assertEquals(true,Sfile.isFile());

                //Le contenu doit etre le meme
                fileManager = new FileManager(Sfile);
                Scontent = fileManager.getRaw();
                content = courseDataBase.loadStudentSave(Sdirectory.getName(),Sfile.getName());
                assertEquals(Scontent,content);
            }
        }
    }

    @Test
    public void createStudentSaveTest(){
        //On creer un nouveau fichier
        File result = courseDataBase.createStudentSave("aa123456","save88");
        //On a seulement creer l'objet
        assertEquals(false,result.exists());
        //Mais le nom correspond.
        assertEquals("save88",result.getName());
        //Le dossier est bien celui de l'etudiant
        assertEquals("aa123456",result.getParentFile().getName());
        //Et il se situe bien a la racine
        assertEquals("TestDirectory",result.getParentFile().getParentFile().getName());

        //On creer un nouveau fichier d'un nouvel etudiant.
        File result1 = courseDataBase.createStudentSave("cc123456","save6");
        //On a seulement creer l'objet
        assertEquals(false,result1.exists());
        //Mais le nom correspond.
        assertEquals("save6",result1.getName());
        //Le dossier est bien celui de l'etudiant
        assertEquals("cc123456",result1.getParentFile().getName());
        //Et il se situe bien a la racine
        assertEquals("TestDirectory",result1.getParentFile().getParentFile().getName());
    }

    @Test
    public void writeStudentSaveTest(){
        //On recupere le fichier
        File file = courseDataBase.getStudentSave("aa123456","save1");
        //Le fichier existe
        assertEquals(true,file.exists());
        //Le contenu fichier est correcte
        assertEquals("content1",courseDataBase.loadStudentSave("aa123456","save1"));

        //On ecrit dans un fichier existant
        courseDataBase.writeStudentSave("aa123456","save1","toto");
        //Le contenu fichier a changer
        assertEquals("toto",courseDataBase.loadStudentSave("aa123456","save1"));


        //On part d'un fichier qui n'existe pas
        File file1 = courseDataBase.getStudentSave("aa123456","save99");
        assertEquals(null,file1);

        //On ecrit dans le fichier (et on le creer)
        courseDataBase.writeStudentSave("aa123456","save99","tata");
        file1 = courseDataBase.getStudentSave("aa123456","save99");
        //Le fichier a ete creer
        assertEquals(true,file1.exists());
        //Le contenu fichier est correcte
        assertEquals("tata",courseDataBase.loadStudentSave("aa123456","save99"));

        courseDataBase.writeStudentSave("cd123456","save100","coucou");

        //On part d'un etudiant inexistant
        File file2 = courseDataBase.getStudentSave("cc123456","save6");
        assertEquals(null,file2);

        //On ecrit dans le fichier (et on le creer)
        courseDataBase.writeStudentSave("cc123456","save6","titi");
        file2 = courseDataBase.getStudentSave("cc123456","save6");
        //Le fichier a ete creer
        assertEquals(true,file2.exists());
        //Le contenu fichier est correcte
        assertEquals("titi",courseDataBase.loadStudentSave("cc123456","save6"));
    }

    @Test
    public void getAllStudentSavesTest(){
        ArrayList<File> result;
        File[] Sresult;

        //On cherche les sauvegardes d'un etudiant qui n'existe pas
        assertEquals(null,courseDataBase.getAllStudentSaves("cc123456"));

        //POUR CHAQUE DOSSIER ETUDIANT
        for(File Sdirectory : directory.listFiles()) {
            //C'est un dossier
            assertEquals(true, Sdirectory.isDirectory());

            result = courseDataBase.getAllStudentSaves(Sdirectory.getName());
            Sresult = Sdirectory.listFiles();

            assertEquals(Sresult.length,result.size());
            for(int i=0;i<Sresult.length;i++){
                //Le nom est le meme
                assertEquals(Sresult[i].getName(),result.get(i).getName());
                //Le contenu est le meme.
                assertEquals(
                        courseDataBase.loadStudentSave(Sdirectory.getName(),Sresult[i].getName()),
                        courseDataBase.loadStudentSave(Sdirectory.getName(),result.get(i).getName())
                );
            }
        }
    }

    @Test
    public void getAllStudentDirectoryTest(){
        //On regarde ce qu'on a actuellement
        ArrayList<File> studentDirectory = courseDataBase.getAllStudentDirectory();
        //La taille voulue
        assertEquals(2,studentDirectory.size());
        //Ils doivent etre des dossier
        assertEquals(true,studentDirectory.get(0).isDirectory());
        assertEquals(true,studentDirectory.get(1).isDirectory());
        //Les noms des dossiers doivent correspondre
        assertEquals("aa123456",studentDirectory.get(0).getName());
        assertEquals("bb123456",studentDirectory.get(1).getName());
        //Le dossier parent aussi
        assertEquals("TestDirectory",studentDirectory.get(0).getParentFile().getName());
        assertEquals("TestDirectory",studentDirectory.get(1).getParentFile().getName());


        //On creer un nouveau dossier
        File newFile = new File(directory,"cc123456");
        newFile.mkdir();

        //On regarde ce qu'on a actuellement
        studentDirectory = courseDataBase.getAllStudentDirectory();
        //La taille voulue
        assertEquals(3,studentDirectory.size());
        //Ils doivent etre des dossier
        assertEquals(true,studentDirectory.get(0).isDirectory());
        assertEquals(true,studentDirectory.get(1).isDirectory());
        assertEquals(true,studentDirectory.get(2).isDirectory());
        //Les noms des dossiers doivent correspondre
        assertEquals("aa123456",studentDirectory.get(0).getName());
        assertEquals("bb123456",studentDirectory.get(1).getName());
        assertEquals("cc123456",studentDirectory.get(2).getName());
        //Le dossier parent aussi
        assertEquals("TestDirectory",studentDirectory.get(0).getParentFile().getName());
        assertEquals("TestDirectory",studentDirectory.get(1).getParentFile().getName());
        assertEquals("TestDirectory",studentDirectory.get(2).getParentFile().getName());
    }

    @Test
    public void getStudentDirectoryTest(){
        //On recupere le premier dossier
        File dir1 = courseDataBase.getStudentDirectory("aa123456");
        //On verifie son nom et dossier parent
        assertEquals("aa123456",dir1.getName());
        assertEquals(true,dir1.isDirectory());
        assertEquals("TestDirectory",dir1.getParentFile().getName());

        //On recupere le deuxieme dossier
        File dir2 = courseDataBase.getStudentDirectory("bb123456");
        //On verifie son nom et dossier parent
        assertEquals("bb123456",dir2.getName());
        assertEquals(true,dir2.isDirectory());
        assertEquals("TestDirectory",dir2.getParentFile().getName());

        //On recupere un dossier inexistant
        File dir3 = courseDataBase.getStudentDirectory("cc123456");
        assertEquals(null,dir3);
    }

    @Test
    public void deleteSaveTest(){
        ArrayList<File> studentSave = courseDataBase.getAllStudentSaves("aa123456");
        ArrayList<String> studentSaveName = courseDataBase.getStudentSaveNames("aa123456");

        //Init
        assertEquals(2,studentSave.size());
        assertEquals(2,studentSaveName.size());

        for(File save : studentSave){
            //Toutes les sauvegardes sont la
            assertEquals(true,studentSaveName.contains(save.getName()));
        }

        //On supprime la sauvegarde
        boolean delete = courseDataBase.deleteSave("aa123456","save1");
        assertEquals(true,delete);

        studentSave = courseDataBase.getAllStudentSaves("aa123456");
        studentSaveName = courseDataBase.getStudentSaveNames("aa123456");
        //Elle n'existe plus
        assertEquals(1,studentSave.size());
        assertEquals(false,studentSaveName.contains("save1"));

        //On supprime la sauvegarde
        boolean delete2 = courseDataBase.deleteSave("aa123456","save2");
        assertEquals(true,delete2);

        studentSave = courseDataBase.getAllStudentSaves("aa123456");
        studentSaveName = courseDataBase.getStudentSaveNames("aa123456");
        //Elle n'existe plus (et plus de sauvegarde)
        assertEquals(null,studentSave);
        assertEquals(null,studentSaveName);


        //On delete un fichier qui n'existe pas
        boolean delete3 = courseDataBase.deleteSave("aa123456","save99");
        assertEquals(false,delete3);
    }

    @Test
    public void renameSaveTest(){
        File save = courseDataBase.getStudentSave("aa123456","save1");
        //Le fichier existe et son contenu est correct
        assertEquals(true, courseDataBase.getStudentSaveNames("aa123456").contains(save.getName()));
        assertEquals("content1",courseDataBase.loadStudentSave("aa123456","save1"));

        //on renomme
        boolean rename = courseDataBase.renameSave("aa123456","save1","newSave1");
        assertEquals(true,rename);
        //L'ancien fichier n'existe plus
        assertEquals(false, courseDataBase.getStudentSaveNames("aa123456").contains(save.getName()));

        //le nouveau existe et son contenu est correct.
        File newSave = courseDataBase.getStudentSave("aa123456","newSave1");
        assertEquals(true, courseDataBase.getStudentSaveNames("aa123456").contains(newSave.getName()));
        assertEquals("content1",courseDataBase.loadStudentSave("aa123456","newSave1"));


        //On renomme un fichier qui n'existe pas
        boolean rename2 = courseDataBase.renameSave("aa123456","save99","save89");
        assertEquals(false,rename2);
    }
}
