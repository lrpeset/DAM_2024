package com.blackjack.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * The RegisterView class represents the graphical user interface (GUI) for the
 * registration screen. This view allows users to input their desired username
 * and password to create a new account.
 */
public class RegisterView extends JFrame implements java.io.Serializable {
	private static final long serialVersionUID = 1L; // Unique identifier for Serializable class
	private JTextField usernameField;
	private JPasswordField passwordField;
	private JPasswordField repeatedPasswordField;
	private JButton okButton;
	private JButton cancelButton;

	/**
	 * Constructs the RegisterView object, initializing the GUI components such as
	 * text fields and buttons. It also sets the layout and behavior of the window.
	 */
	public RegisterView() {
		setTitle("Register"); // Set window title
		setSize(300, 250); // Set window size
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close window on dispose
		setLocationRelativeTo(null); // Center window on the screen

		JPanel panel = new JPanel(); // Create panel to hold components
		panel.setLayout(new GridLayout(4, 2)); // Set grid layout for the panel

		panel.add(new JLabel("Username:")); // Label for username field
		usernameField = new JTextField(); // Text field for entering username
		panel.add(usernameField);

		panel.add(new JLabel("Password:")); // Label for password field
		passwordField = new JPasswordField(); // Password field for entering password
		panel.add(passwordField);

		panel.add(new JLabel("Repeat Password:")); // Label for repeated password field
		repeatedPasswordField = new JPasswordField(); // Password field for confirming password
		panel.add(repeatedPasswordField);

		okButton = new JButton("OK"); // OK button to submit registration
		cancelButton = new JButton("Cancel"); // Cancel button to exit registration

		panel.add(okButton); // Add OK button to the panel
		panel.add(cancelButton); // Add Cancel button to the panel

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
	 * Gets the repeated password from the repeated password field.
	 * 
	 * @return the repeated password as a string
	 */
	public String getRepeatedPassword() {
		return new String(repeatedPasswordField.getPassword());
	}

	/**
	 * Adds a listener for the OK button to handle OK button clicks.
	 * 
	 * @param listener the ActionListener to be added to the OK button
	 */
	public void addOkButtonListener(ActionListener listener) {
		okButton.addActionListener(listener);
	}

	/**
	 * Adds a listener for the Cancel button to handle cancel button clicks.
	 * 
	 * @param listener the ActionListener to be added to the Cancel button
	 */
	public void addCancelButtonListener(ActionListener listener) {
		cancelButton.addActionListener(listener);
	}

	/**
	 * Displays a message to the user in a dialog box.
	 * 
	 * @param message the message to be displayed in the dialog
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}
}
