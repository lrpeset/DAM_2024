package utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**
 * The XmlProcessor class provides methods for handling XML files.
 * It includes functionality to generate XML files, parse XML content, and generate XML representations from data.
 */
public class XmlProcessor {

    /**
     * Generates an XML file with the provided content and saves it in the "xml" directory.
     * 
     * @param country The name of the country to be used as the filename for the XML file.
     * @param xmlContent The XML content to be written to the file.
     */
    public void generateXmlFile(String country, String xmlContent) {
        try {
            File dir = new File("xml");
            if (!dir.exists()) {
                dir.mkdirs();
            }
            FileWriter writer = new FileWriter(new File(dir, country + ".xml"));
            writer.write(xmlContent);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Parses the content of an XML file and extracts specific data fields into an array.
     * The method expects the XML to have tags for country, population, density, area, fertility, age, urban, and share.
     * 
     * @param xmlFile The XML file to be parsed.
     * @return An array of strings containing the extracted data from the XML file.
     * @throws Exception If there is an error parsing the XML file.
     */
    public String[] parseXmlContent(File xmlFile) throws Exception {
        String[] row = new String[8];
        
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse(xmlFile);
        
        NodeList country = doc.getElementsByTagName("country");
        NodeList population = doc.getElementsByTagName("population");
        NodeList density = doc.getElementsByTagName("density");
        NodeList area = doc.getElementsByTagName("area");
        NodeList fertility = doc.getElementsByTagName("fertility");
        NodeList age = doc.getElementsByTagName("age");
        NodeList urban = doc.getElementsByTagName("urban");
        NodeList share = doc.getElementsByTagName("share");
        
        row[0] = country.item(0).getTextContent();
        row[1] = population.item(0).getTextContent();
        row[2] = density.item(0).getTextContent();
        row[3] = area.item(0).getTextContent();
        row[4] = fertility.item(0).getTextContent();
        row[5] = age.item(0).getTextContent();
        row[6] = urban.item(0).getTextContent();
        row[7] = share.item(0).getTextContent();
        
        return row;
    }

    /**
     * Returns the directory where XML files are stored.
     * 
     * @return The directory where XML files are located.
     */
    public File getXmlDirectory() {
        return new File("xml");
    }

    /**
     * Generates an XML content string from the provided row of data.
     * 
     * @param row The array of data to be converted to XML format.
     * @return The generated XML content as a string.
     * @throws IllegalArgumentException If the row has insufficient data.
     */
    public String generateXmlContent(String[] row) {
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
