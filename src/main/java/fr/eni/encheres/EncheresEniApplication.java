package fr.eni.encheres;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.dao.DataAccessException;

import fr.eni.encheres.bo.Utilisateur;

@SpringBootApplication
public class EncheresEniApplication {
	
	private static JdbcTemplate jdbcTemplate;

    public EncheresEniApplication(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


	public static void main(String[] args) throws DataAccessException {
		SpringApplication.run(EncheresEniApplication.class, args);
		
		String sql = "SELECT * FROM utilisateurs";
		Utilisateur user = (Utilisateur) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class));
	
	System.out.println("user nom :  "+user.getNom());
	}

}
