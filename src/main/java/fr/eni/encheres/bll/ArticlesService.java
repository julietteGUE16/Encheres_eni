package fr.eni.encheres.bll;

import java.util.Collection;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;

public interface ArticlesService {
	List<Article> consulterArticles();
	List<Article> consulterArticlesEnModeConnecte(int idUser);
	
	int creerArticle(Article article);

	List<Article> consulterArticlesByCategorie(int idCategorie);
	List<Article> consulterArticlesByNomArticle(String nomArticle);
	List<Article> consulterArticlesByNomArticleAndCategorie(String nomArticle, int idCategorie);
	
	List<Article> consulterArticlesConnecteByCategorie(int idUser, int idCategorie);
	List<Article> consulterArticlesConnecteByNomArticle(int idUser, String nomArticle);
	List<Article> consulterArticlesConnecteByNomArticleAndCategory(int idUser, String nomArticle, int idCategorie);
	
	Article consulterArticleByIdArticle(int idArticle);

	Retrait consulterRetraitByIDArticle(int no_article);
	
	List<Article> consulterArticlesByIdVendeur(int idVendeur);
	List<Article> ConsulterArticlesByIdVenteNonDebutee(int idVendeur);
	List<Article> ConsulterArticlesByIdVenteTerminee(int idVendeur);

	List<Article> consulterArticlesByIdVendeurAndNomArticle(int idVendeur, String mot);
	List<Article> ConsulterArticlesByIdVenteNonDebuteeAndNomArticle(int idVendeur, String mot);
	List<Article> ConsulterArticlesByIdVenteTermineeAndNomArticle(int idVendeur, String mot);

	List<Article> consulterArticlesByIdVendeurAndCategorie(int idVendeur, int idCategorie);
	List<Article> ConsulterArticlesByIdVenteNonDebuteeAndCategorie(int idVendeur, int idCategorie);
	List<Article> ConsulterArticlesByIdVenteTermineeAndCategorie(int idVendeur, int idCategorie);

	List<Article> consulterArticlesByIdVendeurAndCategorieAndNomArticle(int idVendeur, int id, String mot);
	List<Article> ConsulterArticlesByIdVenteNonDebuteeAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	List<Article> ConsulterArticlesByIdVenteTermineeAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);

}
