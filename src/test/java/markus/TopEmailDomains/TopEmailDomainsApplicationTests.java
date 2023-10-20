package markus.TopEmailDomains;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TopEmailDomainsApplicationTests {

	/**
	 * The domainsDataSet is the top 25 email domains on the web, 04.June 2016 by this
	 * sorce https://email-verify.my-addr.com/list-of-most-popular-email-domains.php
	 * 
	 * For the fun of it with statistics we generate a random testInput.txt file with n emails for testing.
	 * domainsProhability = {0.1774, 0.1734, 0.1553, 0.0320, 0.0127, 0.0124, 0.0109, 0.0098, 0.0090, 0.0083, 0.0076, 0.0073, 
	 * 						 0.0069, 0.0060, 0.0056, 0.0051, 0.0051, 0.0044, 0.0043, 0.0042, 0.0041, 0.0038, 0.0034, 0.0033}; 
	 * 						 sorce https://email-verify.my-addr.com/list-of-most-popular-email-domains.php.
	 * 
	 * Using java we then normalize the prohabilities and find the cumulative prohabilities.
	 * 		public static void main(String[] args) {
				double[] domainsProhability = {0.1774, 0.1734, 0.1553, 0.0320, 0.0127, 0.0124, 0.0109, 0.0098, 0.0090, 0.0083,
											   0.0076, 0.0073, 0.0069, 0.0060, 0.0056, 0.0051, 0.0051, 0.0044, 0.0043, 0.0042, 0.0041,
											   0.0038, 0.0034, 0.0033};
		
				double sum = 0;
				for (double prohability : domainsProhability) {
					sum += prohability;
				}
				for (int i = 0; i < domainsProhability.length; i++) {
					domainsProhability[i] = domainsProhability[i] / sum;
				}
				System.out.println(Arrays.toString(domainsProhability));

				double[] cumulativeProhabilities = new double[domainsProhability.length];
				double sum = 0;
				for (int i = 0; i < domainsProhability.length; i++) {
					sum += domainsProhability[i];
					cumulativeProhabilities[i] = sum;
				}
				System.out.println(Arrays.toString(cumulativeProhabilities));
	 */

	private static String testFileLocation = "testInput.txt";
	private static String[] domainsDataSet = {"gmail.com", "yahoo.com", "hotmail.com", "alo.com", "hotmail.co.uk", "hotmail.fr", "msn.com",
											  "yahoo.fr", "wanadoo.fr", "orange.fr", "comcast.net", "yahoo.co.uk", "yahoo.com.br", "yahoo.co.in",
											  "live.com", "rediffmail.com", "free.fr", "gmx.de", "web.de", "yandex.ru", "ymail.com", "libero.it",
											  "outlook.com", "uol.com.br", "bol.com.br"};
	private static double[] cumulativeProhabilities = {0.26387029599881007, 0.5217908671723932, 0.752788933511825, 0.8003867321136396,
													   0.8192771084337348, 0.837721255391938, 0.8539342555406811, 0.8685110813624869,
													   0.8818979622192472, 0.8942436412315928, 0.9055481183995239, 0.9164063662055628,
													   0.9266696415290792, 0.9355942287669194, 0.943923843522237, 0.9515097426744012,
													   0.9590956418265655, 0.965640339134315, 0.9720362933214338, 0.978283504387922,
													   0.9843819723337796, 0.990034210917745, 0.9950914770191879, 1.0};
	private static String dummyName = "dummy.Name";
	private static HashMap<String, Integer> generatedDomains = new HashMap<String, Integer>();
	
	/**
	 * Generates a random domain based on the cumulative prohabilities.
	 * @return a random domain form doainDataSet.
	 */
	private static String generateRandomDomain() {
		double randomNumber = Math.random();
		for (int i = 0; i < cumulativeProhabilities.length; i++) {
			if (randomNumber <= cumulativeProhabilities[i]) {
				generatedDomains.put(domainsDataSet[i], generatedDomains.getOrDefault(domainsDataSet[i], 0) + 1);
				return domainsDataSet[i];
			}
		}
		return null;
	}

	/**
	 * Generates a testInput.txt file with n emails.
	 * @param fileLocation location of the file.
	 * @param inputSize number of emails.
	 */
	public static void generateInput(String fileLocation, int inputSize) {
		generatedDomains = new HashMap<String, Integer>();

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
	 * Deletes the testInput.txt file.
	 */
	private static void deleteTestInput() {
		try {
			if ((new File(testFileLocation)).exists()) {
				Files.delete(Paths.get(testFileLocation));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Sets up the test environment.
	 */
	@BeforeEach
	public void setUp() {
		InputReader.setFileLocation(testFileLocation);
		InputReader.readDomains();
		generateInput(testFileLocation, 200);
	}

	/**
	 * Tests the setFileLocation method.
	 */
	@Test
	public void testSetFileLocation() {
		InputReader.setFileLocation(testFileLocation);
		assertTrue(InputReader.getFileLocation().equals(testFileLocation));
	}

	/**
	 * Tests if the InputReader class reads and putputs correctly.
	 */
	@Test
	public void testInputReader() {
		HashMap<String, Integer> domains = InputReader.getDomains();

		for (String domain : domainsDataSet) {
			if(domains.get(domain) != null && generatedDomains.get(domain) != null) {
				assertTrue(domains.get(domain).equals(generatedDomains.get(domain)));
			}
		}
	}

	/**
	 * Tests if the InputReader class sorts correctly.
	 */
	@Test
	public void testSortDomains() {
		HashMap<String, Integer> sortedDomains = InputReader.sortDomains();
		
		List<Integer> l = sortedDomains.values().stream().toList();
		for (int i = 0; i < l.size() - 1; i++) {
			assertTrue(l.get(i) >= l.get(i + 1));
		}
	}

	/**
	 * Tears down the test environment.
	 */
	@AfterEach
	public void tearDown() {
		deleteTestInput();
	}
	
}
