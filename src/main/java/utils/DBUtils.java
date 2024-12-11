package utils;

import config.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtils {

    private static final String DB_URL = Configuration.getProperty("db.url");
    private static final String USER = Configuration.getProperty("db.username");
    private static final String PASSWORD = Configuration.getProperty("db.password");

    // Method to establish a connection to the database
    public static Connection getConnection() {
        Connection connection = null;
        try {
            // Load PostgreSQL JDBC driver
            Class.forName("org.postgresql.Driver");

            // Establish the connection
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            System.out.println("Connection to PostgreSQL established successfully!");
        } catch (Exception e) {
            System.err.println("Database connection failed: " + e.getMessage());
            e.printStackTrace();
        }
        return connection;
    }

    // Method to execute a SELECT query
    public static ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            // Get database connection
            Connection connection = getConnection();

            if (connection != null) {
                // Create a Statement
                Statement statement = connection.createStatement();

                // Execute the query
                resultSet = statement.executeQuery(query);
            }
        } catch (Exception e) {
            System.err.println("Query execution failed: " + e.getMessage());
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to close the database connection and resources
    public static void closeResources(Connection connection, Statement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        } catch (Exception e) {
            System.err.println("Error closing resources: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
