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

import fr.eni.encheres.bo.Utilisateur;

@Repository
public class EncheresRepositoryImpl implements EncheresDAO {
	


	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	

}
