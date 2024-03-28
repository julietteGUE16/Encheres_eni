package fr.eni.encheres.dal.mapper;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EnchereMapper implements RowMapper<Enchere> {


    @Override
    public Enchere mapRow(ResultSet rs, int rowNum) throws SQLException {
        var e = new Enchere();
        e.setMontant(rs.getInt("meilleure_offre"));
        Utilisateur encherisseur = new Utilisateur (rs.getInt("id_encherisseur"),rs.getString("pseudo_encherisseur"));
        e.setUtilisateur(encherisseur);
        return e;
    }
}
