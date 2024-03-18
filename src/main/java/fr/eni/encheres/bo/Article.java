package fr.eni.encheres.bo;

import java.time.LocalDateTime;

/*
 * class objet de 
 * Un ....
 * 
 * */
public class Article {
	private int idArticle;
	private String nom;
	private String description;
	private LocalDateTime debut;
	private LocalDateTime fin;
	private int miseAprix;
	private int prixVente;
	private Utilisateur vendeur;
	private Utilisateur Acheteur;
	private Categorie categorie;

}
