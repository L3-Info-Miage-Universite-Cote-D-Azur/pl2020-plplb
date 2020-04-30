package com.example.plplbproject.data;

import metier.semestre.SemesterList;

public enum DataSemester {
    SEMESTER;

    private SemesterList semesterList; //list des semestre qui contient les ue

    public void setSemesterList(SemesterList semesterList) {
        this.semesterList = semesterList;
    }

    public SemesterList getSemesterList() {
        return semesterList;
    }

    /**
     * renvoie true ou false selon que l'instance a une liste.
     * @return true ou false.
     */
    public boolean hasSemesterList(){
        if(semesterList==null) return false;
        return true;
    }

    /**
     * Renvoie le nombre de semestre
     * @return le nombre de semestre
     */
    public int getNumberSemesters(){
        if(semesterList==null) return 0;
        return semesterList.size();
    }
}
