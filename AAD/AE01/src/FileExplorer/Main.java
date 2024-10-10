package FileExplorer;

import javax.swing.*;

/**
 * Main class that serves as the entry point for the File Explorer application.
 * This class is responsible for initializing and displaying the FileManagerWithSearch UI.
 */
public class Main {

    /**
     * The main method that launches the File Explorer application.
     * It uses SwingUtilities to ensure that the GUI is created on the Event Dispatch Thread (EDT).
     *
     * @param args Command line arguments (not used).
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            FileManagerWithSearch manager = new FileManagerWithSearch();
            manager.setVisible(true);
        });
    }
}
