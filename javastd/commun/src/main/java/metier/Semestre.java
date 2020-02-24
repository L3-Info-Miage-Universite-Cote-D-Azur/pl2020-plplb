package metier;

import java.util.ArrayList;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des UEs.
 */
public class Semestre{
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<UE> listUE; // la liste des UEs

    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<UE> listUE){
        this.number = number;
        this.listUE = listUE;
    }

    public int getNumber() {
        return number;
    }

    public ArrayList<UE> getListUE() {
        return listUE;
    }

    public void setListUE(ArrayList<UE> listUE) {
        this.listUE = listUE;
    }

}
