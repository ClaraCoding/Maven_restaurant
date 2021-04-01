package com.natixis.restaurant.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Table {

    private final int id;
    private final String emplacement;
    private final int maxConvives;

    public Table(int id, String emplacement, int maxConvives) {
        this.id = id;
        this.emplacement = emplacement;
        this.maxConvives = maxConvives;
    }

    @Override
    public String toString() {
        return "Table# " + id + ": " + emplacement + ", " + maxConvives + "pers";
    }

    // Affichage des données de la BDD:
    public static List<Table> getTable(Connection connection) throws SQLException {

        Statement sqlOrder = connection.createStatement();
        ResultSet rs = sqlOrder.executeQuery("SELECT * FROM table_resto");
        //connection.close();

        List<Table> getTable = new ArrayList<>();

        while (rs.next()) {
            Table table = new Table(rs.getInt("id"), rs.getString("emplacement"), rs.getInt("max_convives"));
            getTable.add(table);
            System.out.println(table);
        }
        rs.close();
        sqlOrder.close();
        return getTable;
    }

}
