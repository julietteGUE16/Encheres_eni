package fr.eni.encheres.dal;

import java.sql.Date;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;

public interface ArticleDAO {
	List<Article> findAll();
	
	void ajoutArticle(String nom_article, String description, Date date_debut, Date date_fin,
			int prix_initial, int prix_vente, Utilisateur utilisateur, Categorie categorie);
	

	List<Article> findAllByCategorie(int idCategorie);
	
	List<Article> findAllByNomArticle(String nomArticle);
	
	List<Article> findAllByNomArticleAndCategory(String nomArticle, int idCategorie);
	
	Article findArticleById(int idArticle);

	Retrait findRetraitById(int no_article);
}
