package metier.parcours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import metier.UE;
import metier.semestre.SemesterList;
import metier.semestre.Semestre;
import metier.semestre.SemestreManager;

/**
 * Classe qui s'occupe de la gestion du parcours et des regle a respecter.
 */
public class Parcours implements Serializable {

    private ParcoursRules parcoursRules;
    private String name = "default";
    private SemesterList semestreList;
    private ArrayList<SemestreManager> semestresManager;
    private HashMap<String, UE> parcoursSelect; //commun a tout les semestre (permet de rajouter des condition UE necesaire, ...)

    private String lastVerifErrorMessage;


    //TODO init avec les ue automatiquement present
    /**
     * constructeur avec une liste valide d'ue (utiliser pour charger les donnée)
     * @param semestreList la liste de semestre.
     * @param allCodeUESelected une liste valide d'ue
     */
    public Parcours(SemesterList semestreList, List<String> allCodeUESelected ,ArrayList<ParcoursType> listparcoursType) {
        this.semestreList =  semestreList;
        initParcoursSemestresManager();
        initParcours(allCodeUESelected, listparcoursType);
        initObligatoryUE();

    }

    /**
     * Constructeur pour un nouveau parcours
     * @param semestreList la liste de semestre.
     */
    public Parcours(SemesterList semestreList,ParcoursType parcoursType,String name){
        this.setName(name);
        this.semestreList = semestreList;
        parcoursSelect = new HashMap<String,UE>();
        parcoursRules = new ParcoursRules(parcoursType);
        initParcoursSemestresManager();
        initObligatoryUE();
    }

    /**
     * Constructeur pour test
     * @param parcoursRules
     * @param name
     * @param semestreList
     * @param semestresManager
     * @param parcoursSelect
     */
    public Parcours(ParcoursRules parcoursRules, String name, SemesterList semestreList, ArrayList<SemestreManager> semestresManager, HashMap<String, UE> parcoursSelect) {
        this.parcoursRules = parcoursRules;
        this.name = name;
        this.semestreList = semestreList;
        this.semestresManager = semestresManager;
        this.parcoursSelect = parcoursSelect;
    }

    /**
     * Recuperer le nom
     * @return le nom du parcours
     */
    public String getName() {
        return name;
    }

    /**
     * Set le nom du parcours
     * @param name le nom de parcours
     */
    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, UE> getParcoursSelect() {
        return parcoursSelect;
    }

    /**
     * Permet de mettre a jour le parcours (a utiliser quand on ajoute un semestre au modele)
     * @param semestreList si il a un nouveau semestre a set
     */
    public void updateSemestre(SemesterList semestreList){
        this.semestreList = semestreList;
        initParcoursSemestresManager();

        //on rajoute de nouveau tout les ue (si il existe encore)
        Set<String> setAllSelected = parcoursSelect.keySet();
        parcoursSelect.clear(); //on vide la liste d'ue
        for(String codeUE : setAllSelected){
            checkUENoVerif(semestreList.findUE(codeUE));
        }
        initObligatoryUE();
    }

    /**
     * Permet de mettre a jour le parcours (a utiliser quand on ajoute un semestre au modele)
     */
    public void updateSemestre(){

        initParcoursSemestresManager();

        //on rajoute de nouveau tout les ue(si il existe encore
        Set<String> setAllSelected = parcoursSelect.keySet();
        parcoursSelect.clear(); //on vide la liste d'ue

        for(String codeUE : setAllSelected){
            checkUENoVerif(semestreList.findUE(codeUE));
        }
        initObligatoryUE();
    }



    /**
     * Initialisation des ue avec une liste de code d'ue
     * @param allCodeUESelected tout les ue de la liste
     */
    private void initParcours(List<String> allCodeUESelected, ArrayList<ParcoursType> listparcoursType){
        parcoursSelect = new HashMap<String, UE>();

        //Le premier element de la liste est le nom du parcours;
        this.setName(allCodeUESelected.get(0));

        //Le deuxieme est le nom du semestre type.
        String parcoursName = allCodeUESelected.get(1);

        for(ParcoursType parcoursType : listparcoursType){

            if(parcoursType.getName().equals(parcoursName)){
                parcoursRules = new ParcoursRules(parcoursType);
            }
        }

        //On recupere les ues cochées

        UE ue;
        for(int i =2; i < allCodeUESelected.size();i++){
            ue = semestreList.findUE(allCodeUESelected.get(i));
            if(ue != null) checkUENoVerif(ue);
        }


    }

    /**
     * creation des semestre manager
     */
    public void initParcoursSemestresManager(){
        semestresManager = new ArrayList<SemestreManager>();

        for (int i = 0; i <semestreList.size() ; i++) {
            semestresManager.add(semestreList.get(i).getRules().createManager());
        }

    }

    /**
     * Ajout des ue obligatoire a la liste des ue check
     */
    private void initObligatoryUE(){
        UE ue;
        for(Semestre semestres : semestreList){
            for(String codeUE : semestres.getRules().obligatoryUEList()){
                ue = semestreList.findUE(codeUE);
                if(ue!=null){
                    checkUENoVerif(ue);
                }
            }
        }

        //Ajout des ues obligatoire du parcours.
        if(parcoursRules.getParcoursType().getObligatoryUes() != null) {
            for (String ueCode : parcoursRules.getParcoursType().getObligatoryUes()) {
                ue = semestreList.findUE(ueCode);
                if (ue != null) {
                    if(!isChecked(ue)) checkUENoVerif(ue);
                }
            }
        }
    }




    /**
     * Regarde a l'aide de toutes les fonction intermediaire si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheckedUE(UE ue){
        if(isChecked(ue)) return false;
        Boolean semestreCheck = semestresManager.get(ue.getSemestreNumber()-1).canBeCheck(ue);
        //iteration ? verification des prerequis (graphe?)
        //Boolean uePrerequisCheck = ...

        return semestreCheck;
    }

    /**
     * Regarde a l'aide de toutes les fonction intermediaire si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheckedUE(UE ue){
        if(!isChecked(ue)) return false;

        //L'ue ne peux etre deselectionner si elle appartient au ues obligatoire du parcours.
        if(parcoursRules.getParcoursType().getObligatoryUes() != null){
            if(parcoursRules.getParcoursType().getObligatoryUes().contains(ue.getUeCode())){
                return false;
            }
        }

        Boolean semestreUncheck = semestresManager.get(ue.getSemestreNumber()-1).canBeUncheck(ue);
        //iteration ? verification des prerequis (graphe?)
        //Boolean uePrerequisCheck = ...

        return semestreUncheck;
    }


    /**
     * ajoute une UE au parcours (verification effectuer a l'interrieur aucun changement si non possible)
     * @param ue l'ue a ajouter
     */
    public void checkUE(UE ue){
        //il faut que ca respecte les regle
        if(canBeCheckedUE(ue)){
            parcoursSelect.put(ue.getUeCode(),ue);
            semestresManager.get(ue.getSemestreNumber()-1).check(ue);
        }
    }

    /**
     * ajoute une UE au parcours sans verification
     * @param ue l'ue a ajouter
     */
    public void checkUENoVerif(UE ue){
        parcoursSelect.put(ue.getUeCode(),ue);
        semestresManager.get(ue.getSemestreNumber()-1).check(ue);

    }



    /**
     * Supprime l'ue du parcours, si celle ci y est present
     * @param ue l'ue a supprimer du parcours
     */
    public void uncheckUE(UE ue){
        //si elle fait partie de la liste des ue selectionnées et qu'elle peut etre uncheck
        if(parcoursSelect.containsKey(ue.getUeCode()) && canBeUncheckedUE(ue)) {
            parcoursSelect.remove(ue.getUeCode());
            semestresManager.get(ue.getSemestreNumber()-1).uncheck(ue);
        }
    }

    /**
     * Regarde si une UE est selectionner par l'utilisateur
     * @param ue l'ue que l'on regarde si elle est selectionner
     * @return true : l'ue est selectionner; false: l'ue ne l'est pas
     */
    public boolean isChecked(UE ue){
        return parcoursSelect.containsKey(ue.getUeCode());
    }

    /**
     * renvoie la liste des code d'UE du parcours
     * @return la liste des code d'UE
     */
    public ArrayList<String> createListCodeUE(){
        ArrayList<String> codeUEToList = new ArrayList<String>();
        for(UE ueCode: parcoursSelect.values()){
            codeUEToList.add(ueCode.getUeCode());
        }
        return codeUEToList;
    }


    /**
     * Cree une liste pres pour une sauvegarde
     * @return le liste pour sauvegarder le parcours
     */
    public ArrayList<String> createSaveList(){
        ArrayList<String> saveList = new ArrayList<String>();
        saveList.add(name);
        saveList.add(parcoursRules.getParcoursType().getName());
        saveList.addAll(createListCodeUE());
        return saveList;
    }

    /**
     * Verification du parcours entier
     * @return l'etat de la verification
     */
    public boolean verifiParcours(){

        //verification des regle du semestre
        for(int i = 0; i< semestresManager.size(); i++){
            if(!semestresManager.get(i).verifCompleteParcours()){
                lastVerifErrorMessage = "Vous n'avez pas selectionner les ue necessaire au semestre "+(i+1);
                return false;
            }
        }


        ArrayList<HashMap<String,Integer>> listHasmap = new ArrayList<HashMap<String, Integer>>();
        //On recupere les hashmap du nombre d'ue par categorie (par semestre).

        for (int i = 0; i <semestresManager.size() ; i++) {
            listHasmap.add(semestresManager.get(i).getCountByCategory());
        }

        //Si le parcours ne repond pas aux critères du parcours type on renvoie faux.
        if(!parcoursRules.acceptParcours(listHasmap,this.createListCodeUE())){
            return false;
        }

        lastVerifErrorMessage= null;
        return true;
    }




    /**
     * Nombre d'ue encore necessaire de cocher pour le semestre
     * @param semestreIndex l'index du semestre
     * @return le nombre d'ue a cocher
     */
    public int ueNeedToCompleteSemester(int semestreIndex){
        if(semestresManager == null) return 0;
        if(semestresManager.size()<=semestreIndex) return 0;
        return semestresManager.get(semestreIndex).ueNeedToCompleteSemester();
    }

    public ParcoursRules getParcoursRules() {
        return parcoursRules;
    }

    /**
     * Permet de recuperer le dernier message d'erreur generer par la verification
     * @return le message d'erreur
     */
    public String getLastVerifErrorMessage(){
        return lastVerifErrorMessage;
    }

}
