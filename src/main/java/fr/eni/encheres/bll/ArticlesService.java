package fr.eni.encheres.bll;

import java.util.Collection;
import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;

public interface ArticlesService {
	List<Article> consulterArticles();
	
	void creerArticle(Article article);

	List<Article> consulterArticlesByCategorie(int idCategorie);
	List<Article> consulterArticlesByNomArticle(String nomArticle);
	List<Article> consulterArticlesByNomArticleAndCategorie(String nomArticle, int idCategorie);
	
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
	
	List<Article> consulterArticlesByIdVendeurAyantEncheriAndCategorieAndNomArticle(int idVendeur, int id, String mot);
	List<Article> consulterArticlesByIdVendeurAyantEncheriAndCategorie(int idVendeur, int id);
	List<Article> consulterArticlesByIdVendeurAyantEncheriAndRecherche(int idVendeur, String mot);
	List<Article> consulterArticlesByIdVendeurAyantEncheri(int idVendeur);
	
	List<Article> ConsulterArticlesByIdVendeurAyantRemporteAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot);
	List<Article> ConsulterArticlesByIdVendeurAyantRemporteAndCategorie(int idVendeur, int idCategorie);
	List<Article> ConsulterArticlesByIdVendeurAyantRemporteAndRecherche(int idVendeur, String mot);
	List<Article> ConsulterArticlesByIdVendeurAyantRemporte(int idVendeur);

}
