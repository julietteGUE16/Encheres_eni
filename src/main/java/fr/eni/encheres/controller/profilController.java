package fr.eni.encheres.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;
import jakarta.validation.Valid;

@Controller
public class profilController {

	private UtilisateurService utilisateurService;
	private boolean pswBlank = false;
	private boolean oldPswWrong = false;
	private boolean pswNotTheSame = false;

	public profilController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}

	/*
	 * affichage de la page profil
	 */
	@GetMapping({ "/profil", "/" })
	public String afficherProfil(Model modele) throws UserNotFound { // @RequestParam(name = "id") int id,
		int id = 2;
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(id);
		modele.addAttribute("user", user.get());
		return "profil";
	}

	@GetMapping("/modifierProfil")
	public String afficherModifierProfil(Model modele) throws UserNotFound { // @RequestParam(name = "id") int id,
		int id = 2;
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(id);
		modele.addAttribute("user", user.get());
		modele.addAttribute("pswBlank", false);
		modele.addAttribute("oldPswWrong", false);
		modele.addAttribute("pswNotTheSame", false);
		return "modifierProfil";
	}

	@PostMapping("/validerModifProfil")
	public String validerModifProfil(@Valid @ModelAttribute("user") Utilisateur user, BindingResult result,
			RedirectAttributes redirectAttributes, @RequestParam String ancienMdp, @RequestParam String nouveauMdp,
			@RequestParam String confirmationMdp, Model modele) {
		pswBlank = oldPswWrong = pswNotTheSame = false;
		modele.addAttribute("pswBlank", false);
		modele.addAttribute("oldPswWrong", false);
		modele.addAttribute("pswNotTheSame", false);
		String mdp = utilisateurService.getUserPasswordById(user.getNoUtilisateur());

		if (result.hasErrors()) {
			modele.addAttribute("user", user);
			modele.addAttribute("errorResult", result);
		}
		if (!ancienMdp.isEmpty() || !nouveauMdp.isEmpty() || !nouveauMdp.isEmpty()) {
			if (ancienMdp.isEmpty() || nouveauMdp.isEmpty() || confirmationMdp.isEmpty()) {
				modele.addAttribute("pswBlank", true);
				pswBlank = true;
			}
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			// TODO : requete enlever la version mock    !!
			//!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
			//mdp = "" ;
		
			if (!mdp.equals(ancienMdp)) {
				modele.addAttribute("oldPswWrong", true);
				oldPswWrong = true;
			}
			if (!confirmationMdp.equals(nouveauMdp)) {
				modele.addAttribute("pswNotTheSame", true);
				pswNotTheSame = true;
			}
		}

		if (!result.hasErrors() && !pswBlank && !oldPswWrong && !pswNotTheSame) {
			if(!nouveauMdp.isEmpty()) {
				mdp = nouveauMdp;
			}
			user.setMotDePasse(mdp);		
			utilisateurService.updateUser(user);
			return "redirect:/profil";
		}

		return "modifierProfil";
	}

	/*
	 * @RequestMapping("/ajouter")
	 * //@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MEMBRE')") public String
	 * ajoutFilm (@Valid @ModelAttribute("film") Film film) { try {
	 * filmService.ajouterFilm(film); } catch (FilmNotFull e) { // TODO
	 * Auto-generated catch block e.printStackTrace(); } return
	 * "redirect:/listFilm"; }
	 */

}
