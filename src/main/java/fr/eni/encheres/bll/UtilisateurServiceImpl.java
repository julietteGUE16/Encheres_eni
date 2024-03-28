package fr.eni.encheres.bll;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dal.UtilisateurDAOImpl;
import jakarta.validation.Valid;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurDAO utilisateurRepository;
	
	public UtilisateurServiceImpl(UtilisateurDAO utilisateurRepository) {
		this.utilisateurRepository = utilisateurRepository;
	}
	
	@Override
	public Optional<Utilisateur> getUserById(int id) {
		return utilisateurRepository.getUserById(id);
	}

	@Override
	public void updateUser(Utilisateur user) {
		utilisateurRepository.updateUser(user);
		
	}

	@Override
	public String getUserPasswordById(int id) {
		return	utilisateurRepository.getUserPasswordById(id);
	}

	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		return utilisateurRepository.findUtilisateurByPseudo(pseudo);
	}

	@Override
	public void deleteUser(int idUser) {
		utilisateurRepository.deleteUser(idUser);
		
	}

	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		return utilisateurRepository.pseudoExisteDeja(pseudo);
	}

	@Override
	public void save(Utilisateur utilisateur) {
		utilisateurRepository.save(utilisateur);
		
	}

	@Override
	public boolean emailExisteDeja(String email, int id) {
		return utilisateurRepository.emailExisteDeja(email, id);
	}

	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		return utilisateurRepository.findUtilisateurByEmail(email);
	}

	@Override
	public int findEmail(String email) {
		return utilisateurRepository.findEmail(email);
	}
	


}
