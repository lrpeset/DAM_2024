package FileExplorer;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class responsible for performing file operations, including listing files,
 * searching for terms within files, and replacing text in text files.
 */
public class FileOperations {

    /**
     * Recursively lists files in the specified directory and its subdirectories.
     * For each file, it processes it to search for a given term and replaces text if necessary.
     *
     * @param dir               The directory to list files from.
     * @param parentNode        The parent node in the tree structure where the files will be added.
     * @param searchTerm        The term to search for within files (can be null).
     * @param replacementText   The text to replace the search term with (can be null).
     * @param caseSensitive     Whether the search should be case sensitive.
     * @param accentSensitive   Whether the search should be accent sensitive.
     * @param searchPerformed   Indicates if a search has been performed.
     */
    public void listFilesRecursive(File dir, DefaultMutableTreeNode parentNode, String searchTerm, String replacementText, boolean caseSensitive, boolean accentSensitive, boolean searchPerformed) {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    DefaultMutableTreeNode directoryNode = new DefaultMutableTreeNode(file.getName());
                    listFilesRecursive(file, directoryNode, searchTerm, replacementText, caseSensitive, accentSensitive, searchPerformed);
                    if (directoryNode.getChildCount() > 0) {
                        parentNode.add(directoryNode);
                    }
                } else {
                    processFile(file, parentNode, searchTerm, replacementText, caseSensitive, accentSensitive, searchPerformed);
                }
            }
        }
    }

    /**
     * Processes a single file to search for a term and potentially replace it.
     *
     * @param file             The file to process.
     * @param parentNode       The parent node in the tree structure where the file's details will be added.
     * @param searchTerm       The term to search for within the file (can be null).
     * @param replacementText   The text to replace the search term with (can be null).
     * @param caseSensitive    Whether the search should be case sensitive.
     * @param accentSensitive  Whether the search should be accent sensitive.
     * @param searchPerformed  Indicates if a search has been performed.
     */
    private void processFile(File file, DefaultMutableTreeNode parentNode, String searchTerm, String replacementText, boolean caseSensitive, boolean accentSensitive, boolean searchPerformed) {
        int matches = 0;
        if (searchTerm != null) {
            matches = searchFileForTerm(file, searchTerm, caseSensitive, accentSensitive);
        }

        if (replacementText != null && !replacementText.isEmpty() && matches > 0) {
            int replacements = replaceTextInFile(file, searchTerm, replacementText);
            if (replacements > 0) {
                String newFileName = "MOD_" + file.getName();
                parentNode.add(new DefaultMutableTreeNode(newFileName + " (Replaced " + replacements + " times)"));
            }
        }

        String fileDetails = getFileDetails(file, matches, searchPerformed);
        parentNode.add(new DefaultMutableTreeNode(fileDetails));
    }

    /**
     * Retrieves file details including the name, size, last modified date,
     * and the number of matches found if a search was performed.
     *
     * @param file           The file to retrieve details from.
     * @param matches        The number of matches found in the file.
     * @param searchPerformed Indicates if a search has been performed.
     * @return A string containing file details.
     */
    private String getFileDetails(File file, int matches, boolean searchPerformed) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String lastModified = sdf.format(new Date(file.lastModified()));

        if (searchPerformed) {
            return String.format("%s (%d matches)", file.getName(), matches);
        } else {
            return String.format("%s (%.1f KB – %s)", file.getName(), file.length() / 1024.0, lastModified);
        }
    }

    /**
     * Searches for a given term within the specified file.
     *
     * @param file             The file to search within.
     * @param searchTerm       The term to search for.
     * @param caseSensitive    Whether the search should be case sensitive.
     * @param accentSensitive  Whether the search should be accent sensitive.
     * @return The number of occurrences of the search term in the file.
     */
    private static int searchFileForTerm(File file, String searchTerm, boolean caseSensitive, boolean accentSensitive) {
        if (file.isFile() && file.canRead()) {
            if (file.getName().endsWith(".pdf")) {
                String content = readPDFContent(file);
                if (content != null) {
                    return countOccurrences(content, searchTerm, caseSensitive, accentSensitive);
                }
            } else {
                return searchInTextFile(file, searchTerm, caseSensitive, accentSensitive);
            }
        }
        return 0;
    }

    /**
     * Searches for a term within a text file and counts the occurrences.
     *
     * @param file             The text file to search within.
     * @param searchTerm       The term to search for.
     * @param caseSensitive    Whether the search should be case sensitive.
     * @param accentSensitive  Whether the search should be accent sensitive.
     * @return The number of occurrences of the search term in the text file.
     */
    private static int searchInTextFile(File file, String searchTerm, boolean caseSensitive, boolean accentSensitive) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int matchCount = 0;
            while ((line = reader.readLine()) != null) {
                matchCount += countOccurrences(line, searchTerm, caseSensitive, accentSensitive);
            }
            return matchCount;
        } catch (IOException e) {
            return 0;
        }
    }

    /**
     * Counts the occurrences of a search term in the provided text.
     *
     * @param text             The text to search in.
     * @param searchTerm       The term to search for.
     * @param caseSensitive    Whether the search should be case sensitive.
     * @param accentSensitive  Whether the search should be accent sensitive.
     * @return The number of occurrences of the search term in the text.
     */
    private static int countOccurrences(String text, String searchTerm, boolean caseSensitive, boolean accentSensitive) {
        if (!caseSensitive) {
            text = text.toLowerCase();
            searchTerm = searchTerm.toLowerCase();
        }

        if (!accentSensitive) {
            text = removeAccents(text);
            searchTerm = removeAccents(searchTerm);
        }

        return (text.split(searchTerm, -1).length - 1);
    }

    /**
     * Reads the content of a PDF file and returns it as a string.
     *
     * @param file The PDF file to read.
     * @return The content of the PDF as a string, or null if an error occurs.
     */
    private static String readPDFContent(File file) {
        try (PDDocument document = PDDocument.load(file)) {
            PDFTextStripper pdfStripper = new PDFTextStripper();
            return pdfStripper.getText(document);
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Replaces occurrences of a search term in a text file with the specified replacement text.
     *
     * @param file             The text file to modify.
     * @param searchTerm       The term to replace.
     * @param replacementText   The text to replace the term with.
     * @return The number of replacements made in the file.
     */

    private static int replaceTextInFile(File file, String searchTerm, String replacementText) {
        if (!file.isFile() || !file.canRead()) return 0;

        String fileName = file.getName();
        if (!fileName.endsWith(".txt")) {
            JOptionPane.showMessageDialog(null, "Unable to modify due to its extension (." + getFileExtension(fileName) + ")", "Error", JOptionPane.ERROR_MESSAGE);
            return 0;
        }
        String newFileName = "MOD_" + file.getName();
        File newFile = new File(file.getParent(), newFileName);
        int replacements = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file));
             PrintWriter writer = new PrintWriter(new FileWriter(newFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                int lineReplacements = countOccurrences(line, searchTerm, true, true);
                replacements += lineReplacements;
                line = line.replace(searchTerm, replacementText);
                writer.println(line);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred while modifying the file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("IOException: " + e.getMessage());
        }
        return replacements;
    }

    /**
     * Removes accents from a given string.
     *
     * @param text The string from which accents will be removed.
     * @return The string without accents.
     */
    private static String removeAccents(String text) {
        return text.replaceAll("[áàäâ]", "a")
                .replaceAll("[éèëê]", "e")
                .replaceAll("[íìïî]", "i")
                .replaceAll("[óòöô]", "o")
                .replaceAll("[úùüû]", "u");
    }

    /**
     * Retrieves the extension of a given file name.
     *
     * @param fileName The name of the file.
     * @return The file extension (without the dot) or an empty string if there is no extension.
     */
    private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        return (dotIndex == -1) ? "unknown" : fileName.substring(dotIndex + 1);
    }
}
