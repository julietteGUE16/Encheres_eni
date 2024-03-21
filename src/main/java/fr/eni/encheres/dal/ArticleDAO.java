package fr.eni.encheres.dal;

import java.util.List;

import fr.eni.encheres.bo.Article;

public interface ArticleDAO {
	List<Article> findAll();

	List<Article> findAllByCategorie(int idCategorie);
}
