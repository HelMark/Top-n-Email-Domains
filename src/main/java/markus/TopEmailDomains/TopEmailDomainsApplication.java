package markus.TopEmailDomains;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopEmailDomainsApplication {

	/**
	 * Main method of the application.
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(TopEmailDomainsApplication.class, args);
		
		/**
		 * Printing out all the domains and the number of occurrences, and the top 3 domains inside the terminal.
		 * With print statements, I have tried to output it somewhat readable.
		 */
		System.out.println("------------------------------------------------------------------");
		InputReader.readDomains();
		HashMap<String, Integer> domains = InputReader.getDomains();
		System.out.println("Domains:");
		System.out.println(domains);

		System.out.println("------------------------------------------------------------------");
		HashMap<String, Integer> sortedDomains = InputReader.sortDomains();
		if (sortedDomains.size() < 3) {
			int mapZize = sortedDomains.size();
			System.out.println("Top " + mapZize + ":");
			for (int i = 0; i < mapZize; i++) {
				System.out.println((i + 1) + ". " + sortedDomains.keySet().toArray()[i] + ": " + sortedDomains.values().toArray()[i]);
			}
		} else {
			System.out.println("Top 3 domains:");
			for (int i = 0; i < 3; i++) {
				System.out.println((i + 1) + ". " + sortedDomains.keySet().toArray()[i] + ": " + sortedDomains.values().toArray()[i]);
			}
		}
		System.out.println("------------------------------------------------------------------");
	}
}
