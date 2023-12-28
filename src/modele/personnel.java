package modele;

public class personnel {

	int num_projet ;
	String pseudo_personnel , description_personnel;
	String nom_client;
	
	public personnel(int num_projet, String pseudo_personnel, String description_personnel) {
		super();
		this.num_projet = num_projet;
		this.pseudo_personnel = pseudo_personnel;
		this.description_personnel = description_personnel;
	}
	
	public personnel (int num_projet , String description_personnel)
	{
		this.num_projet=num_projet;
		this.description_personnel = description_personnel;
	}
	
	
	public personnel (int num_projet)
	{
		this.num_projet=num_projet;
	}
	

	public int getNum_projet() {
		return num_projet;
	}

	public void setNum_projet(int num_projet) {
		this.num_projet = num_projet;
	}

	public String getPseudo_personnel() {
		return pseudo_personnel;
	}

	public void setPseudo_personnel(String pseudo_personnel) {
		this.pseudo_personnel = pseudo_personnel;
	}

	public String getDescription_personnel() {
		return description_personnel;
	}

	public void setDescription_personnel(String description_personnel) {
		this.description_personnel = description_personnel;
	}

	@Override
	public String toString() {
		return "personnel [num_projet=" + num_projet + ", pseudo_personnel=" + pseudo_personnel
				+ ", description_personnel=" + description_personnel + "]";
	}
	
	
	
	
}
