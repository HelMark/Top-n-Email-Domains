package markus.TopEmailDomains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class InputReader {

    private HashMap<String, Integer> domains = new HashMap<String, Integer>();
    private static String fileLocation = "input.txt";

    /**
     * Private constructor to prevent instantiation.
     */
    public InputReader() {
    }

    /**
     * Sets the file loacation for EmailDomainCounter.
     * 
     * @param fileLocation the file location.
     */
    public static void setFileLocation(String fileLocation) {
        InputReader.fileLocation = fileLocation;
    }

    /**
     * Gets the file location for InputReader class.
     * 
     * @return the file location.
     */
    public static String getFileLocation() {
        return fileLocation;
    }

    /**
     * Reads the file and counts the domains.
     * 
     * @return the domains HashMap.
     */
    public HashMap<String, Integer> readDomains() {
        try {
            File file = new File(fileLocation);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                String domain = line.substring(line.indexOf("@") + 1);
                domains.put(domain, domains.getOrDefault(domain, 0) + 1);
            }
            br.close();

            return domains;
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Error reading the file!");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Sorts the domains HashMap by value. If value is equal it sorts the strings in
     * descending alphabetical order.
     * 
     * @return the sorted domains HashMap.
     */
    public HashMap<String, Integer> sortDomains() {
        this.readDomains();

        HashMap<String, Integer> sortedDomains = domains.entrySet()
                .stream()
                .sorted((i1, i2) -> {
                    int compareValue = i2.getValue().compareTo(i1.getValue());
                    if (compareValue != 0) {
                        return compareValue;
                    } else {
                        return i1.getKey().compareTo(i2.getKey());
                    }
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));
        return sortedDomains;
    }
}
