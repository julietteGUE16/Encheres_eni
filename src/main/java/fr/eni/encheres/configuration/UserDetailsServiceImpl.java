package fr.eni.encheres.configuration;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import fr.eni.encheres.bll.UtilisateurService;
import fr.eni.encheres.bo.Utilisateur;


@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{

	private UtilisateurService utilisateurService;
	
	public UserDetailsServiceImpl(UtilisateurService utilisateurService) {
		this.utilisateurService = utilisateurService;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Utilisateur> optUser = utilisateurService.findUtilisateurByPseudo(username);
		if(optUser.isEmpty()) {
			throw new UsernameNotFoundException(username);
		}
		return new UtilisateurDetails(optUser.get());


	}

	
}