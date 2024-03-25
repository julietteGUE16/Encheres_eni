package fr.eni.encheres.controller;

import java.util.ArrayList;
import java.util.List;

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

@Controller
// Injection de la liste des attributs en session
//@SessionAttributes({ "genresEnSession", "membreEnSession", "participantsEnSession" })
public class EncheresController {
	
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	private ArticlesService articlesService;
	private CategorieService categorieService;
	private UtilisateurService utilisateurService;
	private EnchereService enchereService;

	public EncheresController(ArticlesService articlesService, CategorieService categorieService, UtilisateurService utilisateurService,
			EnchereService encheresService) {
		this.articlesService = articlesService;
		this.categorieService = categorieService;
		this.utilisateurService = utilisateurService;
		this.enchereService = encheresService;
	}

	@GetMapping({ "/", "/encheres" })
	public String afficherArticles(Model model) {
		List<Article> articles = articlesService.consulterArticles();
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("articles", articles);
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
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("categories", categories);
		if (id != -1 && nom.trim() == "" && enCours == false && nonDebutee == false && terminee == false ) { // seule la categorie est selectionnée
			List<Article> articles = articlesService.consulterArticlesByCategorie(id);
			model.addAttribute("articles", articles);
		}
		if (nom != "" && id == -1 && id == -1 && enCours == false && nonDebutee == false && terminee == false ) {// seule la recherche est selectionnée
			List<Article> articles = articlesService.consulterArticlesByNomArticle(nom);
			model.addAttribute("articles", articles);
		}
		if (nom != "" && id != -1 && enCours == false && nonDebutee == false && terminee == false ) { //recherche & categorie selectionnées
			List<Article> articles = articlesService.consulterArticlesByNomArticleAndCategorie(nom, id);
			model.addAttribute("articles", articles);
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
		Article article = articlesService.consulterArticleByIdArticle(no_article);
		Retrait retrait = articlesService.consulterRetraitByIDArticle(no_article);
		Enchere enchere = enchereService.consulterBestEnchereByIdArticle(no_article);
		model.addAttribute("article", article);
		model.addAttribute("retrait", retrait);
		model.addAttribute("enchere", enchere);
		return "view-detail";
	}
	
	private int getIdUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return utilisateurService.findUtilisateurByPseudo(name).get().getNoUtilisateur();
	}
}
