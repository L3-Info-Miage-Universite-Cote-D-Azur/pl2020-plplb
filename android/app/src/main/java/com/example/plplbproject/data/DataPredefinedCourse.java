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

    public ArrayList<String> getPredefinedCourseName(){
        ArrayList<String> predefinedCourseName = new ArrayList<String>();

        //On parcours nos ParcoursType et on recupère leurs noms.
        for(ParcoursType parcoursType : predefinedCourseList){
            predefinedCourseName.add(parcoursType.getName());
        }
        return predefinedCourseName;
    }

}
