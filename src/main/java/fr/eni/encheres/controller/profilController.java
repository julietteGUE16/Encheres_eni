package fr.eni.encheres.controller;

import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.exceptions.UserNotFound;
import jakarta.validation.Valid;

@Controller
public class profilController {
	
	private UtilisateurService utilisateurService;
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	private boolean pswBlank = false;
	private boolean oldPswWrong = false;
	private boolean pswNotTheSame = false;
	
	public profilController(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	
	private int getIdUser() {
		authentication = SecurityContextHolder.getContext().getAuthentication();
		String name = authentication.getName();
		return utilisateurService.findUtilisateurByPseudo(name).get().getNoUtilisateur();
	}
	
	/*
	 * affichage de la page profil
	 */
	@GetMapping("/profil")
	public String afficherProfil( Model modele) throws UserNotFound {
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(getIdUser());
		modele.addAttribute("user",user.get());
		return "profil";
	}
	
	@GetMapping("/profilOther")
	public String afficherProfilOther( Model modele) throws UserNotFound {
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(2); //TODO : other profil
		modele.addAttribute("user",user.get());
		return "profil";
	}
	
	@GetMapping("/modifierProfil")
	public String afficherModifierProfil( Model modele) throws UserNotFound { 
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(getIdUser());
		modele.addAttribute("user",user.get());
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
	
	@GetMapping("/login")
	public String afficherLogin() { 
		return "login";
	}
	
	@GetMapping("/register")
    public String registerForm() {
        return "register";
    }
	@GetMapping("/logout")
	public String afficherLogout() { 
		return "redirect:/encheres";
	}


}
