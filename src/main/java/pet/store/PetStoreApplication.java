package pet.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//This notes to JPA that this is a Spring Boot application
@SpringBootApplication
public class PetStoreApplication {

	public static void main(String[] args) {
		//starts Spring Boot
		SpringApplication.run(PetStoreApplication.class, args);

	}

}
