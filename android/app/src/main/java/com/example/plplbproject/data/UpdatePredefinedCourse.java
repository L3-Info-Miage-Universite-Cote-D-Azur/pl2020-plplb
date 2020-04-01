package com.example.plplbproject.data;

import com.google.gson.Gson;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.ParcoursType;

public class UpdatePredefinedCourse implements Emitter.Listener {

    @Override
    public void call(Object... args) {
        Gson gson = new Gson();
        ArrayList<ParcoursType> predefinedCourseList = gson.fromJson((String) args[0], ArrayList.class);

        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(predefinedCourseList);
    }
}
