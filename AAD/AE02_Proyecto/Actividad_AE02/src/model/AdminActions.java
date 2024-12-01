package model;

import utils.Authentication;
import utils.CsvProcessor;
import utils.XmlProcessor;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The AdminActions class contains various administrative operations that can be performed by an admin user.
 * These operations include user registration, querying the database, exporting results to CSV, processing CSV files,
 * generating XML content, and managing database connections.
 *
 * <p>This class serves as the core administrative functionalities for managing users, processing data,
 * and handling database interactions.</p>
 */
public class AdminActions {
    private Connection connection;
    private CsvProcessor csvProcessor;
    private XmlProcessor xmlProcessor;

    /**
     * Constructs an AdminActions instance with the provided database connection.
     * 
     * @param connection The connection to the database.
     */
    public AdminActions(Connection connection) {
        this.connection = connection;
        this.csvProcessor = new CsvProcessor(connection);
        this.xmlProcessor = new XmlProcessor();
    }

    /**
     * Sets a new connection to the database.
     * 
     * @param connection The new database connection.
     */
    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Closes the current database connection.
     * 
     * @throws SQLException If an error occurs while closing the connection.
     */
    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    /**
     * Checks if the current database connection is active and open.
     * 
     * @return true if the connection is active; false otherwise.
     */
    public boolean isConnectionActive() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Registers a new user in the database by an admin user.
     * 
     * @param adminUser The admin user performing the registration.
     * @param newUser The new user to be registered.
     * @return true if the user was successfully registered; false otherwise.
     */
    public boolean registerUser(User adminUser, User newUser) {
        if (!adminUser.getUserType().equals("admin")) {
            return false;
        }

        String query = "INSERT INTO users (login, password, type) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, newUser.getUsername());
            stmt.setString(2, Authentication.hashPassword(newUser.getHashedPassword()));
            stmt.setString(3, newUser.getUserType());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Processes the provided CSV file, clears the population table, inserts data into the database,
     * and generates XML files for each row of data.
     * 
     * @param csvFilePath The path of the CSV file to be processed.
     * @return The content of the generated XML as a string.
     * @throws Exception If an error occurs during the CSV processing or XML generation.
     */
    public String processCsvAndGenerateXml(String csvFilePath) throws Exception {
        clearPopulationTable();
        List<String[]> rows = csvProcessor.readCsv(csvFilePath);

        StringBuilder xmlContent = new StringBuilder();
        for (String[] row : rows) {
            csvProcessor.insertRowToDatabase(row);
            String xml = xmlProcessor.generateXmlContent(row);
            String country = row[0];
            xmlProcessor.generateXmlFile(country, xml);
            xmlContent.append(String.join(", ", row)).append("\n");
        }
        return xmlContent.toString();
    }

    /**
     * Clears all the data from the population table in the database.
     * 
     * @throws SQLException If an error occurs while clearing the table.
     */
    private void clearPopulationTable() throws SQLException {
        String deleteSQL = "DELETE FROM population";
        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(deleteSQL);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error clearing the population table.", e);
        }
    }

    /**
     * Executes a SELECT SQL query on the database.
     * 
     * @param query The SQL query to be executed.
     * @param userType The type of user performing the query (either "admin" or "client").
     * @return A list of results, where each result is represented as an array of strings.
     * @throws Exception If an error occurs during query execution or if the query is invalid for the user type.
     */
    public List<String[]> executeSelectQuery(String query, String userType) throws Exception {
        if ("client".equals(userType) && !query.toLowerCase().contains("from population")) {
            throw new IllegalAccessException("Clients are only allowed to query the 'population' table.");
        }

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            int columnCount = rs.getMetaData().getColumnCount();
            List<String[]> results = new ArrayList<>();

            String[] headers = new String[columnCount];
            for (int i = 1; i <= columnCount; i++) {
                headers[i - 1] = rs.getMetaData().getColumnName(i);
            }
            results.add(headers);

            while (rs.next()) {
                String[] row = new String[columnCount];
                for (int i = 1; i <= columnCount; i++) {
                    row[i - 1] = rs.getString(i);
                }
                results.add(row);
            }

            return results;
        }
    }

    /**
     * Exports query results to a CSV file.
     * 
     * @param queryResults The query results to be exported, represented as a list of maps.
     * @param filePath The path where the CSV file should be saved.
     * @throws RuntimeException If an error occurs during the CSV export process.
     */
    public void exportToCsv(List<Map<String, Object>> queryResults, String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            if (queryResults.isEmpty()) {
                throw new RuntimeException("No data to export.");
            }

            Set<String> headers = queryResults.get(0).keySet();
            writer.write(String.join(";", headers));
            writer.newLine();

            for (Map<String, Object> row : queryResults) {
                List<String> values = new ArrayList<>();
                for (String header : headers) {
                    values.add(row.get(header) != null ? row.get(header).toString() : "");
                }
                writer.write(String.join(";", values));
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error exporting data to CSV.", e);
        }
    }

    /**
     * Converts a list of query results into a list of maps, where each map represents a row of data with column names as keys.
     * 
     * @param results The list of query results to be converted.
     * @return A list of maps, each representing a row of data.
     */
    public List<Map<String, Object>> convertResultsToMap(List<String[]> results) {
        List<Map<String, Object>> mappedResults = new ArrayList<>();

        if (results.isEmpty()) {
            return mappedResults;
        }

        String[] headers = results.get(0);

        for (int i = 1; i < results.size(); i++) {
            String[] row = results.get(i);
            Map<String, Object> rowMap = new HashMap<>();
            for (int j = 0; j < headers.length; j++) {
                rowMap.put(headers[j], row[j]);
            }
            mappedResults.add(rowMap);
        }
        return mappedResults;
    }
}
