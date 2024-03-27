package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
	Object ajoutRetrait(Retrait retrait);

	void deleteByArticleId(int no_article);
}
