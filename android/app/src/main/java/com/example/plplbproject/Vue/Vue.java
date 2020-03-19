package com.example.plplbproject.Vue;

public interface Vue {

    /**
     * permet de generer un toast message
     * @param msg le message que l'on veut generer
     */
    public void toastMessage(String msg);

    /**
     * notifie que les ue doivent etre update
     */
    public void notifyUeListView();

    /**
     * Replis toutes les liste de categorie
     */
    public void collapseList();

    /**
     * notifie l'affichage que le semestre courant a changer
     */
    public void notifySemestreChange();

    /**
     * permet de quité l'intent et passe a l'intent precedent
     */
    public void exitIntent();
}