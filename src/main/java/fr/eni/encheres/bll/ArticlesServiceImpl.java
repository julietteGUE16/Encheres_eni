package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.dal.ArticleDAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

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
}
