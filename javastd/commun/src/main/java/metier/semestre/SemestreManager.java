package metier.semestre;

import java.util.HashMap;

import metier.UE;


public class SemestreManager {

    protected SemestreRules rules;
    protected HashMap<String,Integer> countByCategori;
    protected int ueLibreSelected = 0;
    private int chooseUESelected = 0;



    public SemestreManager(SemestreRules rules){
        this.rules = rules;
        countByCategori = new HashMap<String,Integer>();
    }

    public HashMap<String, Integer> getCountByCategory() {
        return countByCategori;
    }

    public int getUeLibreSelected() {
        return ueLibreSelected;
    }

    public int getChooseUeSelected() {
        return chooseUESelected;
    }

    public void check(UE ue) {
        Boolean isChooseUE = rules.isChooseUE(ue.getUeCode());
        System.out.println("================="+isChooseUE);
        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE &&  rules.getNumberChooseUE() <= chooseUESelected)){
            countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)+1);
            ueLibreSelected+=1;
        }
        if(isChooseUE) chooseUESelected+=1; //si c'est une ue parmis les choix
    }



    public void uncheck(UE ue) {

        Boolean isChooseUE = rules.isChooseUE(ue.getUeCode());
        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE && rules.getNumberChooseUE()<chooseUESelected)){
            countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)-1);
            if(countByCategori.getOrDefault(ue.getCategorie(),0)<0) System.exit(-1); //TODO replace by throws exception
            ueLibreSelected-=1;
            if(ueLibreSelected<0) System.exit(-1);//TODO replace by throws exception
        }
        if(isChooseUE) { //si c'est une ue parmis les choix
            chooseUESelected-=1; //on retire
            if(chooseUESelected<0) System.exit(-1);//TODO replace by throws exception
        }

    }



    public boolean canBeCheck(UE ue) {
        return rules.canBeCheck(ue,this);
    }

    public boolean canBeUncheck(UE ue) {
        return rules.canBeUncheck(ue,this);
    }

    public boolean verifCompleteParcours(){
        return rules.verifCorrectSemestre(this);
    }

}

