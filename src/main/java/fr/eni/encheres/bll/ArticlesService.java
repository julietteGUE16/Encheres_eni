package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticlesService {
	List<Article> consulterArticles();
	
	void creerArticle(Article article);
}
