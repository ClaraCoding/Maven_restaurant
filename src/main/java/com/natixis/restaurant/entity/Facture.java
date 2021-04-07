package com.natixis.restaurant.entity;

import java.sql.*;

public class Facture {
    private final int id;
    private final int serveurIdx;
    private final int nbConvives;
    private final int tableIdx;

    public Facture(int id, int serveurIdx, int nbConvives, int tableIdx, double totalTTC) {
        this.id = id;
        this.serveurIdx = serveurIdx;
        this.nbConvives = nbConvives;
        this.tableIdx = tableIdx;
    }

    public static int newCmd(Connection connection, int serveur, int table, int nbConvives) throws SQLException { // fonction pour entrer une nouvelle commande = nvelle facture
        // Création d'une nouvelle ligne facture dans la base de donnée
        String insertCmd = "INSERT INTO facture (id, date, serveur_idx, nb_convives, table_idx) VALUES(DEFAULT, NOW(), ?, ?, ?);";
        int key = 0;

        try (PreparedStatement stmt = connection.prepareStatement(insertCmd, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, serveur);
            stmt.setInt(2, nbConvives);
            stmt.setInt(3, table);

            stmt.execute();
            ResultSet rs = stmt.getGeneratedKeys();
            key = rs.next() ? rs.getInt(1) : 0;

            //pour afficher la clé
            //if (key != 0) { //
            // System.out.println("Generated key=" + key);}
        }

        return key; // récuperation de l'id facture pour alimenter le détail de la commande
    }

//public static void totalFacture (Connection connection, int key, double totalTTC) throws SQLException { // fonction pour enregistrer le total TTC de la facture
//    String insertTotal = "SELECT total_TTC FROM facture WHERE (id =?) INSERT INTO facture.total TTC VALUES(?);";
//
//    try (PreparedStatement stmt = connection.prepareStatement(insertTotal)) {
//        stmt.setInt(1, key);
//        stmt.setInt(2, totalTTC);
//
//        stmt.execute();
//
//    }
}



