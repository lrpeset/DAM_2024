package model;

/**
 * The User class represents a user in the system. It contains the user's credentials,
 * including their username, hashed password, and user type.
 * 
 * <p>This class is used to store and retrieve user information such as login credentials
 * and the role of the user (e.g., "admin" or "client").</p>
 */
public class User {
    private String username;
    private String hashedPassword;
    private String userType;

    /**
     * Constructs a User object with the specified username, hashed password, and user type.
     * 
     * @param username The username of the user.
     * @param hashedPassword The hashed password of the user.
     * @param userType The type of the user (e.g., "admin" or "client").
     */
    public User(String username, String hashedPassword, String userType) {
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
    }

    /**
     * Gets the username of the user.
     * 
     * @return The username of the user.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets the hashed password of the user.
     * 
     * @return The hashed password of the user.
     */
    public String getHashedPassword() {
        return hashedPassword;
    }

    /**
     * Gets the type of the user (e.g., "admin" or "client").
     * 
     * @return The type of the user.
     */
    public String getUserType() {
        return userType;
    }
}
