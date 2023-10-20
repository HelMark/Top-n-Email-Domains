package markus.TopEmailDomains;

import org.springframework.boot.test.context.TestConfiguration;


@TestConfiguration(proxyBeanMethods = false)
public class generateInputData {
	
	/**
	 * Main method for running the tests.
	 * @param args 
	 */
	public static void main(String[] args) {
		TopEmailDomainsApplicationTests.generateInput("input.txt", 200);
	}

}
