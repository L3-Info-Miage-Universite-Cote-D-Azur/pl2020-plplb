package com.example.plplbproject.data;

import java.util.ArrayList;

import metier.parcours.ParcoursType;

public enum  DataPredefinedCourse {
    PREDEFINEDCOURSE;

    private ArrayList<ParcoursType> predefinedCourseList;

    public void setPredefinedCourseList(ArrayList<ParcoursType> predefinedCourseList) {
        this.predefinedCourseList = predefinedCourseList;
    }

    public ArrayList<ParcoursType> getPredefinedCourseList() {
        return predefinedCourseList;
    }


    public boolean hasPredefinedCourseList(){
        if(predefinedCourseList==null) return false;
        return true;
    }

    /**
     * Permet de récuprer la liste des noms des parcours type
     * @return une arraylist de string pour les noms des parcours type
     */
    public ArrayList<String> getPredefinedCourseName(){
        ArrayList<String> predefinedCourseName = new ArrayList<String>();

        //On verifique que l'on a reçu des données
        if(hasPredefinedCourseList()){
            //On parcours nos ParcoursType et on recupère leurs noms.
            for(ParcoursType parcoursType : predefinedCourseList){
                //On les ajoute a notre liste
                predefinedCourseName.add(parcoursType.getName());
            }
        }
        return predefinedCourseName;
    }

    /**
     * Permet de récupérer un parcours type en connaissant son nom
     * @param predifinedCourseName le nom du parcours à trouver
     * @return renvoie le parcoursType si trouvé, null sinon
     */
    public ParcoursType getPredefinedCourse(String predifinedCourseName){
        //On verifique l'on a des données
        if(hasPredefinedCourseList()){
            for (ParcoursType p: predefinedCourseList) {
                //Si le nom correspond
                if(p.getName() == predifinedCourseName){
                    //On renvoie l'objet parcoursType
                    return p;
                }
            }
        }
        return null;
    }

}
