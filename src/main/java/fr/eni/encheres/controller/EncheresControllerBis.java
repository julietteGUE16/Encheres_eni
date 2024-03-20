package fr.eni.encheres.controller;
import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import fr.eni.encheres.bo.Categorie;
import fr.eni.encheres.bo.Utilisateur;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class EncheresControllerBis {
	@GetMapping("/ajout-encheres")
	public String ajoutEnchere() {
		return "ajoutEnchere";
	}
	
	/*@RequestMapping(value="/encheres", method = RequestMethod.GET)
	public String redirectListeEncheres() {
		return "redirect:encheres";
	}*/
	
   /* @PostMapping(path="/encheres") //Ajout d'une vente/enchère
	 public @ResponseBody String ajouterVente (@RequestParam String nom
	     , @RequestParam String description, @RequestParam LocalDateTime debut,
	        @RequestParam LocalDateTime fin, @RequestParam int miseAprix, 
	        @RequestParam Utilisateur vendeur, @RequestParam Categorie categorie) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request


	  }*/
}
