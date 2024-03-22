package fr.eni.encheres.controller;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import fr.eni.encheres.bo.Article;
import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Retrait;

@Controller
// Injection de la liste des attributs en session
//@SessionAttributes({ "genresEnSession", "membreEnSession", "participantsEnSession" })
public class EncheresController {

	@Autowired
	private ArticlesService articlesService;
	@Autowired
	private CategorieService categorieService;

	public EncheresController(ArticlesService articlesService, CategorieService categorieService) {
		this.articlesService = articlesService;
		this.categorieService = categorieService;
	}

	@GetMapping({"/","/encheres"})
	public String afficherArticles(Model model) {
//		System.out.println("\nTous les articles : ");
		List<Article> articles = articlesService.consulterArticles();
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("articles", articles);
		model.addAttribute("categories", categories);
		
		return "view-encheres";
	}

	@PostMapping("/encheresParCategorieEtNom")
	public String afficherArticlesParCategorie(@RequestParam(name = "categorySelect", required = true) int id,
			@RequestParam(name = "searchInput", required = true) String nom, Model model) {
		List<Categorie> categories = categorieService.consulterCategories();
		model.addAttribute("categories", categories);
		if (id != -1 && nom == null) {
			List<Article> articles = articlesService.consulterArticlesByCategorie(id);
			model.addAttribute("articles", articles);
		}
		if (nom != null && id == -1) {
			List<Article> articles = articlesService.consulterArticlesByNomArticle(nom);
			model.addAttribute("articles", articles);
		}
		if (nom != null && id != -1) {
			List<Article> articles = articlesService.consulterArticlesByNomArticleAndCategorie(nom, id);
			model.addAttribute("articles", articles);
		}
		return "view-encheres";
	}
	
	@GetMapping("/encheres/detail")
	public String AfficherUnArticle(@RequestParam(name = "noArticle") int no_article, Model model) {
		Article article = articlesService.consulterArticleByIdArticle(no_article);
		Retrait retrait = articlesService.consulterRetraitByIDArticle(no_article);
		model.addAttribute("article", article);
		System.out.println(retrait);
		model.addAttribute("retrait", retrait);
		return "view-detail";
	}

//	@GetMapping("/detail")
//	public String afficherUnFilm(@RequestParam(name = "id", required = true) long id, Model model) {
//		if (id > 0) {// L'identifiant en base commencera en 1
//			Film film = filmService.consulterFilmParId(id);
//			System.out.println(film);
//			if (film != null) {
//				// Ajout de l'instance dans le modèle
//				model.addAttribute("film", film);
//				return "view-film-detail";
//			} else
//				System.out.println("Film inconnu!!");
//		} else {
//			System.out.println("Identifiant inconnu");
//		}
//		// Par défaut redirection vers l'url pour afficher les films
//		return "redirect:/films";
//	}
//
//	@ModelAttribute("genresEnSession")
//	public List<Genre> chargerGenres() {
//		System.out.println("Chargement en Session - GENRES");
//		return filmService.consulterGenres();
//	}
//
//	/**
//	* - Cette méthode va transmettre l'instance de l'objet Film pour le formulaire
//	*/
//	@GetMapping("/creer")
//	public String creerFilm(Model model, @ModelAttribute("membreEnSession") Membre membreEnSession) {
//		if (membreEnSession != null && membreEnSession.getId() >= 1 && membreEnSession.isAdmin()) {
//		// Il y a un membre en session et c'est un administrateur
//		// Ajout de l'instance dans le modèle
//			model.addAttribute("film", new Film());
//			return "view-film-add";
//		} else {
//			// redirection vers la page des films
//			return "redirect:/films";
//		}
//	}
//
//	// Création d'un nouveau film
//	@PostMapping("/creer")
//	public String creerFilm(@Valid @ModelAttribute("film") Film film, BindingResult result,
//						@ModelAttribute("membreEnSession") Membre membreEnSession) {
//		if (membreEnSession != null && membreEnSession.isAdmin()) {
//
//			if(result.hasErrors()){
//				return "view-film-add";
//			}
//		// Il y a un membre en session et c'est un administrateur
//
//			System.out.println(film);
//			filmService.creerFilm(film);
//			return "redirect:/films";
//		} else {
//			System.out.println("Aucun membre en session");
//			return "redirect:/films";
//		}
//	}
//
//	/**
//	 * - Cette méthode va transmettre l'instance de l'objet Film pour le formulaire
//	 */
//	@GetMapping("/edit")
//	public String editFilm(@RequestParam(name = "idFilm", required = true) long id,Model model, @ModelAttribute("membreEnSession") Membre membreEnSession) {
//		if (membreEnSession != null && membreEnSession.getId() >= 1 && membreEnSession.isAdmin()) {
//			// Il y a un membre en session et c'est un administrateur
//			// Ajout de l'instance dans le modèle
//			Film film =  filmService.consulterFilmParId(id);
//			System.out.println(film);
//			model.addAttribute("film", filmService.consulterFilmParId(id));
//			return "view-film-edit";
//		} else {
//			// redirection vers la page des films
//			return "redirect:/films";
//		}
//	}
//
//	// Création d'un nouveau film
//	@PostMapping("/edit")
//	public String editFilm(@Valid @ModelAttribute("film") Film film, BindingResult result,
//							@ModelAttribute("membreEnSession") Membre membreEnSession) {
//		if (membreEnSession != null && membreEnSession.isAdmin()) {
//
//			if(result.hasErrors()){
//				return "view-film-edit";
//			}
//			// Il y a un membre en session et c'est un administrateur
//
//			System.out.println(film);
//			filmService.updateFilm(film);
//			return "redirect:/films";
//		} else {
//			System.out.println("Aucun membre en session");
//			return "redirect:/films";
//		}
//	}
//	// Injection en session des listes représentant les participants
//	@ModelAttribute("participantsEnSession")
//	public List<Participant> chargerParticipants() {
//		System.out.println("Chargement en Session - PARTICIPANTS");
//		return filmService.consulterParticipants();
//	}
//
//	

}
