package fr.eni.encheres.dal;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

//import fr.eni.encheres.bll.Utilisateur;

@Repository
public class EncheresRepositoryImpl {
	
	private final String INSERT_ENCHERE = "INSERT INTO ENCHERES(no_utilisateur, no_article, date_enchere, montant_enchere) "
									+ " VALUES(:)";

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
/*	public void ajoutEnchere(int id_utilisateur, int id_article, Date date_enchere, int montant_enchere) {
		// create a mysql database connection
	    String myDriver = "org.gjt.mm.mysql.Driver";
	    String myUrl = "jdbc:sqlserver://localhost";
	    Class.forName(myDriver);
	    Connection conn = DriverManager.getConnection(myUrl, "root", "");
		//db.insert_date(sqlDate);
		PreparedStatement preparedStmt = conn.prepareStatement(query);
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_utilisateur", id_utilisateur);
		namedParameters.addValue("no_article", id_article );
		namedParameters.addValue("date_enchere", date_enchere);
		namedParameters.addValue("montant_enchere", montant_enchere );
		namedParameterJdbcTemplate.update(INSERT_ENCHERE, namedParameters);
	}
	
	public String modifierEnchere() {
		
	}*/
}
