package com.example.plplbproject.controleur.courseBuilder;

import metier.parcours.Parcours;

import java.io.Serializable;

/**
 * Class main du modele java elle a pour but de gerer les different action sur les UE du client
 */

public class CourseBuilderModele implements Serializable {

    private Parcours course; //Le parcours qui est charge.
    private int indexCurrentSemester = 0;
    private int numberSemesters;

    /* CONSTRUCTOR */
    public CourseBuilderModele(int numberSemesters){

        this.numberSemesters = numberSemesters;
        this.indexCurrentSemester = 0; // On pense en terme d'index de liste
    }



    /**
     * Permet de recupere le parcours courrant
     * @return le parcours courrant
     */
    public Parcours getCourse() {
        return course;
    }

    /**
     * permet de set un parcours
     * @param parcours le parcours que l'on veut set
     */
    public void setCourse(Parcours parcours) {
        this.course = parcours;
    }


    /**
     * Renvoie le semestre courant à afficher sur le MainActivity
     * @return
     */
    public int getIndexCurrentSemester() {
        return indexCurrentSemester;
    }



    /**
     * Cette fonction est utilisée pour changer le semestre courant du semestre.
     * On rappelle qu'on pense en terme d'index de liste.
     * @param index l'index qui contient le semestre. Pour spécifier le semestre 4, il faut passer en parametre index = 3
     */
    public void changeSemester(int index){
        this.indexCurrentSemester = index;
    }


    /**
     * le semestre courrant passe au semestre suivant (si il existe)
     */
    public void nextSemester(){
        if(hasNextSemestre()){
            indexCurrentSemester+=1;
        }
    }

    /**
     * le semestre courrant passe au semestre precedent (si il existe)
     */
    public void prevSemestre(){
        if(hasPrevSemestre()){
            indexCurrentSemester-=1;
        }
    }

    /**
     * Verifie si il existe un semestre suivant
     * @return true il en existe 1; false il en existe pas
     */
    public boolean hasNextSemestre(){
        if(indexCurrentSemester+1<numberSemesters){
            return true;
        }
        return false;
    }


    /**
     * Verifie si il existe un semestre precedent
     * @return true il en existe 1; false il en existe pas
     */
    public boolean hasPrevSemestre(){
        if(indexCurrentSemester>0){
            return true;
        }
        return false;
    }

}
