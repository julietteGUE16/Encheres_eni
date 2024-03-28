package fr.eni.encheres.bll;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.ArticleDAO;
import fr.eni.encheres.dal.EnchereDAO;
import fr.eni.encheres.dal.RetraitDAO;
import fr.eni.encheres.dal.UtilisateurDAO;
import fr.eni.encheres.dal.UtilisateurDAOImpl;
import jakarta.validation.Valid;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

	private UtilisateurDAO utilisateurDAO;
	private RetraitDAO retraitDAO;
	private EnchereDAO enchereDAO;
	private ArticleDAO articleDAO;

	public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO, RetraitDAO retraitDAO, EnchereDAO enchereDAO,
			ArticleDAO articleDAO) {
		this.utilisateurDAO = utilisateurDAO;
		this.retraitDAO = retraitDAO;
		this.enchereDAO = enchereDAO;
		this.articleDAO = articleDAO;
	}

	@Override
	public Optional<Utilisateur> getUserById(int id) {
		return utilisateurDAO.getUserById(id);
	}

	@Override
	public void updateUser(Utilisateur user) {
		utilisateurDAO.updateUser(user);

	}

	@Override
	public String getUserPasswordById(int id) {
		return utilisateurDAO.getUserPasswordById(id);
	}

	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		return utilisateurDAO.findUtilisateurByPseudo(pseudo);
	}

	@Transactional
	@Override
	public void deleteUser(int idUser) {
		// vente non débuté
		List<Article> articlesNonDebute = articleDAO.findAllByIdVenteNonDebutee(idUser);
		if (articlesNonDebute.size() > 0) {
			for (Article article : articlesNonDebute) {
				// delete retrait de mes ventes non débuté
				retraitDAO.deleteByArticleId(article.getNoArticle());
				// delete mes vente non débuté
				articleDAO.deleteArticleById(article.getNoArticle());
			}
		}

		// vente en cours
		List<Article> articlesEnCours = articleDAO.findAllByIdVendeur(idUser);

		if (articlesEnCours.size() > 0) {
			for (Article article : articlesEnCours) {
				// delete retrait de mes ventes en cours
				retraitDAO.deleteByArticleId(article.getNoArticle());

				// rembourser best enchere
				Enchere bestEnchere = enchereDAO.findBestEnchereByIdArticle(article.getNoArticle());
				int bestEncherisseurId = bestEnchere.getUtilisateur().getNoUtilisateur();
				Optional<Utilisateur> user = this.getUserById(bestEncherisseurId);
				if (user.isPresent()) {
					user.get().setCredit(user.get().getCredit() + bestEnchere.getMontant());
					this.updateUser(user.get());
				}
				// delete enchere
				enchereDAO.deleteAllEnchereByArticleId(article.getNoArticle());
				// delete mes vente en cours
				articleDAO.deleteArticleById(article.getNoArticle());
			}

		}
		// delete mes encheres sur les ventes en cours
		List<Article> ArticlesMesEncheres = articleDAO.findAllArticlesByIdVendeurAyantEncheri(idUser);
		if (articlesEnCours.size() > 0) {
			for (Article article : ArticlesMesEncheres) {
				enchereDAO.deleteAllEnchereByArticleIdAndIdUser(article.getNoArticle(), idUser);
			}
		}
		// delete utilisateur
	}

	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		return utilisateurDAO.pseudoExisteDeja(pseudo);
	}

	@Override
	public void save(Utilisateur utilisateur) {
		utilisateurDAO.save(utilisateur);

	}

	@Override
	public boolean emailExisteDeja(String email, int id) {
		return utilisateurDAO.emailExisteDeja(email, id);
	}

	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		return utilisateurDAO.findUtilisateurByEmail(email);
	}

	@Override
	public int findEmail(String email) {
		return utilisateurDAO.findEmail(email);
	}

}
