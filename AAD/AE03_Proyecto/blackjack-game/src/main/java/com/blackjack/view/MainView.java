package com.blackjack.view;

import javax.swing.*;
import java.awt.*;
import java.util.Base64;

import com.blackjack.controller.GameController;
import com.blackjack.model.MongoDBManager;

public class MainView extends JFrame {
	private static final long serialVersionUID = 1L;

	// UI Components
	private JButton loadCardsButton, registerButton, loginButton, startButton, saveButton, hallOfFameButton,
			logoutButton;
	public JButton standButton, newCardButton;
	public JComboBox<String> cardLanSelector;
	public JLabel cardSlot1;
	public JLabel cardSlot2;
	public JLabel scoreSlot1;
	public JLabel scoreSlot2;

	private GameController gameController;
	private MongoDBManager mongoDBManager;

	/**
	 * Constructor for initializing the main view of the game.
	 * 
	 * @param controller     The game controller.
	 * @param mongoDBManager The MongoDB manager for handling the database.
	 */
	public MainView(GameController controller, MongoDBManager mongoDBManager) {
		this.gameController = controller;
		this.mongoDBManager = mongoDBManager;
		initializeView();
	}

	/**
	 * Initializes the layout and sets up the user interface components.
	 */
	private void initializeView() {
		setTitle("Blackjack");
		setSize(900, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());

		// Create and add panels for the top, center, and bottom sections of the UI
		JPanel topPanel = createTopPanel();
		JPanel centerPanel = createCenterPanel();
		JPanel bottomPanel = createBottomPanel();

		add(topPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);

		// Action listeners for buttons
		registerButton.addActionListener(e -> gameController.showRegisterView());
		loginButton.addActionListener(e -> gameController.loginAction(this));
		logoutButton.addActionListener(e -> gameController.logoutAction(this));
		loadCardsButton.addActionListener(e -> gameController.handleLoadCards(getSelectedCardLan()));
		startButton.addActionListener(e -> gameController.onStartButtonPressed());
		saveButton.addActionListener(e -> {
			String username = mongoDBManager.getActiveUsername();
			if (username == null || username.isEmpty()) {
				showMessage("No user is logged in.");
				return;
			}
			mongoDBManager.saveUserScore(username, getSelectedCardLan(), gameController.getUserPoints());
		});
		hallOfFameButton.addActionListener(e -> gameController.showHallOfFame());
	}

	/**
	 * Creates and returns the top panel of the UI.
	 * 
	 * @return The top panel containing buttons and combo box.
	 */
	private JPanel createTopPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		loadCardsButton = new JButton("Load Cards");
		registerButton = new JButton("Register");
		loginButton = new JButton("Login");
		cardLanSelector = new JComboBox<>(new String[] { "ES", "FR" });
		startButton = new JButton("Start");
		saveButton = new JButton("Save");
		hallOfFameButton = new JButton("Hall of Fame");
		logoutButton = new JButton("Logout");

		newCardButton = new JButton("New Card");
		standButton = new JButton("Stand");

		setButtonsEnabled(false);

		panel.add(loadCardsButton);
		panel.add(registerButton);
		panel.add(loginButton);
		panel.add(cardLanSelector);
		panel.add(startButton);
		panel.add(saveButton);
		panel.add(hallOfFameButton);
		panel.add(logoutButton);
		return panel;
	}

	/**
	 * Creates and returns the center panel containing the card slots for the
	 * crupier and player.
	 * 
	 * @return The center panel with the card slots.
	 */
	private JPanel createCenterPanel() {
		JPanel panel = new JPanel(new GridLayout(1, 2));
		panel.add(createCardPanel("CRUPIER"));
		panel.add(createCardPanel("PLAYER"));
		return panel;
	}

	/**
	 * Creates a panel for displaying a card slot with a label and score.
	 * 
	 * @param labelText The label for the card slot (e.g., "CRUPIER" or "PLAYER").
	 * @return The card panel with a card slot and score display.
	 */
	private JPanel createCardPanel(String labelText) {
		JPanel panel = new JPanel(new BorderLayout());
		JLabel label = new JLabel(labelText, SwingConstants.CENTER);
		panel.add(label, BorderLayout.NORTH);

		JLabel cardSlot = new JLabel();
		cardSlot.setHorizontalAlignment(SwingConstants.CENTER);
		cardSlot.setVerticalAlignment(SwingConstants.CENTER);
		cardSlot.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		panel.add(cardSlot, BorderLayout.CENTER);

		JLabel scoreSlot = new JLabel("Score: 0", SwingConstants.CENTER);
		panel.add(scoreSlot, BorderLayout.SOUTH);

		if (labelText.equals("CRUPIER")) {
			cardSlot1 = cardSlot;
			scoreSlot1 = scoreSlot;
		} else {
			cardSlot2 = cardSlot;
			scoreSlot2 = scoreSlot;
		}

		return panel;
	}

	/**
	 * Creates and returns the bottom panel containing action buttons (e.g., "New
	 * Card" and "Stand").
	 * 
	 * @return The bottom panel with action buttons.
	 */
	private JPanel createBottomPanel() {
		JPanel panel = new JPanel(new FlowLayout());

		panel.add(newCardButton);
		panel.add(standButton);

		newCardButton.addActionListener(e -> gameController.drawCardForPlayer());
		standButton.addActionListener(e -> gameController.onStandButtonPress());
		return panel;
	}

	/**
	 * Displays a message in a dialog box.
	 * 
	 * @param message The message to display.
	 */
	public void showMessage(String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	/**
	 * Returns the currently selected card language (ES/FR).
	 * 
	 * @return The selected card language.
	 */
	public String getSelectedCardLan() {
		return (String) cardLanSelector.getSelectedItem();
	}

	/**
	 * Updates the login button state and enables or disables buttons based on the
	 * login status.
	 * 
	 * @param isLoggedIn The login status of the user.
	 */
	public void updateLoginButtonState(boolean isLoggedIn) {
		loginButton.setBackground(isLoggedIn ? Color.GREEN : null);
		setButtonsEnabled(isLoggedIn);
	}

	/**
	 * Enables or disables the buttons based on the login status.
	 * 
	 * @param isLoggedIn The login status of the user.
	 */
	private void setButtonsEnabled(boolean isLoggedIn) {
		loadCardsButton.setEnabled(isLoggedIn);
		startButton.setEnabled(isLoggedIn);
		saveButton.setEnabled(isLoggedIn);
		hallOfFameButton.setEnabled(isLoggedIn);
		logoutButton.setEnabled(isLoggedIn);
		cardLanSelector.setEnabled(isLoggedIn);

		if (newCardButton != null) {
			newCardButton.setEnabled(isLoggedIn);
		}
		if (standButton != null) {
			standButton.setEnabled(isLoggedIn);
		}
	}

	/**
	 * Updates the displayed images of the crupier and player cards.
	 * 
	 * @param base64CrupierCard The base64-encoded image for the crupier's card.
	 * @param base64PlayerCard  The base64-encoded image for the player's card.
	 */
	public void updateCardImages(String base64CrupierCard, String base64PlayerCard) {
		setCardImage(cardSlot1, base64CrupierCard);
		setCardImage(cardSlot2, base64PlayerCard);
	}

	/**
	 * Updates the displayed scores for the crupier and player.
	 * 
	 * @param crupierScore The crupier's score.
	 * @param playerScore  The player's score.
	 */
	public void updateScore(String crupierScore, String playerScore) {
		scoreSlot1.setText("Score: " + crupierScore);
		scoreSlot2.setText("Score: " + playerScore);
	}

	/**
	 * Displays a dialog box to select the player type (Crupier or User).
	 * 
	 * @return The selected player type index (0 for Crupier, 1 for User).
	 */
	public int showPlayerSelectionDialog() {
		Object[] options = { "Crupier(A.I.)", "User(Human)" };
		return JOptionPane.showOptionDialog(this, "Select the player", "Player Selection", JOptionPane.DEFAULT_OPTION,
				JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
	}

	/**
	 * Sets the card image for a given card slot.
	 * 
	 * @param cardSlot    The label representing the card slot.
	 * @param base64Image The base64-encoded image for the card.
	 */
	private void setCardImage(JLabel cardSlot, String base64Image) {
		if (cardSlot.getWidth() == 0 || cardSlot.getHeight() == 0) {
			System.err.println("Invalid cardSlot size.");
			return;
		}

		try {
			byte[] imageBytes = Base64.getDecoder().decode(base64Image);
			ImageIcon originalIcon = new ImageIcon(imageBytes);

			Image scaledImage = originalIcon.getImage().getScaledInstance(cardSlot.getWidth(), cardSlot.getHeight(),
					Image.SCALE_SMOOTH);

			cardSlot.setIcon(new ImageIcon(scaledImage));
			cardSlot.revalidate();
			cardSlot.repaint();
		} catch (IllegalArgumentException e) {
			System.err.println("Error decoding image: " + e.getMessage());
		}
	}
}
