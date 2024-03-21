package fr.eni.encheres.controller;
import java.sql.Date;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Enchere;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EncheresControllerBis {
	@GetMapping("/ajout-vente")
	public String pageAjoutEnchere() {
		return "ajoutVente";
	}
	
	//Ajout d'une enchère
	@PostMapping("/ajout-vente")
	public String ajoutEnchere(@RequestParam("id_article") int id_article, 
			@RequestParam("date_enchere") Date date_enchere, 
				@RequestParam("montant_enchere") int montant_enchere,
					Model model, @ModelAttribute("SessionUtilisateur") Utilisateur SessionUtilisateur) {
		if(SessionUtilisateur != null && SessionUtilisateur.getIdUtilisateur() >= 1) {
			model.addAttribute("id_article", id_article);
			model.addAttribute("date_enchere", date_enchere);
			model.addAttribute("montant_enchere", montant_enchere);
			
			Enchere enchere = new Enchere();
			
			model.addAttribute("enchere", enchere);
			
		}
		return "redirect:/encheres";
	}
}
