package modele;

public class utilisateur {

	String pseudo , nom , prenom , mail , mdp , type_user , fonction;


	public utilisateur(String pseudo, String nom, String prenom, String mail, String mdp, String type_user) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.prenom = prenom;
		this.mail = mail;
		this.mdp = mdp;
		this.type_user = type_user;
	}
	
	public utilisateur(String pseudo , String fonction)
	{
		this.pseudo=pseudo;
		this.fonction=fonction;
	}
	
	public utilisateur (String pseudo)
	{
		this.pseudo=pseudo;
	}
	
	public  utilisateur (String nom, String prenom,String mail)
	{
		this.nom = nom;
		this.prenom = prenom;
		this.mail=mail;
	}
	
	public String affichage()
	{
		return pseudo ;
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

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getMdp() {
		return mdp;
	}

	public void setMdp(String mdp) {
		this.mdp = mdp;
	}

	public String getType_user() {
		return type_user;
	}

	public void setType_user(String type_user) {
		this.type_user = type_user;
	}
	

	public String getFonction() {
		return fonction;
	}

	public void setFonction(String fonction) {
		this.fonction = fonction;
	}

	public String toString() {
		return pseudo ;
	}
	
	
}
