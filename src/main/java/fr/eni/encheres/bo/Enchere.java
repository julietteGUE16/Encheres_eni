package fr.eni.encheres.bo;

import java.time.LocalDateTime;

/*
 * class objet de 
 * Un ....
 * 
 * */
public class Enchere {
	private int idEnchere;
	private LocalDateTime date;
	private int Montant;
	private Article article;
	private Utilisateur utilisateur;
	
	public Enchere(LocalDateTime date, int montant, Article article, Utilisateur utilisateur,int idEnchere) {
		super();
		this.date = date;
		Montant = montant;
		this.article = article;
		this.utilisateur = utilisateur;
		this.idEnchere = idEnchere;
	}
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getMontant() {
		return Montant;
	}
	public void setMontant(int montant) {
		Montant = montant;
	}
	public Article getIdArticle() {
		return article;
	}
	public void setIdArticle(Article article) {
		this.article = article;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public int getIdEnchere() {
		return idEnchere;
	}
	public void setIdEnchere(int idEnchere) {
		this.idEnchere = idEnchere;
	}
	
	
	
	

}
