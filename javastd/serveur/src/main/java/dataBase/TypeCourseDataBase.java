package dataBase;

import log.Logger;

import java.io.File;
import java.util.ArrayList;

public class TypeCourseDataBase {

    private File directory;//Le repertoire des sauvegardes.

    //la liste des parcours type sous JSON
    public ArrayList<String> allParcoursType;




    /* CONSTRUCTOR */
    public TypeCourseDataBase(File directory){
        this.directory = directory;
    }

    /**
     * Permet de charger le contenue des fichier des parcours type
     */
    public void initParcoursType(){
        if(!directory.exists() || ! directory.isDirectory()){
            Logger.error("can load Course Type directory");
            return;
        }

        allParcoursType = new ArrayList<String>();
        for(File courseType : directory.listFiles()){
            FileManager current = new FileManager(courseType);
            String content = current.getRaw();
            allParcoursType.add(current.getRaw());
        }
    }

    /**
     * Renvoie si la liste des semestre a etait charger depuis les fichier
     * @return true -> initialiser; false -> non initialiser
     */
    public boolean isInit(){
        return allParcoursType!=null || allParcoursType.size()!=0;
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
