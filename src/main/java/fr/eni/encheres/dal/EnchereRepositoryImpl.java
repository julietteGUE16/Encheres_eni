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
public class EnchereRepositoryImpl implements EnchereDAO {
	
	private final String FIND_BEST_ENCHERE_BY_ID_Article = "SELECT "
	        + "    e.montant_enchere AS meilleure_offre, "
	        + "    u.pseudo AS pseudo_encherisseur "
	        + "FROM "
	        + "    ENCHERES e "
	        + "    JOIN UTILISATEURS u ON e.no_utilisateur = u.no_utilisateur "
	        + "    JOIN ARTICLES_VENDUS a ON e.no_article = a.no_article "
	        + "WHERE "
	        + "    e.montant_enchere = ( "
	        + "        SELECT MAX(montant_enchere) "
	        + "        FROM ENCHERES "
	        + "        WHERE no_article = a.no_article "
	        + "    ) "
	        + "    AND a.no_article = ? ";


    @Autowired
    private JdbcTemplate jdbcTemplate;
    
	@Override
	public Enchere findBestEnchereByIdArticle(int no_article) {
		return jdbcTemplate.queryForObject(FIND_BEST_ENCHERE_BY_ID_Article, new EnchereMapper(),no_article);
	}

}
