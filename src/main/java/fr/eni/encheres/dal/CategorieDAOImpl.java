package fr.eni.encheres.dal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bo.Categorie;

@Repository
public class CategorieDAOImpl implements CategorieDAO {
	private final String FIND_ALL = "SELECT * from CATEGORIES";
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Override
	public List<Categorie> findAll() {
		return namedParameterJdbcTemplate.query(FIND_ALL, new BeanPropertyRowMapper<>(Categorie.class));
	}

}
