package fr.eni.encheres.bo;

import java.sql.Date;
import java.time.LocalDateTime;

/*
 * class objet de 
 * Un ....
 * 
 * */
public class Article {
	private int noArticle;
	private String nom;
	private String description;
	private Date debut;
	private Date fin;
	private int miseAprix;
	private int prixVente;
	private Utilisateur vendeur;
//	private Utilisateur Acheteur;
	private Categorie categorie;
	
	public Article() {
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	public String toString() {
		return "Article [noArticle=" + noArticle + ", nom=" + nom + ", description=" + description + ", debut=" + debut
				+ ", fin=" + fin + ", miseAprix=" + miseAprix + ", prixVente=" + prixVente + ", vendeur=" + vendeur
				+ ", categorie=" + categorie + "]";
	}


	public Article(int noArticle, String nom, String description, Date debut, Date fin, int miseAprix, int prixVente,
		Utilisateur vendeur, Categorie categorie) {
	super();
	this.noArticle = noArticle;
	this.nom = nom;
	this.description = description;
	this.debut = debut;
	this.fin = fin;
	this.miseAprix = miseAprix;
	this.prixVente = prixVente;
	this.vendeur = vendeur;
	this.categorie = categorie;
}
	public int getNoArticle() {
		return noArticle;
	}
	public void setNoArticle(int noArticle) {
		this.noArticle = noArticle;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDebut() {
		return debut;
	}
	public void setDebut(Date debut) {
		this.debut = debut;
	}
	public Date getFin() {
		return fin;
	}
	public void setFin(Date fin) {
		this.fin = fin;
	}
	public int getMiseAprix() {
		return miseAprix;
	}
	public void setMiseAprix(int miseAprix) {
		this.miseAprix = miseAprix;
	}
	public int getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(int prixVente) {
		this.prixVente = prixVente;
	}
	public Utilisateur getVendeur() {
		return vendeur;
	}
	public void setVendeur(Utilisateur vendeur) {
		this.vendeur = vendeur;
	}
	public Categorie getCategorie() {
		return categorie;
	}
	public void setCategorie(Categorie categorie) {
		this.categorie = categorie;
	}
}


