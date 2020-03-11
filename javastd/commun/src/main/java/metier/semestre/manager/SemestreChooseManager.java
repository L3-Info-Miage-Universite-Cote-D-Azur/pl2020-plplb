package metier.semestre.manager;

import metier.UE;
import metier.semestre.rules.SemestreRulesWithChoose;

public class SemestreChooseManager extends BasicSemestreManager{

    private int chooseUESelected = 0;

    public SemestreChooseManager(SemestreRulesWithChoose rules) {
        super(rules);
    }

    @Override
    public int getChooseUeSelected() {
        return chooseUESelected;
    }

    @Override
    public void check(UE ue) {
        Boolean isChooseUE = ((SemestreRulesWithChoose) rules).isChooseUE(ue.getUeCode());
        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE && ((SemestreRulesWithChoose) rules).getNumberChooseUE() <= chooseUESelected)){
            super.check(ue);
        }
        if(isChooseUE) chooseUESelected+=1; //si c'est une ue parmis les choix
    }

    @Override
    public void uncheck(UE ue) {
        Boolean isChooseUE = ((SemestreRulesWithChoose) rules).isChooseUE(ue.getUeCode());
        //si ce n'est pas une ue parmis les choix ou que le nombre d'ue a choix est deja atteint on traite l'ue normalement
        if(!isChooseUE ||(isChooseUE && ((SemestreRulesWithChoose) rules).getNumberChooseUE()<chooseUESelected)){
            super.uncheck(ue);
        }
        if(isChooseUE) { //si c'est une ue parmis les choix
            chooseUESelected-=1; //on retire
            if(chooseUESelected<0) System.exit(-1);//TODO replace by throws exception
        }

    }

}
