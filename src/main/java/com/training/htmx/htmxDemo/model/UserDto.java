package com.training.htmx.htmxDemo.model;

import jakarta.persistence.*;

public class UserDto {

    private String nom;

    private String prenom;


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }
}
