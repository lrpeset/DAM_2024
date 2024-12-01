package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.function.Function;

/**
 * AdminView is a GUI class for the administrator panel of the application.
 * It provides functionality for various administrative tasks, including importing CSV files,
 * registering users, executing SQL queries, exporting CSV files, and handling login/logout.
 */
public class AdminView extends JFrame {
    private final JButton importCsvButton;
    private final JButton registerUserButton;
    private final JButton sqlQueryButton;
    private final JButton exportCsvButton;
    private final JButton logoutButton;
    private final JLabel messageLabel;
    private final JButton loginButton;

    /**
     * Constructs the AdminView interface.
     * Initializes the GUI components such as buttons, message label, and layout.
     */
    public AdminView() {
        setTitle("Panel de Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        importCsvButton = new JButton("Importar CSV");
        registerUserButton = new JButton("Registrar Usuario");
        sqlQueryButton = new JButton("Hacer Consulta SQL");
        exportCsvButton = new JButton("Exportar CSV");
        logoutButton = new JButton("Logout");
        loginButton = new JButton("Login");

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(importCsvButton);
        buttonPanel.add(registerUserButton);
        buttonPanel.add(sqlQueryButton);
        buttonPanel.add(logoutButton);
        buttonPanel.add(loginButton);

        add(buttonPanel, BorderLayout.NORTH);

        messageLabel = new JLabel(" ");
        add(messageLabel, BorderLayout.SOUTH);

        loginButton.setVisible(false);
    }

    /**
     * Returns the button to import a CSV file.
     * 
     * @return The import CSV button.
     */
    public JButton getImportCsvButton() {
        return importCsvButton;
    }

    /**
     * Returns the button to register a new user.
     * 
     * @return The register user button.
     */
    public JButton getRegisterButton() {
        return registerUserButton;
    }

    /**
     * Returns the button to execute an SQL query.
     * 
     * @return The SQL query button.
     */
    public JButton getSqlQueryButton() {
        return sqlQueryButton;
    }

    /**
     * Displays a message in the message label at the bottom of the window.
     * 
     * @param message The message to be displayed.
     */
    public void showMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Returns the button to export a CSV file.
     * 
     * @return The export CSV button.
     */
    public JButton getExportCsvButton() {
        return exportCsvButton;
    }

    /**
     * Returns the button to log out of the admin panel.
     * 
     * @return The logout button.
     */
    public JButton getLogoutButton() {
        return logoutButton;
    }

    /**
     * Returns the button to log in to the admin panel.
     * 
     * @return The login button.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Toggles the visibility of the login and logout buttons based on the user's login status.
     * 
     * @param isLoggedIn A boolean indicating whether the user is logged in.
     */
    public void toggleLoginLogout(boolean isLoggedIn) {
        logoutButton.setVisible(isLoggedIn);
        loginButton.setVisible(!isLoggedIn);
    }

    /**
     * Displays the results of an SQL query in a new window with a table.
     * 
     * @param results A list of string arrays representing the rows of data to be displayed.
     */
    public void displayQueryResults(List<String[]> results) {
        if (results.isEmpty()) {
            showMessage("No se encontraron resultados.");
            return;
        }

        String[] headers = results.get(0);
        String[][] data = results.subList(1, results.size()).toArray(new String[0][]);

        JTable resultTable = new JTable(data, headers);
        JScrollPane scrollPane = new JScrollPane(resultTable);

        JFrame resultFrame = new JFrame("Resultados de la Consulta");
        resultFrame.setSize(800, 600);
        resultFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Atrás");

        buttonPanel.add(backButton);
        buttonPanel.add(exportCsvButton);

        resultFrame.add(scrollPane, BorderLayout.CENTER);
        resultFrame.add(buttonPanel, BorderLayout.SOUTH);

        resultFrame.setLocationRelativeTo(null);
        resultFrame.setVisible(true);

        backButton.addActionListener(e -> resultFrame.dispose());
    }

    /**
     * Adds listeners for the actions on the register user dialog.
     * 
     * @param frame The frame containing the registration form.
     * @param onRegister A function that takes the user data and returns a boolean indicating success.
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
     * Creates the register user frame, where admin can input user data.
     * 
     * @return The JFrame for registering a user.
     */
    public JFrame createRegisterUserFrame() {
        JFrame frame = new JFrame("Registrar Nuevo Usuario");
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        JTextField loginField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPasswordField confirmPasswordField = new JPasswordField();

        inputPanel.add(new JLabel("Usuario:"));
        inputPanel.add(loginField);
        inputPanel.add(new JLabel("Contraseña:"));
        inputPanel.add(passwordField);
        inputPanel.add(new JLabel("Confirmar Contraseña:"));
        inputPanel.add(confirmPasswordField);

        frame.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton confirmButton = new JButton("Confirmar");
        JButton backButton = new JButton("Atrás");
        buttonPanel.add(confirmButton);
        buttonPanel.add(backButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    /**
     * Displays the content of an XML file in a new window with a non-editable text area.
     * 
     * @param content The XML content to be displayed.
     */
    public void displayXmlContent(String content) {
        JFrame frame = new JFrame("Contenido XML");
        JTextArea textArea = new JTextArea(content);
        textArea.setEditable(false);

        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setSize(600, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
