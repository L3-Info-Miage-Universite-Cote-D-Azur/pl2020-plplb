package com.example.plplbproject.controleur.creationMenu;

import java.util.ArrayList;

public class CreationMenuModele {
    private ArrayList<String> listPredefinedCourse;//Liste des noms des parcours prédéfinis.
    private ArrayList<String> listCourseName;//La liste des noms de parcours sauvegardes.

    private String predefinedCourseName;//Le nom du parcours prédéfini selectionner.
    private String courseName;//Le nom du parcours.

    public CreationMenuModele(ArrayList<String> listPredefinedCourse,ArrayList<String> listCourseName){
        this.listPredefinedCourse = listPredefinedCourse;
        this.listCourseName = listCourseName;
    }

    /**
     * Renvoie true ou false, selon si on peut choisir le nom
     * @param name : le nom qu'il faut vérifier.
     * @return true ou false.
     */
    public boolean canBeChooseName(String name){
        if(name.trim().equals("")) return false;
        if(listCourseName.contains(name)){//Le nom est déjà pris.
            return false;
        }
        return true;//Le nom est dispo.
    }

    /**
     * Retourne true ou false selon que un parcours predefini a ete choisi.
     * @return true ou false.
     */
    public boolean isSelectedPredefinedCourse(){
        if(predefinedCourseName == null) return false;
        else return true;
    }


    /* GETTERS AND SETTERS */
    public String getPredefinedCourseName() {
        return predefinedCourseName;
    }

    public void setPredefinedCourseName(String predefinedCourseName) {
        this.predefinedCourseName = predefinedCourseName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public ArrayList<String> getListPredefinedCourse() {
        return listPredefinedCourse;
    }
}