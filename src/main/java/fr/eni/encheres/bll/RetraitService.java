package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retrait;

public interface RetraitService {
	Object creerRetrait(Retrait retrait);

	void deleteByArticleId(int no_article);
}
 