package com.natixis.restaurant.entity;

import java.sql.*;

public class Facture {
    private final int id;
    private final int serveurIdx;
    private final int nbConvives;
    private final int tableIdx;

    public Facture(int id, int serveurIdx, int nbConvives, int tableIdx) {
        this.id = id;
        this.serveurIdx = serveurIdx;
        this.nbConvives = nbConvives;
        this.tableIdx = tableIdx;
    }

    public static int newCmd(Connection connection, int serveur, int table, int nbConvives) throws SQLException { // fonction pour entrer une nouvelle commande = nvelle facture
        // Données recueillies dans le Main
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

            //if (key != 0) { // pour afficher la clé
              //  System.out.println("Generated key=" + key);
            }

        return key; // récuperation de l'id facture pour alimenter le détail de la commande
    }


}
