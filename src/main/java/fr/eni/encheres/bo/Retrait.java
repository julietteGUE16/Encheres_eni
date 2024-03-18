package fr.eni.encheres.bo;


/*
 * class objet du retrait pour un article
 * Un article possède un lieu de retrait
 * 
 * */
public class Retrait {
	private int idRetrait;
	private String rue;
	private String codePostal;
	private String ville;
	
	
	
	public Retrait(int idRetrait, String rue, String codePostal, String ville) {
		super();
		this.idRetrait = idRetrait;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
	}
	public int getIdRetrait() {
		return idRetrait;
	}
	public void setIdRetrait(int idRetrait) {
		this.idRetrait = idRetrait;
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
	
	
	

}
