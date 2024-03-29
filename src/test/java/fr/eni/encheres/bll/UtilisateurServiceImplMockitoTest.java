package fr.eni.encheres.bll;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAOImpl;
import fr.eni.encheres.exceptions.UserNotFound;

public class UtilisateurServiceImplMockitoTest {
	

	
	@InjectMocks
	public UtilisateurServiceImpl service;

	@Mock
	UtilisateurDAOImpl utilisateurDAO;
	
	@Test
	@DisplayName("Vérifie que la méthode findParticipantById renvoit une "
			+ "exception quand l'id demande n'est pas connu.")
	public void testFindUtilisateurByIdScenarioErreur() throws UserNotFound {
		// AAA
		when(utilisateurDAO.getUserById(11)).thenReturn(Optional.empty());
		assertThrows(UserNotFound.class, () -> service.getUserById(11));

	}
	
	
	@Test
	@DisplayName("Vérifie que la méthode findParticipantById fonctionne quand l'id demande est connu.")
	public void testFindParticipantByIdScenarioNominal() throws UserNotFound {

		// AAA
		// Arrange = préparer le test
		when(utilisateurDAO.getUserById(12)).thenReturn(Optional.of(new Utilisateur(12, "", "", null, null, null)));

		// Act : appeler la méthode que l'on veut tester
		Optional<Utilisateur> utilisateur = null;
		utilisateur = service.getUserById(12);

		// Assert
		assertNotNull(utilisateur);
		assertEquals(12, utilisateur.get().getNoUtilisateur());

	}

}
