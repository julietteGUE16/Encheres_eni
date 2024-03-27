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
import org.springframework.security.crypto.password.PasswordEncoder;

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
	private boolean passwordTooShort = false;
	private boolean emailExists = false;
	private String emailForReset = "";
    private PasswordEncoder passwordEncoder;

	public profilController(UtilisateurService utilisateurService, PasswordEncoder passwordEncoder) {
		this.utilisateurService = utilisateurService;
		this.passwordEncoder = passwordEncoder;
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
	public String afficherProfil(Model modele) throws UserNotFound {
		modele.addAttribute("utilisateurService", utilisateurService);
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(getIdUser());
		modele.addAttribute("user", user.get());
		modele.addAttribute("userSession", getIdUser());
		return "profil";
	}

	@GetMapping("/profilOther")
	public String afficherProfilOther(@RequestParam(name = "noUtilisateur") int noUtilisateur, Model modele) throws UserNotFound {
		modele.addAttribute("utilisateurService", utilisateurService);
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(noUtilisateur); 
		modele.addAttribute("user", user.get());
		modele.addAttribute("userSession", getIdUser());
		return "profil";
	}

	@GetMapping("/modifierProfil")
	public String afficherModifierProfil(Model modele) throws UserNotFound {
		modele.addAttribute("utilisateurService", utilisateurService);
		Optional<Utilisateur> user = Optional.empty();
		user = utilisateurService.getUserById(getIdUser());
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
		modele.addAttribute("utilisateurService", utilisateurService);
		pswBlank = oldPswWrong = pswNotTheSame = passwordTooShort = emailExists = false;
		modele.addAttribute("pswBlank", false);
		modele.addAttribute("oldPswWrong", false);
		modele.addAttribute("pswNotTheSame", false);
		modele.addAttribute("passwordTooShort", false);
		modele.addAttribute("emailExists", false);
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
			if (!passwordEncoder.matches(ancienMdp, mdp)) {
				modele.addAttribute("oldPswWrong", true);
				oldPswWrong = true;
			}
			if (!confirmationMdp.equals(nouveauMdp)) {
				modele.addAttribute("pswNotTheSame", true);
				pswNotTheSame = true;
			}
			if (nouveauMdp.length() < 5) {
				modele.addAttribute("passwordTooShort", true);
				passwordTooShort = true;
			}
		}
		if (utilisateurService.emailExisteDeja(user.getEmail(), getIdUser())) {
			modele.addAttribute("emailExists", true);
			emailExists = true;

		}
		
		if (!result.hasErrors() && !pswBlank && !oldPswWrong && !pswNotTheSame && !passwordTooShort && !emailExists) {
			if (!nouveauMdp.isEmpty()) {
				mdp = nouveauMdp;
				String motDePasseCrypte = passwordEncoder.encode(mdp);
				user.setMotDePasse(motDePasseCrypte);
			}else {
				user.setMotDePasse(utilisateurService.getUserById(getIdUser()).get().getMotDePasse());
			}
			utilisateurService.updateUser(user);
			return "redirect:/profil";
		}

		return "modifierProfil";

	}

	@GetMapping("/login")
	public String afficherLogin(HttpServletRequest request) {
		// String rememberMe = request.getParameter("remember-me");
		return "login";
	}

	@GetMapping("/register")
	public String displayRegister(Model model) {
		Utilisateur user = new Utilisateur();
		model.addAttribute("user", user);
		return "register";
	}

	@PostMapping("/register")
	public String registerForm(@Valid @ModelAttribute("user") Utilisateur utilisateur, BindingResult result,
			@RequestParam String confirmationMdp, @RequestParam String mdp, Model model) {
		pswBlank = pswNotTheSame = pseudoExists = passwordTooShort = emailExists = false;
		model.addAttribute("pswBlank", false);
		model.addAttribute("pswNotTheSame", false);
		model.addAttribute("pseudoExists", false);
		model.addAttribute("passwordTooShort", false);
		model.addAttribute("emailExists", false);

		if (result.hasErrors()) {
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
		if (utilisateurService.emailExisteDeja(utilisateur.getEmail(),-1)) {
			model.addAttribute("emailExists", true);
			emailExists = true;
		}
		if (mdp.length() < 5) {
			model.addAttribute("passwordTooShort", true);
			passwordTooShort = true;
		}
	
		if (!result.hasErrors() && !pswBlank && !pswNotTheSame && !pseudoExists && !passwordTooShort && !emailExists) {
			String motDePasseCrypte = passwordEncoder.encode(mdp);
	        utilisateur.setMotDePasse(motDePasseCrypte);
			utilisateurService.save(utilisateur);
			return "redirect:/login";
		}

		return "register";


	}
	
	
	
	@GetMapping("/crediter")
	public String crediterAccount(Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		return "credite";
	}
	
	@PostMapping("/crediterAccount")
	public String crediterAccount(@RequestParam int credit, Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		Utilisateur user = utilisateurService.getUserById(getIdUser()).get();
		user.setCredit(user.getCredit() + credit);
		utilisateurService.updateUser(user);
		return "redirect:/profil";
	}
	
	

	@GetMapping("/resetPassword")
	public String resetPassword(Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		return "resetPassword";
	}
	
	@GetMapping("/newPassword")
	public String newPassword(Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		String email = emailForReset;
		model.addAttribute("email", email);
		emailForReset = "";
		return "newPassword";
	}
	
	@PostMapping("/newPasswordValid")
	public String newPasswordForm(Model modele, @RequestParam String email, @RequestParam String mdp, @RequestParam String mdpConfirm) {
		modele.addAttribute("utilisateurService", utilisateurService);
		modele.addAttribute("mdpError", false);
		modele.addAttribute("mdpOk", false);
		if(mdp.length()>=5 && mdp.equals(mdpConfirm)) {
			modele.addAttribute("mdpOk", true);
			Utilisateur user = utilisateurService.findUtilisateurByEmail(email);
			String motDePasseCrypte = passwordEncoder.encode(mdp);
			user.setMotDePasse(motDePasseCrypte);
			utilisateurService.updateUser(user);
		} else {
			modele.addAttribute("mdpError", true);
		}
		return "newPassword";
	}

	@PostMapping("/resetPasswordValid")
	public String resetPasswordForm(Model modele, @RequestParam String email) {
		modele.addAttribute("utilisateurService", utilisateurService);
		int emailExiste = utilisateurService.findEmail(email);
		if (!email.isBlank() && email.contains("@")) {
			if(emailExiste > 0) {
				modele.addAttribute("message", true);
				modele.addAttribute("noEmail", false);
			} else {
				modele.addAttribute("noEmail", true);
				modele.addAttribute("message", false);
			}
		
			modele.addAttribute("messageAlert", false);
		} else {
			modele.addAttribute("message", false);
			modele.addAttribute("messageAlert", true);
		}
		emailForReset = email;
		return "resetPassword";
	}

	@GetMapping("/deleteProfil")
	public String deleteProfil(Model model) {
		model.addAttribute("utilisateurService", utilisateurService);
		utilisateurService.deleteUser(getIdUser());

		return "redirect:/logout";
	}

}
