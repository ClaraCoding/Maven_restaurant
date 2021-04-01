package com.natixis.restaurant;

import com.natixis.restaurant.entity.*;

import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //connexion à la base avec l URL
        String url = "jdbc:postgresql://localhost:5432/Resto_Maven";
        String user = "postgres";
        String password = "postgres";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            int key = 0;

            // Recueil des données de la facture
            int serveur = 0;
            while (serveur == 0) {
                System.out.println("Id Serveur? Pour afficher la liste des serveurs, taper 0");
                serveur = sc.nextInt();
                if (serveur == 0) {
                    Serveur.getServeurs(connection);
                }
            }
            int table = 0;
            while (table == 0) {
                System.out.println("Numéro de table? Pour afficher la liste des table, taper 0");
                table = sc.nextInt();
                if (table == 0) {
                    Table.getTable(connection);
                }
            }

            System.out.println("Nombre de convives?");
            int nbConvives = sc.nextInt();

            key = Facture.newCmd(connection, serveur, table, nbConvives);
            int typePlatIdx = 1;
            int endKey = 0;
            String finCommande = "No";
            while (!finCommande.isEmpty()) {
                endKey = (TypePlat.getTypePlat(connection) + 1);
                System.out.println("Choisir le type de commande (" + endKey + " pour sortir)");
                typePlatIdx = sc.nextInt(); // choix entrée, plat etc

                if (typePlatIdx < endKey) {
                    System.out.println("Nombre commande type # " + typePlatIdx + "? (0 pour revenir au choix du type de commande)");
                    int quantité = sc.nextInt();
                    int qty = 1;
                    if (quantité > 0) {
                        Plat.printMenu(connection, typePlatIdx); // affichage du type de plat par id croissant
                        for (int i = 0; i < quantité; ) { // boucle en fonction du nombre de type de plat // attention erreur clé = ne permet pas de resaisir un plat
                            System.out.println("Ref # ?");
                            int course = sc.nextInt();
                            if (quantité > 1) {  // si 1 seule ref commandée, enregistrement ss demander la qté
                                System.out.println("Quantité?");
                                qty = sc.nextInt();
                            }
                            Plat.savePlatFacture(connection, key, course, qty);
                            i = i + qty; // incrémentation du nb de ref commandées
                        }
                    }
                } else {
                    System.out.println("Confirmez-vous la fin de la commande? (+ to continue/enter to confirm)");
                    finCommande = sc.nextLine();
                }
            }


            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }
    // TO DO
    // Top 3 Table par chiffres d'affaires
    // Plat par chiffres d'affaires


    // Edition de la facture (bonus)
}

