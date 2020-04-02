package metier.semestre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import metier.UE;

public class SemestreRules implements Serializable {

    /*FIELDS*/
    protected int maxUELibre; //nombre d'ue libre necessaire de selectionner pour valider une semestre
    protected int maxByCategory; //nombre maximum d'ue par categorie
    protected ArrayList<String> obligatoryUEList; //liste des ue obligatoire pour le semestre

    protected ArrayList<String> chooseUEList; //liste des ue obligatoire de choisir //WARNING la list ne peut pas avoir des ue de plusieur categorie
    protected int numberChooseUE = 0;

    /*CONSTRUCTOR*/

    /**
     * Constructeur sans ue au choix
     * @param maxUELibre nombre max d'ue libre
     * @param maxByCategory nombre max d'ue par categorie
     * @param obligatoryUEList Liste des ue obligatoire
     */
    public SemestreRules(int maxUELibre, int maxByCategory, ArrayList<String> obligatoryUEList){
        this.maxUELibre = maxUELibre;
        this.maxByCategory = maxByCategory;
        this.obligatoryUEList = obligatoryUEList;
        chooseUEList = new ArrayList<String>();
    }

    /**
     * Constructeur avec ue au choix
     * @param maxUELibre nombre max d'ue libre
     * @param maxByCategory nombre max d'ue par categorie
     * @param obligatoryUEList Liste des ue obligatoire
     * @param chooseUEList List des ue au choix
     * @param numberChooseUE nombre d'ue parmis la list au choix
     */
    public SemestreRules(int maxUELibre, int maxByCategory, ArrayList<String> obligatoryUEList, ArrayList<String> chooseUEList, int numberChooseUE) {
        this.maxUELibre = maxUELibre;
        this.maxByCategory = maxByCategory;
        this.obligatoryUEList = obligatoryUEList;
        this.chooseUEList = chooseUEList;
        this.numberChooseUE = numberChooseUE;
    }

    /**
     * Get le nombre d'ue a choix possible
     * @return return le nombre d'ue a choix possible
     */
    public int getNumberChooseUE() {
        return numberChooseUE;
    }

    /**
     * Get le nombre d'ue a cocher
     * @return le nombre d'ue possible
     */
    public int getMaxUELibre(){
        return maxUELibre;
    }

    /**
     * Regarde si il est possible de cocher les ue libre
     * @param ue l'ue libre que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheckLibreUE(UE ue, SemestreManager parcoursManager) {
        if (parcoursManager.getUeLibreSelected() < maxUELibre //si il reste de la place
                && parcoursManager.getCountByCategory().getOrDefault(ue.getCategorie(), 0) < maxByCategory) //si il reste de la place dans la categorie
        {
            return true;
        }

        return false;
    }

    /**
     * Regarde si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheck(UE ue, SemestreManager parcoursManager) {
        if (isObligatoryUE(ue.getUeCode())) return true; //une UE obligatoire est normalement tout le temps check mais si elle ne l'ai pas on peut la check
        //c'est une ue a choix
        if(isChooseUE(ue.getUeCode())){
            if(parcoursManager.getChooseUeSelected()< numberChooseUE) return true;
            //si on a deja le nombre d'ue au choix on la considere comme une ue classique
        }
        //on fait le traitement normale
        if(canBeCheckLibreUE(ue,parcoursManager)) return true;
        return false;
    }

    /**
     * Regarde si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheck(UE ue, SemestreManager parcoursManager) {
        if(isObligatoryUE(ue.getUeCode())) return false; //on peut pas uncheck une ue obligatoire
        return true;
    }

    /**
     * Cree le gestionnaire de semestre adaptée
     * @return la manager adaptée pour controller les regle de semestre
     */
    public SemestreManager createManager() {
        return new SemestreManager(this);
    }

    /**
     * Verification si une UE fait partie des ue obligatoire du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue obligatoire;
     */
    public boolean isObligatoryUE(String codeUE){
        if(obligatoryUEList == null) return false;
        for(String currentCodeUE: obligatoryUEList) {
            if (currentCodeUE.equals(codeUE)) return true;
        }
        return false;
    }


    /**
     * Verifie si les donner semble correcte
     * @param semestreManager le parcours qui doit etre verifier
     * @return true si correcte sinon false
     */
    public boolean verifCorrectSemestreUELibre(SemestreManager semestreManager){
        //verification ue libre
        if(semestreManager.getUeLibreSelected()!= maxUELibre) return false;
        //verification nombre par categorie
        for(int numberByCat: semestreManager.getCountByCategory().values()){
            if(numberByCat>maxByCategory) return false;
        }
        return true;
    }

    /**
     * Verifie si les donner semble correcte
     * @param semestreManager le parcours qui doit etre verifier
     * @return true si correcte sinon false
     */
    public boolean verifCorrectSemestre(SemestreManager semestreManager){
        //verification super (uelibre + categorie)
        if(!verifCorrectSemestreUELibre(semestreManager)) return false;
        //verification nombre d'ue au choix
        if(semestreManager.getChooseUeSelected()< numberChooseUE) return false;
        return true;
    }

    /**
     * Liste des codeUE des ue obligatoire
     * @return liste des codeUE
     */
    public List<String> obligatoryUEList(){
        return new ArrayList<String>(obligatoryUEList);
    }


    /**
     * Verification si une UE fait partie des ue au choix du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue choix du semestre;
     */
    public boolean isChooseUE(String codeUE){
        if(chooseUEList == null) return false;
        for(String currentCodeUE: chooseUEList) {
            if (currentCodeUE.equals(codeUE)) return true;
        }
        return false;
    }


}
