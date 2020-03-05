package metier;

import java.util.ArrayList;
import java.util.List;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des categorie qui contiennent des UEs.
 */
public class Semestre{
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<Categorie> listCategorie; // la liste des categorie.

    // RULE of semestre
    //TODO adapter la champs suivant en fonction des parametre pour le moment fonctionne uniquement pour le S1
    private int numberUENeedChoose = 5; //nombre d'ue necessaire de selectionner pour valider une semestre
    private int maxNumberByCategorie = 1; //nombre maximum d'ue par categorie
    private ArrayList<String> listObligatory; //liste des ue obligatoire du semestre

    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Categorie> listCategorie,ArrayList<String> listObligatory){
        this.number = number;
        this.listCategorie = new ArrayList<Categorie>(listCategorie);

        //For RULE
        this.listObligatory = listObligatory;


    }

    public Semestre(int number){
        this.number = number;
        this.listCategorie = new ArrayList<Categorie>();
        this.listObligatory = new ArrayList<String>();
    }

    /* Methods */

    public int getNumber() {
        return number;
    }
    public void setNumber(int number){this.number = number;}

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
    public UE findUE(String codeUE){
        UE ue;
        for(Categorie categorie: listCategorie){
            ue = categorie.findUE(codeUE);
            if(ue!= null) return ue;
        }
        //not find
        return null;
    }

    public int getNumberUENeedChoose(){ return numberUENeedChoose; }

    public int getMaxNumberByCategorie(){ return maxNumberByCategorie; }

    public List<String> getListObligatory(){ return listObligatory; }

    /**
     * Verification si une UE fait partie des ue obligatoire du semestre
     * @param ue L'ue que l'on verifie
     * @return true: est une ue obligatoire;
     */
    public boolean isObligatoryUE(UE ue){
        if(listObligatory == null) return false;
        for(String currentCodeUE: listObligatory) {
            if (currentCodeUE.equals(ue.getUeCode())) return true;
        }
        return false;
    }

}
