package fr.eni.encheres.bll;

import org.springframework.stereotype.Service;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.dal.EnchereDAO;

@Service
public class EnchereServiceImpl implements EnchereService{
	
	private EnchereDAO encheredao;
	
	public EnchereServiceImpl(EnchereDAO encheredao) {
		this.encheredao = encheredao;
	}

	@Override
	public Enchere consulterBestEnchereByIdArticle(int no_article) {
		return encheredao.findBestEnchereByIdArticle(no_article);
	}

	@Override
	public void creerEnchere(Enchere enchere) {
		encheredao.insertEnchere(enchere);
	}
}
