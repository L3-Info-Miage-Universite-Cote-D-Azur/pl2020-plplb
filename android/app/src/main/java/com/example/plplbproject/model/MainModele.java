package com.example.plplbproject.model;

import java.util.ArrayList;

import metier.Etudiant;
import metier.Semestre;
import metier.UE;

public class MainModele {

    ArrayList<UE> allUE; //posséde tout les ue a afficher
    Etudiant etudiant; //etudiant qui est connecter a l'application


    public MainModele(){
        allUE = new ArrayList<>();
        this.etudiant = new Etudiant();
    }



}
