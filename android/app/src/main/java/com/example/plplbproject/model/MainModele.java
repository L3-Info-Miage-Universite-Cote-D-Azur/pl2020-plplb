package com.example.plplbproject.model;

import java.util.ArrayList;

import metier.Categorie;
import metier.Etudiant;
import metier.UE;

/**
 * Class main du modele java elle a pour but de gerer les different action sur les UE du client
 */
public class MainModele {

    private ArrayList<Categorie> allCat;
    private ArrayList<UE> allUE; //posséde tout les ue a afficher
    private Etudiant etudiant; //etudiant qui est connecter a l'application

    /* CONSTRUCTOR*/
    public MainModele(){
        allUE = new ArrayList<>();
        allCat = new ArrayList<>();
        this.etudiant = new Etudiant("Etudiant 1");
    }

    /**
     * permet de remplacer l'integralité des ue chargées par une liste d'ue
     * @param newAllUE La nouvelle liste des UE
     */
    public void setAllUE(ArrayList<UE> newAllUE) {
        this.allUE.clear();
        addToAllUE(newAllUE);
    }

    /**
     * permet de remplacer l'integralité des catégories chargées par une liste de catégories
     * @param newAllCat La nouvelle liste des UE
     */
    public void setAllCat(ArrayList<UE> newAllCat) {
        this.allCat.clear();
        addToAllUE(newAllCat);
    }

    /**
     * renvoie l'etutiand de connecter a l'application
     * @return etudiant courrant
     */
    public Etudiant getEtudiant() {
        return etudiant;
    }

    /**
     * Liste des ue present dans l'application
     * @return les liste des UE
     */
    public ArrayList<UE> getAllUE() {
        return allUE;
    }

    /**
     * Liste des catégories presentes dans l'application
     * @return les liste des catégories
     */
    public ArrayList<Categorie> getAllCatgory() {
        return allCat;
    }

    /**
     * Ajouter une UE a la liste des UE
     * @param ue ue a ajouter
     */
    public void addToAllUE(UE ue){
        allUE.add(ue);
    }

    /**
     * Ajoute une liste d'UE au UE courant
     * @param arrayUe liste a ajouter au UE.
     */
    public void addToAllUE(ArrayList<UE> arrayUe){
        for(UE ue: arrayUe){ allUE.add(ue);}
    }

}
