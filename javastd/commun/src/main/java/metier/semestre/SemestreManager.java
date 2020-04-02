package metier.semestre;

import java.io.Serializable;
import java.util.HashMap;

import metier.UE;


public class SemestreManager implements Serializable {

    protected SemestreRules rules; //la regle du semestre
    protected HashMap<String,Integer> countByCategori; //nombre d'ue par categorie
    protected int ueLibreSelected = 0; //ue libre selectionner
    private int chooseUESelected = 0; //nb d'ue a choix


    /**
     * Constructeur du manager
     * @param rules la regle du semestre qu'il vas utiliser
     */
    public SemestreManager(SemestreRules rules){
        this.rules = rules;
        countByCategori = new HashMap<String,Integer>();
    }

    /**
     * get de la hashmap du nombre d'ue par categorie
     * @return la hashmap
     */
    public HashMap<String, Integer> getCountByCategory() {
        return countByCategori;
    }

    /**
     * get le nombre d'ue libre selectionner
     * @return nombre d'ue libre
     */
    public int getUeLibreSelected() {
        return ueLibreSelected;
    }

    /**
     * get le nombre d'ue a choix selectionner
     * @return nombre d'ue a choix
     */
    public int getChooseUeSelected() {
        return chooseUESelected;
    }

    /**
     * Permet de check une ue
     * @param ue l'ue que l'on check
     */
    public void check(UE ue) {
        //la verification si l'ue est deja dedans n'est pas gerer par cette classe mais par parcours

        //pas de traitement necessaire pour les ue obligatoire (normalement impossible)
        if(rules.isObligatoryUE(ue.getUeCode())) return;

        //traitemant classique
        Boolean isChooseUE = rules.isChooseUE(ue.getUeCode());

        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE &&  rules.getNumberChooseUE() <= chooseUESelected)){
            countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)+1);
            ueLibreSelected+=1;
        }
        if(isChooseUE) chooseUESelected+=1; //si c'est une ue parmis les choix
    }


    /**
     * Permet de uncheck une ue
     * @param ue l'ue que l'on uncheck
     */
    public void uncheck(UE ue) {
        //la verification si l'ue est deja dedans n'est pas gerer par cette classe mais par parcours

        //pas de traitement necessaire pour les ue obligatoire (normalement impossible)
        if(rules.isObligatoryUE(ue.getUeCode())) return;

        //traitemant classique
        Boolean isChooseUE = rules.isChooseUE(ue.getUeCode());
        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE && rules.getNumberChooseUE()<chooseUESelected)){
            countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)-1);
            ueLibreSelected-=1;
        }
        if(isChooseUE) { //si c'est une ue parmis les choix
            chooseUESelected-=1; //on retire
        }

    }


    /**
     * possible de check une ue?
     * @param ue l'ue que l'on veut check
     * @return true c'est possible; false pas possible
     */
    public boolean canBeCheck(UE ue) {
        return rules.canBeCheck(ue,this);
    }

    /**
     * possible de uncheck une ue?
     * @param ue l'ue que l'on veut uncheck
     * @return true c'est possible; false pas possible
     */
    public boolean canBeUncheck(UE ue) {
        return rules.canBeUncheck(ue,this);
    }

    /**
     * Verification du parcours renvoie si le parcours est accepter
     * @return true parcours complet et correct; false pas complet ou incorrect
     */
    public boolean verifCompleteParcours(){
        return rules.verifCorrectSemestre(this);
    }

    /**
     * revoie le nombre d'ue necessaire pour complete le parcours
     * @return
     */
    public int ueNeedToCompleteSemester(){

        int chooseSelect = chooseUESelected;

        //si on en a + elle en plus est considere comme normale en plus d'etre considerer a choix
        if(rules.getNumberChooseUE()<chooseUESelected) chooseSelect = rules.getNumberChooseUE();

        int maxUeToChoose = rules.getNumberChooseUE()+rules.getMaxUELibre();

        return maxUeToChoose-(ueLibreSelected+chooseSelect);

    }

}

