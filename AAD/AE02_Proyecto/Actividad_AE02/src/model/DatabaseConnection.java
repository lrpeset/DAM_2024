package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnection class provides a method to establish a connection to a MySQL database.
 * It uses the JDBC DriverManager to connect to a MySQL database with the specified URL, username, and password.
 * 
 * <p>This class encapsulates the connection logic and provides a centralized method to obtain a database connection.</p>
 */
public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/population";
    private static final String USER = "admin";
    private static final String PASSWORD = "21232f297a57a5a743894a0e4a801fc3";

    /**
     * Establishes and returns a connection to the MySQL database.
     * 
     * @return A Connection object representing the established database connection.
     * @throws SQLException If an error occurs while loading the JDBC driver or connecting to the database.
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish and return the connection to the database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            // Handle error when the JDBC driver is not found
            throw new SQLException("Error loading JDBC driver", e);
        } catch (SQLException e) {
            // Handle error when a connection cannot be established
            throw new SQLException("Error connecting to the database", e);
        }
    }
}
