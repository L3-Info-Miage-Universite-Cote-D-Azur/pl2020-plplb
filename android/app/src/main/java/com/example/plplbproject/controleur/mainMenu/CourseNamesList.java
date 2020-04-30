package com.example.plplbproject.controleur.mainMenu;

import com.example.plplbproject.Vue.mainMenu.MainMenuActivity;

import java.util.ArrayList;
import java.util.Collections;

public class CourseNamesList {

    private MainMenuActivity mainMenuActivity;
    private final ArrayList<String> listCoursesNames;

    public CourseNamesList(){
        listCoursesNames = new ArrayList<String>();
    }

    /**
     * Ajoute une liste en supprimant l'ancienne
     * @param newCoursesNames : la liste a ajouter
     */
    public void addAll(ArrayList<String> newCoursesNames){
        listCoursesNames.clear();
        listCoursesNames.addAll(newCoursesNames);
    }

    /**
     * retire un parcours de la liste
     * @param courseName :  le parcours a retirer
     * @return true ou false selon le succès.
     */
    public boolean removeCourse(String courseName){
        return listCoursesNames.remove(courseName);
    }

    /**
     * ajoute un parcours a la liste
     * @param courseName : la parcours a ajouter
     */
    public void addCourse(String courseName){
        if(!isInList(courseName)){
            listCoursesNames.add(courseName);
            this.sort();
        }
    }

    /**
     * renvoie true ou false , selon si le parcours est dans la liste
     * @param courseName :  le parcours
     * @return true ou false;
     */
    public boolean isInList(String courseName){
        return listCoursesNames.contains(courseName);
    }

    /**
     * trie la liste
     */
    public void sort(){
        Collections.sort(listCoursesNames);
    }

    /**
     * renvoie la taille de la liste
     * @return la taille de la liste
     */
    public int getSize(){
        return listCoursesNames.size();
    }

    /**
     * renvoie le parcours d'un index donné
     * @param index : l'index de le parcours a renvoyer
     * @return : le parcours à l'index donné.
     */
    public String getCourse(int index){
        return listCoursesNames.get(index);
    }

    public ArrayList<String> getList(){return listCoursesNames;}

    /**
     * renvoie true ou false selon si le nom peut etre choisi
     * @param name : le nom à verifier
     * @return true ou false.
     */
    public boolean canBeChoosed(String name){
        for(int i =0; i < name.length();i++){
            //Pour eviter qu'il creer son fichier dans un dossier exterieur avec un nom comme "../../Oups"
            if(name.charAt(i) == '/' || name.charAt(i) == '\\'
                    || name.charAt(i) == '.' || name.charAt(i) == '%'){
                return false;
            }
        }
        return true;
    }
}
