package modele;

public class tache {
	
	int numero ,id_tache , num_projet ;
	String pseudo , description;
	
	public tache(int id_tache, String description) {
		this.id_tache = id_tache;
		this.description = description;	
	}
	
	public tache (int numero , int id_tache , String description)
	{
		this.numero=numero;
		this.id_tache=id_tache;
		this.description=description;
	}
	
	//CONSTRUCTEUR POUR ENVOYER LE NUMERO DE LA TACHE (i) DU TABLEAU 
	public tache (int numero)
	{
		this.numero=numero;
	}
	
	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNum_projet() {
		return num_projet;
	}

	public void setNum_projet(int num_projet) {
		this.num_projet = num_projet;
	}

	public int getId_tache() {
		return id_tache;
	}

	public void setId_tache(int id_tache) {
		this.id_tache = id_tache;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
