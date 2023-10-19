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

	private static String testFileLocation = "testInput.txt";
		/* Top 25 email domains and its procentage of occurences on the web by 04.June 2016 by this sorce https://email-verify.my-addr.com/list-of-most-popular-email-domains.php*/
	private static String[] domainsDataSet = {"gmail.com", "yahoo.com", "hotmail.com", "alo.com", "hotmail.co.uk", "hotmail.fr", "msn.com", "yahoo.fr", "wanadoo.fr", "orange.fr", "comcast.net", "yahoo.co.uk", "yahoo.com.br", "yahoo.co.in", "live.com", "rediffmail.com", "free.fr", "gmx.de", "web.de", "yandex.ru", "ymail.com", "libero.it", "outlook.com", "uol.com.br", "bol.com.br"};
	/* domainsProhability = {0.1774, 0.1734, 0.1553, 0.0320, 0.0127, 0.0124, 0.0109, 0.0098, 0.0090, 0.0083, 0.0076, 0.0073, 0.0069, 0.0060, 0.0056, 0.0051, 0.0051, 0.0044, 0.0043, 0.0042, 0.0041, 0.0038, 0.0034, 0.0033}; */
	/* normalizedProhabilities = {0.26387029599881007, 0.2579205711735832, 0.2309980663394318, 0.047597798601814664, 0.018890376320095194, 0.01844414695820318, 0.01621300014874312, 0.014576825821805741, 0.013386880856760373, 0.012345679012345678, 0.011304477167930984, 0.010858247806038971, 0.010263275323516287, 0.00892458723784025, 0.008329614755317566, 0.007585899152164213, 0.007585899152164213, 0.006544697307749517, 0.006395954187118845, 0.006247211066488174, 0.006098467945857505, 0.005652238583965492, 0.005057266101442808, 0.0049085229808121375};
	/* If you are interested how i normoalized and found the cumulativ prohability these presentages se commet below */
	/* 
	public static void main(String[] args) {
		double[] domainsProhability = {0.1774, 0.1734, 0.1553, 0.0320, 0.0127, 0.0124, 0.0109, 0.0098, 0.0090, 0.0083, 0.0076, 0.0073, 0.0069, 0.0060, 0.0056, 0.0051, 0.0051, 0.0044, 0.0043, 0.0042, 0.0041, 0.0038, 0.0034, 0.0033};
		
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
	private static double[] cumulativeProhabilities = {0.26387029599881007, 0.5217908671723932, 0.752788933511825, 0.8003867321136396, 0.8192771084337348, 0.837721255391938, 0.8539342555406811, 0.8685110813624869, 0.8818979622192472, 0.8942436412315928, 0.9055481183995239, 0.9164063662055628, 0.9266696415290792, 0.9355942287669194, 0.943923843522237, 0.9515097426744012, 0.9590956418265655, 0.965640339134315, 0.9720362933214338, 0.978283504387922, 0.9843819723337796, 0.990034210917745, 0.9950914770191879, 1.0};

	private int testInputSize = 200;
	private String dummyName = "dummy.Name";

	private HashMap<String, Integer> generatedDomains = new HashMap<String, Integer>();

	private String generateRandomDomain() {
		double randomNumber = Math.random();
		for (int i = 0; i < cumulativeProhabilities.length; i++) {
			if (randomNumber <= cumulativeProhabilities[i]) {
				generatedDomains.put(domainsDataSet[i], generatedDomains.getOrDefault(domainsDataSet[i], 0) + 1);
				return domainsDataSet[i];
			}
		}
		return null;
	}

	private void generateTestInput() {
		List<String> input = new ArrayList<String>();
		for (int i = 0; i < testInputSize; i++) {
			input.add(dummyName + i + "@" + generateRandomDomain());
		}
		try (Writer file = new FileWriter(testFileLocation, StandardCharsets.UTF_8)) {
			for (String line : input) {
				file.write(line + "\n");
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void deleteTestInput() {
		try {
			if ((new File(testFileLocation)).exists()) {
				Files.delete(Paths.get(testFileLocation));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@BeforeEach
	public void setUp() {
		generateTestInput();
	}

	@Test
	public void testSetFileLocation() {
		InputReader.setFileLocation(testFileLocation);
		assertTrue(InputReader.getFileLocation().equals(testFileLocation));
	}

	@Test
	public void testInputReader() {
		InputReader.setFileLocation(testFileLocation);
		
		InputReader.readDomains();
		HashMap<String, Integer> domains = InputReader.getDomains();

		for (String domain : domainsDataSet) {
			assertTrue(domains.get(domain).equals(generatedDomains.get(domain)));
		}
	}

	@Test
	public void testSortDomains() {
		InputReader.setFileLocation(testFileLocation);
		InputReader.readDomains();
		HashMap<String, Integer> sortedDomains = InputReader.sortDomains();
		
		List<Integer> l = sortedDomains.values().stream().toList();
		for (int i = 0; i < l.size() - 1; i++) {
			assertTrue(l.get(i) >= l.get(i + 1));
		}
	}

	@AfterEach
	public void tearDown() {
		deleteTestInput();
	}
}
