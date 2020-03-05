package metier;

import java.util.ArrayList;

/**
 * Categorie represente une categorie dans le cursus, elle se rapporte a un semestre et contient des UEs.
 */
public class Categorie {
    private String name;//Nom de la categorie.
    private ArrayList<UE> listUE; // la liste des Ues de la categorie.

    /* CONSTRUCTOR */
    public Categorie(String name, ArrayList<UE> UES){
        this.name = name;
        this.listUE = new ArrayList<UE>(UES);
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

    /**
     * Cherche une UE a l'aide de sont code
     * @param codeUE le code de l'ue rechercher
     * @return l'ue si elle est trouver
     */
    public UE findUE(String codeUE){
        for(UE ue: listUE){
            if(ue.getUeCode().equals(codeUE)) return ue;
        }
        //not find
        return null;
    }
}
