package markus.TopEmailDomains;

import static org.junit.Assert.assertTrue;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class TopEmailDomainsApplicationTests {

	private InputReader ip = new InputReader();

	private static String testFileLocation = "testInput.txt";
	private static String[] domainsDataSet = GenerateInputData.getDomainsDataSet();
	private HashMap<String, Integer> generatedDomains;

	/**
	 * Sets up the test environment.
	 */
	@BeforeEach
	public void setUp() {
		GenerateInputData gip = new GenerateInputData();
		gip.generateInput(testFileLocation, 200);
		generatedDomains = gip.getGeneratedDomains();

		InputReader.setFileLocation(testFileLocation);
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
	 * Tests if the InputReader class reads and outputs correctly.
	 */
	@Test
	public void testInputReader() {
		HashMap<String, Integer> domains = ip.readDomains();

		for (String domain : domainsDataSet) {
			if (domains.get(domain) != null && generatedDomains.get(domain) != null) {
				assertTrue(domains.get(domain).equals(generatedDomains.get(domain)));
			}
		}
	}

	/**
	 * Tests if the InputReader class sorts correctly.
	 */
	@Test
	public void testSortDomains() {
		HashMap<String, Integer> sortedDomains = ip.sortDomains();

		List<Integer> l = sortedDomains.values().stream().toList();
		for (int i = 0; i < l.size() - 1; i++) {
			assertTrue(l.get(i) >= l.get(i + 1));
			if (l.get(i) == l.get(i + 1)) {
				assertTrue(sortedDomains.keySet().toArray()[i].toString()
						.compareTo(sortedDomains.keySet().toArray()[i + 1].toString()) < 0);
			}
		}
	}

	/**
	 * Tears down the test environment.
	 */
	@AfterEach
	public void tearDown() {
		GenerateInputData.deleteFile(testFileLocation);
	}
}
