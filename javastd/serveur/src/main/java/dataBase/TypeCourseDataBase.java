package dataBase;

import file.FileManager;
import log.Logger;

import java.io.File;
import java.util.ArrayList;

/**
 * TypeCourseDataBase est la classe qui permet la gestion des parcours types.
 * Un parcours type est un parcours pregenere que le client a le droit de charger
 * pour creer un nouveau parcours.
 */
public class TypeCourseDataBase {

    /** Le repertoire des parcours types. */
    private File directory;

    /** la liste des parcours type sous JSON */
    public ArrayList<String> allParcoursType;

    /* CONSTRUCTOR */
    /**
     * Constructeur de base
     * @param directory | Repertoire des parcours types
     */
    public TypeCourseDataBase(File directory){
        this.directory = directory;
    }

    /**
     * Permet de charger le contenue des fichier des parcours type
     * @return si l'initialisation c'est fait correctement
     */
    public boolean initParcoursType(){
        if(!directory.exists() || ! directory.isDirectory()){
            Logger.error("can load Course Type directory");
            return false;
        }

        ArrayList<String> result = new ArrayList<String>();
        for(File courseType : directory.listFiles()){
            FileManager current = new FileManager(courseType);
            String content = current.getRaw();
            result.add(current.getRaw());
        }
        if(result.size()==0){
            return false;
        }

        allParcoursType = result;
        return true;
    }

    /**
     * Renvoie si la liste des semestre a etait charger depuis les fichier
     * @return true -> initialiser; false -> non initialiser
     */
    public boolean isInit(){
        return allParcoursType!=null && allParcoursType.size()!=0;
    }

    /**
     * Permet de charger les list des parcours type
     * @return la list des parcours type en Json
     */
    public ArrayList<String> loadTypeCourseJson(){
        if(!isInit()){
            initParcoursType();
        }
        return allParcoursType;
    }
}
