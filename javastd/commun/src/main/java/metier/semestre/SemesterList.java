package metier.semestre;

import metier.UE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SemestreList est une arrayList de semestre.
 */
public class SemesterList extends ArrayList<Semestre> implements Serializable {

    /**
     * Recherche d'une ue parmis l'ensemble des ue
     * @param codeUE le code de l'ue
     * @return l'ue si elle est trouver.
     */
    public UE findUE(String codeUE){
        UE ue;
        for(Semestre semestre: this){
            ue = semestre.findUE(codeUE);
            if(ue != null) return ue;
        }
        return null;
    }

    public Semestre getSemester(int number){
        for(Semestre semestre : this){
            if(semestre.getNumber()==number){
                return semestre;
            }
        }
        return null;//not found
    }


    /**
     * Permet d'initialiser la list des semestre
     * @param allUe tout les eu que l'on peut retrouver dans les semestre
     * @param numberSemester le nombre de smeestre qu'il y a
     */
    public void initWithListUe(List<UE> allUe, int numberSemester){
        this.clear(); //on vide si elle etait pas vide
        //on crée les semestre
        for(int i = 1; i<= numberSemester; i++){
            Semestre current = new Semestre();
            current.setNumber(i);
            this.add(current);
        }
        //on ajoute tout les ue au semestre
        for (UE ue : allUe){
            if(ue.getSemestreNumber()>0 && ue.getSemestreNumber()<= numberSemester)
                getSemester(ue.getSemestreNumber()).addUE(ue);
        }
    }
}
