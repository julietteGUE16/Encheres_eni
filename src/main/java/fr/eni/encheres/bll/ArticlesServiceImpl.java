package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.ArticleDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticlesServiceImpl implements ArticlesService{
	
	@Autowired
    private ArticleDAO articleDAO;

    public ArticlesServiceImpl(ArticleDAO articleDAO) {
        this.articleDAO = articleDAO;
    }

	@Override
	public List<Article> consulterArticles() {
		return articleDAO.findAll();
	}

	@Override
	public List<Article> consulterArticlesByCategorie(int idCategorie) {
		return articleDAO.findAllByCategorie(idCategorie);
	}

	@Override
	public List<Article> consulterArticlesByNomArticle(String nomArticle) {
		return articleDAO.findAllByNomArticle(nomArticle);
	}

	@Override
	public List<Article> consulterArticlesByNomArticleAndCategorie(String nomArticle, int idCategorie) {
		return articleDAO.findAllByNomArticleAndCategory(nomArticle, idCategorie);
	}

	@Override
	public Article consulterArticleByIdArticle(int idArticle) {
		return articleDAO.findArticleById(idArticle);
	}

	@Override
	public Retrait consulterRetraitByIDArticle(int no_article) {
		return articleDAO.findRetraitById(no_article);
	}
}
