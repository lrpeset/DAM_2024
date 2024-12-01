package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The CsvProcessor class provides methods for processing CSV files, interacting with a database, and generating XML content.
 * It reads CSV data, inserts it into a database, and generates XML representations of the data.
 * 
 * <p>This class handles the conversion of CSV data into database entries and XML content for further processing.</p>
 */
public class CsvProcessor {
    private Connection connection;

    /**
     * Constructor for CsvProcessor.
     * 
     * @param connection The database connection to use for inserting data into the database.
     */
    public CsvProcessor(Connection connection) {
        this.connection = connection;
    }

    /**
     * Reads a CSV file, creates the necessary table in the database, inserts rows into the database,
     * and generates corresponding XML content for each row.
     * 
     * @param filePath The path to the CSV file to be processed.
     */
    public void processCsvAndGenerateXml(String filePath) {
        List<String[]> rows;
        try {
            rows = readCsv(filePath);

            createPopulationTable();

            for (String[] row : rows) {
                String xmlContent = generateXmlContent(row);

                insertRowToDatabase(row);

                System.out.println(xmlContent);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the CSV file and returns a list of rows where each row is represented as an array of strings.
     * 
     * @param filePath The path to the CSV file to be read.
     * @return A list of string arrays representing the rows of the CSV file.
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public List<String[]> readCsv(String filePath) throws IOException {
        List<String[]> rows = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] values = line.split(";");

                if (values.length < 8) {
                    System.out.println("Fila con datos incompletos: " + line);
                    continue;
                }

                rows.add(values);
            }
        }
        return rows;
    }

    /**
     * Creates the population table in the database if it does not exist.
     * This table will store data such as country, population, density, and more.
     */
    private void createPopulationTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS population (" +
                "country VARCHAR(30), " +
                "population VARCHAR(30), " +
                "density VARCHAR(30), " +
                "area VARCHAR(30), " +
                "fertility VARCHAR(30), " +
                "age VARCHAR(30), " +
                "urban VARCHAR(30), " +
                "share VARCHAR(30))";

        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(createTableSQL);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a row of data into the population table in the database.
     * 
     * @param row The row of data to be inserted into the database.
     */
    public void insertRowToDatabase(String[] row) {
        String insertSQL = "INSERT INTO population (country, population, density, area, fertility, age, urban, share) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(insertSQL)) {
            for (int i = 0; i < 8; i++) {
                pstmt.setString(i + 1, row[i]);
            }
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Generates an XML representation of a population data row.
     * 
     * @param row The row of data to be converted to XML.
     * @return The generated XML content as a string.
     * @throws IllegalArgumentException If the row has insufficient data.
     */
    private String generateXmlContent(String[] row) {
        if (row.length < 8) {
            throw new IllegalArgumentException("Fila con datos incompletos: " + String.join(",", row));
        }

        String country = escapeXml(row[0]);
        String population = escapeXml(row[1]);
        String density = escapeXml(row[2]);
        String area = escapeXml(row[3]);
        String fertility = escapeXml(row[4]);
        String age = escapeXml(row[5]);
        String urban = escapeXml(row[6]);
        String share = escapeXml(row[7]);

        String xmlContent = "<populationData>";
        xmlContent += "<country>" + country + "</country>";
        xmlContent += "<population>" + population + "</population>";
        xmlContent += "<density>" + density + "</density>";
        xmlContent += "<area>" + area + "</area>";
        xmlContent += "<fertility>" + fertility + "</fertility>";
        xmlContent += "<age>" + age + "</age>";
        xmlContent += "<urban>" + urban + "</urban>";
        xmlContent += "<share>" + share + "</share>";
        xmlContent += "</populationData>";

        return xmlContent;
    }

    /**
     * Escapes special characters in a string to make it XML-safe.
     * 
     * @param value The string to be escaped.
     * @return The escaped string.
     */
    private String escapeXml(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&apos;");
    }
}
