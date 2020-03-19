package metier;

import metier.parcours.Parcours;
import metier.semestre.Semestre;
import metier.semestre.SemestreList;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Class main du modele java elle a pour but de gerer les different action sur les UE du client
 */

public class MainModele implements Serializable {

    private Etudiant etudiant; //etudiant qui est connecter a l'application
    private Parcours parcours; //Le parcours qui est charge.
    private SemestreList semestres;
    private int semestreCourant = 0;

    /* CONSTRUCTOR */
    public MainModele(){
        this.etudiant = new Etudiant("Etudiant defaut");
        this.semestres = new SemestreList();
        this.parcours = new Parcours(semestres);
        this.semestreCourant = 0; // On pense en terme d'index de liste
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

    /**
     * Renvoie l'arrayList contenant tous les semestres
     * @return
     */
    public SemestreList getSemestres() {
        return semestres;
    }

    /**
     * Renvoie le semestre courant à afficher sur le MainActivity
     * @return
     */
    public int getSemestreCourant() {
        return semestreCourant;
    }

    /**
     * Renvoie le semestre à la position donnée
     * On assume que la position 0 correspond au semestre 1, la position 1 au semestre 2 etc.
     * @param index
     */
    public Semestre getSemestre(int index){
        if(semestres==null) return null;
        if(index>=semestres.size()) return null;
        return this.semestres.get(index);
    }

    public void setSemestre(Semestre semestre, int index){

        this.semestres.set(index,semestre);
        // TODO parcours.setSemestre(semestre);
    }

    /**
     * Cette fonction est utilisée pour changer le semestre courant du semestre.
     * On rappelle qu'on pense en terme d'index de liste.
     * @param index l'index qui contient le semestre. Pour spécifier le semestre 4, il faut passer en parametre index = 3
     */
    public void changeSemestre(int index){
        this.semestreCourant = index;
    }

    public void addSemestre(Semestre semestre){
        this.semestres.add(semestre);
    }



    /**
     * le semestre courrant passe au semestre suivant (si il existe)
     */
    public void nextSemestre(){
        if(hasNextSemestre()){
            semestreCourant+=1;
        }
    }

    /**
     * le semestre courrant passe au semestre precedent (si il existe)
     */
    public void prevSemestre(){
        if(hasPrevSemestre()){
            semestreCourant-=1;
        }
    }

    /**
     * Verifie si il existe un semestre suivant
     * @return true il en existe 1; false il en existe pas
     */
    public boolean hasNextSemestre(){
        int nbSemestre = semestres.size();
        if(semestreCourant+1<nbSemestre){
            return true;
        }
        return false;
    }


    /**
     * Verifie si il existe un semestre precedent
     * @return true il en existe 1; false il en existe pas
     */
    public boolean hasPrevSemestre(){
        if(semestreCourant>0){
            return true;
        }
        return false;
    }

}
