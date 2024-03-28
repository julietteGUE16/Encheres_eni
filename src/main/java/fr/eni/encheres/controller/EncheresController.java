package fr.eni.encheres.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.EnchereService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;

@Controller
// Injection de la liste des attributs en session
//@SessionAttributes({ "genresEnSession", "membreEnSession", "participantsEnSession" })
public class EncheresController {
	
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	private ArticlesService articlesService;
	private CategorieService categorieService;
	private UtilisateurService utilisateurService;
	private EnchereService enchereService;
	private boolean errorPrice;
	private boolean noMoney;

	public EncheresController(ArticlesService articlesService, CategorieService categorieService, UtilisateurService utilisateurService,
			EnchereService encheresService) {
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.enchereService = encheresService;
	}

	@GetMapping({ "/", "/encheres" })
	public String afficherArticles(Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		List<Article> articles = articlesService.consulterArticles();
		model.addAttribute("articles", articles);
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("categories", categories);
		return "view-encheres";
	}

	@PostMapping("/encheresParCategorieEtNom")
	public String afficherArticlesParCategorie(@RequestParam(name = "categorySelect", required = true) int id,
			@RequestParam(name = "searchInput", required = true) String nom,
			@RequestParam(name = "mesVentesEnCours", required = false) boolean enCours,
			@RequestParam(name = "ventesNonDebutees", required = false) boolean nonDebutee,
			@RequestParam(name = "ventesTerminees", required = false) boolean terminee,
			@RequestParam(name = "enchèresEnCours", required = false) boolean achatsEnCours,
			@RequestParam(name = "enchèresRemportées", required = false) boolean remportees,
			@RequestParam(name = "choix", required = false) String choix, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("categories", categories);
		
		if (choix == null) {
			choix = "ventes";
		}
		
		if (id == -1 && nom.trim() == "" && ((enCours == false && nonDebutee == true && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("achats") || ((enCours == false && nonDebutee == false && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("ventes")))) { // rien de selectionné
			return "redirect:/encheres";
	}
		
		if (id != -1 && nom.trim() == "" && ((enCours == false && nonDebutee == true && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("achats") || ((enCours == false && nonDebutee == false && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("ventes")))) { // seule la categorie est selectionnée
				List<Article> articles = articlesService.consulterArticlesByCategorie(id);
				model.addAttribute("articles", articles);
				return "view-encheres";
		}
		if (nom != "" && id == -1 && ((enCours == false && nonDebutee == true && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("achats") || ((enCours == false && nonDebutee == false && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("ventes")))) {// seule la recherche est selectionnée
				List<Article> articles = articlesService.consulterArticlesByNomArticle(nom);
				model.addAttribute("articles", articles);
				return "view-encheres";
		}
		if (nom != "" && id != -1 && ((enCours == false && nonDebutee == true && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("achats") || ((enCours == false && nonDebutee == false && terminee == false && achatsEnCours == false && remportees == false) & choix.equals("ventes")))) { //recherche & categorie selectionnées
				List<Article> articles = articlesService.consulterArticlesByNomArticleAndCategorie(nom, id);
				model.addAttribute("articles", articles);
				return "view-encheres";
		}
		if (nom.trim() == "" && id == -1 ) { // seules les checkbox sont cochées
			List<Article> articles = new ArrayList<Article>();
			if (choix.equals("ventes")) {
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeur(getIdUser()));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebutee(getIdUser()));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTerminee(getIdUser()));
			};
			} else {
			if (achatsEnCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAyantEncheri(getIdUser()));
			};
			if (remportees ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVendeurAyantRemporte(getIdUser()));
			};
			}
			model.addAttribute("articles", articles);
		}
		if (nom.trim() != "" && id == -1) { // seules les checkbox sont cochées et la recherche
			List<Article> articles = new ArrayList<Article>();
			if (choix.equals("ventes")) {
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAndNomArticle(getIdUser(), nom));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebuteeAndNomArticle(getIdUser(), nom));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTermineeAndNomArticle(getIdUser(), nom));
			};
			} else {
			if (achatsEnCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAyantEncheriAndRecherche(getIdUser(), nom));
			};
			if (remportees ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVendeurAyantRemporteAndRecherche(getIdUser(), nom));
			};
			}
			model.addAttribute("articles", articles);
		}
		if (nom.trim() == "" && id != -1 ) { // les checkbox sont cochées et la categorie
			List<Article> articles = new ArrayList<Article>();
			if (choix.equals("ventes")) {
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAndCategorie(getIdUser(), id));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebuteeAndCategorie(getIdUser(), id));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTermineeAndCategorie(getIdUser(), id));
			};
			} else {
			if (achatsEnCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAyantEncheriAndCategorie(getIdUser(), id));
			};
			if (remportees ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVendeurAyantRemporteAndCategorie(getIdUser(), id));
			};
			}
			model.addAttribute("articles", articles);
		}
		if (nom.trim() != "" && id != -1 ) { // les checkbox sont cochées, la categorie et la recherche
			List<Article> articles = new ArrayList<Article>();
			if (choix.equals("ventes")) {
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebuteeAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTermineeAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			} else {
			if (achatsEnCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAyantEncheriAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (remportees ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVendeurAyantRemporteAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			}
			model.addAttribute("articles", articles);
		}
		return "view-encheres";
	}

	@GetMapping("/encheres/detail")
	public String AfficherUnArticle(@RequestParam(name = "noArticle") int no_article, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		model.addAttribute("pseudoUser", name); 
		Article article = articlesService.consulterArticleByIdArticle(no_article);
		Retrait retrait = articlesService.consulterRetraitByIDArticle(no_article);
		Enchere enchere = enchereService.consulterBestEnchereByIdArticle(no_article);
		model.addAttribute("enchere", enchere); 
		model.addAttribute("article", article);
		model.addAttribute("retrait", retrait);
		model.addAttribute("errorPrice", errorPrice);
		model.addAttribute("noMoney", noMoney);
		noMoney = false;
		errorPrice = false;
		
		return "view-detail";
	}
	
	@PostMapping("/encheres/ajouter")
	public String ajoutVente(@RequestParam(name = "nouvelleEnchere")int nouvelleEnchereNumber, @RequestParam(name = "noArticle") int noArticle,
			@RequestParam(name = "enchereMax")int enchereMax, @RequestParam(name = "miseAPrix") int miseAPrix, Model model
			, @RequestParam(name = "noEncherrisseur") int noEncherrisseur) {
		model.addAttribute("utilisateurService", utilisateurService);
		int enchereEnCours = 0;
		//vérifier en base si un autre user à changer la valeur entre temps
		Enchere enchere = enchereService.consulterBestEnchereByIdArticle(noArticle);
		if(enchere != null) {
			enchereMax = enchere.getMontant();
		}
		
		if (enchereMax == 0) {
			enchereEnCours = miseAPrix;
			if (nouvelleEnchereNumber <= miseAPrix) {
				errorPrice = true;
			}
		} else {
			enchereEnCours = enchereMax;
			if (nouvelleEnchereNumber <= enchereMax) {
				errorPrice = true;
			}
		}
		if(nouvelleEnchereNumber > utilisateurService.getUserById(getIdUser()).get().getCredit()) {
			noMoney = true;
		}
		if(noMoney || errorPrice) {
			return "redirect:/encheres/detail?noArticle=" + noArticle;
		}
		LocalDateTime now = LocalDateTime.now();
		//crédité le nouvel encherisseur
		Utilisateur utilisateur = utilisateurService.getUserById(getIdUser()).get();
		utilisateur.setCredit(utilisateur.getCredit()-nouvelleEnchereNumber);
		utilisateurService.updateUser(utilisateur);
		

		//recrédité l'ancien encherisseur
		Optional<Utilisateur> encherisseur = utilisateurService.getUserById(noEncherrisseur);
		if(encherisseur.isPresent()) {
			Utilisateur user = encherisseur.get();
			user.setCredit(user.getCredit()+enchereEnCours);
			utilisateurService.updateUser(user);
		}
		
		Article article = new Article();
		//set prix vente du plus grand encherrisseur 
		articlesService.changerPrixVente(noArticle, nouvelleEnchereNumber);
		
		
		article.setNoArticle(noArticle);
		Enchere nouvelleEnchere = new Enchere(now,nouvelleEnchereNumber, article, utilisateur);
		
		enchereService.creerEnchere(nouvelleEnchere);
		return "redirect:/encheres/detail?noArticle=" + noArticle;
	}	
	
	@GetMapping("/encheres/delete") 
	public String deleteEnchere(@RequestParam(name = "noArticle") int no_article,@RequestParam(name = "montantRembourse") int montantRembourse ) {
		enchereService.deleteBestEnchere(no_article,getIdUser());
		Utilisateur utilisateur = utilisateurService.getUserById(getIdUser()).get();
		utilisateur.setCredit(utilisateur.getCredit()+montantRembourse);
		utilisateurService.updateUser(utilisateur);
		
		//crédité l'ancien encherrisseur
		
		Enchere ancienneEnchere = enchereService.consulterBestEnchereByIdArticle(no_article);
		if(ancienneEnchere != null) {
			Optional<Utilisateur>  ancienEncherriseur = utilisateurService.getUserById(ancienneEnchere.getUtilisateur().getNoUtilisateur());
			if(ancienEncherriseur.isPresent()) {
				Utilisateur user = ancienEncherriseur.get();
				user.setCredit(user.getCredit()-ancienneEnchere.getMontant());
				utilisateurService.updateUser(user);
				articlesService.changerPrixVente(no_article,ancienneEnchere.getMontant());
			}else {
				articlesService.changerPrixVente(no_article,0);
			}
		}

		return "redirect:/encheres/detail?noArticle=" + no_article;
		
		}
	
	
	@GetMapping("/article/delete")
	public String deleteArticle(@RequestParam(name = "noArticle") int no_article,@RequestParam(name = "montant") int montant ) {
		//rembourser best enchere
		Enchere bestEnchere = enchereService.consulterBestEnchereByIdArticle(no_article);
		if(bestEnchere != null) {
			Optional<Utilisateur>  bestEncherriseur = utilisateurService.getUserById(bestEnchere.getUtilisateur().getNoUtilisateur());
			if(bestEncherriseur.isPresent()) {
				Utilisateur user = bestEncherriseur.get();
				user.setCredit(user.getCredit()+ montant);
				utilisateurService.updateUser(user);
			}
		}
		//delete article (un retrait et les enchères
		articlesService.deleteArticleById(no_article);
		return "redirect:/encheres";
	}
	
	@GetMapping("/encheres/retrait") 
	public String retraitEnchere(@RequestParam(name = "noArticle") int no_article,@RequestParam(name = "montant") int montant ) {
		Article article = articlesService.consulterArticleByIdArticle(no_article);
		Optional<Utilisateur> vendeurOpt = utilisateurService.getUserById(article.getVendeur().getNoUtilisateur());
		Utilisateur vendeur = vendeurOpt.get();
		//on rembourse le vendeur
		vendeur.setCredit(vendeur.getCredit()+montant);
		utilisateurService.updateUser(vendeur);
		//on change le prix de vente pour pouvoir ne plus afficher le retrait
		articlesService.changerPrixVente(no_article,-1);
		return "redirect:/encheres/detail?noArticle=" + no_article;
	}
	
	private int getIdUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return utilisateurService.findUtilisateurByPseudo(name).get().getNoUtilisateur();
	}
}
