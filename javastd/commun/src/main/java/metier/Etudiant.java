package metier;

public class Etudiant{

    private  String nom;

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

