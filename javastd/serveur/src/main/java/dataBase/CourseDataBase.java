package dataBase;

import file.FileManager;
import log.Logger;

import java.io.File;
import java.util.ArrayList;

public class CourseDataBase {

    private File directory;//Le repertoire des sauvegardes.

    /* CONSTRUCTOR */
    public CourseDataBase(File directory){
        this.directory = directory;
    }


    /* METHODS */

    /**
     * renvoie la liste des noms de sauvegardes de l'etudiant.
     * @param studentName : l'etudiant
     * @return une arraylist de string qui contient le nom des fichiers de sauvegardes.
     */
    public ArrayList<String> getStudentSaveNames(String studentName){
        ArrayList<String> studentSave = new ArrayList<>();
        //On retrouve toutes ses sauvegardes (uniquement les fichiers)
        ArrayList<File> allStudentSaves = this.getAllStudentSaves(studentName);

        if(allStudentSaves != null){//Si il y a des sauvegardes.
            for(File saves : allStudentSaves){
                    studentSave.add(saves.getName());//On ajoute le nom du fichier.
            }
            return studentSave;//Ne peut pas etre vide.
        }
        else{
            //Il n'y a pas de sauvegardes ou le directory n'existe pas.
            return  null;
        }
    }

    /**
     * retourn le fichier de la sauvegarde de l'etudiant
     * @param studentName : le nom de l'etudiant
     * @param saveName : le nom de la sauvegarde
     * @return un objet File du fichier de la sauvegarde.
     */
    public File getStudentSave(String studentName, String saveName){
        ArrayList<File> allStudentSaves = this.getAllStudentSaves(studentName);

        if(allStudentSaves != null){//Si il y a des sauvegardes.
            for(File save : allStudentSaves){
                if(save.getName().equals(saveName)){//Si le nom de la sauvegarde est trouve
                    return save;//On retourne le fichier de la sauvegarde.
                }
            }
        }
        return null;//On n'a rien trouver
    }

    /**
     * renvoie le contenu de la sauvegarde de l'etudiant.
     * @param studentName : le nom de l'etudiant.
     * @param saveName : le nom de la sauvegarde.
     * @return null si rien trouve, le contenu du fichier sinon.
     */
    public String loadStudentSave(String studentName, String saveName){
        //On recherche le fichier de sauvegarde.
        File saveFile = this.getStudentSave(studentName,saveName);

        if(saveFile != null){//Si on a trouver le fichier.
            FileManager fileManager = new FileManager(saveFile);
            String content = fileManager.getRaw();//On recupere le contenue du fichier.

            Logger.log("Load save "+saveName+" for "+studentName);
            //On renvoie le contenu du fichier
            return  content;
        }
        return null; //Le fichier n'existe pas.
    }

    /**
     * creer un objet File de sauvegarde pour l'etudiant.
     * @param studentName : le nom de l'etudiant
     * @param saveName : le nom de sa sauvegarde
     * @return un objet File qui represente le fichier de sauvegarde.
     */
    public File createStudentSave(String studentName, String saveName){
        //On retrouve le dossier de l'etudiant
        File studentDirectory = this.getStudentDirectory(studentName);

        //Si son dossier n'existe pas encore.
        if(studentDirectory == null){
            //On le creer
            studentDirectory = new File(directory,studentName);
            Logger.log("Create Directory for "+studentName);
            //On le creer aussi rééellement
            studentDirectory.mkdir();
        }

        File saveFile = new File(studentDirectory,saveName);//On creer l'objet file.
        //on le renvoie.
        return saveFile;
    }

    /**
     * ecrit la sauvegarde dans le fichier de sauvegarde de l'etudiant
     * @param studentName : le nom de l'etudiant.
     * @param saveName : le nom de la sauvegarde.
     * @param save : le contenu de la sauvegarde.
     */
    public void writeStudentSave(String studentName,String saveName, String save){
        File saveFile = this.getStudentSave(studentName,saveName);

        if(saveFile == null){//Si le fichier n'existe pas.
            //On creer l'objet File.
            saveFile = this.createStudentSave(studentName,saveName);
        }
        FileManager fileManager = new FileManager(saveFile);//On relie la save au filemanager
        //Le fileManager s'occupe de géréer la creation eventuelle
        //du fichier, des erreurs du au droit d'ecriture, ect....
        fileManager.write(save);//On ecrit le contenue save dans le fichier.
        Logger.log("Write save "+saveName+" for "+studentName);
    }

    /**
     * renvoie toute les sauvegardes d'un etudiant
     * @param studentName : le nom de l'etudiant
     * @return une arraylist de files qui sont les sauvegardes du client.
     */
    public ArrayList<File> getAllStudentSaves(String studentName){
        ArrayList<File> allStudentSaves = new ArrayList<>();
        //On retrouve le repertoire de l'etudiant.
        File studentDirectory = this.getStudentDirectory(studentName);

        //Si le directory de l'etudiant n'as pas été trouvé.
        if(studentDirectory == null){
            return null;
        }
        else {
            //On ajoute chacune de ses sauvegardes.
            for(File saves : studentDirectory.listFiles()){
                if(saves.isFile()){//Si c'est un fichier.
                    allStudentSaves.add(saves);
                }
            }
        }

        //Si il n'y a pas de sauvegardes.
        if(allStudentSaves.size() == 0){
            Logger.error("No saves for student : "+studentName+" in database.");
            return null;
        }

        return allStudentSaves;
    }


    /**
     * renvoie la liste des dossiers de chaque étudiant
     * @return une arrayList de file (qui sont des directory).
     */
    public ArrayList<File> getAllStudentDirectory(){
        ArrayList<File> allStudentDirectory = new ArrayList<>();

        //Pour tout les dossier etudiant dans directory
        for(File file : directory.listFiles()){
            allStudentDirectory.add(file);//On ajoute.
        }
        return allStudentDirectory;
    }

    /**
     * retourne le directory de l'etudiant
     * @param studentName : le nom de l'etudiant.
     * @return un objet File, qui est un directory.
     */
    public File getStudentDirectory(String studentName){
        //On parcours tout les directory du dossier directory.
        for(File studentDirectory : this.getAllStudentDirectory()) {
            //Si on trouve le dossier de l'etudiant
            if (studentDirectory.getName().equals(studentName)) {
                return studentDirectory;
            }
        }
        return null; //L'etudiant n'existe pas dans la DB.
    }

    /**
     * supprimer un fichier de sauvegarde de l'etudiant
     * @param studentName : le nom de l'etudiant
     * @param saveName : le nom de la sauvegarde
     * @return true ou false selon la reussite de la suppression.
     */
    public boolean deleteSave(String studentName, String saveName){
        File saveFile = this.getStudentSave(studentName,saveName);

        if(saveFile == null){//Si le fichier n'existe pas, on ne peut pas le delete.
            Logger.error("Can't delete "+saveName+" for "+studentName+" : No such file.");
            return false;
        }

        //On affecte le file au fileManager.
        FileManager fileManager = new FileManager(saveFile);
        //On essaye de le supprimer.
        boolean isDeleted = fileManager.deleteFile();

        //On renvoie le resultat.
        return isDeleted;
    }

    /**
     * renomme le fichier de sauvegarde de l'etudiant.
     * @param studentName : le nom de l'etudiant
     * @param saveName : l'ancien nom du fichier
     * @param newSaveName : le nouveau nom du fichier.
     * @return
     */
    public boolean renameSave(String studentName, String saveName, String newSaveName){
        File oldSave = this.getStudentSave(studentName,saveName);

        if(oldSave == null){//Si l'ancien fichier n'existe pas.
            Logger.error("Can't rename "+saveName+" for "+studentName+" : No such file.");
            return false;
        }
        //On recupere le contenue du fichier
        String content = this.loadStudentSave(studentName,saveName);
        //On supprime l'ancien fichier.
        boolean isDelete = this.deleteSave(studentName,saveName);

        //On recreer ou ecrit dans le nouveau fichier le contenu de l'ancien.
        if(isDelete){//Seulement si on a reussi a le supprimer.
            this.writeStudentSave(studentName,newSaveName,content);
        }

        return isDelete;//retourne le resultat du renommage.
    }

}
