package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Enchere;

public interface EnchereService {

	Enchere consulterBestEnchereByIdArticle(int no_article);

	void creerEnchere(Enchere enchere);

	void deleteBestEnchere(int no_article, int i);

}
