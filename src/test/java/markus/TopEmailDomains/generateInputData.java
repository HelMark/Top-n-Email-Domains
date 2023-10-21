package markus.TopEmailDomains;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class is used to generate a .txt file with n emails for testing.
 * A main ethod is also added so that a user can also use it to generate
 * a input.txt file with n emails.
 * 
 * 
 * For the fun of it we generate a random representativ .txt file with n emails
 * where we use a domainsDataSet.
 * The domainsDataSet is the top 25 email domains on the web, 04.June 2016 by
 * this sorce :
 * https://email-verify.my-addr.com/list-of-most-popular-email-domains.php
 * Using java we then normalize the probabilities and find the cumulative
 * probabilities.
 */
public class GenerateInputData {

	private static String[] domainsDataSet = { "gmail.com", "yahoo.com", "hotmail.com", "alo.com", "hotmail.co.uk",
			"hotmail.fr", "msn.com", "yahoo.fr", "wanadoo.fr", "orange.fr", "comcast.net", "yahoo.co.uk",
			"yahoo.com.br", "yahoo.co.in", "live.com", "rediffmail.com", "free.fr", "gmx.de", "web.de", "yandex.ru",
			"ymail.com", "libero.it", "outlook.com", "uol.com.br", "bol.com.br" };
	private static double[] domainsProbability = { 0.1774, 0.1734, 0.1553, 0.0320, 0.0127, 0.0124, 0.0109, 0.0098,
			0.0090, 0.0083, 0.0076, 0.0073, 0.0069, 0.0060, 0.0056, 0.0051, 0.0051, 0.0044, 0.0043, 0.0042, 0.0041,
			0.0038, 0.0034, 0.0033 };
	private static String dummyName = "dummy.Name";
	private HashMap<String, Integer> generatedDomains = new HashMap<String, Integer>();

	/**
	 * Normalizes the probabilities and finds the cumulative probabilities.
	 * 
	 * @param domainsProbability the probabilities of the domains.
	 * @return the cumulative probabilities.
	 */
	private double[] findCumulativeProbability(double[] domainsProbability) {
		double sum = 0;
		for (double prohability : domainsProbability) {
			sum += prohability;
		}
		for (int i = 0; i < domainsProbability.length; i++) {
			domainsProbability[i] = domainsProbability[i] / sum;
		}

		double[] cumulativeProbability = new double[domainsProbability.length];
		sum = 0;
		for (int i = 0; i < domainsProbability.length; i++) {
			sum += domainsProbability[i];
			cumulativeProbability[i] = sum;
		}
		return cumulativeProbability;
	}

	/**
	 * Generates a random domain based on the cumulative prohabilities.
	 * 
	 * @return a random domain form domainDataSet.
	 */
	private String generateRandomDomain() {
		double randomNumber = Math.random();
		double[] cumulativeProbability = findCumulativeProbability(domainsProbability);
		for (int i = 0; i < cumulativeProbability.length; i++) {
			if (randomNumber <= cumulativeProbability[i]) {
				generatedDomains.put(domainsDataSet[i], generatedDomains.getOrDefault(domainsDataSet[i], 0) + 1);
				return domainsDataSet[i];
			}
		}
		return null;
	}

	/**
	 * Generates a testInput.txt file with n emails.
	 * 
	 * @param fileLocation location of the file.
	 * @param inputSize    number of emails.
	 */
	public void generateInput(String fileLocation, int inputSize) {
		List<String> input = new ArrayList<String>();
		for (int i = 0; i < inputSize; i++) {
			input.add(dummyName + i + "@" + generateRandomDomain());
		}
		try (Writer file = new FileWriter(fileLocation, StandardCharsets.UTF_8)) {
			for (String line : input) {
				file.write(line + "\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Deletes a file.
	 * 
	 * @param fileLocation location of the file.
	 */
	public static void deleteFile(String fileLocation) {
		try {
			if ((new File(fileLocation)).exists()) {
				Files.delete(Paths.get(fileLocation));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns the domainsDataSet.
	 * 
	 * @return the domainsDataSet.
	 */
	public static String[] getDomainsDataSet() {
		return domainsDataSet;
	}

	/**
	 * Returns the generatedDomains.
	 * 
	 * @return the generatedDomains.
	 */
	public HashMap<String, Integer> getGeneratedDomains() {
		return generatedDomains;
	}

	/**
	 * Main method for generating a testInput.txt file with n emails.
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		GenerateInputData gip = new GenerateInputData();
		gip.generateInput("input.txt", 200);
	}
}