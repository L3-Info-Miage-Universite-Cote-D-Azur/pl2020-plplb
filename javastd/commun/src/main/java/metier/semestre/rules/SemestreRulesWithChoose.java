package metier.semestre.rules;

import java.util.ArrayList;


import metier.UE;
import metier.semestre.manager.ParcoursSemestreManager;
import metier.semestre.manager.SemestreChooseManager;

public class SemestreRulesWithChoose extends BasicSemestreRules{

    protected ArrayList<String> chooseUEList; //liste des ue obligatoire de choisir //WARNING la list ne peut pas avoir des ue de plusieur categorie
    protected int numberChooseUE;

    public SemestreRulesWithChoose(int maxUELibre, int maxByCategory, ArrayList<String> obligatoryUEList, ArrayList<String> chooseUEList, int numberChooseUE) {
        super(maxUELibre, maxByCategory, obligatoryUEList);
        this.chooseUEList = chooseUEList;
        this.numberChooseUE = numberChooseUE;
    }

    protected boolean superCanBeCheck(UE ue, ParcoursSemestreManager parcoursManager){
        return super.canBeCheck(ue,parcoursManager);
    }

    @Override
    /**
     * Regarde si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheck(UE ue, ParcoursSemestreManager parcoursManager) {
        //c'est une ue a choix
        if(isChooseUE(ue.getUeCode())){
            if(parcoursManager.getChooseUeSelected()< numberChooseUE) return true;
            //si on a deja le nombre d'ue au choix on la considere comme une ue classique
        }
        //on fait le traitement normale
        if(superCanBeCheck(ue,parcoursManager)) return true;
        return false;
    }

    @Override
    /**
     * Cree le gestionnaire de semestre adaptée
     * @return la manager adaptée pour controller les regle de semestre
     */
    public ParcoursSemestreManager createManager() {
        return new SemestreChooseManager(this);
    }

    public int getNumberChooseUE() {
        return numberChooseUE;
    }

    /**
     * Verification si une UE fait partie des ue au choix du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue choix du semestre;
     */
    @Override
    public boolean isChooseUE(String codeUE){
        if(chooseUEList == null) return false;
        for(String currentCodeUE: chooseUEList) {
            if (currentCodeUE.equals(codeUE)) return true;
        }
        return false;
    }

    @Override
    /**
     * Verifie si les donner semble correcte
     * @param semestreManager le parcours qui doit etre verifier
     * @return true si correcte sinon false
     */
    public boolean verifCorrectSemestre(ParcoursSemestreManager semestreManager){
        //verification super (uelibre + categorie)
        if(!superVerifCorrectSemestre(semestreManager)) return false;
        //verification nombre d'ue au choix
        if(semestreManager.getChooseUeSelected()!= numberChooseUE) return false;
        return true;
    }

    protected  boolean superVerifCorrectSemestre(ParcoursSemestreManager semestreManager){
        return super.verifCorrectSemestre(semestreManager);
    }

}
