package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.dal.mapper.ArticleMapper;
import fr.eni.encheres.dal.mapper.RetraitMapper;

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
		    + "    U.telephone AS vendeur_telephone "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur ";
	private final String FIND_ALL_BY_CATEGORY = "SELECT "
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
		    + "    U.telephone AS vendeur_telephone "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur "
			+ "WHERE "
			+ "	   A.no_categorie = ?";
	private final String FIND_ALL_BY_NOM_ARTICLE = "SELECT "
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
		    + "    U.telephone AS vendeur_telephone "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur "
			+ "WHERE "
			+ "	   A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ALL_BY_NOM_ARTICLE_AND_CATEGORY = "SELECT "
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
		    + "    U.telephone AS vendeur_telephone "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur "
			+ "WHERE "
			+ "    A.no_categorie = ? "
			+ "	   AND A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ARTICLE_BY_ID = "SELECT "
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
		    + "    U.telephone AS vendeur_telephone "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur "
			+ "WHERE "
			+ "    A.no_article = ? ";
	private final String FIND_RETRAIT_BY_ID = "SELECT "
			+ "r.ville, r.code_postal, r.rue "
			+ "FROM "
			+ "    Retraits AS r "
			+ "    JOIN ARTICLES_VENDUS AS a ON r.no_article = a.no_article "
			+ "WHERE "
	  		+ "    a.no_article = ?";


    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new ArticleMapper());
	}

	@Override
	public List<Article> findAllByCategorie(int idCategorie) {
		return jdbcTemplate.query(FIND_ALL_BY_CATEGORY, new ArticleMapper(), idCategorie);
	}

	@Override
	public List<Article> findAllByNomArticle(String nomArticle) {
		return jdbcTemplate.query(FIND_ALL_BY_NOM_ARTICLE, new ArticleMapper(), nomArticle);
	}

	@Override
	public List<Article> findAllByNomArticleAndCategory(String nomArticle, int idCategorie) {
		return jdbcTemplate.query(FIND_ALL_BY_NOM_ARTICLE_AND_CATEGORY, new ArticleMapper(),idCategorie, nomArticle);
	}

	@Override
	public Article findArticleById(int idArticle) {
		return jdbcTemplate.queryForObject(FIND_ARTICLE_BY_ID, new ArticleMapper(),idArticle);
	}

	@Override
	public Retrait findRetraitById(int no_article) {
		return jdbcTemplate.queryForObject(FIND_RETRAIT_BY_ID, new RetraitMapper(),no_article);
	}
}
