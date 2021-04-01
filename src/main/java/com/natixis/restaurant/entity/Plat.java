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

        String insertCmd = "INSERT INTO plat_facture_combo (plat_idx, facture_idx, quantity) VALUES( ?, ?, ?);";

        try (PreparedStatement stmt = connection.prepareStatement(insertCmd)) {
            stmt.setInt(1, plat);
            stmt.setInt(2, key);
            stmt.setInt(3, quantity);

            stmt.execute();
        }
    }
}


