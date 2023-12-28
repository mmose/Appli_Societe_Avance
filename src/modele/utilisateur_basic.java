package modele;

public class utilisateur_basic {
	
	String nom , prenom , mail ;

	public utilisateur_basic(String nom, String prenom) {
		this.nom = nom;
		this.prenom = prenom;
	
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

	@Override
	public String toString() {
		return nom+" "+prenom+"  ";
	}
	
	
	

}
