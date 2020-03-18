package metier.parcours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import metier.MainModele;
import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.SemestreList;
import metier.semestre.SemestreManager;

/**
 * Classe qui s'occupe de la gestion du parcours et des regle a respecter.
 */
public class Parcours implements Serializable {

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
    public Parcours(SemestreList semestreList){
        this.semestreList = semestreList;
        parcoursSelect = new HashMap<String,UE>();
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
     * Initialisation des ue avec une liste de code d'ue
     * @param allCodeUESelected tout les ue de la liste
     */
    private void initParcours(List<String> allCodeUESelected){
        parcoursSelect = new HashMap<String, UE>();

        UE ue;
        for(String codeUE : allCodeUESelected){
            ue = semestreList.findUE(codeUE);
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

        //for(SemestreManager manager: semestresManager){
        //Pour le test
        for(int i = 0; i< semestresManager.size(); i++){
            if(!semestresManager.get(i).verifCompleteParcours()) return false;
        }
        return true;
    }




}
