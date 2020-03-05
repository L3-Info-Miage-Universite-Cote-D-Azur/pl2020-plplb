package metier;

/**
 * Class main du modele java elle a pour but de gerer les different action sur les UE du client
 */

public class MainModele {

    private Etudiant etudiant; //etudiant qui est connecter a l'application
    private Parcours parcours; //Le parcours qui est charge.
    private Semestre semestre; //Le semestre affiche.

    /* CONSTRUCTOR */
    public MainModele(){
        this.etudiant = new Etudiant("Etudiant 1");
        this.semestre = new Semestre(-1);
        this.parcours = new Parcours(semestre);
    }

    /* GETTERS AND SETTERS */
    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Parcours getParcours() {
        return parcours;
    }

    public void setParcours(Parcours parcours) {
        this.parcours = parcours;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre){
        this.semestre = semestre;
    }
}
