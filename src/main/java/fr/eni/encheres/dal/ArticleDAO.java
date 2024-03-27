package fr.eni.encheres.dal;

import java.sql.Date;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;

public interface ArticleDAO {
	List<Article> findAll();
	
	int ajoutArticle(Article article);
	
	List<Article> findAllByCategorie(int idCategorie);
	List<Article> findAllByNomArticle(String nomArticle);
	List<Article> findAllByNomArticleAndCategory(String nomArticle, int idCategorie);

	Article findArticleById(int idArticle);

	Retrait findRetraitById(int no_article);
	
	List<Article> findAllByIdVendeur(int idVendeur);
	List<Article> findAllByIdVenteNonDebutee(int idVendeur);
	List<Article> findAllByIdVenteTerminee(int idVendeur);
	
	List<Article> findAllByIdVendeurAndNomArticle(int idVendeur, String mot);
	List<Article> findAllByIdVenteNonDebuteeAndNomArticle(int idVendeur, String mot);
	List<Article> findAllByIdVenteTermineeAndNomArticle(int idVendeur, String mot);
	
	List<Article> findAllByIdVendeurAndCategorie(int idVendeur, int idCategorie);
	List<Article> findAllByIdVenteNonDebuteeAndCategorie(int idVendeur, int idCategorie);
	List<Article> findAllByIdVenteTermineeAndCategorie(int idVendeur, int idCategorie);
	
	List<Article> findAllByIdVendeurAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	List<Article> findAllByIdVenteNonDebuteeAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	List<Article> findAllByIdVenteTermineeAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	
	List<Article> findAllArticlesByIdVendeurAyantEncheriAndCategorieAndNomArticle(int idVendeur, int id, String mot);
	List<Article> findAllArticlesByIdVendeurAyantEncheriAndCategorie(int idVendeur, int id);
	List<Article> findAllArticlesByIdVendeurAyantEncheriAndRecherche(int idVendeur, String mot);
	List<Article> findAllArticlesByIdVendeurAyantEncheri(int idVendeur);
	
	List<Article> findAllArticlesByIdVendeurAyantRemporteAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	List<Article> findAllArticlesByIdVendeurAyantRemporteAndCategorie(int idVendeur, int idCategorie);
	List<Article> findAllArticlesByIdVendeurAyantRemporteAndRecherche(int idVendeur, String mot);
	List<Article> findAllArticlesByIdVendeurAyantRemporte(int idVendeur);
	void changerPrixVente(int noArticle, int nouvelleEnchereNumber);
	int getPrixVente(int no_article);

	void deleteArticleById(int no_article);
}
