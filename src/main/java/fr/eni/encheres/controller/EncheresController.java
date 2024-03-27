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
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		model.addAttribute("utilisateurService", utilisateurService);
		if (name.equals("anonymousUser")) {
			List<Article> articles = articlesService.consulterArticles();
			model.addAttribute("articles", articles);
		}else {
			List<Article> articles = articlesService.consulterArticlesEnModeConnecte(getIdUser());
			model.addAttribute("articles", articles);
		}
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
			@RequestParam(name = "encheresOuvertes", required = false) boolean ouvertes,
			@RequestParam(name = "enchèresEnCours", required = false) boolean achatsEnCours,
			@RequestParam(name = "enchèresRemportées", required = false) boolean remportees, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("categories", categories);
		
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		
		if (id != -1 && nom.trim() == "" && enCours == false && nonDebutee == false && terminee == false ) { // seule la categorie est selectionnée
			if (name.equals("anonymousUser")) {
				List<Article> articles = articlesService.consulterArticlesByCategorie(id);
				model.addAttribute("articles", articles);
			}else {
				List<Article> articles = articlesService.consulterArticlesConnecteByCategorie(getIdUser(), id);
				model.addAttribute("articles", articles);
			}
		}
		if (nom != "" && id == -1 && id == -1 && enCours == false && nonDebutee == false && terminee == false ) {// seule la recherche est selectionnée
			if (name.equals("anonymousUser")) {
				List<Article> articles = articlesService.consulterArticlesByNomArticle(nom);
				model.addAttribute("articles", articles);
			}else {
				List<Article> articles = articlesService.consulterArticlesConnecteByNomArticle(getIdUser(), nom);
				model.addAttribute("articles", articles);
			}
		}
		if (nom != "" && id != -1 && enCours == false && nonDebutee == false && terminee == false ) { //recherche & categorie selectionnées
			if (name.equals("anonymousUser")) {
				List<Article> articles = articlesService.consulterArticlesByNomArticleAndCategorie(nom, id);
				model.addAttribute("articles", articles);
			} else {
				List<Article> articles = articlesService.consulterArticlesConnecteByNomArticleAndCategory(getIdUser(), nom, id);
				model.addAttribute("articles", articles);
			}
		}
		if (nom.trim() == "" && id == -1 && (enCours != false || nonDebutee != false || terminee != false || ouvertes != false || achatsEnCours != false || remportees != false)) { // seules les checkbox sont cochées
			List<Article> articles = new ArrayList<Article>();
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeur(getIdUser()));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebutee(getIdUser()));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTerminee(getIdUser()));
			};
			if (ouvertes ==  true) {
				//
			};
			if (achatsEnCours ==  true) {
				
			};
			if (remportees ==  true) {
				//
			};
			model.addAttribute("articles", articles);
		}
		if (nom.trim() == "" && id != -1 && (enCours != false || nonDebutee != false || terminee != false || ouvertes != false || achatsEnCours != false || remportees != false)) { // les checkbox sont cochées et la categorie
			List<Article> articles = new ArrayList<Article>();
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAndCategorie(getIdUser(), id));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebuteeAndCategorie(getIdUser(), id));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTermineeAndCategorie(getIdUser(), id));
			};
			if (ouvertes ==  true) {
				//
			};
			if (achatsEnCours ==  true) {
				//
			};
			if (remportees ==  true) {
				//
			};
			model.addAttribute("articles", articles);
		}
		if (nom.trim() != "" && id != -1 && (enCours != false || nonDebutee != false || terminee != false || ouvertes != false || achatsEnCours != false || remportees != false)) { // les checkbox sont cochées, la categorie et la recherche
			List<Article> articles = new ArrayList<Article>();
			if (enCours ==  true) {
				articles.addAll(articlesService.consulterArticlesByIdVendeurAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (nonDebutee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteNonDebuteeAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (terminee ==  true) {
				articles.addAll(articlesService.ConsulterArticlesByIdVenteTermineeAndCategorieAndNomArticle(getIdUser(), id, nom));
			};
			if (ouvertes ==  true) {
				//
			};
			if (achatsEnCours ==  true) {
				//
			};
			if (remportees ==  true) {
				//
			};
			model.addAttribute("articles", articles);
		}
		if (nom.trim() == "" && id == -1 && enCours == false && nonDebutee == false && terminee == false && ouvertes == false && achatsEnCours == false && remportees == false) {
			return "redirect:/encheres";
		}
		System.out.println("id: " + id + " nom: " + nom + " enCours: " + enCours + " nonDebutee: " + nonDebutee + " terminee: " + terminee);
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
		utilisateur.setNoUtilisateur(getIdUser());
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
		article.setNoArticle(noArticle);
		Enchere nouvelleEnchere = new Enchere(now,nouvelleEnchereNumber, article, utilisateur);
		
		enchereService.creerEnchere(nouvelleEnchere);
		return "redirect:/encheres/detail?noArticle=" + noArticle;
	}	
	
	private int getIdUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return utilisateurService.findUtilisateurByPseudo(name).get().getNoUtilisateur();
	}
}
