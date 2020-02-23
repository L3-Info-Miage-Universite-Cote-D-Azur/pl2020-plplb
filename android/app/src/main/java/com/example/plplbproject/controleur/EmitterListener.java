package com.example.plplbproject.controleur;

import com.example.plplbproject.Vue.Vue;
import com.example.plplbproject.model.MainModele;
import com.example.plplbproject.reseau.Connexion;

import io.socket.emitter.Emitter;

public abstract class EmitterListener implements Emitter.Listener {

    private final Vue vue;
    private final Connexion connexion;
    private final MainModele model;

    public EmitterListener(Vue vue, Connexion connexion, MainModele model) {
        this.vue = vue;
        this.connexion = connexion;
        this.model = model;
    }

    @Override
    public abstract void call(Object... args);
}
