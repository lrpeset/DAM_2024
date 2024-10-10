package FileExplorer;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.io.File;

/**
 * GUI application for browsing files and searching for text within them.
 */
public class FileManagerWithSearch extends JFrame {
    private DefaultMutableTreeNode root;
    private DefaultTreeModel treeModel;
    private JLabel statusLabel;
    private boolean searchPerformed;

    /**
     * Initializes the file manager GUI.
     */
    public FileManagerWithSearch() {
        initializeUI();
        searchPerformed = false;
    }

    /**
     * Sets up the main user interface components.
     */
    private void initializeUI() {
        setTitle("File Explorer");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        root = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(root);
        JTree fileTree = new JTree(treeModel);

        JScrollPane scrollPane = new JScrollPane(fileTree);

        JPanel buttonPanel = createButtonPanel();

        statusLabel = new JLabel("Status: ");
        add(buttonPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(statusLabel, BorderLayout.SOUTH);
    }

    /**
     * Creates the panel containing buttons for selecting a directory and searching.
     *
     * @return JPanel with buttons.
     */
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton selectDirectoryButton = new JButton("Select Directory");
        JButton searchButton = new JButton("Search");

        selectDirectoryButton.addActionListener(e -> chooseDirectory());
        searchButton.addActionListener(e -> performSearch());

        buttonPanel.add(selectDirectoryButton);
        buttonPanel.add(searchButton);
        return buttonPanel;
    }

    /**
     * Prompts the user to select a directory and displays its contents.
     */
    private void chooseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int option = fileChooser.showOpenDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();
            root.setUserObject(selectedDirectory.getAbsolutePath());
            root.removeAllChildren();
            new FileOperations().listFilesRecursive(selectedDirectory, root, null, null, false, false, searchPerformed);
            treeModel.reload();
            statusLabel.setText("Status: Directory selected: " + selectedDirectory.getName());

            searchPerformed = false;
        }
    }

    /**
     * Displays a dialog for entering search parameters and performs the search.
     */
    private void performSearch() {
        SearchPanel searchPanel = new SearchPanel();
        int option = JOptionPane.showConfirmDialog(this, searchPanel.getPanel(), "Search", JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            String searchTerm = searchPanel.getSearchTerm();
            String replacementText = searchPanel.isReplacementEnabled() ? searchPanel.getReplacementText() : null;
            boolean caseSensitive = searchPanel.isCaseSensitive();
            boolean accentSensitive = searchPanel.isAccentSensitive();

            if (!searchTerm.isEmpty()) {
                root.removeAllChildren();
                searchPerformed = true;
                new FileOperations().listFilesRecursive(new File(root.getUserObject().toString()), root, searchTerm, replacementText, caseSensitive, accentSensitive, searchPerformed);
                treeModel.reload();
                statusLabel.setText("Status: Search completed for \"" + searchTerm + "\".");
            }
        }
    }

}

