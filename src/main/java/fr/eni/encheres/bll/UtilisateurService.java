package fr.eni.encheres.bll;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;
import jakarta.validation.Valid;

public interface UtilisateurService {
	
	public abstract Optional<Utilisateur> getUserById(int id);

	public abstract void updateUser(@Valid Utilisateur user); 
	
	public abstract String getUserPasswordById(int id);


}
