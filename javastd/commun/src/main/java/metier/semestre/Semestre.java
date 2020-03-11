package metier.semestre;

import java.util.ArrayList;

import metier.Categorie;
import metier.UE;
import metier.semestre.rules.BasicSemestreRules;
import metier.semestre.rules.SemestreRules;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des categorie qui contiennent des UEs.
 */
public class Semestre {
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<Categorie> listCategorie; // la liste des categorie.

    // RULE of semestre
    private SemestreRules rules;


    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Categorie> listCategorie, SemestreRules rules) {
        this.number = number;
        this.listCategorie = new ArrayList<Categorie>(listCategorie);
        //For RULE
        this.rules = rules;

    }

    public Semestre() {
        this.number = -1;
        this.listCategorie = new ArrayList<Categorie>();

        //For RULE
        this.rules = new BasicSemestreRules(-1, -1, null);
    }

    /* Methods */


    public SemestreRules getRules() {
        return rules;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Categorie> getListCategorie() {
        return listCategorie;
    }

    public void setListCategorie(ArrayList<Categorie> listCategorie) {
        this.listCategorie = listCategorie;
    }


    /**
     * Cherche une UE a l'aide de sont code
     * @param codeUE le code de l'ue rechercher
     * @return l'ue si elle est trouver
     */
    public UE findUE(String codeUE) {
        UE ue;
        for (Categorie categorie : listCategorie) {
            ue = categorie.findUE(codeUE);
            if (ue != null) return ue;
        }
        //not find
        return null;
    }




}
