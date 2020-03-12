package metier.semestre.manager;

import java.util.HashMap;

import metier.UE;
import metier.semestre.rules.BasicSemestreRules;

public class BasicSemestreManager implements ParcoursSemestreManager{
    protected final BasicSemestreRules rules;
    protected HashMap<String,Integer> countByCategori;
    protected int ueLibreSelected = 0;


    public BasicSemestreManager(BasicSemestreRules rules){
        this.rules = rules;
        countByCategori = new HashMap<String,Integer>();
    }

    @Override
    public HashMap<String, Integer> getCountByCategory() {
        return countByCategori;
    }

    @Override
    public int getUeLibreSelected() {
        return ueLibreSelected;
    }

    @Override
    public int getChooseUeSelected() {
        return 0;
    }

    @Override
    public void check(UE ue) {
        countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)+1);
        ueLibreSelected+=1;
    }

    @Override
    public void uncheck(UE ue) {
        countByCategori.put(ue.getCategorie(),countByCategori.getOrDefault(ue.getCategorie(),0)-1);
        if(countByCategori.getOrDefault(ue.getCategorie(),0)<0) System.exit(-1); //TODO replace by throws exception
        ueLibreSelected-=1;
        if(ueLibreSelected<0) System.exit(-1);//TODO replace by throws exception

    }

    @Override
    public boolean canBeCheck(UE ue) {
        return rules.canBeCheck(ue,this);
    }

    @Override
    public boolean canBeUncheck(UE ue) {
        return rules.canBeUncheck(ue,this);
    }

    public boolean verifCompleteParcours(){
        return rules.verifCorrectSemestre(this);
    }

}
