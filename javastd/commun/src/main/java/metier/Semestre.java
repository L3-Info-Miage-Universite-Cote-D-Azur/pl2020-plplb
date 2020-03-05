package metier;

import java.util.ArrayList;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des categorie qui contiennent des UEs.
 */
public class Semestre{
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<Categorie> listCategorie; // la liste des categorie.

    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Categorie> listCategorie){
        this.number = number;
        this.listCategorie = new ArrayList<Categorie>(listCategorie);
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

}
