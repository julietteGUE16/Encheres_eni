package fr.eni.encheres.dal;

import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;

@Repository
public class UtilisateurDAOImpl implements UtilisateurDAO {

	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public UtilisateurDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	private final String GET_USER_BY_ID = "SELECT * FROM utilisateurs where no_utilisateur = ?";
	
	private final String UPDATE_USER = "UPDATE utilisateurs SET pseudo = ?, nom = ?, prenom = ?, email = ?, telephone = ?,"
			+ "rue = ?, code_postal = ?, ville = ?, mot_de_passe = ? , credit = ? WHERE no_utilisateur = ?";
	
	private final String GET_USER_PASSWORD_BY_ID  = "SELECT mot_de_passe FROM utilisateurs where no_utilisateur = ?";
	
	private final String FIND_USER_BY_PSEUDO = "select * from utilisateurs where pseudo = ?";
	
	private final String PSEUSO_EXIST = "SELECT COUNT(*) FROM UTILISATEURS WHERE pseudo = ?";
	
	private final String SAVE_USER = "INSERT INTO UTILISATEURS (pseudo, nom, prenom, email, telephone, rue, code_postal, ville, mot_de_passe, credit, administrateur) "
			+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	private final String MODIFIER_EMAIL_EXIST = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ? AND no_utilisateur <> ?";
	
	private final String EMAIL_EXIST = "SELECT COUNT(*) FROM UTILISATEURS WHERE email = ?";
	
	private final String FIND_EMAIL_BY_PSEUDO = "select * from utilisateurs where email = ?";
	
	private final String FIND_EMAIL = "SELECT COUNT(*) FROM utilisateurs WHERE email = ?";
	
	@Override
	public Optional<Utilisateur> getUserById(int id) throws UserNotFound   {
		Utilisateur user = null;
		Optional<Utilisateur> optUser = null;
		try {
			user = jdbcTemplate.queryForObject(GET_USER_BY_ID, new BeanPropertyRowMapper<>(Utilisateur.class), id);
			optUser = Optional.of(user);
		} catch (EmptyResultDataAccessException exc) {
			optUser = Optional.empty();
		}
		return optUser;
	}

	@Override
	public void updateUser(Utilisateur user) {
		jdbcTemplate.update(UPDATE_USER, user.getPseudo(), user.getNom(), user.getPrenom(), user.getEmail(),
				user.getTelephone(), user.getRue(), user.getCodePostal(), user.getVille(), user.getMotDePasse(),
				user.getCredit(), user.getNoUtilisateur());

	}

	@Override
	public String getUserPasswordById(int id) {
		return jdbcTemplate.queryForObject(GET_USER_PASSWORD_BY_ID, String.class, id);
	}

	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		Utilisateur user = null;
		Optional<Utilisateur> optUser = null;
		try {
			user = jdbcTemplate.queryForObject(FIND_USER_BY_PSEUDO, new BeanPropertyRowMapper<>(Utilisateur.class), pseudo);

			optUser = Optional.of(user);
		} catch (EmptyResultDataAccessException exc) {
			optUser = Optional.empty();
		}
		return optUser;
	}

	@Override
	public void deleteUser(int idUser) {
		Utilisateur user = new Utilisateur(idUser, "utilisateur supprimé", "nom","prenom","email", "0", "rue", "codePostal", "ville", "password", 0, false);
		this.updateUser(user);
	}

	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		int count = jdbcTemplate.queryForObject(PSEUSO_EXIST, Integer.class, pseudo);
		return count > 0;
	}

	@Override
	public void save(Utilisateur utilisateur) {
		jdbcTemplate.update(SAVE_USER, utilisateur.getPseudo(), utilisateur.getNom(), utilisateur.getPrenom(),
				utilisateur.getEmail(), utilisateur.getTelephone(), utilisateur.getRue(), utilisateur.getCodePostal(),
				utilisateur.getVille(), utilisateur.getMotDePasse(), utilisateur.getCredit(),
				utilisateur.isAdministrateur());
	}

	@Override
	public boolean emailExisteDeja(String email, int id) {
		if (id != -1) {
			int count = jdbcTemplate.queryForObject(MODIFIER_EMAIL_EXIST, Integer.class, email, id);
			return count > 0;
		} else {
			int count = jdbcTemplate.queryForObject(EMAIL_EXIST, Integer.class, email);
			return count > 0;
		}
	}

	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		return jdbcTemplate.queryForObject(FIND_EMAIL_BY_PSEUDO, new BeanPropertyRowMapper<>(Utilisateur.class), email);
	}

	@Override
	public int findEmail(String email) {
		return jdbcTemplate.queryForObject(FIND_EMAIL, Integer.class, email);
	}

}
