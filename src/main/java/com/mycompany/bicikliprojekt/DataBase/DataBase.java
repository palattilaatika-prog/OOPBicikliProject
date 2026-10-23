/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bicikliprojekt.DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Denes
 */
public class DataBase {
    private static final String DB_URL = "jdbc:sqlite:bicikli_app.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void createTables() {
        String enableForeignKeys = "PRAGMA foreign_keys = ON;";

        String createUsersTable = "CREATE TABLE IF NOT EXISTS users ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT NOT NULL, "
                + "email TEXT NOT NULL UNIQUE, "
                + "password TEXT NOT NULL"
                + ");";

        String createBikesTable = "CREATE TABLE IF NOT EXISTS bikes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "userId INTEGER NOT NULL, "
                + "brand TEXT NOT NULL, "
                + "model TEXT NOT NULL, "
                + "type TEXT NOT NULL, "
                + "serialNumber TEXT NOT NULL UNIQUE, "
                + "FOREIGN KEY (userId) REFERENCES users(id) ON DELETE CASCADE"
                + ");";

        String createForSaleBikesTable = "CREATE TABLE IF NOT EXISTS for_sale_bikes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "bikeId INTEGER NOT NULL, "
                + "condition TEXT NOT NULL, "
                + "price REAL NOT NULL, "
                + "imagePath TEXT, "
                + "description TEXT, "
                + "listedDate TEXT NOT NULL, "
                + "FOREIGN KEY (bikeId) REFERENCES bikes(id) ON DELETE CASCADE"
                + ");";

        String createStolenBikesTable = "CREATE TABLE IF NOT EXISTS stolen_bikes ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "serialNumber TEXT NOT NULL, "
                + "brand TEXT NOT NULL, "
                + "model TEXT NOT NULL, "
                + "reportedDate TEXT NOT NULL, "
                + "source TEXT NOT NULL"
                + ");";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.execute(enableForeignKeys);
            stmt.execute(createUsersTable);
            stmt.execute(createBikesTable);
            stmt.execute(createForSaleBikesTable);
            stmt.execute(createStolenBikesTable);

            System.out.println("Database tables initialized successfully.");

        } catch (SQLException e) {
            System.err.println("Database initialization error: " + e.getMessage());
        }
    }
}
