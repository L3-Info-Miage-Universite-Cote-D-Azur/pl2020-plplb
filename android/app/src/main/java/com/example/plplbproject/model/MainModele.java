package com.example.plplbproject.model;

import java.util.ArrayList;

import metier.Etudiant;
import metier.UE;

public class MainModele {

    private ArrayList<UE> allUE; //posséde tout les ue a afficher
    private Etudiant etudiant; //etudiant qui est connecter a l'application


    public MainModele(){
        allUE = new ArrayList<>();
        this.etudiant = new Etudiant("Etudiant 1");
    }

    public void setAllUE(ArrayList<UE> allUE) {
        this.allUE = allUE;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }


    public ArrayList<UE> getAllUE() {
        return allUE;
    }

    public void addToAllUE(UE ue){
        allUE.add(ue);
    }
    public void addToAllUE(ArrayList<UE> arrayUe){
        for(UE ue: arrayUe){ allUE.add(ue);}
    }

}
