package metier;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLowerCase;

public class LoginModele {

    /**
     * acceptINE vérifie si un ine correspond au format : ab123456.
     * @param ine : l'ine a vérifié.
     * @return true ou false, selon si il est accepté ou non.
     */
    public boolean acceptINE(String ine){
        if(ine.length() == 8 //La taille d'un ine doit etre 8.
                    && isLetter(ine.charAt(0)) && isLetter(ine.charAt(1)) //Les deux premiers caractères doivent etre des lettres
                    && isLowerCase(ine.charAt(0)) && isLowerCase(ine.charAt(1))){//Et etre en minuscules.
                for(int i = 2; i < ine.length(); i++){
                    //Tout les autres caractères doivent etre des chiffres.
                    if(!isDigit(ine.charAt(i))) return false;
                }
                return true; //L'ine est accepté.
        }
        return false;//On ne repond pas aux critères.
    }
}
