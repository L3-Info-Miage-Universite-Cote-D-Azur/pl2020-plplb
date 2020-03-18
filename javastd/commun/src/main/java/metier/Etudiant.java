package metier;

import java.io.Serializable;

/**
 * Etudiant represente le client qui se connecte au serveur.
 */
public class Etudiant implements Serializable {

    /* FIELDS */
    private  String nom; //le nom de l'etudiant

    /* CONSTRUCTOR */
    public Etudiant() {
        this("nom par défaut");
    }

    public Etudiant(String nom) {
        this.nom = nom;
    }



    /*GETTERS/SETTERS*/
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String toString() {
        return this.getNom();
    }


}

