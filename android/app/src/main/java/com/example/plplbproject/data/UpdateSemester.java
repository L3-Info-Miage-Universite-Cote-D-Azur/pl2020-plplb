package com.example.plplbproject.data;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.semestre.Semestre;
import metier.semestre.SemestreList;

/**
 * Permet de mettre a jour la liste des semestre du client
 */
public class UpdateSemester implements Emitter.Listener {

    @Override
    public void call(Object... args) {
        Gson gson = new Gson();
        ArrayList<String> semesters = gson.fromJson((String) args[0], ArrayList.class);

        SemestreList semeterList = new SemestreList();

        for (String semester: semesters) {
            semeterList.add(gson.fromJson(semester, Semestre.class));
        }

        DataSemester.SEMESTER.setSemesterList(semeterList);
    }
}
