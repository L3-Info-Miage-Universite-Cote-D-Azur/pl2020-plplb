package dataBase;

import debug.Debug;
import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.SemestreRules;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Permet de gerer la list des semestre
 */
public class SemesterDataBase {

    private final File directory;
    private final int numberSemester;

    private SemesterList semesterList;
    private final Parser parser = new Parser();


    public SemesterDataBase(File directory, int numberSemester){
        this.numberSemester = numberSemester;
        this.directory = directory;
    }

    /**
     * Renvoie si la liste des semestre a etait charger depuis les fichier
     * @return true -> initialiser; false -> non initialiser
     */
    public boolean isInit(){
        return semesterList!=null || semesterList.size()!=0;
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
     */
    public void initSemesterList(){
        semesterList = new SemesterList();
        List<UE> allUE = new ArrayList<UE>();

        //on charge le fichier des ue
        FileManager ueFileManager = new FileManager(new File(directory,"ue.csv"));
        if(!ueFileManager.exists()){
            Debug.error("can't find file ue.csv in :"+directory.getAbsolutePath());
            return;
        }
        List<List<String>> allUeString = parser.parseCsv(ueFileManager.getRaw());

        Converter converter = new Converter();
        semesterList.initWithListUe(converter.csvToUe(allUeString),4);

        //initialisation des regle
        for(int i = 0; i<numberSemester; i++){
            FileManager semesterRule = new FileManager(new File(directory,"s"+(i+1)+"rule"));
            if(!semesterRule.exists()){
                Debug.error("can't find file "+"s"+(i+1)+"rule"+" in directory :"+directory.getAbsolutePath());
                return;
            }
            SemestreRules semestreRules =  converter.stringToSemestreRule(semesterRule.getRaw());
            if(semestreRules==null){
                Debug.error("can't initilise semester, s"+(i+1)+"rule is not completed");
            }
            else {
                semesterList.get(i).setRules(semestreRules);
            }
        }

    }



}
