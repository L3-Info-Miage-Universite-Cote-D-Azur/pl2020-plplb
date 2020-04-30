package dataBase;


import file.FileManager;
import log.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

/**
 * SharedCourseDataBase permet la gestion des parcours partages de l'application.
 * Les parcours partages sont des parcours que des clients ont voulu rendre accessible
 * aux autres clients. Ces parcours sont des fichiers qui sont generes automatiquement.
 * Chaque fichier est compose de 10 chiffres aleatoire.
 */
public class SharedCourseDataBase {

    /** Le repertoire des sauvegardes des parcours partages. */
    private File directory;

    /* CONSTRUCTOR */
    /**
     * Constructeur de base
     * @param directory | Repertoire des fichiers des parcours partages
     */
    public SharedCourseDataBase(File directory){
        this.directory = directory;
    }

    /**
     * Renvoie le nom de tout les parcours partages.
     * @return une arrayList de String (qui sont les noms).
     */
    public ArrayList<String> getAllSharedCourseName(){
        ArrayList<String> allSharedCourseName = new ArrayList<>();

        //Pour chacunes des sauvegardes
        for(File sharedSave : directory.listFiles()){
            //On ajoute le code (leur nom)
            allSharedCourseName.add(sharedSave.getName());
        }
        return  allSharedCourseName;
    }

    /**
     * Renvoie un code (string) unique genere aleatoirement.
     * @return le code unique.
     */
    public String generateSharedCourseCode(){
        //On charge les codes deja existant
        ArrayList<String> existingCode = this.getAllSharedCourseName();

        Random random = new Random();
        String code = "todo";

        //Tant que le code n'a pas été créer ou qu'il existe déjà.
        while(code == "todo" || existingCode.contains(code)){
            //On reinitialise le code.
            code = "";

            //On ajoute 5 fois un chiffre aléatoire entre 0 et 9
            for(int i = 0; i < 5;i++){
                code  += ""+random.nextInt(10);
            }
        }
        //On retourne le code.
        return code;
    }

    /**
     * Creer l'objet file du parcours partage.
     * @param fileName : le nom du parcours partage
     * @return l'objet File.
     */
    public File createSharedCourseFile(String fileName){
        File shareFile = new File(directory,fileName);//On creer l'objet file dans directory.
        //on le renvoie.
        return shareFile;
    }

    /**
     * Ajoute un parcours partage dans la DB et renvoie le nom du fichier (le code)
     * @param code : le code que l'on veut ajouter
     * @param content : le contenu du parcours partage.
     */
    public void addShareCourse(String code, String content){
        //On creer l'objet File
        File shareFile = this.createSharedCourseFile(code);
        FileManager fileManager = new FileManager(shareFile);

        //On ecrit dans le fichier. (fileManager s'occupe de le creer)
        fileManager.write(content);

        Logger.log("Create share course :"+code);
    }

    /**
     * Renvoie le fichier du nom donne en parametre, ou null si il n'est pas trouve
     * @param shareCourseName : le nom du fichier
     * @return File : le fichier, null si il n'est pas trouve
     */
    public File findShareCourseFile(String shareCourseName){
        for(File shareCourse : directory.listFiles()){
            //On a trouve un nom qui correspond a un fichier
            if(shareCourse.getName().equals(shareCourseName)){
                return shareCourse;
            }
        }
        //On n'as rien trouvé
        return null;
    }

    /**
     * Charge le contenu d'un fichier partage
     * @param shareCourseName : le nom du fichier partage
     * @return le contenu du fichier sous forme de string.
     */
    public String loadShareCourse(String shareCourseName){
        //On recherche le fichier
        File shareCourse = this.findShareCourseFile(shareCourseName);

        //Si rien n'a été trouvé.
        if(shareCourse == null){
            Logger.error("Can't find the share file "+shareCourseName+ " : No such file in DB.");
            return null;
        }
        FileManager fileManager = new FileManager(shareCourse);
        String content = fileManager.getRaw();

        Logger.log("load content in share file "+shareCourseName);
        return content;
    }

    /**
     * verifie si un code est un code existant dans la base de donnees;
     * @param code : le code a verifier
     * @return true ou false, si le code existe.
     */
    public boolean verifyCode(String code){
        ArrayList<String> existingCode = this.getAllSharedCourseName();
        return existingCode.contains(code);
    }

}
