package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * This class represents the client panel view.
 * It extends JFrame and provides a graphical user interface for client interaction.
 * It includes buttons for performing SQL queries, exporting CSV, logging in, and logging out,
 * and displays messages and relevant information to the user.
 */
public class ClientView extends JFrame {

    private final JButton sqlQueryButton;
    private final JButton exportCsvButton;
    private final JButton logoutButton;
    private final JLabel messageLabel;
    private final JButton loginButton;

    /**
     * Constructor for the ClientView class.
     * Sets up the graphical interface by adding components such as buttons and labels.
     */
    public ClientView() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        sqlQueryButton = new JButton("Perform SQL Query");
        exportCsvButton = new JButton("Export CSV");
        logoutButton = new JButton("Logout");
        loginButton = new JButton("Login");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(sqlQueryButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(loginButton);

        add(buttonPanel, BorderLayout.NORTH);

        messageLabel = new JLabel(" ");
        add(messageLabel, BorderLayout.SOUTH);

        loginButton.setVisible(false);
    }

    /**
     * Gets the button for performing SQL queries.
     *
     * @return The button for SQL queries.
     */
    public JButton getSqlQueryButton() {
        return sqlQueryButton;
    }

    /**
     * Displays a message on the graphical interface.
     *
     * @param message The message to display.
     */
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Gets the button for exporting data to CSV.
     *
     * @return The button for exporting CSV.
     */
    public JButton getExportCsvButton() {
        return exportCsvButton;
    }

    /**
     * Gets the button for logging out.
     *
     * @return The button for logging out.
     */
    public JButton getLogoutButton() {
        return logoutButton;
    }

    /**
     * Gets the button for logging in.
     *
     * @return The button for logging in.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Toggles the visibility of the login and logout buttons,
     * depending on the user's authentication status.
     *
     * @param isLoggedIn The authentication status of the user (true if authenticated, false otherwise).
     */
    public void toggleLoginLogout(boolean isLoggedIn) {
        logoutButton.setVisible(isLoggedIn);
        loginButton.setVisible(!isLoggedIn);
    }

    /**
     * Displays the results of an SQL query in a new window with a table.
     * If no results are found, a message is shown to the user.
     *
     * @param results The results of the SQL query, represented as a list of arrays of strings.
     */
    public void displayQueryResults(List<String[]> results) {
        if (results.isEmpty()) {
            showMessage("No results found.");
            return;
        }

        String[] headers = results.get(0);
        String[][] data = results.subList(1, results.size()).toArray(new String[0][]);

        JTable resultTable = new JTable(data, headers);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        JFrame resultFrame = new JFrame("Query Results");
        resultFrame.setSize(800, 600);
        resultFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");

        buttonPanel.add(backButton);
        buttonPanel.add(exportCsvButton);

        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.add(buttonPanel, BorderLayout.SOUTH);

        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);

        backButton.addActionListener(e -> resultFrame.dispose());
    }

    /**
     * Adds listeners for the confirm and back buttons in the user registration window.
     * The "confirm" listener handles the registration logic with the entered values.
     *
     * @param frame     The JFrame of the user registration window.
     * @param onRegister A function that handles the registration process.
     */
    public void addRegisterUserListeners(JFrame frame, Function<String[], Boolean> onRegister) {
        JPanel buttonPanel = (JPanel) frame.getContentPane().getComponent(1);

        JButton confirmButton = (JButton) buttonPanel.getComponent(0);
        JButton backButton = (JButton) buttonPanel.getComponent(1);

        confirmButton.addActionListener(e -> {
            JPanel inputPanel = (JPanel) frame.getContentPane().getComponent(0);
            String login = ((JTextField) inputPanel.getComponent(1)).getText();
            String password = new String(((JPasswordField) inputPanel.getComponent(3)).getPassword());
            String confirmPassword = new String(((JPasswordField) inputPanel.getComponent(5)).getPassword());

            boolean success = onRegister.apply(new String[]{login, password, confirmPassword});
            if (success) frame.dispose();
        });

        backButton.addActionListener(e -> frame.dispose());
    }

    /**
     * Creates a window for registering a new user, including fields for username,
     * password, and password confirmation, along with the corresponding buttons.
     *
     * @return The JFrame for registering a new user.
     */
    public JFrame createRegisterUserFrame() {
        JFrame frame = new JFrame("Register New User");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        inputPanel.add(new JLabel("Username:"));
        inputPanel.add(loginField);
        inputPanel.add(new JLabel("Password:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Confirm Password:"));
        inputPanel.add(confirmPasswordField);

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirm");
        JButton backButton = new JButton("Back");
        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    /**
     * Displays the content of an XML file in a window with a JTextArea.
     *
     * @param content The content of the XML file.
     */
    public void displayXmlContent(String content) {
        JFrame frame = new JFrame("XML Content");
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
