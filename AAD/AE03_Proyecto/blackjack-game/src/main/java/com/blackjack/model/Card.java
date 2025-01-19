package com.blackjack.model;

/**
 * The Card class represents a playing card in the game of Blackjack. Each card
 * has a suit, a point value, and an associated image in Base64 format.
 */
public class Card {
	private String suit;
	private int points;
	private String base64;

	/**
	 * Constructs a Card object with the specified suit, points, and Base64 image
	 * representation.
	 * 
	 * @param suit   the suit of the card (e.g., "hearts", "diamonds", etc.)
	 * @param points the points associated with the card (e.g., 1, 10, 11, etc.)
	 * @param base64 the Base64 encoded string representing the image of the card
	 */
	public Card(String suit, int points, String base64) {
		this.suit = suit;
		this.points = points;
		this.base64 = base64;
	}

	/**
	 * Gets the suit of the card.
	 * 
	 * @return the suit of the card
	 */
	public String getSuit() {
		return suit;
	}

	/**
	 * Gets the points of the card.
	 * 
	 * @return the points of the card
	 */
	public int getPoints() {
		return points;
	}

	/**
	 * Gets the Base64 encoded image representation of the card.
	 * 
	 * @return the Base64 encoded string of the card's image
	 */
	public String getBase64() {
		return base64;
	}

	/**
	 * Sets the points of the card.
	 * 
	 * @param points the points to set for the card
	 */
	public void setPoints(int points) {
		this.points = points;
	}

	/**
	 * Returns a string representation of the card. This includes the suit and
	 * points of the card.
	 * 
	 * @return a string representation of the card
	 */
	@Override
	public String toString() {
		return "Card [suit=" + suit + ", points=" + points + "]";
	}
}
