package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The Authentication class provides utility methods for performing authentication tasks.
 * In this case, it includes a method to hash passwords using the MD5 algorithm.
 * 
 * <p>This class helps to securely store user passwords by hashing them before they are saved in a database.</p>
 */
public class Authentication {
    
    /**
     * Hashes a password using the MD5 hashing algorithm.
     * 
     * <p>This method takes a plain-text password and returns its MD5 hash in hexadecimal format.</p>
     * 
     * @param password The plain-text password to be hashed.
     * @return The MD5 hash of the password as a hexadecimal string.
     * @throws NoSuchAlgorithmException If the MD5 algorithm is not available.
     */
    public static String hashPassword(String password) {
        try {
            // Create an MD5 hash instance
            MessageDigest md = MessageDigest.getInstance("MD5");
            // Update the digest with the password bytes
            md.update(password.getBytes());
            // Generate the hash
            byte[] digest = md.digest();
            // Convert the hash to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
            
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
