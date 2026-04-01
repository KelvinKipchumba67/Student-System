package com.system.dao;

import com.system.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public String authenticateUser(String username, String password) {
        String role = null;
        String sql = "SELECT role FROM System_Users WHERE username = ? AND password = ?";

        try {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                role = rs.getString("role");
            }
        } catch (Exception e) {
            System.err.println("Login Error: " + e.getMessage());
        }

        return role;
    }
}