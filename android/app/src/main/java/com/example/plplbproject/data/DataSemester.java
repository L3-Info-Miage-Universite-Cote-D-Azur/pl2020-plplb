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


    public boolean hasSemesterList(){
        if(semesterList==null) return false;
        return true;
    }

    public int getNumberSemesters(){
        if(semesterList==null) return 0;
        return semesterList.size();
    }
}
