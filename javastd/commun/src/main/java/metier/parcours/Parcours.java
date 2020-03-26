package metier.parcours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.SemestreList;
import metier.semestre.SemestreManager;
import metier.semestre.SemestreRules;

/**
 * Classe qui s'occupe de la gestion du parcours et des regle a respecter.
 */
public class Parcours implements Serializable {

    private ParcoursRules parcoursRules;
    private String name = "default";
    private SemestreList semestreList;
    private ArrayList<SemestreManager> semestresManager;
    private HashMap<String, UE> parcoursSelect; //commun a tout les semestre (permet de rajouter des condition UE necesaire, ...)


    //TODO init avec les ue automatiquement present
    /**
     * constructeur avec une liste valide d'ue (utiliser pour charger les donnée)
     * @param semestreList la liste de semestre.
     * @param allCodeUESelected une liste valide d'ue
     */
    public Parcours(SemestreList semestreList, List<String> allCodeUESelected ) {
        this.semestreList =  semestreList;
        initParcoursSemestresManager();
        initParcours(allCodeUESelected);
        initObligatoryUE();

    }

    /**
     * Constructeur pour un nouveau parcours
     * @param semestreList la liste de semestre.
     */
    public Parcours(SemestreList semestreList,ParcoursType parcoursType,String name){
        this.setName(name);
        this.semestreList = semestreList;
        parcoursSelect = new HashMap<String,UE>();
        parcoursRules = new ParcoursRules(parcoursType);
        initParcoursSemestresManager();
        initObligatoryUE();
    }


    /**
     * Constructeur pour test
     */
    public Parcours(SemestreList semestreList,ArrayList<SemestreManager> semestresManager,HashMap<String, UE> parcoursSelect){
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
    public void updateSemestre(SemestreList semestreList){
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
    private void initParcours(List<String> allCodeUESelected){
        parcoursSelect = new HashMap<String, UE>();

        //Le premier element de la liste est le nom du parcours;
        this.setName(allCodeUESelected.get(0));

        //On recupere la liste des parcours type.
        ParcoursSample.init();
        ArrayList<ParcoursType> listparcoursType = ParcoursSample.parcoursTypes;
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
        for(Semestre semestre: semestreList){
            semestresManager.add(semestre.getRules().createManager());
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
                    parcoursSelect.put(codeUE,ue);
                }
            }
        }

        //Ajout des ues obligatoire du parcours.
        if(parcoursRules.getParcoursType().getObligatoryUes() != null) {
            for (String ueCode : parcoursRules.getParcoursType().getObligatoryUes()) {
                ue = semestreList.findUE(ueCode);
                if (ue != null) {
                    checkUENoVerif(ue);
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
        //si elle fait parti de la liste des ue selectionner et qu'elle peut etre uncheck
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
     * Verification du parcours entier
     * @return l'etat de la verification
     */
    public boolean verifiParcours(){
        ArrayList<HashMap<String,Integer>> listHasmap = new ArrayList<HashMap<String, Integer>>();
        //On recupere les hashmap du nombre d'ue par categorie (par semestre).
        for(SemestreManager semestreManager : this.semestresManager){
            listHasmap.add(semestreManager.getCountByCategory());
        }

        //Si le parcours ne repond pas aux critères du parcours type on renvoie faux.
        if(!parcoursRules.acceptParcours(listHasmap,this.createListCodeUE())){
            return false;
        }

        //for(SemestreManager manager: semestresManager){
        //Pour le test
        for(int i = 0; i< semestresManager.size(); i++){
            if(!semestresManager.get(i).verifCompleteParcours()) return false;
        }
        return true;
    }






}
