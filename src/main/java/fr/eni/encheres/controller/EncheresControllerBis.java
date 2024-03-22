package fr.eni.encheres.controller;
import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.eni.encheres.bll.ArticlesService;
import fr.eni.encheres.bll.CategorieService;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
//@RequestMapping("/ajout-vente")
public class EncheresControllerBis {
	
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	
	private CategorieService categorieService;
	private ArticlesService articleService;
	private UtilisateurService utilisateurService;
	
	public EncheresControllerBis(CategorieService categorieService, ArticlesService articleService, UtilisateurService utilisateurService) {
		this.categorieService = categorieService;
		this.articleService = articleService;
		this.utilisateurService = utilisateurService;
	}
	
	private Utilisateur getUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		Utilisateur user = utilisateurService.findUtilisateurByPseudo(name).get();
		return user;
	}
	
	
	@GetMapping("/ajout-vente")
	public String pageAjoutVente(Model model, 
			@ModelAttribute("userSession") Utilisateur userSession) {
		//Si l'user est connecté
		//if(userSession != null && userSession.getNoUtilisateur() >= 1) {
			//Instanciation du formulaire 
			Article article = new Article();
			model.addAttribute(article);
			model.addAttribute("categories", categorieService.consulterCategories());
			return "ajoutVente";
		//}
		//return "ajoutVente";
	}
	
	
	@PostMapping("/ajout-vente-valider")
	public String ajoutVente(@ModelAttribute("article") Article article) {

		article.setVendeur(this.getUser());
		articleService.creerArticle(article);
		return "redirect:/encheres";
	}	
}
