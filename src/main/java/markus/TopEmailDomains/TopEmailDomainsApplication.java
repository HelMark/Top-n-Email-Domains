package markus.TopEmailDomains;

import java.util.HashMap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TopEmailDomainsApplication {
	public static void main(String[] args) {
		SpringApplication.run(TopEmailDomainsApplication.class, args);
		
		
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
