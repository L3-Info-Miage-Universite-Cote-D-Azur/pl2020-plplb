package metier.semestre;

import metier.UE;

import java.util.ArrayList;

/**
 * SemestreList est une arrayList de semestre.
 */
public class SemestreList extends ArrayList<Semestre>{

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
}
