package com.natixis.restaurant.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Serveur {

    // objectif = récuperer les éléments de la base de données pour les ranger dans les objets java

    // attribut de la classe
    private final int id;
    private final String prenom;
    private final String nom;

    //alt+inser = choix constructor + selection des attributs
    public Serveur(int id, String prenom, String nom) {
        this.id = id;
        this.prenom = prenom;
        this.nom = nom;
    }

    @Override
    public String toString() {
        return "Serveur#" + id +": " + prenom +  " " + nom;
    }

    // faire une fonction statique = méthode qui concerne une classe dans son ensemble;
    // si cela ne concerne qu'une seule instance = alors static n'est pas précisé;

    public static List<Serveur> getServeurs(Connection connection) throws SQLException {

// Récupération des données dans la base de données
        Statement ordreSQL = connection.createStatement();
        ResultSet result = ordreSQL.executeQuery("SELECT * from serveur");


        List<Serveur> serveurList = new ArrayList<>();

        while (result.next()) {
            Serveur serveur = new Serveur (result.getInt("id"), result.getString("prenom"), result.getString("nom") );
            serveurList.add(serveur);
            System.out.println (serveur);
        }

        result.close();
        ordreSQL.close();
        return serveurList;
    }
}



