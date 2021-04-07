package com.natixis.restaurant.entity;

import java.sql.*;
import java.util.*;

public class Plat {

    private final int id;
    private final String nom;
    private final double prix;
    private final int typePlatIdx;

    public Plat(int id, String nom, double prix, int typePlatIdx) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.typePlatIdx = typePlatIdx;
    }


    @Override
    public String toString() {
        return "Ref # " + id +
                "\t\t\t" + nom + "\t\t\t = " + prix + "€";
    }

    public static void printMenu(Connection connection, int typePlatIdx) throws SQLException {
        // preparedstatement pour sélectionner le type de plat
        String sqlTypePlat = ("SELECT * FROM plat WHERE type_plat_idx=? ORDER BY id ASC");

        try (PreparedStatement stmt = connection.prepareStatement(sqlTypePlat)) {
            stmt.setInt(1, typePlatIdx);
            ResultSet result = stmt.executeQuery();


            List<Plat> listPlats = new ArrayList<>();

            while (result.next()) {
                Plat platDuMenu = new Plat(result.getInt("id"), result.getString("nom"),
                        result.getDouble("prix"), result.getInt("type_plat_idx"));
                listPlats.add(platDuMenu);
                System.out.println(platDuMenu);
            }
        }
    }

    // Enregistrement d'un plat dans une facture = création d'une nouvelle ligne dans la table plat_facture de la base de donnée
    public static void savePlatFacture(Connection connection, int key, int plat, int quantity) throws SQLException {

        // Récupération du nom et du prix du plat => si le menu change (prix/plat), il faut garder cette information "en dur" dans la facture

        // Selection du nom et du prix du plat
        String sqlTypePlat = ("SELECT nom, prix FROM plat WHERE id=?");
        String platCmd = "";
        double platPrix = 0;

        try (PreparedStatement stmt = connection.prepareStatement(sqlTypePlat)) {
            stmt.setInt(1, plat);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                platCmd = result.getString("nom");
                platPrix = result.getDouble("prix");
            }

            System.out.println(platCmd + " " + quantity + " x " + platPrix + "€");
        }

        // #V2 = mise à jour quantité
        //Vérification si le plat existe déjà pour la facture en cours
        String sqlFacturePlat = ("SELECT plat_nom, quantity FROM plat_facture_combo WHERE facture_idx =? AND plat_nom=?");
        String platVerif = "";

        try (PreparedStatement stmt = connection.prepareStatement(sqlFacturePlat)) {
            stmt.setInt(1, key);
            stmt.setString(2, platCmd);
            ResultSet result = stmt.executeQuery();
            while (result.next()) {
                platVerif = result.getString("plat_nom");
                int qty = result.getInt("quantity");
                quantity = quantity + qty;
            }

            if (platVerif.isEmpty()) {

                // Si non = Ajout de la ligne
                String insertCmd = "INSERT INTO plat_facture_combo (facture_idx, quantity, plat_nom, plat_prix) VALUES( ?, ?, ?, ?);";

                try (PreparedStatement stmtn = connection.prepareStatement(insertCmd)) {
                    stmtn.setInt(1, key);
                    stmtn.setInt(2, quantity);
                    stmtn.setString(3, platCmd);
                    stmtn.setDouble(4, platPrix);

                    stmtn.execute();
                }
            } else { // Si oui = mise à jour de la ligne
                String insertCmd = "UPDATE plat_facture_combo SET quantity = ? WHERE facture_idx=? AND plat_nom=?";

                try (PreparedStatement stmtu = connection.prepareStatement(insertCmd)) {
                    stmtu.setInt(1, quantity);
                    stmtu.setInt(2, key);
                    stmtu.setString(3, platCmd);

                    stmtu.execute();
                }
            }
        }
    }
}



