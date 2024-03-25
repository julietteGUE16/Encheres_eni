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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

@Controller
public class profilController {
	
	private UtilisateurService utilisateurService;
	private Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	private boolean pswBlank = false;
	private boolean oldPswWrong = false;
	private boolean pswNotTheSame = false;
	private boolean pseudoExists = false;
	
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
	public String afficherLogin(HttpServletRequest request) { 
		//String rememberMe = request.getParameter("remember-me");
		return "login";
	}
	
	@GetMapping("/register")
    public String displayRegister(Model model) {
		Utilisateur user = new Utilisateur();
		model.addAttribute("user",user);
        return "register";
    }
	
	 @PostMapping("/register")
	    public String registerForm(@Valid @ModelAttribute("user") Utilisateur utilisateur,BindingResult result,@RequestParam String confirmationMdp,@RequestParam String mdp, Model model) {
			model.addAttribute("pswBlank", false);
			model.addAttribute("pswNotTheSame", false);
			model.addAttribute("pseudoExists", false);
		 if (result.hasErrors() ) {
			 	model.addAttribute("user", utilisateur);
				model.addAttribute("errorResult", result);
			}
		 
		 if (mdp.isEmpty() || confirmationMdp.isEmpty()) {
				model.addAttribute("pswBlank", true);
				pswBlank = true;
			}		
			if (!mdp.equals(confirmationMdp)) {
				model.addAttribute("pswNotTheSame", true);
				pswNotTheSame = true;
			}
			
			if (utilisateurService.pseudoExisteDeja(utilisateur.getPseudo())) {
	            model.addAttribute("pseudoExists", true);
	            pseudoExists = true;
			}
			

			if (!result.hasErrors() && !pswBlank && !pswNotTheSame && !pseudoExists) {
				utilisateur.setMotDePasse(mdp);		
				utilisateurService.save(utilisateur);
				return "redirect:/login";
			}

			return "register";
		 
		 
	   
	       /* 
	            return "register"; 
	        } else {
	            utilisateurService.save(utilisateur);
	            return "redirect:/login"; 
	        }*/
	    }
	
	@PostMapping()
	
	@GetMapping("/resetPassword")
    public String resetPassword() {
        return "resetPassword";
    }
	
	
	
	@PostMapping("/resetPasswordValid")
    public String resetPasswordForm(Model modele, @RequestParam String email) {
		if(!email.isBlank() && email.contains("@")) {
			modele.addAttribute("message", true);
			modele.addAttribute("messageAlert",false);
		} else {
			modele.addAttribute("message", false);
			modele.addAttribute("messageAlert",true);
		}
        return "resetPassword";
    }
	
	@GetMapping("/deleteProfil")
	public String deleteProfil() {
		utilisateurService.deleteUser(getIdUser());
		
		return "redirect:/logout";
	}
	

	


}
