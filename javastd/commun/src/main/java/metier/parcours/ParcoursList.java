package metier.parcours;

import java.util.ArrayList;

/**
 * Liste des parcours que possede l'etudiant
 */
public class ParcoursList extends ArrayList<Parcours> {

    int currentParcours = 0;

    /**
     * Recupere un parcours par sont nom si il existe
     * @param name le nom du parcours que l'on cherche
     * @return le parcours si il a etait trouvé.
     */
    public Parcours getParcoursByName(String name){
        for(Parcours parcours: this){
            if(parcours.getName().equals(name)) return parcours;
        }
        return null;
    }


    public Parcours getCurrentParcours(){
        if(currentParcours>=0 && currentParcours<this.size()){
            return this.get(currentParcours);
        }
        return null;
    }

    /**
     * Permet de prevenir le parcours que la liste de semestre a changer
     */
    public void updateSemestre(){
        for(Parcours parcours : this) parcours.updateSemestre();
    }

    /**
     * Permet de generer la list de nom des parcours
     * @return list des nom les nom de parcours
     */
    public ArrayList<String> getAllParcoursName(){
        ArrayList<String> allName = new ArrayList<String>();
        for(Parcours current : this) allName.add(current.getName());
        return allName;
    }






}
