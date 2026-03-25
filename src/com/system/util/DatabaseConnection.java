package com.system.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // 1. Load the properties file
                Properties props = new Properties();
                FileInputStream in = new FileInputStream("database.properties");
                props.load(in);
                in.close();

                // 2. Fetch the credentials securely
                String url = props.getProperty("db.url");
                String user = props.getProperty("db.username");
                String pass = props.getProperty("db.password");

                // 3. Make the connection
                connection = DriverManager.getConnection(url, user, pass);
                System.out.println("Database Connected Successfully");

            } catch (IOException e) {
                System.out.println("CRITICAL ERROR: Could not find database.properties file.");
                e.printStackTrace();
            } catch (SQLException e) {
                System.out.println("CRITICAL ERROR: Database connection failed. Check your credentials.");
                e.printStackTrace();
            }
        }
        return connection;
    }
}