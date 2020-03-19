package com.example.plplbproject.controleur.listener;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.reseau.Connexion;

import io.socket.emitter.Emitter;
import metier.MainModele;

/**
 * Listener d'event du serveur
 */
public abstract class EmitterListener implements Emitter.Listener {

    /*FIELDS*/
    private final Vue vue;
    private final MainModele model;

    /*CONSTRUCTOR*/
    public EmitterListener(Vue vue, MainModele model) {
        this.vue = vue;
        this.model = model;
    }

    /**
     * gestion de l'event
     * @param args arguments de l'event
     */
    @Override
    public abstract void call(Object... args);
}
