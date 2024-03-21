package fr.eni.encheres.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;
import jakarta.validation.Valid;

@Controller
public class profilController {
	
	private UtilisateurService utilisateurService;
	
	public profilController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	/*
	 * affichage de la page profil
	 */
	@GetMapping({"/profil","/"})
	public String afficherProfil( Model modele) throws UserNotFound { //@RequestParam(name = "id") int id,
		int id = 2;
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(id);
		modele.addAttribute("user",user.get());
		return "profil";
	}
	
	@GetMapping("/modifierProfil")
	public String afficherModifierProfil( Model modele) throws UserNotFound { //@RequestParam(name = "id") int id,
		int id = 2;
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(id);
		modele.addAttribute("user",user.get());
		return "modifierProfil";
	}
	
	@PostMapping("/validerModifProfil")
	public String validerModifProfil(@Valid @ModelAttribute("user") Utilisateur user, BindingResult result,  RedirectAttributes redirectAttributes) {
		//System.out.println("credit"+user.getCredit());
		if (result.hasErrors()) {
			redirectAttributes.addFlashAttribute("user", user);
			redirectAttributes.addFlashAttribute("errorResult", result);
			System.out.println("ERROR");
			return "modifierProfil";
		}else {
			//System.out.println("pseudo ="+user.getPseudo());
			return "redirect:/profil";
		}
		
		
	
	}
	
	/*
	 * @RequestMapping("/ajouter")
	//@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBRE')")
	public String ajoutFilm (@Valid @ModelAttribute("film") Film film) {	
		try {
			filmService.ajouterFilm(film);
		} catch (FilmNotFull e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "redirect:/listFilm";
	}
	 */

}
