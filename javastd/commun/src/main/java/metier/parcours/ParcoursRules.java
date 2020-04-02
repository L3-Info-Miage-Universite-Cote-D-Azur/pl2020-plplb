package metier.parcours;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class ParcoursRules implements Serializable {

    private ParcoursType parcoursType;//Le parcours type.
    private String currentErrorMessage;

    /* CONSTRUCTOR */
    public ParcoursRules(ParcoursType parcoursType){
        this.parcoursType = parcoursType;
    }

    /**
     * Retourne vrai ou faux selon que le parcours est accepter selon un parcours type.
     * @param listHashmap : 4 hashmaps qui contienne le nombre d'ue selectionnee par catégorie.
     * @param listSelectUe : la liste des ues selectionnee.
     * @return true ou false.
     */
    public boolean acceptParcours(ArrayList<HashMap<String,Integer>> listHashmap,ArrayList<String> listSelectUe){
        //VERIFICATION SUR LE NOMBRE
        int res;//Le nombre d'ue de la catégorie key dans les 4 semestres.

        setCurrentErrorMessage("");

        if(parcoursType.getNumberUes() != null){
            for(String key : parcoursType.getNumberUes().keySet()){
                res = 0;//On remet res à 0.

                for(HashMap<String,Integer> UebyCategorie : listHashmap){
                    //On ajoute le nombre d'ue de la catégorie key
                    res += UebyCategorie.getOrDefault(key,0);
                }

                if(res < parcoursType.getNumberUes().get(key)){
                    setCurrentErrorMessage("Pas assez d'UE de la catégorie " + key + ".");
                    return false;//Pas assez d'ue de la catégorie key.
                }
            }
        }

        //VERIFICATION SUR LES UES OBLIGATOIRE.
        if(parcoursType.getObligatoryUes() != null){
            for(String obligatoryUe : parcoursType.getObligatoryUes()){
                if(!listSelectUe.contains(obligatoryUe)){
                    setCurrentErrorMessage("Une UE obligatoire n'a pas été chochée: " + obligatoryUe + ".");
                    return false;//On ne contient pas une ue obligatoire.
                }
            }
        }

        //Toute les vérif sont ok.
        return true;
    }

    public ParcoursType getParcoursType(){
        return this.parcoursType;
    }

    public String getCurrentErrorMessage() {
        return currentErrorMessage;
    }

    public void setCurrentErrorMessage(String currentErrorMessage) {
        this.currentErrorMessage = currentErrorMessage;
    }
}
