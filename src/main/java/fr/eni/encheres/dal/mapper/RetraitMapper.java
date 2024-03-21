package fr.eni.encheres.dal.mapper;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class RetraitMapper implements RowMapper<Retrait> {


    @Override
    public Retrait mapRow(ResultSet rs, int rowNum) throws SQLException {
        var r = new Retrait();
        r.setCodePostal(rs.getString("code_postal"));
        r.setRue(rs.getString("rue"));
        r.setVille(rs.getString("ville"));
        return r;
    }
}
