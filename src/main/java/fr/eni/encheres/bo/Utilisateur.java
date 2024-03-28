package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * class objet de l'utilisateur
 * Un user peut être ou non un admin.
 * 
 * */
@SuppressWarnings("serial")
public class Utilisateur {

	private int noUtilisateur;
	@NotBlank(message = "Le pseudo ne peut pas être vide")
	@Pattern(regexp = "[a-zA-Z0-9]+", message = "autorisé : (a-z, A-Z, 0-9)")
	private String pseudo;
	@NotBlank(message = "Le nom ne peut pas être vide")
	private String nom;
	@NotBlank(message = "Le prenom ne peut pas être vide")
	private String prenom;
	@NotBlank(message = "L'email ne peut pas être vide")
	@Pattern(regexp = ".+@.+\\..+", message = "L'email doit contenir le symbole '@'")
	private String email;
	@NotBlank(message = "Le telephone ne peut pas être vide")
	@Size(min = 10, max = 10, message = "Le téléphone doit contenir exactement 10 numéros")
	private String telephone;
	@NotBlank(message = "La rue ne peut pas être vide")
	private String rue;
	@NotBlank(message = "Le codePostal ne peut pas être vide")
	@Size(min = 5, max = 5, message = "Le code postale doit contenir 5 numéros")
	private String codePostal;
	@NotBlank(message = "La ville ne peut pas être vide")
	private String ville;
	private String motDePasse;
	private int credit;
	private boolean administrateur;

	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.rue = rue;
		this.codePostal = codePostal;
		this.ville = ville;
		this.motDePasse = motDePasse;
		this.credit = credit;
		this.administrateur = administrateur;
	}

	public Utilisateur(int noUtilisateur, String pseudo) {
		this.noUtilisateur = noUtilisateur;
		this.pseudo = pseudo;

	}

	@Override
	public String toString() {
		return "Utilisateur [idUtilisateur=" + noUtilisateur + ", pseudo=" + pseudo + ", nom=" + nom + ", prenom="
				+ prenom + ", email=" + email + ", telephone=" + telephone + ", rue=" + rue + ", codePostal="
				+ codePostal + ", ville=" + ville + ", motDePasse=" + motDePasse + ", credit=" + credit
				+ ", administrateur=" + administrateur + "]";
	}

	public Utilisateur(String pseudo, String nom, String prenom, String email, String telephone) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
	}

	public Utilisateur(int noUtilisateur, String pseudo, String nom, String prenom, String email, String telephone) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.email = email;
		this.telephone = telephone;
		this.noUtilisateur = noUtilisateur;
	}

	public Utilisateur(String pseudo) {
		this.pseudo = pseudo;
	}

	public int getNoUtilisateur() {
		return noUtilisateur;
	}

	public void setNoUtilisateur(int idUtilisateur) {
		this.noUtilisateur = idUtilisateur;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getRue() {
		return rue;
	}

	public void setRue(String rue) {
		this.rue = rue;
	}

	public String getCodePostal() {
		return codePostal;
	}

	public void setCodePostal(String codePostal) {
		this.codePostal = codePostal;
	}

	public String getVille() {
		return ville;
	}

	public void setVille(String ville) {
		this.ville = ville;
	}

	public String getMotDePasse() {
		return motDePasse;
	}

	public void setMotDePasse(String motDePasse) {
		this.motDePasse = motDePasse;
	}

	public int getCredit() {
		return credit;
	}

	public void setCredit(int credit) {
		this.credit = credit;
	}

	public boolean isAdministrateur() {
		return administrateur;
	}

	public void setAdministrateur(boolean administrateur) {
		this.administrateur = administrateur;
	}

}
