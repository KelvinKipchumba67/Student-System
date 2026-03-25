package com.system.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    // 1. Database Credentials
    // Candid note: I kept the spelling "StudentSytem_db" exactly as you wrote it in your SQL script!
    private static final String URL = "jdbc:mysql://localhost:3306/StudentSytem_db";

    // Change these if your MySQL setup uses a different username or password
    private static final String USER = "root";
    private static final String PASSWORD = "@kelvin";

    // 2. The method to grab the connection
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Step A: Load the "Translator" (The MySQL Driver)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Step B: Actually connect to the database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Success: Connected to the database");

        } catch (ClassNotFoundException e) {
            System.out.println("Critical Error: MySQL Driver not found. We need to add the .jar file to IntelliJ.");
        } catch (SQLException e) {
            System.out.println("Critical Error: Could not connect. Is your MySQL server (Workbench) currently running?");
            e.printStackTrace();
        }
        return connection;
    }
}