package fr.eni.encheres.configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import fr.eni.encheres.bo.Utilisateur;

public class UtilisateurDetails implements UserDetails {

	private Utilisateur user;

	public UtilisateurDetails(Utilisateur user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		List<GrantedAuthority> roles = new ArrayList<>();
		roles.add(new SimpleGrantedAuthority("ROLE_MEMBRE"));

		if (user.isAdministrateur()) {
			roles.add(new SimpleGrantedAuthority("ROLE_ADMINISTRATEUR"));
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getMotDePasse();
	}

	public int getNoUtilisateur() {
		return user.getNoUtilisateur();
	}

	@Override
	public String getUsername() {
		return user.getPseudo();
	}

	public String getNom() {
		return user.getNom();
	}

	public int getCredit() {
		return user.getCredit();
	}

	public String getPrenom() {
		return user.getPrenom();
	}

	public String isAdmin() {
		if (user.isAdministrateur()) {
			return "administrateur";
		}
		return "membre";
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
