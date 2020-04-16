package com.example.plplbproject.data;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.semestre.Semestre;
import metier.semestre.SemesterList;

/**
 * Permet de mettre a jour la liste des semestre du client
 */
public class UpdateSemester implements Emitter.Listener {

    @Override
    public void call(Object... args) {
        Gson gson = new Gson();
        DataSemester.SEMESTER.setSemesterList(gson.fromJson((String) args[0], SemesterList.class));

    }
}
