package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;

public interface ArticleDAO {
	List<Article> findAll();

	List<Article> findAllByCategorie(int idCategorie);
	
	List<Article> findAllByNomArticle(String nomArticle);
	
	List<Article> findAllByNomArticleAndCategory(String nomArticle, int idCategorie);
	
	Article findArticleById(int idArticle);

	Retrait findRetraitById(int no_article);
	
}
