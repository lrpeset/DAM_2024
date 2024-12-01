package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import view.LoginView;
import view.AdminView;
import view.ClientView;
import utils.Authentication;

/**
 * The controller class for managing the login process.
 * It handles the user's login attempt, validates their credentials, 
 * and navigates to the appropriate view based on their user type.
 * 
 * <p>This class communicates with the database to validate the user's login and starts the corresponding
 * controller (either AdminController or ClientController) upon successful authentication.</p>
 */
public class LoginController {
    private Connection connection;
    private LoginView view;

    /**
     * Constructs a LoginController instance with the specified database connection and view.
     *
     * @param connection The connection to the database.
     * @param view The LoginView instance controlled by this controller.
     */
    public LoginController(Connection connection, LoginView view) {
        this.connection = connection;
        this.view = view;

        this.view.getLoginButton().addActionListener(e -> handleLogin());
    }

    /**
     * Handles the login process.
     * It retrieves the username and password from the view, validates the credentials,
     * and navigates to the appropriate view based on the user type (admin or client).
     * Displays appropriate messages for login success or failure.
     */
    private void handleLogin() {
        String username = view.getUsername();
        String password = view.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            view.setMessage("Please complete all fields.");
            return;
        }

        // Hash the password for comparison with the stored value
        String hashedPassword = Authentication.hashPassword(password);

        // Validate the user credentials
        String userType = validateUser(username, hashedPassword);
        if (userType != null) {
            view.setMessage("Login successful.");

            // Launch the appropriate controller based on the user type
            if (userType.equals("admin")) {
                AdminController adminController = new AdminController(new AdminView(), connection, userType);
                adminController.start();
            } else if (userType.equals("client")) {
                ClientController userController = new ClientController(new ClientView(), connection, userType);
                userController.start();
            }

            // Close the login view after successful login
            view.dispose();
        } else {
            view.setMessage("Incorrect username or password.");
        }
    }

    /**
     * Validates the user credentials by checking the username and hashed password against the database.
     * 
     * @param username The username to be validated.
     * @param hashedPassword The hashed password to be validated.
     * @return The user type (either "admin" or "client") if the credentials are valid, or null if invalid.
     */
    private String validateUser(String username, String hashedPassword) {
        String query = "SELECT type FROM users WHERE login = ? AND password = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("type");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
