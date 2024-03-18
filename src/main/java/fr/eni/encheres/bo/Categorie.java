package fr.eni.encheres.bo;
/*
 * class objet de 
 * Un ....
 * 
 * */
public class Categorie {
	private int idCategorie;
	private String libelle;
	

	public Categorie(int idCategorie, String libelle) {
		super();
		this.idCategorie = idCategorie;
		this.libelle = libelle;
	}
	public int getIdCategorie() {
		return idCategorie;
	}
	public void setIdCategorie(int idCategorie) {
		this.idCategorie = idCategorie;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	
	
	

}
