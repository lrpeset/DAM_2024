package com.blackjack.controller;

import java.awt.Image;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.bson.Document;

import com.blackjack.model.Card;
import com.blackjack.model.MongoDBManager;
import com.blackjack.view.LoginView;
import com.blackjack.view.MainView;
import com.blackjack.view.RegisterView;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

/**
 * This class is responsible for controlling the flow of the Blackjack game,
 * handling user registration, login, game actions, and database interactions.
 */
public class GameController {

	// MongoDB Manager to handle database operations
	public MongoDBManager dbManager;

	// Boolean to check if the user is logged in
	private boolean isUserLoggedIn;

	// Main view of the application
	private MainView mainView;

	// List to hold the cards of the dealer (Crupier)
	private List<Card> CrupierHand = new ArrayList<>();

	// List to hold the cards of the player (User)
	private List<Card> UserHand = new ArrayList<>();

	// Boolean to indicate if it's the player's turn
	private boolean isPlayerTurn = true;

	// Boolean to track if the dealer has stood
	@SuppressWarnings("unused")
	private boolean isCrupierStand = false;

	// Boolean to indicate if the game is over
	private boolean isGameOver = false;

	/**
	 * Constructor to initialize the GameController.
	 * 
	 * @param dbManager MongoDB manager instance for database operations
	 * @param mainView  Main view of the application
	 */
	public GameController(MongoDBManager dbManager, MainView mainView) {
		this.dbManager = dbManager;
		this.isUserLoggedIn = false;
		this.mainView = mainView;
	}

	/**
	 * Displays the register view to allow a user to register.
	 */
	public void showRegisterView() {
		RegisterView registerView = new RegisterView();
		registerView.setVisible(true);
		registerView.addOkButtonListener(okEvent -> handleRegister(registerView));
		registerView.addCancelButtonListener(cancelEvent -> registerView.dispose());
	}

	/**
	 * Handles user registration logic.
	 * 
	 * @param registerView The register view instance
	 */
	private void handleRegister(RegisterView registerView) {
		String username = registerView.getUsername();
		String password = registerView.getPassword();
		String repeatedPassword = registerView.getRepeatedPassword();

		if (!password.equals(repeatedPassword)) {
			registerView.showMessage("Passwords do not match!");
			return;
		}

		if (registerUser(username, password)) {
			registerView.showMessage("User registered successfully!");
			registerView.dispose();
		} else {
			registerView.showMessage("User already exists!");
		}
	}

	/**
	 * Registers a new user in the database.
	 * 
	 * @param username The username to register
	 * @param password The password for the user
	 * @return True if registration was successful, false otherwise
	 */
	public boolean registerUser(String username, String password) {
		try {
			return dbManager.registerUser(username, password);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Handles the login action and displays the login view.
	 * 
	 * @param mainView The main view instance
	 */
	public void loginAction(MainView mainView) {
		LoginView loginView = new LoginView();
		loginView.addLoginButtonListener(e -> handleLogin(loginView, mainView));
		loginView.addCancelButtonListener(e -> loginView.dispose());
		loginView.setVisible(true);
	}

	/**
	 * Handles the login process and updates the main view upon success or failure.
	 * 
	 * @param loginView The login view instance
	 * @param mainView  The main view instance
	 */
	private void handleLogin(LoginView loginView, MainView mainView) {
		String username = loginView.getUsername();
		String password = loginView.getPassword();

		if (loginUser(username, password)) {
			mainView.updateLoginButtonState(true);
			loginView.dispose();
			mainView.showMessage("Login successful!");
		} else {
			mainView.showMessage("Invalid credentials!");
		}
	}

	/**
	 * Authenticates the user with the provided username and password.
	 * 
	 * @param username The username to authenticate
	 * @param password The password to authenticate
	 * @return True if login is successful, false otherwise
	 */
	public boolean loginUser(String username, String password) {
		try {
			boolean loginSuccess = dbManager.loginUser(username, password);
			if (loginSuccess) {
				isUserLoggedIn = true;
			}
			return loginSuccess;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Handles the logout action and updates the main view.
	 * 
	 * @param mainView The main view instance
	 */
	public void logoutAction(MainView mainView) {
		isUserLoggedIn = false;
		mainView.updateLoginButtonState(false);
	}

	/**
	 * Checks if the user is logged in.
	 * 
	 * @return True if the user is logged in, false otherwise
	 */
	public boolean isUserLoggedIn() {
		return isUserLoggedIn;
	}

	/**
	 * Loads the cards from the database based on the selected card language
	 * (ES/FR).
	 * 
	 * @param cardLan The language of the card collection (either "ES" or "FR")
	 */
	public void handleLoadCards(String cardLan) {
		try {
			dbManager.resetCardsCollection(cardLan);
			dbManager.loadCardsIntoCollection(cardLan);
			System.out.println("Cards loaded successfully for the version " + cardLan);
		} catch (Exception e) {
			e.printStackTrace();
			mainView.showMessage("Error loading cards: " + e.getMessage());
		}
	}

	/**
	 * Starts the game by handling player and dealer turns.
	 */
	public void onStartButtonPressed() {
		try {
			isGameOver = false;
			resetGame();
			int choice = mainView.showPlayerSelectionDialog();

			if (choice == JOptionPane.CLOSED_OPTION) {
				return;
			}

			if (choice == 0) {
				mainView.showMessage("The dealer begins, good luck.");
				isPlayerTurn = false;
				startCrupierTurn();
			} else if (choice == 1) {
				mainView.showMessage("The player begins, good luck.");
				isPlayerTurn = true;
				playerTurn();
			} else {
				System.out.println("Invalid selection.");
				return;
			}

			String selectedCollection = determineCardCollection();
			if (selectedCollection == null) {
				return;
			}

		} catch (Exception e) {
			System.out.println("An error occurred while starting the turn: " + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * Resets the game state, clearing hands and resetting the UI.
	 */
	private void resetGame() {
		mainView.cardSlot1.setIcon(null);
		mainView.cardSlot2.setIcon(null);
		CrupierHand.clear();
		UserHand.clear();
		mainView.newCardButton.setEnabled(true);
		mainView.standButton.setEnabled(true);
	}

	/**
	 * Determines which card collection to use based on the database availability.
	 * 
	 * @return The name of the selected card collection or null if no valid
	 *         collection is found
	 */
	private String determineCardCollection() {
		MongoDatabase database = dbManager.getDatabase();
		if (database.listCollectionNames().into(new ArrayList<>()).contains(dbManager.cardsEsCollection)) {
			return dbManager.cardsEsCollection;
		} else if (database.listCollectionNames().into(new ArrayList<>()).contains(dbManager.cardsFrCollection)) {
			return dbManager.cardsFrCollection;
		}
		System.out.println("No valid collection found.");
		return null;
	}

	/**
	 * Loads a random card from the database collection and displays it.
	 * 
	 * @param collection The MongoDB collection to fetch the card from
	 * @param choice     The slot (0 for dealer, 1 for player) where the card should
	 *                   be displayed
	 */
	private void loadRandomCardFromCollection(MongoCollection<Document> collection, int choice) {
		long count = collection.countDocuments();
		int randomIndex = new Random().nextInt((int) count);
		Document document = collection.find().skip(randomIndex).first();

		if (document != null) {
			String suit = document.getString("suit");
			int points = document.getInteger("points");
			String base64 = document.getString("base64");

			Card card = new Card(suit, points, base64);
			displayCardInSlot(card, choice);
		}
	}

	/**
	 * Decodes the base64 image bytes into an Image object.
	 * 
	 * @param imageBytes The base64 encoded image bytes
	 * @return The decoded Image object
	 */
	private Image decodeImage(byte[] imageBytes) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(imageBytes);
			return ImageIO.read(bis);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Updates the score for both the player and the dealer.
	 */
	public void updateScore() {
		updatePlayerScore(CrupierHand, mainView.scoreSlot1);
		updatePlayerScore(UserHand, mainView.scoreSlot2);
	}

	/**
	 * Updates the score for a specific player's hand.
	 * 
	 * @param hand      The list of cards representing the player's hand
	 * @param scoreSlot The JLabel to display the score
	 */
	private void updatePlayerScore(List<Card> hand, JLabel scoreSlot) {
		int totalPoints = hand.stream().mapToInt(Card::getPoints).sum();
		scoreSlot.setText(String.valueOf(totalPoints));
	}

	/**
	 * Draws a random card for the player and updates the score. If the player
	 * exceeds 21 points, the game ends and the winner is determined.
	 */
	public void drawCardForPlayer() {
		if (isGameOver) {
			mainView.showMessage("El juego ha terminado. Inicia un nuevo juego.");
			return;
		}

		String selectedCollection = determineCardCollection();
		if (selectedCollection == null)
			return;

		MongoCollection<Document> collection = dbManager.getDatabase().getCollection(selectedCollection);
		if (collection.countDocuments() == 0) {
			System.out.println("La colección está vacía.");
			return;
		}

		loadRandomCardFromCollection(collection, 1);
		updateScore();

		if (getUserPoints() > 21) {
			isGameOver = true;
			mainView.newCardButton.setEnabled(false);
			mainView.standButton.setEnabled(false);
			determineWinner();
		} else {
			switchTurn();
		}
	}

	/**
	 * Handles the event when the "Stand" button is pressed. The player chooses to
	 * stand, and the game proceeds to the crupier's turn if necessary.
	 */
	public void onStandButtonPress() {
		if (isPlayerTurn) {
			isPlayerTurn = false;
			mainView.showMessage("El jugador se ha plantado.");

			if (isCrupierStand == true) { // Correct comparison
				determineWinner();
			} else { // Case where the crupier has not yet stood
				switchTurn();
			}
		}
	}

	/**
	 * Starts the crupier's turn. The crupier draws cards if their points are less
	 * than or equal to 16, and the game continues until the crupier stands or
	 * exceeds 21 points.
	 */
	private void startCrupierTurn() {
		mainView.newCardButton.setEnabled(false);
		mainView.standButton.setEnabled(false);
		if (getCrupierPoints() <= 16 && !isGameOver) {
			String selectedCollection = determineCardCollection();
			if (selectedCollection == null)
				return;

			MongoCollection<Document> collection = dbManager.getDatabase().getCollection(selectedCollection);
			if (collection.countDocuments() == 0) {
				System.out.println("La colección está vacía.");
				return;
			}
			Timer timer = new Timer(1000, e -> {
				loadRandomCardFromCollection(collection, 0);
				updateScore();
				if (getCrupierPoints() > 21) {
					isGameOver = true;
					mainView.newCardButton.setEnabled(false);
					mainView.standButton.setEnabled(false);
					determineWinner();
				} else {
					switchTurn();
				}
			});

			timer.setRepeats(false);
			timer.start();

		} else if (getCrupierPoints() > 16) {
			mainView.showMessage("El crupier se planta con " + getCrupierPoints() + " puntos.");
			isCrupierStand = true;
			switchTurn();
		}
	}

	/**
	 * Switches the turn between the player and the crupier. If it's the player's
	 * turn, the game allows them to draw a card or stand. If it's the crupier's
	 * turn, the crupier starts drawing cards.
	 */
	private void switchTurn() {
		if (isPlayerTurn) {
			isPlayerTurn = false;
			mainView.showMessage("Es el turno del crupier.");
			startCrupierTurn();
		} else {
			isPlayerTurn = true;
			playerTurn();
			mainView.showMessage("Es el turno del jugador.");
		}
	}

	/**
	 * Determines the winner of the game based on the points of the player and the
	 * crupier. If either exceeds 21 points, the other wins. If the points are tied,
	 * it results in a draw.
	 */
	private void determineWinner() {
		int userPoints = getUserPoints();
		int crupierPoints = getCrupierPoints();

		if (userPoints > 21) {
			mainView.showMessage("El jugador se ha pasado de 21. El crupier gana.");
		} else if (crupierPoints > 21) {
			mainView.showMessage("El crupier se ha pasado de 21. El jugador gana.");
		} else if (userPoints > crupierPoints) {
			mainView.showMessage("El jugador gana con " + userPoints + " puntos.");
		} else if (userPoints < crupierPoints) {
			mainView.showMessage("El crupier gana con " + crupierPoints + " puntos.");
		} else {
			mainView.showMessage("Empate con " + userPoints + " puntos.");
		}

		isGameOver = true;
		mainView.newCardButton.setEnabled(false);
		mainView.standButton.setEnabled(false);
	}

	/**
	 * Enables the player's turn, allowing them to draw a card or stand.
	 */
	private void playerTurn() {
		mainView.newCardButton.setEnabled(true);
		mainView.standButton.setEnabled(true);
	}

	/**
	 * Displays the card in the appropriate slot (either for the player or the
	 * crupier) and updates the score accordingly. If the card is an Ace, it adjusts
	 * its value based on the situation.
	 *
	 * @param card   The card to be displayed.
	 * @param choice The slot where the card should be displayed (0 for crupier, 1
	 *               for player).
	 */
	private void displayCardInSlot(Card card, int choice) {
		byte[] imageBytes = Base64.getDecoder().decode(card.getBase64());
		Image image = decodeImage(imageBytes);

		if (image != null) {
			if (choice == 0) {
				setCardImage(mainView.cardSlot1, card.getBase64());
				CrupierHand.add(card);

				if (card.getPoints() == 1) {
					if (getCrupierPoints() + 11 <= 21) {
						card.setPoints(11);
					} else {
						card.setPoints(1);
					}
				}

			} else if (choice == 1) {
				setCardImage(mainView.cardSlot2, card.getBase64());
				UserHand.add(card);

				if (card.getPoints() == 1) {
					handleAceForUser(card);
				}
			}
			updateScore();
		} else {
			System.out.println("Error al decodificar la imagen.");
		}
	}

	/**
	 * Handles the logic for selecting the value of an Ace for the player. The
	 * player can choose between 1 or 11.
	 *
	 * @param card The Ace card to be handled.
	 */
	private void handleAceForUser(Card card) {
		String[] options = { "1", "11" };
		int choice = JOptionPane.showOptionDialog(mainView, "Has robado un As. ¿Qué valor quieres asignar?",
				"Selecciona valor para el As", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options,
				options[0]);

		if (choice == 0) {
			card.setPoints(1);
		} else if (choice == 1) {
			card.setPoints(11);
		}
	}

	/**
	 * Sets the image of the card in the specified slot.
	 *
	 * @param cardSlot    The slot where the card image will be displayed.
	 * @param base64Image The Base64 encoded image string of the card.
	 */
	private void setCardImage(JLabel cardSlot, String base64Image) {
		if (cardSlot.getWidth() == 0 || cardSlot.getHeight() == 0) {
			System.err.println("Tamaño de cardSlot no válido.");
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
			System.err.println("Error al decodificar la imagen: " + e.getMessage());
		}
	}

	/**
	 * Returns the total points of the player.
	 *
	 * @return The total points of the player.
	 */
	public int getUserPoints() {
		return getTotalPoints(UserHand);
	}

	/**
	 * Returns the total points of the crupier.
	 *
	 * @return The total points of the crupier.
	 */
	private int getCrupierPoints() {
		return getTotalPoints(CrupierHand);
	}

	/**
	 * Calculates the total points of a hand (either the player's or crupier's
	 * hand).
	 *
	 * @param hand The hand of cards to calculate the total points for.
	 * @return The total points of the hand.
	 */
	private int getTotalPoints(List<Card> hand) {
		int score = 0;
		for (Card card : hand) {
			score += card.getPoints();
		}
		return score;
	}

	/**
	 * Sets the main view for the game.
	 *
	 * @param mainView The main view to be set.
	 */
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	/**
	 * Displays the "Hall of Fame" with the top scores from the database.
	 */
	public void showHallOfFame() {
		MongoCollection<Document> collection = dbManager.getDatabase().getCollection("scores");
		FindIterable<Document> results = collection.find().sort(Sorts.descending("points"));

		StringBuilder hallOfFameData = new StringBuilder("Hall of Fame:\n");
		int position = 1;

		for (Document doc : results) {
			String user = doc.getString("user");
			int points = doc.getInteger("points");
			String suit = doc.getString("suit");
			String timestamp = doc.getString("timestamp");

			hallOfFameData.append(String.format("%d. Usuario: %s | Puntos: %d | Baraja: %s | Fecha: %s\n", position,
					user, points, suit, timestamp));
			position++;
		}

		if (hallOfFameData.length() > 0) {
			mainView.showMessage(hallOfFameData.toString());
		} else {
			mainView.showMessage("No hay registros en el Hall of Fame.");
		}
	}

}
