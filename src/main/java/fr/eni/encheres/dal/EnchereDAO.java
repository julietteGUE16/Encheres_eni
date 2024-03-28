package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Enchere;

public interface EnchereDAO {
	
	Enchere findBestEnchereByIdArticle(int no_article);

	void insertEnchere(Enchere enchere);

	void deleteBestEnchere(int no_article, int idUser);

	void deleteAllEnchereByArticleId(int no_article);

	void deleteAllEnchereByArticleIdAndIdUser(int no_article, int idUser);
}
