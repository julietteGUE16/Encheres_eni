package fr.eni.encheres.bo;

import java.time.LocalDateTime;

/*
 * class objet de 
 * Un ....
 * 
 * */
public class Enchere {
	private LocalDateTime date;
	private int Montant;
	private Article article;
	private Utilisateur utilisateur;
	
	@Override
	public String toString() {
		return "Enchere [date=" + date + ", Montant=" + Montant + ", article=" + article + ", utilisateur="
				+ utilisateur + "]";
	}
	public Enchere(LocalDateTime date, int montant, Article article, Utilisateur utilisateur) {
		this.date = date;
		Montant = montant;
		this.article = article;
		this.utilisateur = utilisateur;
	}
	public Enchere() {
		// TODO Auto-generated constructor stub
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
	public Article getArticle() {
		return article;
	}
	public void setArticle(Article article) {
		this.article = article;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
}
