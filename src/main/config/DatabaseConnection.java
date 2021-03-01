package main.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/eventdatabase";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345";

    private Connection connection;

    public static Connection connect() throws SQLException {
        try {
            Class.forName(DATABASE_DRIVER);
            return DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new SQLException(e.getMessage());
        }
    }
}
