package fr.eni.encheres.bo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/*
 * class objet de l'utilisateur
 * Un user peut être ou non un admin.
 * 
 * */
public class Utilisateur {
	
	private int idUtilisateur;
	@NotBlank(message = "Le pseudo ne peut pas être vide")
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
	private String codePostal;
	@NotBlank(message = "La ville ne peut pas être vide")
	private String ville;
	private String motDePasse;
	private int credit;
	private boolean administrateur;
	
	public Utilisateur() {
		// TODO Auto-generated constructor stub
	}
	
	
	public Utilisateur(int idUtilisateur, String pseudo, String nom, String prenom, String email, String telephone,
			String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur) {
		this.idUtilisateur = idUtilisateur;
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
	public int getIdUtilisateur() {
		return idUtilisateur;
	}
	public void setIdUtilisateur(int idUtilisateur) {
		this.idUtilisateur = idUtilisateur;
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
