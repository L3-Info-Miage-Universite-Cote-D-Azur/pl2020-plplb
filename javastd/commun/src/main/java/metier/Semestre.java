package metier;

import java.util.ArrayList;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des matieres qui contiennent des UEs.
 */
public class Semestre{
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<Matiere> listMatiere; // la liste des matieres.

    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Matiere> listUE){
        this.number = number;
        this.listMatiere = listMatiere;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<Matiere> getListUE() {
        return listMatiere;
    }

    public void setListUE(ArrayList<Matiere> listMatiere) {
        this.listMatiere = listMatiere;
    }

}
