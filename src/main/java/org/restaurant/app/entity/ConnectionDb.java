package org.restaurant.app.entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDb {
    private static final String url;
    private static final String userName;
    private static final String password;

    static {
        String dbUrl = System.getenv("DB_URL");
        String dbUsername = System.getenv("DB_USERNAME");
        String dbPassword = System.getenv("DB_PASSWORD");
        url = dbUrl;
        userName = dbUsername;
        password = dbPassword;
    }
    public static Connection createConnection() {
        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create a database connection", e);
        }

    }
}
