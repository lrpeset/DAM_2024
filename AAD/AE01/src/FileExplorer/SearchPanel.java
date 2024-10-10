package FileExplorer;

import javax.swing.*;

/**
 * Panel for entering search parameters including the search term and replacement text.
 */
public class SearchPanel {
    private final JTextField searchTermField;
    private final JTextField replacementTextField;
    private final JCheckBox caseSensitiveCheckBox;
    private final JCheckBox accentSensitiveCheckBox;
    private final JPanel panel;

    /**
     * Constructs a SearchPanel with fields for search and replacement text,
     * and options for case and accent sensitivity.
     */
    public SearchPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        searchTermField = new JTextField(20);
        replacementTextField = new JTextField(20);
        caseSensitiveCheckBox = new JCheckBox("Case Sensitive");
        accentSensitiveCheckBox = new JCheckBox("Accent Sensitive");

        panel.add(new JLabel("Search Term:"));
        panel.add(searchTermField);
        panel.add(new JLabel("Replacement Text (optional):"));
        panel.add(replacementTextField);
        panel.add(caseSensitiveCheckBox);
        panel.add(accentSensitiveCheckBox);
    }

    /**
     * Gets the panel containing the search fields.
     *
     * @return JPanel with search inputs.
     */
    public JPanel getPanel() {
        return panel;
    }

    /**
     * Retrieves the entered search term.
     *
     * @return String representing the search term.
     */
    public String getSearchTerm() {
        return searchTermField.getText();
    }

    /**
     * Checks if replacement text is provided.
     *
     * @return true if replacement text is present, false otherwise.
     */
    public boolean isReplacementEnabled() {
        return !replacementTextField.getText().isEmpty();
    }

    /**
     * Checks if the search is case-sensitive.
     *
     * @return true if case sensitivity is enabled, false otherwise.
     */
    public boolean isCaseSensitive() {
        return caseSensitiveCheckBox.isSelected();
    }

    /**
     * Checks if the search is accent-sensitive.
     *
     * @return true if accent sensitivity is enabled, false otherwise.
     */
    public boolean isAccentSensitive() {
        return accentSensitiveCheckBox.isSelected();
    }

    /**
     * Retrieves the entered replacement text.
     *
     * @return String representing the replacement text.
     */
    public String getReplacementText() {
        return replacementTextField.getText();
    }
}

