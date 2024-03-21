package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
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
	public void creerArticle(Article article){
		//Vérification des informations saisies
		BusinessException be = new BusinessException();
		boolean isValid = true;
		int id_article = article.getIdArticle();
		String nom_article = article.getNom();
		String description = article.getDescription();
		Date debut = article.getDebut();
		Date fin = article.getFin();
		int prix_intial = article.getMiseAprix();
		int prix_vente = article.getPrixVente();
		Utilisateur vendeur = article.getVendeur();
	    Categorie categorie = article.getCategorie();
		
	/*	if(isValid) {
			articleDAO.ajoutArticle(null, null, null, null, 0, 0, null, null)
		}*/
		
		
	}
}
