package fr.eni.encheres.bll;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurService {
	
	public abstract Optional<Utilisateur> getUserById(int id); 

}
