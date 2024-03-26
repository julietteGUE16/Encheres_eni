package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;

/*
 * class objet du retrait pour un article
 * Un article possède un lieu de retrait
 * 
 * */
public class Retrait {
	private int noRetrait;
	
	@NotBlank(message = "La rue doit être saisie")
	private String rue;
	
	@NotBlank(message = "Le code postal doit être saisi")
	private String codePostal;
	
	@NotBlank(message = "La ville doit être saisie")
	private String ville;
	
	public Retrait() {
		
	}	
	
	public Retrait(int noRetrait, String rue, String codePostal, String ville) {
		this.noRetrait = noRetrait;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	public Retrait( String rue, String codePostal, String ville) {
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	
	public int getNoRetrait() {
		return noRetrait;
	}
	public void setNoRetrait(int noRetrait) {
		this.noRetrait = noRetrait;
	}
	public String getRue() {
		return rue;
	}
	public void setRue(String rue) {
		this.rue = rue;
	}
	public String getCodePostal() {
		return codePostal;
	}
	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}

	@Override
	public String toString() {
		return "Retrait [noRetrait=" + noRetrait + ", rue=" + rue + ", codePostal=" + codePostal + ", ville=" + ville
				+ "]";
	}
	
	
	

}
