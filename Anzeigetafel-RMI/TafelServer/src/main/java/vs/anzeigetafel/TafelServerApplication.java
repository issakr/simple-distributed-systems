package vs.anzeigetafel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Die main Klasse des Servers
 * durch @SpringBootApplication werden @Configuration, @Repository und @Service erkannt.
 */
@SpringBootApplication
public class TafelServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TafelServerApplication.class, args);
		System.out.println("Server Ready");
	}

	
}
