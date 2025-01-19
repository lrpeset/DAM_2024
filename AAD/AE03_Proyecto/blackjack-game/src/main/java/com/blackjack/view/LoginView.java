package com.blackjack.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The LoginView class represents the graphical user interface (GUI) for the
 * login screen in the Blackjack game. This view allows users to input their
 * username and password to authenticate.
 */
public class LoginView extends JFrame implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JButton loginButton;
	private JButton cancelButton;

	/**
	 * Constructs the LoginView object, initializing the GUI components such as text
	 * fields and buttons. It also sets the layout and behavior of the window.
	 */
	public LoginView() {
		setTitle("Login"); // Set window title
		setSize(300, 200); // Set window size
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close window on dispose
		setLocationRelativeTo(null); // Center window on the screen

		JPanel panel = new JPanel(); // Create panel to hold components
		panel.setLayout(new GridLayout(3, 2)); // Set grid layout for the panel

		panel.add(new JLabel("Username:")); // Label for username field
		usernameField = new JTextField(); // Text field for entering username
		panel.add(usernameField);

		panel.add(new JLabel("Password:")); // Label for password field
		passwordField = new JPasswordField(); // Password field for entering password
		panel.add(passwordField);

		loginButton = new JButton("Login"); // Login button
		cancelButton = new JButton("Cancel"); // Cancel button

		panel.add(loginButton); // Add login button to the panel
		panel.add(cancelButton); // Add cancel button to the panel

		add(panel); // Add the panel to the frame
	}

	/**
	 * Gets the entered username from the username text field.
	 * 
	 * @return the username as a string
	 */
	public String getUsername() {
		return usernameField.getText();
	}

	/**
	 * Gets the entered password from the password field.
	 * 
	 * @return the password as a string
	 */
	public String getPassword() {
		return new String(passwordField.getPassword());
	}

	/**
	 * Adds a listener for the login button to handle login button clicks.
	 * 
	 * @param listener the ActionListener to be added to the login button
	 */
	public void addLoginButtonListener(ActionListener listener) {
		loginButton.addActionListener(listener);
	}

	/**
	 * Adds a listener for the cancel button to handle cancel button clicks.
	 * 
	 * @param listener the ActionListener to be added to the cancel button
	 */
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}
}
