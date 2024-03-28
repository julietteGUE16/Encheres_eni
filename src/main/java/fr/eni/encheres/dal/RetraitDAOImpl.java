package fr.eni.encheres.dal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import fr.eni.encheres.bo.Retrait;

@Repository
public class RetraitDAOImpl implements RetraitDAO{
	private final String INSERT_RETRAIT = "INSERT INTO RETRAITS(no_article, rue, code_postal, ville) " 
			+ " VALUES(:no_article, :rue, :code_postal, :ville)";
	
	private final String UPDATE_VENTE_RETRAIT = "UPDATE RETRAITS"
			+ " SET rue = :rue, code_postal = :code_postal, ville = :ville"
				+ " WHERE no_article = :no_article";
	
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    
    @Override
	public Object ajoutRetrait(Retrait retrait) {
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_article", retrait.getNoRetrait());
		namedParameters.addValue("rue", retrait.getRue());
		namedParameters.addValue("code_postal", retrait.getCodePostal());
		namedParameters.addValue("ville", retrait.getVille());
		
		return namedParameterJdbcTemplate.update(INSERT_RETRAIT, namedParameters);
		
		
	}


	@Override
	public Object modifierRetrait(Retrait retrait) {
		var namedParameters = new MapSqlParameterSource();
		namedParameters.addValue("no_article", retrait.getNoRetrait());
		namedParameters.addValue("rue", retrait.getRue());
		namedParameters.addValue("code_postal", retrait.getCodePostal());
		namedParameters.addValue("ville", retrait.getVille());
		
		return namedParameterJdbcTemplate.update(UPDATE_VENTE_RETRAIT, namedParameters);
	}
}
