package dataBase;


import file.Converter;
import file.FileManager;
import file.Parser;
import log.Logger;
import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.SemestreRules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * SemesterDataBase permet la gestion des semestres de l'application.
 * Cette class gere les semestres grace a un repertoire ou vont se
 * trouver les semestres, et un objet SemesterList representant
 * la liste des semestres du repertoire.
 */
public class SemesterDataBase {

    /** Repertoire ou se trouvent les fichiers contenant les semestres */
    private final File directory;
    /** Entier qui represente le nombre de semestres */
    private final int numberSemester;
    /** Represente la liste des semestres de l'application */
    private SemesterList semesterList;
    /** Parseur */
    private final Parser parser = new Parser();

    /**
     * Constructeur de base de la classe.
     * @param directory | Repertoire ou se trouvent les fichiers representant les semestres
     * @param numberSemester | Le nombre de semestres
     */
    public SemesterDataBase(File directory, int numberSemester){
        this.numberSemester = numberSemester;
        this.directory = directory;
    }

    /**
     * Renvoie si la liste des semestre a etait charger depuis les fichier
     * @return true: initialiser; false: non initialiser
     */
    public boolean isInit(){
        return semesterList!=null && semesterList.size()!=0;
    }

    /**
     * Permet de recuperer la list de semestre
     * si elle est pas initialiser on l'initialise pour le recurperer
     * @return la liste de semestre
     */
    public SemesterList getSemesterList(){
        if (!isInit()){
            initSemesterList();
        }
        return semesterList;
    }

    /**
     * Permet d'intialiser les list de semestre depuis
     * les fichier enregistrer dans les fichier.
     * @return si l'initialisation c'est fait correctement
     */
    public boolean initSemesterList(){
        SemesterList result = new SemesterList();
        List<UE> allUE = new ArrayList<UE>();

        //on charge le fichier des ue
        FileManager ueFileManager = loadFile("ue.csv");
        if(ueFileManager==null){
            return false;
        }
        List<List<String>> allUeString = parser.parseCsv(ueFileManager.getRaw());

        Converter converter = new Converter();
        result.initWithListUe(converter.csvToUe(allUeString),numberSemester);

        //initialisation des regle
        for(int i = 0; i<numberSemester; i++){
            FileManager semesterRule = loadFile("s"+(i+1)+"rule");
            if(semesterRule==null){
                return false;
            }
            SemestreRules semestreRules =  converter.stringToSemestreRule(semesterRule.getRaw());
            if(semestreRules==null){
                Logger.error("can't initilise semester, s"+(i+1)+"rule is not correct");
                return false;
            }
            else {
                result.get(i).setRules(semestreRules);
            }
        }
        //on assigne a la fin la semesterList si l'initialisation a pue ce faire
        //sinon on garde l'ancienne initialisation (si il en a une)
        semesterList = result;
        return true;

    }

    /**
     * Permet de charger le fichier a l'aide de sont nom
     * ou afficher une erreur si c'est impossible
     * @param name le nom du fichier a charger
     * @return le filemanager du fichier charger
     */
    protected FileManager loadFile(String name){
        FileManager ueFileManager = new FileManager(new File(directory,name));
        if(!ueFileManager.exists()){
            Logger.error("can't find file "+name+" in :"+directory.getAbsolutePath());
            return null;
        }
        return ueFileManager;
    }



}
