package fr.eni.encheres.bll;

import java.util.Optional;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurRepositoryImpl;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
	
	private UtilisateurRepositoryImpl utilisateurRepositoryImpl;
	
	public UtilisateurServiceImpl(UtilisateurRepositoryImpl utilisateurRepositoryImpl) {
		this.utilisateurRepositoryImpl = utilisateurRepositoryImpl;
	}
	
	@Override
	public Optional<Utilisateur> getUserById(int id) {
		return utilisateurRepositoryImpl.getUserById(id);
	}
	


}
