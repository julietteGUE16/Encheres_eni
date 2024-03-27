package fr.eni.encheres.dal;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;

public interface UtilisateurRepository {
	
	public abstract Optional<Utilisateur> getUserById(int id);

	public abstract void updateUser(Utilisateur user);

	public abstract String getUserPasswordById(int id);

	public abstract Optional<Utilisateur> findUtilisateurByPseudo(String pseudo);

	public abstract void deleteUser(int idUser);

	public abstract boolean pseudoExisteDeja(String pseudo);

	public abstract void save(Utilisateur utilisateur);

	public abstract boolean emailExisteDeja(String email, int id);

	public abstract Utilisateur findUtilisateurByEmail(String email);

	public abstract int findEmail(String email);
	

}
