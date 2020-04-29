package com.example.plplbproject.controleur.mainMenu;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class CoursesNamesListTest {

    ArrayList<String> list;
    ArrayList<String> list2;
    CourseNamesList courseNamesList;

    @Before
    public void init(){
        list = new ArrayList<>();
        list.add("nom1");
        list.add("nom2");
        list.add("nom3");
        list.add("nom4");

        list2 = new ArrayList<>();
        list2.add("nom5");
        list2.add("nom6");
        list2.add("nom7");

        courseNamesList = new CourseNamesList();
        courseNamesList.addAll(list);
    }

    @Test
    public void addAllTest(){

        //Les éléments sont bien ceux qu'ont veut au début
        assertEquals(4,courseNamesList.getSize());
        assertEquals("[nom1, nom2, nom3, nom4]", Arrays.toString(courseNamesList.getList().toArray()));

        //On appelle la méthodes
        courseNamesList.addAll(list2);
        //La taille a changée
        assertEquals(3,courseNamesList.getSize());
        //Les éléments sont corrects
        assertEquals("[nom5, nom6, nom7]",Arrays.toString(courseNamesList.getList().toArray()));

    }

    @Test
    public void removeCourseTest(){
        int oldsize;

        //Nécessaire car on ne peut parcourir une liste qui change.
        ArrayList<String> copy = new ArrayList<>();
        for(String name : courseNamesList.getList()){
            copy.add(name);
        }

        for(String name : copy){//On utilise donc la copie
            //Il est dans la liste
            oldsize = courseNamesList.getSize();
            assertEquals(true,courseNamesList.isInList(name));
            //On le remove
            courseNamesList.removeCourse(name);
            assertEquals(oldsize-1,courseNamesList.getSize());
            assertEquals(false,courseNamesList.isInList(name));
        }
        //Il ne reste plus rien
        assertEquals(0,courseNamesList.getSize());
    }

    @Test
    public void addCourseTest(){
        int oldsize = courseNamesList.getSize();

        for(String name : list){
            assertEquals(true, courseNamesList.isInList(name));
            //Normalement il ne sera pas ajouter car existe déjà dans la liste.
            courseNamesList.addCourse(name);
            assertEquals(true, courseNamesList.isInList(name));
        }
        assertEquals(oldsize, courseNamesList.getSize());

        for(String newName : list2){
            //ils ne sont aps dans la liste
            assertEquals(false, courseNamesList.isInList(newName));
            courseNamesList.addCourse(newName);
            //Ils y sont
            assertEquals(true, courseNamesList.isInList(newName));
        }
        //On a bien ajouter tout les elements
        assertEquals(oldsize+list2.size(),courseNamesList.getSize());
    }

    @Test
    public void isInListTest(){
        for(String s : list){
            assertEquals(true,courseNamesList.isInList(s));
        }

        for(String s : list2){
            assertEquals(false,courseNamesList.isInList(s));
        }
    }

    @Test
    public void sortTest(){

        //Liste qui n'est pas triée alphabétiquement
        ArrayList<String> list3 = new ArrayList<>();
        list3.add("cc");
        list3.add("bb");
        list3.add("aa");

        //On l'ajoute
        courseNamesList.addAll(list3);
        //La liste est non triée
        assertEquals("[cc, bb, aa]", Arrays.toString(courseNamesList.getList().toArray()));
        courseNamesList.sort();
        //La liste est maintenant triée
        assertEquals("[aa, bb, cc]", Arrays.toString(courseNamesList.getList().toArray()));
    }

    @Test
    public void getSizeTest(){
        assertEquals(4,courseNamesList.getSize());

        //Nécessaire car on ne peut parcourir une liste qui change.
        ArrayList<String> copy = new ArrayList<>();
        for(String name : courseNamesList.getList()){
            copy.add(name);
        }

        for(String name : copy){
            int oldsize = courseNamesList.getSize();
            courseNamesList.removeCourse(name);
            assertEquals(oldsize-1,courseNamesList.getSize());
        }
        //Il ne reste plus rien
        assertEquals(0,courseNamesList.getSize());
    }

    @Test
    public void getCourseTest(){
        assertEquals(list.size(),courseNamesList.getSize());

        for(int i =0; i < list.size(); i++){
            String el = courseNamesList.getCourse(i);
            String el2 = list.get(i);
            assertEquals(el2,el);
        }
    }

    @Test
    public void canBeChoosedTest(){
        //Nos test
        String true1 = "un nom classique";
        String true2 = "test";
        String true3 = "un_n@m_m4rr4nt";

        String false1 = "%appdata%";
        String false2 = "../../oups";
        String false3 = "..........";

        assertEquals(true, courseNamesList.canBeChoosed(true1));
        assertEquals(true, courseNamesList.canBeChoosed(true2));
        assertEquals(true, courseNamesList.canBeChoosed(true3));

        assertEquals(false, courseNamesList.canBeChoosed(false1));
        assertEquals(false, courseNamesList.canBeChoosed(false2));
        assertEquals(false, courseNamesList.canBeChoosed(false3));
    }
}
