package metier.semestre.rules;

import java.util.ArrayList;
import java.util.List;

import metier.UE;
import metier.semestre.manager.BasicSemestreManager;
import metier.semestre.manager.ParcoursSemestreManager;

public class BasicSemestreRules{

    /*FIELDS*/
    protected int maxUELibre; //nombre d'ue libre necessaire de selectionner pour valider une semestre
    protected int maxByCategory; //nombre maximum d'ue par categorie
    protected ArrayList<String> obligatoryUEList; //liste des ue obligatoire pour le semestre

    /*CONSTRUCTOR*/
    public BasicSemestreRules(int maxUELibre, int maxByCategory, ArrayList<String> obligatoryUEList){
        this.maxUELibre = maxUELibre;
        this.maxByCategory = maxByCategory;
        this.obligatoryUEList = obligatoryUEList;
    }


    /**
     * Regarde si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheck(UE ue, ParcoursSemestreManager parcoursManager){
        if(isObligatoryUE(ue.getUeCode())) return true; //une UE obligatoire est normalement tout le temps check mais si elle ne l'ai pas on peut la check
        if(parcoursManager.getUeLibreSelected() < maxUELibre //si il reste de la place
                && parcoursManager.getCountByCategory().getOrDefault(ue.getCategorie(),0) < maxByCategory) //si il reste de la place dans la categorie
            {
                return true;
            }

        return false;
    }

    /**
     * Regarde si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheck(UE ue, ParcoursSemestreManager parcoursManager) {
        if(isObligatoryUE(ue.getUeCode())) return false; //on peut pas uncheck une ue obligatoire
        return true;
    }

    /**
     * Cree le gestionnaire de semestre adaptée
     * @return la manager adaptée pour controller les regle de semestre
     */
    public ParcoursSemestreManager createManager() {
        return new BasicSemestreManager(this);
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
     * Verification si une UE fait partie des ue a choix du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue obligatoire;
     */
    public boolean isChooseUE(String codeUE) {
        return false; //il n'y a pas d'ue a choix dans un semestre basic
    }

    /**
     * Verifie si les donner semble correcte
     * @param semestreManager le parcours qui doit etre verifier
     * @return true si correcte sinon false
     */
    public boolean verifCorrectSemestre(ParcoursSemestreManager semestreManager){
        //verification ue libre
        if(semestreManager.getUeLibreSelected()!= maxUELibre) return false;
        //verification nombre par categorie
        for(int numberByCat: semestreManager.getCountByCategory().values()){
            if(numberByCat>maxByCategory) return false;
        }
        return true;
    }

    /**
     * Liste des codeUE des ue obligatoire
     * @return liste des codeUE
     */
    public List<String> obligatoryUEList(){
        return new ArrayList<String>(obligatoryUEList);
    }


}
