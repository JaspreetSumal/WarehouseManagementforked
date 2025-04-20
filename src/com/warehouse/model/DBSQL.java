package com.warehouse.model;

import java.sql.*;

public class DBSQL {
    private Connection connection;

    // Constructor establishes the connection to the database.
    public DBSQL() {
        try {
            // Using SQLite for simplicity. Replace the connection string if using another DB.
            String url = "jdbc:sqlite:warehouse.db";
            connection = DriverManager.getConnection(url);
            // Create the Users table if it does not exist.
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Create the Users table.
    public void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                     "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                     "username TEXT UNIQUE NOT NULL, " +
                     "password TEXT NOT NULL);";
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Insert a sample user for testing purposes.
    public void insertUser(String username, String password) {
        String sql = "INSERT OR IGNORE INTO Users(username, password) VALUES(?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate user-provided credentials against those stored in the database.
    public boolean validateUser(String username, String password) {
        String sql = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true; // Valid login
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}