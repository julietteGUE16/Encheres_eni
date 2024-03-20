package fr.eni.encheres;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;

@SpringBootApplication
public class EncheresEniApplication {

	public static void main(String[] args) throws DataAccessException {
		SpringApplication.run(EncheresEniApplication.class, args);
	}

	
}
