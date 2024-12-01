package app;

import java.sql.Connection;
import java.sql.SQLException;
import controller.LoginController;
import view.LoginView;
import model.DatabaseConnection;

/**
 * The main class of the application. It serves as the entry point that establishes 
 * the connection to the database and initializes the login view and controller.
 * 
 * <p>This class handles the database connection and displays a success or error 
 * message depending on the connection status. If the connection is successful, 
 * it initializes the {@link LoginView} login view and its corresponding 
 * {@link LoginController} controller.</p>
 */
public class Main {

    /**
     * The main method executed when the application starts. It establishes the
     * connection to the database and displays the login view if the connection
     * is successful.
     *
     * @param args Command-line arguments (not used in this class).
     */
    public static void main(String[] args) {
        try {
            // Establish the database connection
            Connection connection = DatabaseConnection.getConnection();
            System.out.println("Successfully connected to the database.");

            // Initialize the login view
            LoginView loginView = new LoginView();
            
            // Display the login view
            loginView.setVisible(true);

        } catch (SQLException e) {
            // Handle database connection errors
            System.err.println("Error connecting to the database:");
            e.printStackTrace();
        }
    }
}
