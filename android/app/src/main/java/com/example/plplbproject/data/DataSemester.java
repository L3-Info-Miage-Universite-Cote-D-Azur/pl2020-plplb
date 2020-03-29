package com.example.plplbproject.data;

import metier.semestre.SemestreList;

public enum DataSemester {
    SEMESTER;

    private SemestreList semesterList; //list des semestre qui contient les ue

    public void setSemesterList(SemestreList semesterList) {
        this.semesterList = semesterList;
    }

    public SemestreList getSemesterList() {
        return semesterList;
    }


    public boolean hasSemesterList(){
        if(semesterList==null) return false;
        return true;
    }
}
