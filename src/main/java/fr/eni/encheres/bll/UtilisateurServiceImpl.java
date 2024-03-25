package fr.eni.encheres.bll;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepository;
import fr.eni.encheres.dal.UtilisateurRepositoryImpl;
import jakarta.validation.Valid;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurRepository utilisateurRepository;
	
	public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
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
	


}
