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
    private int numberUENeedChoose; //nombre d'ue necessaire de selectionner pour valider une semestre
    private int maxNumberByCategorie; //nombre maximum d'ue par categorie
    private List<String> listObligatory; //liste des ue obligatoire du semestre

    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Categorie> listCategorie,List<String> listObligatory){
        this.number = number;
        this.listCategorie = new ArrayList<Categorie>(listCategorie);

        //For RULE
        this.listObligatory = listObligatory;

        //TODO adapter la champs suivant en fonction des parametre pour le moment fonctionne uniquement pour le S1
        numberUENeedChoose = 6;
        maxNumberByCategorie = 1;

    }

    public int getNumber() {
        return number;
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
