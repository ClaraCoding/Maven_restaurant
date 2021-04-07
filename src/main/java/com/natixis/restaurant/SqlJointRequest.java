package com.natixis.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SqlJointRequest {

   //TO DO!!

    public static void showTurnover (Connection connection, int choice) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet rs = sql.executeQuery("SELECT plat_nom, plat_prix, SUM (quantity) AS total_quantity FROM plat_facture_combo GROUP BY plat_nom , plat_prix ORDER BY  total_quantity DESC ");
        double turnover = 0;
        int i=0;
        while (rs.next()){ // récupération du résultat de la requête ligne à ligne
            String plat = rs.getString("plat_nom");
            int qty= rs.getInt("total_quantity");
            double prix = rs.getDouble ("plat_prix");
            double turnoverByPlat=qty*prix;
            turnover=turnover+turnoverByPlat; // incrémentation du turnover
            i++;
            if (choice >1 && i<=choice) {
                System.out.println("#"+i+":" +plat+"\t\t\t "+ qty+" x "+ prix + "€ = "+ turnoverByPlat+"€");

        }}

        rs.close();
        sql.close();
            System.out.println("Chiffre d'affaires total: " + turnover + "€\n");

    }



//    public static void bestTables (Connection connection){
//
//
//    }
//    //turnover by table = jointure avec facture et tables
//

}
