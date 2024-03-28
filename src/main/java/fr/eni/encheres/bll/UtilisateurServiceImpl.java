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

	/**
	 * Retourne un utilisateur à partir de son identifiant.
	 * 
	 * @param id L'identifiant de l'utilisateur.
	 * @return L'utilisateur trouvé, s'il existe.
	 */
	@Override
	public Optional<Utilisateur> getUserById(int id) {
		return utilisateurDAO.getUserById(id);
	}

	/**
	 * Met à jour les données d'un utilisateur.
	 * 
	 * @param user L'utilisateur à mettre à jour.
	 */
	@Override
	public void updateUser(Utilisateur user) {
		utilisateurDAO.updateUser(user);

	}

	/**
	 * Retourne le mot de passe d'un utilisateur à partir de son id.
	 * 
	 * @param id L'identifiant de l'utilisateur.
	 * @return Le mot de passe de l'utilisateur.
	 */
	@Override
	public String getUserPasswordById(int id) {
		return utilisateurDAO.getUserPasswordById(id);
	}

	/**
	 * Recherche un utilisateur par son pseudo.
	 * 
	 * @param pseudo Le pseudo de l'utilisateur à rechercher.
	 * @return L'utilisateur trouvé, s'il existe.
	 */
	@Override
	public Optional<Utilisateur> findUtilisateurByPseudo(String pseudo) {
		return utilisateurDAO.findUtilisateurByPseudo(pseudo);
	}

	/**
	 * Supprime un utilisateur et toutes ses informations associées.
	 * 
	 * @param idUser L'identifiant de l'utilisateur à supprimer.
	 */
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
		utilisateurDAO.deleteUser(idUser);
	}

	/**
	 * Vérifie si un pseudo existe déjà dans la base de données.
	 * 
	 * @param pseudo Le pseudo à vérifier.
	 * @return true si le pseudo existe déjà, sinon false.
	 */
	@Override
	public boolean pseudoExisteDeja(String pseudo) {
		return utilisateurDAO.pseudoExisteDeja(pseudo);
	}

	/**
	 * Enregistre un nouvel utilisateur dans la base de données.
	 * 
	 * @param utilisateur L'utilisateur à enregistrer.
	 */
	@Override
	public void save(Utilisateur utilisateur) {
		utilisateurDAO.save(utilisateur);

	}

	/**
	 * Vérifie si une adresse email existe déjà dans la base de données.
	 * 
	 * @param email L'adresse email à vérifier.
	 * @param id    L'identifiant de l'utilisateur (à exclure de la vérification).
	 * @return true si l'adresse email existe déjà, sinon false.
	 */
	@Override
	public boolean emailExisteDeja(String email, int id) {
		return utilisateurDAO.emailExisteDeja(email, id);
	}

	/**
	 * Recherche un utilisateur par son adresse email.
	 * 
	 * @param email L'adresse email de l'utilisateur à rechercher.
	 * @return L'utilisateur trouvé, s'il existe.
	 */
	@Override
	public Utilisateur findUtilisateurByEmail(String email) {
		return utilisateurDAO.findUtilisateurByEmail(email);
	}

	/**
	 * Recherche le nombre d'occurrences d'une adresse e-mail dans la base de
	 * données.
	 * 
	 * @param email L'adresse e-mail à rechercher.
	 * @return Le nombre d'occurrences de l'adresse e-mail.
	 */
	@Override
	public int findEmail(String email) {
		return utilisateurDAO.findEmail(email);
	}

}
