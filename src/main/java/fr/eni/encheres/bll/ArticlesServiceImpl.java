package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.exceptions.BusinessException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
@Primary
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
	public int creerArticle(Article article){
		return articleDAO.ajoutArticle(article);		
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

	@Override
	public List<Article> consulterArticlesByIdVendeur(int idVendeur) {
		return articleDAO.findAllByIdVendeur(idVendeur);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteNonDebutee(int idVendeur) {
		return articleDAO.findAllByIdVenteNonDebutee(idVendeur);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteTerminee(int idVendeur) {
		return articleDAO.findAllByIdVenteTerminee(idVendeur);
	}
	
	@Override
	public List<Article> consulterArticlesByIdVendeurAndNomArticle(int idVendeur, String mot) {
		return articleDAO.findAllByIdVendeurAndNomArticle(idVendeur, mot);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteNonDebuteeAndNomArticle(int idVendeur, String mot) {
		return articleDAO.findAllByIdVenteNonDebuteeAndNomArticle(idVendeur, mot);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteTermineeAndNomArticle(int idVendeur, String mot) {
		return articleDAO.findAllByIdVenteTermineeAndNomArticle(idVendeur, mot);
	}

	@Override
	public List<Article> consulterArticlesByIdVendeurAndCategorie(int idVendeur, int idCategorie) {
		return articleDAO.findAllByIdVendeurAndCategorie(idVendeur, idCategorie);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteNonDebuteeAndCategorie(int idVendeur, int idCategorie) {
		return articleDAO.findAllByIdVenteNonDebuteeAndCategorie(idVendeur, idCategorie);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteTermineeAndCategorie(int idVendeur, int idCategorie) {
		return articleDAO.findAllByIdVenteTermineeAndCategorie(idVendeur, idCategorie);
	}

	@Override
	public List<Article> consulterArticlesByIdVendeurAndCategorieAndNomArticle(int idVendeur, int id, String mot) {
		return articleDAO.findAllByIdVendeurAndCategorieAndNomArticle(idVendeur, id, mot);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteNonDebuteeAndCategorieAndNomArticle(int idVendeur, int idCategorie,
			String mot) {
		return articleDAO.findAllByIdVenteNonDebuteeAndCategorieAndNomArticle(idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> ConsulterArticlesByIdVenteTermineeAndCategorieAndNomArticle(int idVendeur, int idCategorie,
			String mot) {
		return articleDAO.findAllByIdVenteTermineeAndCategorieAndNomArticle(idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> consulterArticlesEnModeConnecte(int idUser) {
		return articleDAO.findArticlesEnModeConnecte(idUser);
	}

	@Override
	public List<Article> consulterArticlesConnecteByCategorie(int idUser, int idCategorie) {
		return articleDAO.findAllConnecteByCategorie(idUser, idCategorie);
	}

	@Override
	public List<Article> consulterArticlesConnecteByNomArticle(int idUser, String nomArticle) {
		return articleDAO.findAllConnecteByNomArticle(idUser, nomArticle);
	}

	@Override
	public List<Article> consulterArticlesConnecteByNomArticleAndCategory(int idUser, String nomArticle,
			int idCategorie) {
		return articleDAO.findAllConnecteByNomArticleAndCategory(idUser, nomArticle, idCategorie);
	}
	
	
}
