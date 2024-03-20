package fr.eni.encheres.dal.mapper;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ArticleMapper implements RowMapper<Article> {


    @Override
    public Article mapRow(ResultSet rs, int rowNum) throws SQLException {
        var a = new Article();
        a.setIdArticle(rs.getInt("no_article"));
        a.setNom(rs.getString("nom_article"));
        a.setDescription(rs.getString("description"));
        a.setDebut(rs.getDate("date_debut_encheres"));
        a.setFin(rs.getDate("date_fin_encheres"));
        a.setMiseAprix(rs.getInt("prix_initial"));
        Utilisateur vendeur = new Utilisateur(rs.getString("vendeur_pseudo"),rs.getString("vendeur_nom"), rs.getString("vendeur_prenom"), rs.getString("vendeur_email"), rs.getString("vendeur_telephone"));
        System.out.println(vendeur);
        a.setVendeur(vendeur);
        
        return a;
    }
}
