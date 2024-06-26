package fr.eni.encheres.bll;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;
import jakarta.validation.Valid;

public interface UtilisateurService {

	public abstract Optional<Utilisateur> getUserById(int id) throws UserNotFound;

	public abstract void updateUser(Utilisateur user);

	public abstract String getUserPasswordById(int id);

	public abstract Optional<Utilisateur> findUtilisateurByPseudo(String username);

	public abstract void deleteUser(int idUser);

	public abstract boolean pseudoExisteDeja(String pseudo);

	public abstract void save(Utilisateur utilisateur);

	public abstract boolean emailExisteDeja(String email, int i);

	public abstract Utilisateur findUtilisateurByEmail(String email);

	public abstract int findEmail(String email);

}
