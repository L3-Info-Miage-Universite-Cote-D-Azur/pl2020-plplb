package metier;

import java.io.Serializable;

import static java.lang.Character.isDigit;
import static java.lang.Character.isLetter;
import static java.lang.Character.isLowerCase;

/**
 * Etudiant represente le client qui se connecte au serveur.
 */
public class Student implements Serializable {

    /* FIELDS */
    private  String nom; //le nom de l'etudiant

    /* CONSTRUCTOR */
    public Student() {
        this("nom par défaut");
    }

    public Student(String nom) {
        this.nom = nom;
    }



    /*GETTERS/SETTERS*/
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String toString() {
        return this.getNom();
    }

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

