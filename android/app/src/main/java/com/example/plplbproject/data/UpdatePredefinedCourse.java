package com.example.plplbproject.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import io.socket.emitter.Emitter;
import metier.parcours.ParcoursType;

public class UpdatePredefinedCourse implements Emitter.Listener {

    @Override
    public void call(Object... args) {
        Gson gson = new GsonBuilder().create();
        ArrayList<ParcoursType> predefinedCourseList = new ArrayList<ParcoursType>();
        ArrayList<String> data = gson.fromJson((String) args[0], ArrayList.class);

        //On recupere une liste de json de parcoursType
        for(String s : data){
            predefinedCourseList.add(gson.fromJson(s,ParcoursType.class));
        }

        DataPredefinedCourse.PREDEFINEDCOURSE.setPredefinedCourseList(predefinedCourseList);
    }
}
