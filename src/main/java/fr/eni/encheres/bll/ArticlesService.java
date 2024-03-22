package fr.eni.encheres.bll;

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
}
