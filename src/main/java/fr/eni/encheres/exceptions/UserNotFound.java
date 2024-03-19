package fr.eni.encheres.exceptions;

public class UserNotFound extends Exception {
	private int idUser;
	
	public UserNotFound(int idUser) {
		this.idUser = idUser;
	}
	
	@Override
	public String getMessage() {
		
		return "Le user " + idUser + " n'a pas été trouvé.";
	}
}
