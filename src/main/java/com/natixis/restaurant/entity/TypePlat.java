package com.natixis.restaurant.entity;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class TypePlat {

    private final int id;
    private final String type;


    public TypePlat(int id, String type) {
        this.id = id;
        this.type = type;
    }

    @Override
    public String toString() {
        return "# " + id + "\t "+ type;
    }

    public static int getTypePlat (Connection connection) throws SQLException {
        // extraction des données de la BDD
        int lastKey =0;
        Statement sqlOrder = connection.createStatement();
        ResultSet rs = sqlOrder.executeQuery("SELECT * FROM type_plat");

        //insertion dans une liste et affichage
        List<TypePlat> getTypePlat = new ArrayList<>();

        while(rs.next()) {
            TypePlat typePlat = new TypePlat(rs.getInt("id"), rs.getString("type_plat"));
            getTypePlat.add(typePlat);
            System.out.println(typePlat);
            lastKey= rs.getInt ("id");
        }
        rs.close();
        sqlOrder.close();

        return lastKey;
    }

}
