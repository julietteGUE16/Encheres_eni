package fr.eni.encheres.controllers;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;

@Controller
public class profilController {
	
	private UtilisateurService utilisateurService;
	
	public profilController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	/*
	 * affichage de la page profil
	 */
	@GetMapping("/profil")
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

}
