package controller;

import model.AdminActions;
import model.DatabaseConnection;
import view.ClientView;

import javax.swing.*;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

/**
 * The controller class for managing actions related to the Client view.
 * It handles user interactions and connects the view to the model.
 * 
 * <p>This class includes logic to handle client-specific actions such as executing 
 * SQL queries, exporting query results to a CSV file, and managing login/logout functionality.</p>
 */
public class ClientController {
    private final ClientView view;
    private final AdminActions adminActions;
    private List<String[]> currentQueryResults;

    /**
     * Constructs a ClientController instance with the specified view, database connection, 
     * and current user login.
     *
     * @param clientView The ClientView instance to be controlled by this controller.
     * @param connection The connection to the database.
     * @param currentUserLogin The login of the currently authenticated user.
     */
    public ClientController(ClientView clientView, Connection connection, String currentUserLogin) {
        this.view = clientView;
        this.adminActions = new AdminActions(connection);

        this.view.getSqlQueryButton().addActionListener(e -> handleSqlQuery());
        this.view.getExportCsvButton().addActionListener(e -> handleExportToCsv());
        this.view.getLogoutButton().addActionListener(e -> handleLogout());
        this.view.getLoginButton().addActionListener(e -> handleLogin());
    }

    /**
     * Starts the view and makes it visible.
     */
    public void start() {
        view.setVisible(true);
    }

    /**
     * Handles the execution of an SQL query entered by the client.
     * Only queries that reference the 'population' table are allowed.
     * Results are displayed if successful, or an error message is shown.
     */
    private void handleSqlQuery() {
        String sqlQuery = JOptionPane.showInputDialog(view, "Enter your SQL query:");

        if (sqlQuery == null || sqlQuery.trim().isEmpty()) {
            view.showMessage("Query cannot be empty.");
            return;
        }

        try {
            if (!sqlQuery.toLowerCase().contains("from population")) {
                view.showMessage("You can only query the 'population' table.");
                return;
            }

            List<String[]> results = adminActions.executeSelectQuery(sqlQuery, "client");
            if (results.isEmpty()) {
                view.showMessage("Query returned no results.");
            } else {
                currentQueryResults = results;
                view.displayQueryResults(results);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error executing query: " + ex.getMessage());
        }
    }

    /**
     * Handles exporting the query results to a CSV file.
     * The results are mapped and written to a CSV file in the 'csv' directory.
     */
    private void handleExportToCsv() {
        if (currentQueryResults == null || currentQueryResults.isEmpty()) {
            view.showMessage("No results to export.");
            return;
        }

        try {
            List<Map<String, Object>> mappedResults = adminActions.convertResultsToMap(currentQueryResults);

            String directoryPath = "csv";
            String csvFileName = "query_results.csv";
            String csvFilePath = directoryPath + File.separator + csvFileName;

            adminActions.exportToCsv(mappedResults, csvFilePath);
            view.showMessage("Data exported to: " + csvFilePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error exporting data: " + ex.getMessage());
        }
    }

    /**
     * Handles the login process. It establishes a connection to the database and
     * updates the view to reflect the login state.
     */
    private void handleLogin() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            adminActions.setConnection(connection);

            view.toggleLoginLogout(true);
            view.showMessage("Connection established successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error connecting to the database: " + ex.getMessage());
        }
    }

    /**
     * Handles the logout process. It closes the connection to the database and
     * updates the view to reflect the logout state.
     */
    private void handleLogout() {
        try {
            adminActions.closeConnection();
            view.toggleLoginLogout(false);
            view.showMessage("Logged out successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error logging out: " + ex.getMessage());
        }
    }
}
