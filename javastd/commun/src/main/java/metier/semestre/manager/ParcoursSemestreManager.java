package metier.semestre.manager;

import java.util.HashMap;

import metier.UE;


public interface ParcoursSemestreManager {

    /**
     * ajoute une UE au parcours (verification effectuer a l'interrieur aucun changement si non possible)
     * @param ue l'ue a ajouter
     */
    public void check(UE ue);

    /**
     * Supprime l'ue du parcours, si celle ci y est present
     * @param ue l'ue a supprimer du parcours
     */
    public void uncheck(UE ue);


    /**
     * Regarde si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheck(UE ue);

    /**
     * Regarde si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheck(UE ue);



    /**
     * nombre d'ue par categorie selectionner
     * @return la hashmap nomCatergorie, nombreUESelectionner
     */
    public HashMap<String,Integer> getCountByCategory();

    /**
     * Nombre d'ue libre selectionner dans le parcours
     * @return le nombre d'ue libre
     */
    public int getUeLibreSelected();

    /**
     * nombre d'ue choix selectionner
     * @return le nombre d'ue a choix selectionner
     */
    public int getChooseUeSelected();

    /**
     * Verification d'un parcours complete
     * @return true parcours accepter; false non accepter
     */
    public boolean verifCompleteParcours();

}
