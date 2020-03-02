package metier;

import java.util.ArrayList;

/**
 * Matiere represente une matiere dans le cursus, elle se rapporte a un semestre et contient des UEs.
 */
public class Matiere {
    private String name;//Nom de la matiere.
    private ArrayList<UE> listUE; // la liste des Ues de la matiere.

    /* CONSTRUCTOR */
    public Matiere(String name, ArrayList<UE> UES){
        this.name = name;
        this.listUE = UES;
    }

    /* GETTERS AND SETTERS */
    public String getName() {
        return name;
    }

    public ArrayList<UE> getListUE() {
        return listUE;
    }

    public void setListUE(ArrayList<UE> listUE) {
        this.listUE = listUE;
    }
}
