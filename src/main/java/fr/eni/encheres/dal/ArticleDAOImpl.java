package fr.eni.encheres.dal;
 
import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
 

import fr.eni.encheres.bo.Article;

import fr.eni.encheres.bo.Retrait;

import fr.eni.encheres.dal.mapper.ArticleMapper;
import fr.eni.encheres.dal.mapper.RetraitMapper;
 
@Repository

public class ArticleDAOImpl implements ArticleDAO{
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public ArticleDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}

	private String date = "CURRENT_DATE";
	//private String date = "GETDATE()";

	
	
	private final String FIND_ALL = "SELECT DISTINCT "
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
		    + "    U.no_utilisateur AS vendeur_noUtilisateur "
		    + "FROM "
		    + "    ARTICLES_VENDUS A "
		    + "    INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie "
		    + "    INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur ";
	
	private final String FIND_ALL_EN_COURS = FIND_ALL + " WHERE A.date_debut_encheres <= "+date+" AND A.date_fin_encheres >= "+date;
	
	private final String FIND_ALL_BY_CATEGORY = FIND_ALL_EN_COURS + " AND A.no_categorie = ?";
	private final String FIND_ALL_BY_NOM_ARTICLE = FIND_ALL_EN_COURS +" AND A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ALL_BY_NOM_ARTICLE_AND_CATEGORY = FIND_ALL_EN_COURS + " AND A.no_categorie = ? AND A.nom_article LIKE '%' + ? + '%'";
	
	private final String FIND_ARTICLE_BY_ID = FIND_ALL + " WHERE A.no_article = ?";
	
	private final String FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS = FIND_ALL + " WHERE A.no_utilisateur = ? AND A.date_debut_encheres <= "+date+" AND A.date_fin_encheres >= "+date;
	private final String FIND_ARTICLES_BY_VENTE_NON_DEBUTE = FIND_ALL + " WHERE A.no_utilisateur = ? AND A.date_debut_encheres > "+date;
	private final String FIND_ARTICLES_BY_VENTE_TERMINEE = FIND_ALL + " WHERE A.no_utilisateur = ? AND A.date_fin_encheres < "+date;
	
	private final String FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS_AND_MOT = FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS + " AND A.nom_article LIKE '%' + ? + '%'" ;
	private final String FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_MOT = FIND_ARTICLES_BY_VENTE_NON_DEBUTE + " AND A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ARTICLES_BY_VENTE_TERMINEE_AND_MOT = FIND_ARTICLES_BY_VENTE_TERMINEE + " AND A.nom_article LIKE '%' + ? + '%'";
	
	private final String FIND_ARTICLES_BY_ID_VENDEUR_AND_CATEGORIE = FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS + " AND A.no_categorie = ?" ;
	private final String FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_CATEGORIE = FIND_ARTICLES_BY_VENTE_NON_DEBUTE + " AND A.no_categorie = ?";
	private final String FIND_ARTICLES_BY_VENTE_TERMINEE_AND_CATEGORIE = FIND_ARTICLES_BY_VENTE_TERMINEE + " AND A.no_categorie = ?";
	
	private final String FIND_ARTICLES_BY_ID_VENDEUR_AND_CATEGORIE_AND_NOM_ARTICLE = FIND_ARTICLES_BY_ID_VENDEUR_AND_CATEGORIE  + " AND A.nom_article LIKE '%' + ? + '%'" ;
	private final String FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_CATEGORIE_AND_NOM_ARTICLE = FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_CATEGORIE + " AND A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ARTICLES_BY_VENTE_TERMINEE_AND_CATEGORIE_AND_NOM_ARTICLE = FIND_ARTICLES_BY_VENTE_TERMINEE_AND_CATEGORIE + " AND A.nom_article LIKE '%' + ? + '%'";
	
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI = FIND_ALL + " INNER JOIN ENCHERES E ON A.no_article = E.no_article WHERE E.no_utilisateur = ? AND A.date_fin_encheres >= "+date;
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_CATEGORIE = FIND_ALL + " INNER JOIN ENCHERES E ON A.no_article = E.no_article WHERE E.no_utilisateur = ?  AND A.no_categorie = ? AND A.date_fin_encheres >= "+date;
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_RECHERCHE = FIND_ALL + " INNER JOIN ENCHERES E ON A.no_article = E.no_article WHERE E.no_utilisateur = ? AND A.nom_article LIKE '%' + ? + '%' AND A.date_fin_encheres >= "+date;
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_CATEGORIE_AND_RECHERCHE = FIND_ALL + " INNER JOIN ENCHERES E ON A.no_article = E.no_article WHERE E.no_utilisateur = ? AND A.no_categorie = ? AND A.nom_article LIKE '%' + ? + '%' AND A.date_fin_encheres >= "+date;
	
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE = "SELECT DISTINCT " +
		    "A.no_article, " +
		    "A.nom_article, " +
		    "A.description, " +
		    "A.date_debut_encheres, " +
		    "A.date_fin_encheres, " +
		    "A.prix_initial, " +
		    "A.prix_vente, " +
		    "C.libelle AS categorie, " +
		    "U.pseudo AS vendeur_pseudo, " +
		    "U.nom AS vendeur_nom, " +
		    "U.prenom AS vendeur_prenom, " +
		    "U.email AS vendeur_email, " +
		    "U.telephone AS vendeur_telephone, " +
		    "U.no_utilisateur AS vendeur_noUtilisateur, " +
		    "E.date_enchere, " +
		    "E.montant_enchere " +
		    "FROM " +
		    "ARTICLES_VENDUS A " +
		    "INNER JOIN CATEGORIES C ON A.no_categorie = C.no_categorie " +
		    "INNER JOIN UTILISATEURS U ON A.no_utilisateur = U.no_utilisateur " +
		    "INNER JOIN ENCHERES E ON A.no_article = E.no_article " +
		    "WHERE " +
		    "A.date_fin_encheres < "+date+" " +
		    "AND E.no_utilisateur = ? " +
		    "AND E.montant_enchere = ( " +
		    "SELECT MAX(montant_enchere) " +
		    "FROM ENCHERES " +
		    "WHERE no_article = A.no_article " +
		    ")";
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_CATEGORIE = FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE + " AND A.no_categorie = ?";
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_RECHERCHE = FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE + " AND A.nom_article LIKE '%' + ? + '%'";
	private final String FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_CATEGORIE_AND_RECHERCHE = FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE + " AND A.no_categorie = ? AND A.nom_article LIKE '%' + ? + '%'";
	
	private final String FIND_RETRAIT_BY_ID = "SELECT "
			+ "r.ville, r.code_postal, r.rue "
			+ "FROM "
			+ "    Retraits AS r "
			+ "    JOIN ARTICLES_VENDUS AS a ON r.no_article = a.no_article "
			+ "WHERE "
	  		+ "    a.no_article = ?";
	
	private final String INSERT_ENCHERE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) "
			+ " VALUES(:nom_article, :description, :date_debut, :date_fin, :prix_initial, :prix_vente, :id_utilisateur, :id_categorie )";
	
	/*private final String UPDATE_VENTE_ARTICLE = "UPDATE ARTICLES_VENDUS"
							+ " SET nom_article = :nom_article, description = :description, date_debut_encheres = :date_debut, date_fin_encheres = :date_fin, prix_initial = :prix_initial,"
							+ " prix_vente = :prix_vente, no_utilisateur = :id_utilisateur, no_categorie = :id_categorie"
								+ " WHERE no_article = :no_article";*/
	
	private final String UPDATE_VENTE_ARTICLE = 
			"UPDATE ARTICLES_VENDUS SET nom_article = ?, description = ?, date_debut_encheres = ?, date_fin_encheres = ?, prix_initial = ?,"
			+ " prix_vente = ?, no_utilisateur = ?, no_categorie = ? WHERE no_article = ?";
			
								
			
			
 
	
	private final String CHANGE_PRIX_VENTE = "UPDATE ARTICLES_VENDUS " +
            "SET prix_vente = ? " +
            "WHERE no_article = ?";
	
	private final String GET_PRIX_VENTE = "SELECT prix_vente " +
            "FROM ARTICLES_VENDUS " +
            "WHERE no_article = ?";
	
	private final String DELETE_ARTICLE = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
	
  
	@Override
	public List<Article> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL_EN_COURS, new ArticleMapper());
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
	
	
	
	@Override
	public int ajoutArticle(Article article) {
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("nom_article", article.getNom());
		namedParameters.addValue("description", article.getDescription());
		namedParameters.addValue("date_debut", article.getDebut());
		namedParameters.addValue("date_fin", article.getFin());
		namedParameters.addValue("prix_initial", article.getMiseAPrix());
		namedParameters.addValue("prix_vente", article.getPrixVente());
		namedParameters.addValue("id_utilisateur", article.getVendeur().getNoUtilisateur());
		namedParameters.addValue("id_categorie", article.getCategorie().getNoCategorie());
		
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedParameters, keyHolder);
		Number newPrimaryKey = keyHolder.getKey();
		int idGenere  = 0; 
		if(newPrimaryKey != null) {
			idGenere = newPrimaryKey.intValue();
			
		}
		return idGenere;
		}
	
	@Override
	public void modifierArticle(Article article) {
		 jdbcTemplate.update(UPDATE_VENTE_ARTICLE, article.getNom(), article.getDescription(), article.getDebut(), article.getFin(), article.getMiseAPrix(),
	    		article.getPrixVente(), article.getVendeur().getNoUtilisateur(), article.getCategorie().getNoCategorie(), article.getNoArticle());
		
	}

	
	

	

	@Override
	public List<Article> findAllByIdVendeur(int idVendeur) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS, new ArticleMapper(), idVendeur);
	}

	@Override
	public List<Article> findAllByIdVenteNonDebutee(int idVendeur) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_NON_DEBUTE, new ArticleMapper(), idVendeur);
	}

	@Override
	public List<Article> findAllByIdVenteTerminee(int idVendeur) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_TERMINEE, new ArticleMapper(), idVendeur);
	}

	@Override
	public List<Article> findAllByIdVendeurAndNomArticle(int idVendeur, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_ID_VENDEUR_AND_VENTE_EN_COURS_AND_MOT, new ArticleMapper(), idVendeur, mot);
	}

	@Override
	public List<Article> findAllByIdVenteNonDebuteeAndNomArticle(int idVendeur, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_MOT, new ArticleMapper(), idVendeur, mot);
	}

	@Override
	public List<Article> findAllByIdVenteTermineeAndNomArticle(int idVendeur, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_TERMINEE_AND_MOT, new ArticleMapper(), idVendeur, mot);
	}

	@Override
	public List<Article> findAllByIdVendeurAndCategorie(int idVendeur, int idCategorie) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_ID_VENDEUR_AND_CATEGORIE, new ArticleMapper(), idVendeur, idCategorie);
	}

	@Override
	public List<Article> findAllByIdVenteNonDebuteeAndCategorie(int idVendeur, int idCategorie) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_CATEGORIE, new ArticleMapper(), idVendeur, idCategorie);
	}

	@Override
	public List<Article> findAllByIdVenteTermineeAndCategorie(int idVendeur, int idCategorie) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_TERMINEE_AND_CATEGORIE, new ArticleMapper(), idVendeur, idCategorie);
	}

	@Override
	public List<Article> findAllByIdVendeurAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_ID_VENDEUR_AND_CATEGORIE_AND_NOM_ARTICLE, new ArticleMapper(), idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> findAllByIdVenteNonDebuteeAndCategorieAndNomArticle(int idVendeur, int idCategorie,
			String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_NON_DEBUTE_AND_CATEGORIE_AND_NOM_ARTICLE, new ArticleMapper(), idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> findAllByIdVenteTermineeAndCategorieAndNomArticle(int idVendeur, int idCategorie, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_BY_VENTE_TERMINEE_AND_CATEGORIE_AND_NOM_ARTICLE, new ArticleMapper(), idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantEncheriAndCategorieAndNomArticle(int idVendeur, int id,
			String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_CATEGORIE_AND_RECHERCHE, new ArticleMapper(), idVendeur, id, mot);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantEncheriAndCategorie(int idVendeur, int id) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_CATEGORIE, new ArticleMapper(), idVendeur, id);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantEncheriAndRecherche(int idVendeur, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI_AND_RECHERCHE, new ArticleMapper(), idVendeur, mot);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantEncheri(int idVendeur) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_ENCHERI, new ArticleMapper(), idVendeur);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantRemporteAndCategorieAndNomArticle(int idVendeur,
			int idCategorie, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_CATEGORIE_AND_RECHERCHE, new ArticleMapper(), idVendeur, idCategorie, mot);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantRemporteAndCategorie(int idVendeur, int idCategorie) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_CATEGORIE, new ArticleMapper(), idVendeur, idCategorie);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantRemporteAndRecherche(int idVendeur, String mot) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE_AND_RECHERCHE, new ArticleMapper(), idVendeur, mot);
	}

	@Override
	public List<Article> findAllArticlesByIdVendeurAyantRemporte(int idVendeur) {
		return jdbcTemplate.query(FIND_ARTICLES_WHERE_ID_VENDEUR_REMPORTE, new ArticleMapper(), idVendeur);
	}
	
	@Override
	public void changerPrixVente(int noArticle, int nouvelleEnchereNumber) {
		jdbcTemplate.update(CHANGE_PRIX_VENTE,nouvelleEnchereNumber,noArticle);
	}

	@Override
	public int getPrixVente(int no_article) {
		return jdbcTemplate.queryForObject(GET_PRIX_VENTE, Integer.class, no_article) ;
	}

	@Override
	public void deleteArticleById(int no_article) {
		String sql = "DELETE FROM ARTICLES_VENDUS WHERE no_article = ?";
		jdbcTemplate.update(DELETE_ARTICLE, no_article);
		
		
	}


}