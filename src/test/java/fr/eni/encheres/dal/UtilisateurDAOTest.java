package fr.eni.encheres.dal;

import java.util.Optional;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDAOTest implements UtilisateurDAO{
	
	@Override
	public Optional<Utilisateur> getUserById(int id) {
		Optional<Utilisateur> opt = Optional.empty();
		if(id==12) {
			opt= Optional.of(new Utilisateur(12, "Dupont", "Bob", null, null, null));
		}
		
		return opt;
	}

	@Override
	public void updateUser(Utilisateur user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserPasswordById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void deleteUser(int idUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void save(Utilisateur utilisateur) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean emailExisteDeja(String email, int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findEmail(String email) {
		// TODO Auto-generated method stub
		return 0;
	}

}
