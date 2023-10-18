package markus.TopEmailDomains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestTopEmailDomainsApplication {

	public static void main(String[] args) {
		SpringApplication.from(TopEmailDomainsApplication::main).with(TestTopEmailDomainsApplication.class).run(args);
	}

}
