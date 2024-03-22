package fr.eni.encheres.dal;
 
import java.sql.Date;
import java.util.List;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
 
import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
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
	
	private final String INSERT_ENCHERE = "INSERT INTO ARTICLES_VENDUS(nom_article, description, date_debut_encheres, date_fin_encheres, prix_initial, prix_vente, no_utilisateur, no_categorie) "
			+ " VALUES(:nom_article, :description, :date_debut, :date_fin, :prix_initial, :prix_vente, :id_utilisateur, :id_categorie )";
 
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
	
	
	
	@Override
		public void ajoutArticle(Article article) {
		
		System.out.println("on passe dans la dal");
		System.out.println("vendeur = " + article.getVendeur().getNoUtilisateur());
		System.out.println("cat = " + article.getCategorie().getNoCategorie());
			var namedParameters = new MapSqlParameterSource();
			namedParameters.addValue("nom_article", article.getNom());
			namedParameters.addValue("description", article.getDescription());
			namedParameters.addValue("date_debut", article.getDebut());
			namedParameters.addValue("date_fin", article.getFin());
			namedParameters.addValue("prix_initial", article.getMiseAPrix());
			namedParameters.addValue("prix_vente", article.getPrixVente());
			namedParameters.addValue("id_utilisateur", article.getVendeur().getNoUtilisateur());
			namedParameters.addValue("id_categorie", article.getCategorie().getNoCategorie());
			namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedParameters);
		}
}