package fr.eni.encheres.dal;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurRepository {
	
	public abstract Optional<Utilisateur> getUserById(int id);
	

}