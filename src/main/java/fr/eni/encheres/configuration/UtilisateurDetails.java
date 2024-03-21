package fr.eni.encheres.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.eni.encheres.bo.Utilisateur;




public class UtilisateurDetails implements UserDetails{

	private Utilisateur user;
	
	public UtilisateurDetails(Utilisateur user) {
		this.user = user;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		List<GrantedAuthority> roles= new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_MEMBRE"));
		
		if(user.isAdministrateur()) {
			roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getMotDePasse();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
