package fr.eni.encheres.bll;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticlesService {
	List<Article> consulterArticles();

	List<Article> consulterArticlesByCategorie(int idCategorie);
}
