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
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), id);
			optUser = Optional.of(user);
		} catch (EmptyResultDataAccessException exc) {
			optUser = Optional.empty();
		}
		return optUser;
	}

	@Override
	public void updateUser(Utilisateur user) {
		 String sql = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?,"
				 		+ "rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? WHERE no_utilisateur = ?";

		    jdbcTemplate.update(sql,user.getPseudo(),user.getNom(),user.getPrenom(),user.getEmail(),user.getTelephone(),
		    		user.getRue(),user.getCodePostal(),user.getVille(),user.getMotDePasse(),user.getNoUtilisateur());
		
	}

	@Override
	public String getUserPasswordById(int id) {
		String sql = "SELECT mot_de_passe FROM utilisateurs where no_utilisateur = ?";
		String password = jdbcTemplate.queryForObject(sql, String.class, id);
		return password;
	}

	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		Utilisateur user = null;
		Optional<Utilisateur> optUser = null;
		
		String sql = "select * from utilisateurs where pseudo = ?";
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), pseudo);
			
			optUser = Optional.of(user);
		}catch(EmptyResultDataAccessException exc) {
			optUser = Optional.empty();
		}
		return optUser;
	}

}
