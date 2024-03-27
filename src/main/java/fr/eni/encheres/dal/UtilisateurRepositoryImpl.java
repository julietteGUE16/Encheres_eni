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
		System.out.println("mot de passe = "+ user.getMotDePasse());
		 String sql = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?,"
				 		+ "rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? , credit = ? WHERE no_utilisateur = ?";

		    jdbcTemplate.update(sql,user.getPseudo(),user.getNom(),user.getPrenom(),user.getEmail(),user.getTelephone(),
		    		user.getRue(),user.getCodePostal(),user.getVille(),user.getMotDePasse(),user.getCredit(), user.getNoUtilisateur());
		
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

	@Override
	public void deleteUser(int idUser) {
		String sql = "DELETE FROM UTILISATEURS WHERE no_utilisateur = ?";
		jdbcTemplate.update(sql, idUser);
		
	}

	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, pseudo);
        return count > 0;
	}

	@Override
	public void save(Utilisateur utilisateur) {
		   String sql = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
      jdbcTemplate.update(sql, utilisateur.getPseudo(), utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), 
                          utilisateur.getTelephone(), utilisateur.getRue(), utilisateur.getCodePostal(), utilisateur.getVille(), 
                          utilisateur.getMotDePasse(), utilisateur.getCredit(), utilisateur.isAdministrateur());
	}

	@Override
	public boolean emailExisteDeja(String email,int id) {
		System.out.println("l'id = "+id);
		if(id != -1) {
			System.out.println("email = "+email);
			System.out.println("id = "+id);
			 String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ? AND no_utilisateur <> ?";
		        int count = jdbcTemplate.queryForObject(sql, Integer.class, email, id);
		        return count > 0;
		} else {
			String sql = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";
	        int count = jdbcTemplate.queryForObject(sql, Integer.class, email);
	        return count > 0;	
		}
		
	}

	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		
		String sql = "select * from utilisateurs where email = ?";
		
		return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Utilisateur.class), email);
	}

	@Override
	public int findEmail(String email) {
		String sql = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
		return jdbcTemplate.queryForObject(sql, Integer.class, email);
	}

}
