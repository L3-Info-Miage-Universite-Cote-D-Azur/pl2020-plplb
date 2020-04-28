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

    public void addAll(ArrayList<String> newCoursesNames){
        listCoursesNames.clear();
        listCoursesNames.addAll(newCoursesNames);
    }

    public boolean removeCourse(String courseName){
        return listCoursesNames.remove(courseName);
    }

    public void addCourse(String courseName){
        if(!isInList(courseName)){
            listCoursesNames.add(courseName);
            this.sort();
        }
    }

    public boolean isInList(String courseName){
        return listCoursesNames.contains(courseName);
    }

    public void sort(){
        Collections.sort(listCoursesNames);
    }

    public int getSize(){
        return listCoursesNames.size();
    }

    public String getCourse(int index){
        return listCoursesNames.get(index);
    }

    public ArrayList<String> getList(){return listCoursesNames;}
}
