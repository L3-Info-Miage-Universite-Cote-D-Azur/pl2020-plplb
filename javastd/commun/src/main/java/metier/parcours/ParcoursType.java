package metier.parcours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ParcoursType implements Serializable {
    private String name;//Le nom du parcours Type.

    private HashMap<String,Integer> numberUes;//Le nombre d'ue a avoir dans chaque catégorie pour passer en l3
    private ArrayList<String> obligatoryUes;//Les ues obligatoire pour passer en L3

    /* CONSTRUCTOR */
    public ParcoursType(String name,HashMap<String,Integer> numberUes,ArrayList<String> obligatoryUes){
        this.name = name;
        this.numberUes = numberUes;
        this.obligatoryUes = obligatoryUes;
    }

    /* GETTERS AND SETTERS */
    public String getName() {
        return name;
    }

    public HashMap<String, Integer> getNumberUes() {
        return numberUes;
    }

    public ArrayList<String> getObligatoryUes() {
        return obligatoryUes;
    }
}
