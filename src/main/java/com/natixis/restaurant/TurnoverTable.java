package com.natixis.restaurant;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TurnoverTable {


    //    public static void bestTables (Connection connection){
////    //turnover by table = jointure avec facture et tables
//

    public static void showBestTables (Connection connection, int choice) throws SQLException {
        Statement sql = connection.createStatement();
        ResultSet rs = sql.executeQuery("SELECT SUM (quantity) AS total_quantity, SUM (quantity*plat_prix) AS turnover, table_resto.emplacement FROM plat_facture_combo AS pfc JOIN facture ON pfc.facture_idx=facture.id JOIN table_resto ON facture.table_idx=table_resto.id  GROUP BY table_resto.emplacement ORDER BY turnover DESC ");


        double turnover = 0;
        int i=0;
        while (rs.next()){ // récupération du résultat de la requête ligne à ligne
            String place = rs.getString("emplacement");
            double turnoverByTable= rs.getDouble ("turnover");
            turnover=turnover+turnoverByTable; // incrémentation du turnover total
            i++;
            if (choice >1 && i<=choice) {
                System.out.println("#"+i+":" +place+"\t\t\t\t "+ turnoverByTable+"€");

        }}
        rs.close();
        sql.close();
            System.out.println("Chiffre d'affaires total: " + turnover + "€\n");

    }

}
