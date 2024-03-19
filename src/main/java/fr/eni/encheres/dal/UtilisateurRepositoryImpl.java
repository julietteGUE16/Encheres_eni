package fr.eni.encheres.dal;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.Utilisateur;

@Repository
public class UtilisateurRepositoryImpl implements UtilisateurRepository {
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	
	public UtilisateurRepositoryImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = namedParameterJdbcTemplate.getJdbcTemplate();
	}

	@Override
	public Optional<Utilisateur> getUserById(int id) {
		Utilisateur user = null;
		Optional<Utilisateur> optUser = null;
		String sql = "SELECT * FROM utilisateurs where no_utilisateur = ?";
		
		try {
			user  = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), id);
			optUser = Optional.of(user);
		}catch(EmptyResultDataAccessException exc) {
			optUser = Optional.empty();
		}
		return optUser;
	}
	
	

}
