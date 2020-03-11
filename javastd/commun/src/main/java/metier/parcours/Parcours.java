package metier.parcours;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import metier.MainModele;
import metier.UE;
import metier.semestre.Semestre;
import metier.semestre.manager.ParcoursSemestreManager;

/**
 * Classe qui s'occupe de la gestion du parcours et des regle a respecter.
 */
public class Parcours {

    private String name = "default";
    private MainModele modele;
    private ArrayList<ParcoursSemestreManager> semestresManager;
    private HashMap<String, UE> parcoursSelect; //commun a tout les semestre (permet de rajouter des condition UE necesaire, ...)


    //TODO init avec les ue automatiquement present
    /**
     * constructeur avec une liste valide d'ue (utiliser pour charger les donnée)
     * @param modele le modele
     * @param allCodeUESelected une liste valide d'ue
     */
    public Parcours(MainModele modele, List<String> allCodeUESelected ) {
        this.modele =  modele;
        initParcoursSemestresManager();
        initParcours(allCodeUESelected);
    }

    /**
     * Constructeur pour un nouveau parcours
     * @param modele le modele
     */
    public Parcours(MainModele modele){
        this.modele = modele;
        parcoursSelect = new HashMap<String,UE>();
        initParcoursSemestresManager();
    }


    private void initParcours(List<String> allCodeUESelected){
        parcoursSelect = new HashMap<String, UE>();

        UE ue;
        for(String codeUE : allCodeUESelected){
            ue = modele.findUE(codeUE);
            if(ue != null) checkUENoVerif(ue);
        }
    }

    private void initParcoursSemestresManager(){
        semestresManager = new ArrayList<ParcoursSemestreManager>();
        for(Semestre semestre: modele.getSemestres()){
            semestresManager.add(semestre.getRules().createManager());
        }
    }



    /**
     * Regarde a l'aide de toutes les fonction intermediaire si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheckedUE(UE ue){
        Boolean semestreCheck = semestresManager.get(ue.getSemestreNumber()).canBeCheck(ue);
        //iteration ? verification des prerequis (graphe?)
        //Boolean uePrerequisCheck = ...

        return semestreCheck; //rajouter les condition quand definit:  && uePrerequisCheck
    }

    /**
     * Regarde a l'aide de toutes les fonction intermediaire si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheckedUE(UE ue){
        Boolean semestreUncheck = semestresManager.get(ue.getSemestreNumber()).canBeUncheck(ue);
        //TODO iteration 4 verification des prerequis (graphe?)
        //Boolean uePrerequisCheck = ...

        return semestreUncheck; //rajouter les condition quand definit:  && uePrerequisUnCheck
    }


    /**
     * ajoute une UE au parcours (verification effectuer a l'interrieur aucun changement si non possible)
     * @param ue l'ue a ajouter
     */
    public void checkUE(UE ue){
        //il faut que ca respecte les regle
        if(canBeCheckedUE(ue)){
            parcoursSelect.put(ue.getUeCode(),ue);
            semestresManager.get(ue.getSemestreNumber()).check(ue);
        }
    }

    /**
     * ajoute une UE au parcours sans verification
     * @param ue l'ue a ajouter
     */
    public void checkUENoVerif(UE ue){
        parcoursSelect.put(ue.getUeCode(),ue);
        semestresManager.get(ue.getSemestreNumber()).check(ue);
    }



    /**
     * Supprime l'ue du parcours, si celle ci y est present
     * @param ue l'ue a supprimer du parcours
     */
    public void uncheckUE(UE ue){
        //si elle fait parti de la liste des ue selectionner et qu'elle peut etre uncheck
        if(parcoursSelect.containsKey(ue.getUeCode()) && canBeUncheckedUE(ue)) {
            parcoursSelect.remove(ue.getUeCode());
            semestresManager.get(ue.getSemestreNumber()).uncheck(ue);
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

    public boolean verifiParcours(){
        for(ParcoursSemestreManager manager: semestresManager){
            if(!manager.verifCompleteParcours()) return false;
        }
        return true;
    }




}
