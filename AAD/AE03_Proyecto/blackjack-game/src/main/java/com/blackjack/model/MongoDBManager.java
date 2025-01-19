package com.blackjack.model;

import com.blackjack.controller.GameController;
import com.blackjack.view.LoginView;
import com.blackjack.view.MainView;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HexFormat;

/**
 * MongoDBManager handles the database interactions for the Blackjack game,
 * including user authentication, saving scores, and managing card collections.
 */
public class MongoDBManager {

	private MongoClient client; // MongoDB client to manage database connections
	private MongoDatabase database; // MongoDB database object
	public String cardsEsCollection; // Name of the collection for Spanish cards
	public String cardsFrCollection; // Name of the collection for French cards
	private String usersCollection; // Name of the collection for users
	private String scoresCollection; // Name of the collection for scores
	private GameController controller; // Reference to the GameController for game logic
	private MainView mainView; // Reference to the MainView for UI interaction
	private LoginView loginView; // Reference to the LoginView for user authentication
	private String activeUsername; // Username of the currently logged-in user

	/**
	 * Sets the active username (the logged-in user).
	 * 
	 * @param username the username to set
	 */
	public void setActiveUsername(String username) {
		this.activeUsername = username;
	}

	/**
	 * Gets the active username (the currently logged-in user).
	 * 
	 * @return the active username
	 */
	public String getActiveUsername() {
		return activeUsername;
	}

	/**
	 * Sets the MainView instance.
	 * 
	 * @param mainView the MainView instance
	 */
	public void setMainView(MainView mainView) {
		this.mainView = mainView;
	}

	/**
	 * Sets the LoginView instance.
	 * 
	 * @param loginView the LoginView instance
	 */
	public void setLoginView(LoginView loginView) {
		this.loginView = loginView;
	}

	/**
	 * Constructor that initializes the MongoDBManager with a given configuration
	 * file.
	 * 
	 * @param configFile the path to the configuration file
	 * @throws Exception if an error occurs while connecting to the database or
	 *                   reading the configuration file
	 */
	public MongoDBManager(String configFile) throws Exception {
		String json = new String(Files.readAllBytes(Paths.get(configFile)));
		Document config = Document.parse(json);

		String ip = config.getString("ip");
		String port = config.getString("port");
		String databaseName = config.getString("database");

		// Create the connection string for MongoDB
		String connectionString = String.format("mongodb://%s:%s/%s", ip, port, databaseName);
		client = MongoClients.create(connectionString);
		database = client.getDatabase(databaseName);

		Document collections = config.get("collections", Document.class);
		this.cardsEsCollection = collections.getString("cards_es");
		this.cardsFrCollection = collections.getString("cards_fr");
		this.usersCollection = collections.getString("users");
		this.scoresCollection = collections.getString("scores");

		System.out.println("Connected to database: " + databaseName);
		System.out.println("Using collections: ");
		System.out.println("  Spanish cards: " + cardsEsCollection);
		System.out.println("  French cards: " + cardsFrCollection);
		System.out.println("  Users: " + usersCollection);
		System.out.println("  Scores: " + scoresCollection);
	}

	/**
	 * Registers a new user with the specified username and password.
	 * 
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return true if registration is successful, false if the username already
	 *         exists
	 */
	public boolean registerUser(String username, String password) {
		MongoCollection<Document> collection = database.getCollection(usersCollection);
		if (userExists(collection, username)) {
			return false;
		}

		String hashedPassword = hashPassword(password);
		Document newUser = new Document("username", username).append("password", hashedPassword);
		collection.insertOne(newUser);
		return true;
	}

	/**
	 * Logs in a user with the specified username and password.
	 * 
	 * @param username the username of the user
	 * @param password the password of the user
	 * @return true if login is successful, false if the credentials are invalid
	 */
	public boolean loginUser(String username, String password) {
		MongoCollection<Document> collection = database.getCollection(usersCollection);
		Document user = collection.find(new Document("username", username)).first();
		boolean isValid = user != null && hashPassword(password).equals(user.getString("password"));

		if (isValid) {
			// Set the active username
			setActiveUsername(username);
		}
		return isValid;
	}

	/**
	 * Checks if a user with the specified username already exists in the
	 * collection.
	 * 
	 * @param collection the MongoDB collection to check
	 * @param username   the username to check
	 * @return true if the user exists, false otherwise
	 */
	private boolean userExists(MongoCollection<Document> collection, String username) {
		return collection.find(new Document("username", username)).first() != null;
	}

	/**
	 * Hashes the given password using the SHA-256 algorithm.
	 * 
	 * @param password the password to hash
	 * @return the hashed password as a hex string
	 */
	private String hashPassword(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] hash = digest.digest(password.getBytes());
			return HexFormat.of().formatHex(hash);
		} catch (Exception e) {
			throw new RuntimeException("Error hashing password", e);
		}
	}

	/**
	 * Resets the collection for the specified card suit (either 'ES' or 'FR').
	 * 
	 * @param cardSuit the card suit (either 'ES' or 'FR')
	 */
	public void resetCardsCollection(String cardSuit) {
		try {
			String targetCollection = getTargetCollection(cardSuit);
			dropCollectionIfExists(cardsEsCollection);
			dropCollectionIfExists(cardsFrCollection);
			database.createCollection(targetCollection);
			System.out.println("Collection " + targetCollection + " created.");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error managing card collections: " + e.getMessage());
		}
	}

	/**
	 * Loads cards into the appropriate collection for the specified card suit.
	 * 
	 * @param cardSuit the card suit ('ES' for Spanish cards or 'FR' for French
	 *                 cards)
	 */
	public void loadCardsIntoCollection(String cardSuit) {
		try {
			String folderPath = "img/" + (cardSuit.equalsIgnoreCase("ES") ? "cards_es" : "cards_fr");
			File folder = new File(folderPath);

			if (!folder.exists() || !folder.isDirectory()) {
				throw new RuntimeException("The folder " + folderPath + " does not exist or is not a directory.");
			}

			String targetCollection = cardSuit.equalsIgnoreCase("ES") ? cardsEsCollection : cardsFrCollection;
			MongoCollection<Document> collection = database.getCollection(targetCollection);

			File[] files = folder.listFiles((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
			if (files == null || files.length == 0) {
				throw new RuntimeException("No .jpg files found in the folder " + folderPath);
			}

			for (File file : files) {
				String fileName = file.getName().replaceAll("\\.jpg$|\\.png$", "");
				String[] parts = fileName.split("_");
				if (parts.length != 2) {
					throw new RuntimeException("Invalid file format: " + fileName);
				}

				String suit = parts[0];
				int points = Integer.parseInt(parts[1]);
				if (points > 10) {
					points = 10;
				}

				String base64Image = encodeFileToBase64(file);

				Document card = new Document("suit", suit).append("points", points).append("base64", base64Image);

				collection.insertOne(card);
			}

			long count = collection.countDocuments();
			System.out.println("Number of cards inserted in " + targetCollection + ": " + count);
			if (count == 0) {
				System.out.println("No cards were inserted into the collection.");
			} else {
				System.out.println("Cards inserted into the collection " + targetCollection);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error loading cards into the collection: " + e.getMessage());
		}
	}

	/**
	 * Drops the specified collection if it exists in the database.
	 * 
	 * @param collectionName the name of the collection to drop
	 */
	private void dropCollectionIfExists(String collectionName) {
		if (database.listCollectionNames().into(new ArrayList<String>()).contains(collectionName)) {
			database.getCollection(collectionName).drop();
			System.out.println("Collection " + collectionName + " dropped.");
		}
	}

	/**
	 * Returns the target collection name based on the card suit.
	 * 
	 * @param cardSuit the card suit ('ES' for Spanish cards or 'FR' for French
	 *                 cards)
	 * @return the collection name for the specified card suit
	 */
	private String getTargetCollection(String cardSuit) {
		return cardSuit.equalsIgnoreCase("ES") ? cardsEsCollection : cardsFrCollection;
	}

	/**
	 * Encodes the content of a file to a Base64 string.
	 * 
	 * @param file the file to encode
	 * @return the Base64 encoded string
	 */
	private String encodeFileToBase64(File file) throws IOException {
		byte[] fileContent = Files.readAllBytes(file.toPath());
		return Base64.getEncoder().encodeToString(fileContent);
	}

	/**
	 * Saves the user's score to the 'scores' collection in the database.
	 * 
	 * @param username the username of the player
	 * @param suit     the suit of the deck used ('es' for Spanish, 'fr' for French)
	 * @param points   the points the player achieved in the game
	 * @return true if the score is saved successfully, false if an error occurs
	 */
	public boolean saveUserScore(String username, String suit, int points) {
		try {
			MongoCollection<Document> collection = database.getCollection("scores");

			// Get the current timestamp in the specified format
			String timestamp = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());

			// Create the document to insert
			Document score = new Document("user", username).append("suit", suit.toLowerCase()) // Ensure the suit is
																								// 'es' or 'fr'
					.append("points", points).append("timestamp", timestamp);

			// Insert the document into the collection
			collection.insertOne(score);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Gets the MongoDatabase instance that is connected to the database.
	 * 
	 * @return the MongoDatabase instance
	 */
	public MongoDatabase getDatabase() {
		return this.database;
	}

}
