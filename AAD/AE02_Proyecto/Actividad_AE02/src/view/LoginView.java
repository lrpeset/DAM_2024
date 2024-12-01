package view;

import javax.swing.*;
import java.awt.*;

/**
 * Class representing the login view.
 * This class extends JFrame and provides a graphical user interface for user authentication.
 * It includes fields for the username and password, a button to log in, 
 * and a label to display messages.
 */
public class LoginView extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel messageLabel;

    /**
     * Constructor of the LoginView class.
     * Sets up the graphical interface by adding components such as text fields, buttons, and labels.
     */
    public LoginView() {
        setTitle("Login");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 1));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Login");
        messageLabel = new JLabel("", SwingConstants.CENTER);

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);

        add(panel, BorderLayout.CENTER);
        add(loginButton, BorderLayout.SOUTH);
        add(messageLabel, BorderLayout.NORTH);
    }

    /**
     * Gets the username entered in the text field.
     *
     * @return The username as a string.
     */
    public String getUsername() {
        return usernameField.getText().trim();
    }

    /**
     * Gets the password entered in the text field.
     *
     * @return The password as a string.
     */
    public String getPassword() {
        return new String(passwordField.getPassword()).trim();
    }

    /**
     * Gets the login button.
     *
     * @return The login button.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Sets a message in the label to be displayed to the user.
     *
     * @param message The message to display.
     */
    public void setMessage(String message) {
        messageLabel.setText(message);
    }
}
