package markus.TopEmailDomains;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;


public class InputReader {

    private static HashMap<String, Integer> domains = new HashMap<String, Integer>();
    private static String fileLocation = "input.txt";

    /** 
     * Private constructor to prevent instantiation. 
     */
    private InputReader() {
    }

    /**
     * Updates the domains HashMap with the domain of the email.
     * 
     * @param email the email to be processed.
     */
    private static void updateDomains(String email) {
        String domain = email.substring(email.indexOf("@") + 1);
        domains.put(domain, domains.getOrDefault(domain, 0) + 1);
    }

    /**
     * Reads the file and updates the domains HashMap.
     */
    public static void readDomains() {
        domains = new HashMap<String, Integer>();
        try{
            File file = new File(fileLocation);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = null;

            while ((line = br.readLine()) != null) {
                InputReader.updateDomains(line);
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Ses the file loacation for EmailDomainCounter.
     * @param fileLocation
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
     * Gets the domains HashMap.
     * 
     * @return the domains HashMap.
     */
    public static HashMap<String, Integer> getDomains() {
        return domains;
    }

    /**
     * Sorts the domains HashMap by value.
     * 
     * @return the sorted domains HashMap.
     */
    public static HashMap<String, Integer> sortDomains() {
        HashMap<String, Integer> sortedDomains = domains.entrySet()
                                                        .stream()
                                                        .sorted((i1, i2) 
                                                            -> i2.getValue().compareTo(i1.getValue()))
                                                        .collect(Collectors.toMap(
                                                            Map.Entry::getKey, 
                                                            Map.Entry::getValue, 
                                                            (e1, e2) -> e1, LinkedHashMap::new));
        return sortedDomains;
    }
}
