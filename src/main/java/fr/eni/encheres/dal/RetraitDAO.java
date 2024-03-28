package fr.eni.encheres.dal;

import fr.eni.encheres.bo.Retrait;

public interface RetraitDAO {
	
	void ajoutRetrait(Retrait retrait);

	void deleteByArticleId(int no_article);

	void modifierRetrait(Retrait retrait);
}
