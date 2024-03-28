package fr.eni.encheres.dal;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.mapper.ArticleMapper;
import fr.eni.encheres.dal.mapper.EnchereMapper;

@Repository
public class EnchereDAOImpl implements EnchereDAO {
	
	
	private JdbcTemplate jdbcTemplate;
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public EnchereDAOImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.jdbcTemplate = this.namedParameterJdbcTemplate.getJdbcTemplate();
	}
	

	private final String FIND_BEST_ENCHERE_BY_ID_Article = "SELECT "
			+ "COALESCE(e.montant_enchere, 0) AS meilleure_offre, "
			+ "COALESCE( u.pseudo, 'Aucun encherisseur') AS pseudo_encherisseur, "
			+ "u.no_utilisateur AS id_encherisseur " +"FROM " + "ARTICLES_VENDUS a "
			+ "LEFT JOIN " + "ENCHERES e ON a.no_article = e.no_article " + "LEFT JOIN "
			+ "UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur " + "WHERE " + "a.no_article = ? "
			+ "AND (e.montant_enchere IS NULL OR e.montant_enchere = (SELECT MAX(montant_enchere) " + "FROM ENCHERES "
			+ "WHERE no_article = a.no_article))";
	
	private final String DELETE_ENCHERE_BEST = "DELETE FROM ENCHERES " +
            "WHERE no_article = ? " +
            "AND no_utilisateur = ? " +
            "AND date_enchere = ( " +
            "    SELECT MAX(date_enchere) " +
            "    FROM ENCHERES " +
            "    WHERE no_article = ? " +
            "    AND no_utilisateur = ? " +
            ")";
	
	private final String DELETE_ENCHERE = "DELETE FROM ENCHERES " +
            "WHERE no_article = ? " +
            "AND no_utilisateur = ? ";
	
	private final String DELETE_ALL_ENCHERE = "DELETE FROM ENCHERES " +
            "WHERE no_article = ? ";
	
	private final String INSERT_ENCHERE = "INSERT INTO ENCHERES (no_utilisateur, no_article, date_enchere, montant_enchere) "
			+ "VALUES (:no_utilisateur, :no_article, :date_enchere, :montant_enchere)";


	@Override
	public Enchere findBestEnchereByIdArticle(int no_article) {
		return jdbcTemplate.queryForObject(FIND_BEST_ENCHERE_BY_ID_Article, new EnchereMapper(), no_article);
	}
	
	@Override
	public void insertEnchere(Enchere enchere) {
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_utilisateur", enchere.getUtilisateur().getNoUtilisateur());
		namedParameters.addValue("no_article", enchere.getArticle().getNoArticle());
		namedParameters.addValue("date_enchere", enchere.getDate());
		namedParameters.addValue("montant_enchere", enchere.getMontant());
		namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedParameters);
	}

	@Override
	public void deleteBestEnchere(int no_article, int idUser) {
		jdbcTemplate.update(DELETE_ENCHERE_BEST, no_article,idUser,no_article,idUser);
	}
	
	@Override
	public void deleteAllEnchereByArticleIdAndIdUser(int no_article, int idUser) {
		jdbcTemplate.update(DELETE_ENCHERE, no_article, idUser);
	}

	@Override
	public void deleteAllEnchereByArticleId(int no_article) {
		jdbcTemplate.update(DELETE_ALL_ENCHERE, no_article);
	}

}
