package fr.eni.encheres.dal;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mapper.ArticleMapper;

@Repository
public class ArticleDAOimpl implements ArticleDAO{
	private final String FIND_ALL = "SELECT "
			+ "    A.no_article, "
		    + "    A.nom_article, "
		    + "    A.description, "
		    + "    A.date_debut_encheres, "
		    + "    A.date_fin_encheres, "
		    + "    A.prix_initial, "
		    + "    A.prix_vente, "
		    + "    C.libelle AS categorie, "
		    + "    U.pseudo AS vendeur_pseudo, "
		    + "    U.nom AS vendeur_nom, "
		    + "    U.prenom AS vendeur_prenom, "
		    + "    U.email AS vendeur_email, "
		    + "    U.telephone AS vendeur_telephone, "
		    + "    R.rue AS adresse_rue, "
		    + "    R.code_postal AS adresse_code_postal, "
		    + "    R.ville AS adresse_ville "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur "
		    + "    LEFT JOIN RETRAITS R ON A.no_article = R.no_article;";

	private final String INSERT_ENCHERE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) "
			+ " VALUES(:nom_article, :description, :date_debut, :date_fin, :prix_initial, :prix_vente, :id_utilisateur, :id_categorie )";
	ArticlesService articleService;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new ArticleMapper());
	}
	
	@Override
	public void ajoutArticle(String nom_article, String description, Date date_debut, Date date_fin, 
			int prix_initial, int prix_vente, Utilisateur utilisateur, Categorie categorie) {
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nom_article", nom_article);
		namedParameters.addValue("description", description);
		namedParameters.addValue("date_debut", date_debut);
		namedParameters.addValue("date_fin", date_fin);
		namedParameters.addValue("prix_initial", prix_initial);
		namedParameters.addValue("prix_vente", prix_vente);
		namedParameters.addValue("id_utilisateur", utilisateur.getIdUtilisateur());
		namedParameters.addValue("id_categorie", categorie.getIdCategorie());
		namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedParameters);
	}

}
