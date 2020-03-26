package metier;

import java.util.ArrayList;

public class MenuInterModele {
    private ArrayList<String> listParcoursPredef;//Liste des noms des parcours prédéfinis.
    private ArrayList<String> listParcoursName;//La liste des noms de parcours sauvegardes.

    private String parcoursTypeName;//Le nom du parcours prédéfini selectionner.
    private String parcoursName;//Le nom du parcours.

    public MenuInterModele(ArrayList<String> listParcoursPredef,ArrayList<String> listParcoursName){
        this.listParcoursName = listParcoursName;
        this.listParcoursPredef = listParcoursPredef;
    }

    /**
     * Renvoie true ou false, selon si on peut choisir le nom
     * @param name : le nom qu'il faut vérifier.
     * @return true ou false.
     */
    public boolean canBeChooseName(String name){
        if(name.trim().equals("")) return false;
        if(listParcoursName.contains(name)){//Le nom est déjà pris.
            return false;
        }
        return true;//Le nom est dispo.
    }

    /**
     * Retourne true ou false selon que un parcours predefini a ete choisi.
     * @return true ou false.
     */
    public boolean isSelectedParcours(){
        if(parcoursTypeName == null) return false;
        else return true;
    }


    /* GETTERS AND SETTERS */
    public String getParcoursTypeName() {
        return parcoursTypeName;
    }

    public void setParcoursTypeName(String parcoursTypeName) {
        this.parcoursTypeName = parcoursTypeName;
    }

    public String getParcoursName() {
        return parcoursName;
    }

    public void setParcoursName(String parcoursName) {
        this.parcoursName = parcoursName;
    }

    public ArrayList<String> getListParcoursPredef() {
        return listParcoursPredef;
    }
}
