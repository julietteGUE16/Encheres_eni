package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Retrait;

public interface RetraitService {
	void creerRetrait(Retrait retrait);
	void modifierRetrait(Retrait retrait);
	void deleteByArticleId(int no_article);
}
