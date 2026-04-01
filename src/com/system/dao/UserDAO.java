package com.system.dao;

import com.system.util.DatabaseConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {

    public Object[] authenticateUser(String username, String password) {
        String sql = "SELECT u.role, u.user_id FROM System_Users u WHERE u.username = ? AND u.password = ?";

        try {
            java.sql.Connection conn = com.system.util.DatabaseConnection.getConnection();
            java.sql.PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setString(2, password);
            java.sql.ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Object[]{ rs.getString("role"), rs.getInt("user_id") };
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }
}