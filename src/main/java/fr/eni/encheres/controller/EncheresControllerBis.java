package fr.eni.encheres.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.RetraitService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Retrait;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/ajout-vente")
public class EncheresControllerBis {

	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	private CategorieService categorieService;
	private ArticlesService articleService;
	private RetraitService retraitService;
	private UtilisateurService utilisateurService;

	public EncheresControllerBis(CategorieService categorieService, ArticlesService articleService,
			UtilisateurService utilisateurService, RetraitService retraitService) {

		this.categorieService = categorieService;
		this.articleService = articleService;
		this.utilisateurService = utilisateurService;
		this.retraitService = retraitService;
	}

	private Utilisateur getUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		Utilisateur user = utilisateurService.findUtilisateurByPseudo(name).get();
		return user;
	}

	private int getIdUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return utilisateurService.findUtilisateurByPseudo(name).get().getNoUtilisateur();
	}

	@GetMapping("/ajout-vente")
	public String pageAjoutVente(Model model, @ModelAttribute("userSession") Utilisateur userSession) {
		model.addAttribute("utilisateurService", utilisateurService);
		// Si l'user est connecté
		// if(userSession != null && userSession.getNoUtilisateur() >= 1) {
		// Instanciation du formulaire
		Article article = new Article();
		model.addAttribute(article);

		Optional<Utilisateur> user = Optional.empty();
		try {
			user = utilisateurService.getUserById(getIdUser());
		} catch (UserNotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		model.addAttribute("user", user.get());

		model.addAttribute("categories", categorieService.consulterCategories());
		return "ajoutVente";
		/*
		 * } return "ajoutVente";
		 */
	}

	@RequestMapping(value = "/ajout-vente", method = RequestMethod.POST, params = "cancel")
	public String annuler(@Valid @ModelAttribute("article") Article article, BindingResult result, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		model.addAttribute("message", "Redirection...");
		return "view-encheres";
	}

	@PostMapping("/ajout-vente-valider")
	public String ajoutVente(@Valid @ModelAttribute("article") Article article, BindingResult result,
			@RequestParam String rue, @RequestParam String code_postal, @RequestParam String ville, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		model.addAttribute("errorDate", false);
		model.addAttribute("errorDateNull", false);
		if(article.getFin() == null || (article.getDebut() == null)) {
			model.addAttribute("errorDateNull", true);
		}
		
		if(article.getFin() != null && (article.getDebut() != null)) {
			if(article.getFin().before(article.getDebut())) {
				model.addAttribute("errorDate", true);
			}
		}

		if (result.hasErrors()) {
			model.addAttribute("errorResult", result);
			
		}
		
		if(article.getFin() == null || (article.getDebut() == null)) {
			Optional<Utilisateur> user = Optional.empty();
			try {
				user = utilisateurService.getUserById(getIdUser());
			} catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			model.addAttribute("user", user.get());

			model.addAttribute("categories", categorieService.consulterCategories());
			return "ajoutVente";
		}
		if(result.hasErrors() || article.getFin().before(article.getDebut())) {
			Optional<Utilisateur> user = Optional.empty();
			try {
				user = utilisateurService.getUserById(getIdUser());
			} catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			model.addAttribute("user", user.get());

			model.addAttribute("categories", categorieService.consulterCategories());
			return "ajoutVente";
		}

		article.setVendeur(this.getUser());
		int cle = articleService.creerArticle(article);

		Retrait retrait = new Retrait(cle, rue, code_postal, ville);
		retraitService.creerRetrait(retrait);

		return "redirect:/encheres";
	}

	@GetMapping("/modifier-vente")
	public String pageModifierVente(@Valid @RequestParam(name = "noArticle") int no_article, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		Article article = articleService.consulterArticleByIdArticle(no_article);
		Retrait retrait = articleService.consulterRetraitByIDArticle(no_article);

		model.addAttribute("article", article);
		model.addAttribute("retrait", retrait);
		model.addAttribute("categories", categorieService.consulterCategories());

		return "modifierVente";
	}

	@PostMapping("/modifier-vente-valider")
	public String modifierVente(@Valid @RequestParam(name = "noArticle") int no_article,
			@ModelAttribute("article") Article article, @ModelAttribute("retrait") Retrait retrait,
			BindingResult result, @RequestParam String rue, @RequestParam String code_postal,
			@RequestParam String ville, Model model) {
		model.addAttribute("errorDate", true);
		model.addAttribute("errorDateNull", false);
		model.addAttribute("utilisateurService", utilisateurService);
		retrait.setRue(rue);
		retrait.setCodePostal(code_postal);
		retrait.setVille(ville);
		
		
		if(article.getFin() == null || (article.getDebut() == null)) {
			model.addAttribute("errorDateNull", true);
		}
		
		retrait.setNoRetrait(no_article);
		
		if(article.getFin() != null && (article.getDebut() != null)) {
			if(article.getFin().before(article.getDebut())) {
				model.addAttribute("errorDate", true);
			}
		}
		

		if (result.hasErrors()) {
			model.addAttribute("errorResult", result);
			
		}
		
		if(article.getFin() == null || (article.getDebut() == null)) {
			Optional<Utilisateur> user = Optional.empty();
			try {
				user = utilisateurService.getUserById(getIdUser());
			} catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			model.addAttribute("user", user.get());

			model.addAttribute("categories", categorieService.consulterCategories());
			return "modifierVente";
		}
		
		if(result.hasErrors() || article.getFin().before(article.getDebut())) {
			Optional<Utilisateur> user = Optional.empty();
			try {
				user = utilisateurService.getUserById(getIdUser());
			} catch (UserNotFound e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			model.addAttribute("user", user.get());
			model.addAttribute("article", article);
			model.addAttribute("retrait", retrait);
			model.addAttribute("categories", categorieService.consulterCategories());
			model.addAttribute("errorResult", result);
			return "modifierVente";
		}

		
		
		article.setVendeur(this.getUser());
		articleService.modifierArticle(article);
		retraitService.modifierRetrait(retrait);
		return "redirect:/encheres";
	}

}
