package com.natixis.restaurant;

import com.natixis.restaurant.entity.*;
import java.sql.*;
import java.util.Scanner;
import static com.natixis.restaurant.TurnoverPlat.showTurnover;
import static com.natixis.restaurant.TurnoverTable.showBestTables;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        //connexion à la base avec l URL
        String url = "jdbc:postgresql://localhost:5432/Resto_Maven";
        String user = "postgres";
        String password = "postgres";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);

            System.out.println("Bonjour, souhaitez-vous passer une nouvelle commande (1) ou suivre les résultats du restaurant (2)?");
            int option = sc.nextInt();

            if (option == 2) {
                System.out.println("Pour afficher les plats les plus vendus, indiquer le max de ligne à afficher (0=chiffres d'affaires)");
                int choice = sc.nextInt();

                showTurnover(connection, choice);
                System.out.println("\nMeilleurs emplacements:");
                showBestTables(connection, choice);
            }

            if (option == 1) {
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
                    System.out.println("Numéro de table? Pour afficher la liste des tables, taper 0");
                    table = sc.nextInt();
                    if (table == 0) {
                        Table.getTable(connection);
                    }
                }
                System.out.println("Nombre de convives?"); // nb réel vs nb max (permet de faire des stats de remplissage)
                int nbConvives = sc.nextInt();

                key = Facture.newCmd(connection, serveur, table, nbConvives);
                int typePlatIdx = 1;
                int endKey = 0;
                int finCommande = 0;

                while (finCommande == 0) {
                    endKey = (TypePlat.getTypePlat(connection) + 1);
                    System.out.println("Choisir le type de commande (" + endKey + " pour finir la commande)");
                    typePlatIdx = sc.nextInt(); // choix entrée, plat etc

                    if (typePlatIdx < endKey) {
                        System.out.println("Nous vous proposons les choix suivants:");
                        Plat.printMenu(connection, typePlatIdx); // affichage des choix par type de plat par id croissant
                        System.out.println("Nombre commande type # " + typePlatIdx + "? (0 pour revenir au choix du type de commande)");

                        int quantité = sc.nextInt();
                        int qty = 1;
                        if (quantité > 0) {// si quantité =0, retour au début de la boucle

                            for (int i = 0; i < quantité; ) { // boucle en fonction du nombre de type de plat // méthode plus propre = augmenter la quantité au lieu de rajouter une ligne
                                System.out.println("Ref choisie ? (0 pour sortir)");
                                int course = sc.nextInt();
                                if (course == 0) {
                                    break; // pour forcer la sortie de la boucle
                                }
                                if (quantité > 1) {  // si 1 seule ref commandée, enregistrement ss demander la qté
                                    System.out.println("Quantité?");
                                    qty = sc.nextInt();
                                }
                                if (qty > 0 || quantité == 1) {
                                    Plat.savePlatFacture(connection, key, course, qty);
                                    i = i + qty; // incrémentation du nb de ref commandées
                                }
                            }
                        }

                    } else {
                        System.out.println("Confirmez-vous la fin de la commande? (0 to continue/ 1 to confirm)");
                        finCommande = sc.nextInt();
                        sc.nextLine();
                    }
                }
            }

            connection.close();

        } catch (SQLException exception) {
            exception.printStackTrace();
        }

        sc.close();
    }
    // TO DO
    // Calcul du chiffre d'affaires
    // Top 3 Table par chiffres d'affaires
    // Plat par chiffres d'affaires

    // Edition de la facture (bonus)
}

