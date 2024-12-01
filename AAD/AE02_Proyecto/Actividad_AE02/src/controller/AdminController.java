package controller;

import model.AdminActions;
import model.DatabaseConnection;
import model.User;
import view.AdminView;

import javax.swing.*;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller class for managing the actions and logic related to the Admin view.
 * It handles user interactions and connects the view to the model.
 * 
 * <p>This class contains logic to handle various actions in the admin panel, such as 
 * registering users, importing/exporting CSV files, executing SQL queries, and managing 
 * user login/logout operations.</p>
 */
public class AdminController {
    private final AdminView view;
    private final AdminActions adminActions;
    private final String currentUserLogin;
    private List<String[]> currentQueryResults;

    /**
     * Constructs an AdminController instance with the specified view, database connection,
     * and current user login.
     *
     * @param view The AdminView instance to be controlled by this controller.
     * @param connection The connection to the database.
     * @param currentUserLogin The login of the currently authenticated user.
     */
    public AdminController(AdminView view, Connection connection, String currentUserLogin) {
        this.view = view;
        this.adminActions = new AdminActions(connection);
        this.currentUserLogin = currentUserLogin;

        this.view.getRegisterButton().addActionListener(e -> openRegisterUserFrame());
        this.view.getImportCsvButton().addActionListener(e -> handleImportCsv());
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
     * Opens the register user frame where an admin can register a new user.
     * Only admins are allowed to register new users.
     */
    private void openRegisterUserFrame() {
        if (!"admin".equals(currentUserLogin)) {
            view.showMessage("You don't have permission to register users.");
            return;
        }

        JFrame registerFrame = view.createRegisterUserFrame();
        view.addRegisterUserListeners(registerFrame, userData -> {
            boolean isValid = validateUserData(userData);
            if (!isValid) return false;

            User adminUser = new User(currentUserLogin, "", "admin");
            User newUser = new User(userData[0], userData[1], "client");

            boolean success = adminActions.registerUser(adminUser, newUser);
            view.showMessage(success ? "User registered successfully." : "Error registering user.");
            return success;
        });

        registerFrame.setVisible(true);
    }

    /**
     * Validates the user data entered during the registration process.
     *
     * @param userData The user data to be validated.
     * @return true if the data is valid, false otherwise.
     */
    private boolean validateUserData(String[] userData) {
        String login = userData[0];
        String password = userData[1];
        String confirmPassword = userData[2];

        if (login.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            view.showMessage("Please complete all fields.");
            return false;
        }

        if (!password.equals(confirmPassword)) {
            view.showMessage("Passwords do not match.");
            return false;
        }

        return true;
    }

    /**
     * Handles importing a CSV file. Only admin users are allowed to perform this action.
     */
    private void handleImportCsv() {
        if (!"admin".equals(currentUserLogin)) {
            view.showMessage("Only admins can import CSV files.");
            return;
        }

        try {
            String xmlContent = adminActions.processCsvAndGenerateXml("csv/AE02_population.csv");
            view.displayXmlContent(xmlContent);
            view.showMessage("CSV file imported and XMLs generated successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error importing CSV: " + ex.getMessage());
        }
    }

    /**
     * Handles exporting query results to a CSV file.
     * The results of the SQL query are converted to a map and written to a CSV file.
     */
    private void handleExportToCsv() {
        if (currentQueryResults == null || currentQueryResults.isEmpty()) {
            view.showMessage("No results to export.");
            return;
        }

        try {
            List<Map<String, Object>> mappedResults = convertResultsToMap(currentQueryResults);

            String directoryPath = "csv";
            String csvFileName = "query_results.csv";
            String csvFilePath = directoryPath + File.separator + csvFileName;

            File directory = new File(directoryPath);
            if (!directory.exists()) {
                directory.mkdirs();
                System.out.println("Directory created: " + directoryPath);
            }

            adminActions.exportToCsv(mappedResults, csvFilePath);
            view.showMessage("Data exported to: " + csvFilePath);
        } catch (Exception ex) {
            ex.printStackTrace();
            view.showMessage("Error exporting data: " + ex.getMessage());
        }
    }

    /**
     * Converts a list of query results (rows) into a list of maps, where each map represents a row
     * and maps column names (headers) to their respective values.
     *
     * @param results The query results to be converted.
     * @return A list of maps representing the query results.
     */
    private List<Map<String, Object>> convertResultsToMap(List<String[]> results) {
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

    /**
     * Handles the execution of an SQL query entered by the user.
     * Results are displayed if successful, or an error message is shown.
     */
    private void handleSqlQuery() {
        String sqlQuery = JOptionPane.showInputDialog(view, "Enter your SQL query:");

        if (sqlQuery == null || sqlQuery.trim().isEmpty()) {
            view.showMessage("Query cannot be empty.");
            return;
        }

        String userType = currentUserLogin.equals("admin") ? "admin" : "client";

        try {
            List<String[]> results = adminActions.executeSelectQuery(sqlQuery, userType);
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
     * Handles the login process. It establishes a connection to the database and
     * updates the view to reflect the login state.
     */
    private void handleLogin() {
        try {
            Connection connection = DatabaseConnection.getConnection();
            adminActions.setConnection(connection);

            view.toggleLoginLogout(true);
            view.showMessage("Connection established successfully.");
        } catch (SQLException ex) {
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

    /**
     * Checks whether the database connection is active.
     *
     * @return true if the connection is active, false otherwise.
     */
    private boolean isConnectionActive() {
        return adminActions.isConnectionActive();
    }
}
