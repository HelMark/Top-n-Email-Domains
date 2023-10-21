package markus.TopEmailDomains;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopEmailDomainsApplication {

	/**
	 * Main method of the application. Runs the program and outputs in the terminal
	 * all domains - nuber of pairs, sorted!
	 * 
	 * @param args not used.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TopEmailDomainsApplication.class, args);

		/**
		 * Printing out all the domains in descending order with number of occurrences
		 * inside the terminal.
		 * With print statements, I have tried to output it somewhat readable.
		 */
		System.out.println("------------------------------------------------------------------");
		InputReader inputReader = new InputReader();

		HashMap<String, Integer> domains = inputReader.sortDomains();
		System.out.println("Domains in descending order (Domain : Number of occurrences):");
		int i = 0;
		for (String domain : domains.keySet()) {
			i++;
			System.out.println(" " + i + " " + domain + " : " + domains.get(domain));
		}

		System.out.println("------------------------------------------------------------------");
	}
}
