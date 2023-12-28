package modele;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class projet {
	
	int numero , budget ;
	String intitule , cree_par , etat , nom_client ,informations ;
	Date date_debut;
	Date date_limite;
	
	
	
	public projet(int numero ,String intitule , String etat , Date date_debut , Date date_limite , String cree_par , String nom_client , int budget , String informations)
	{
		this.numero = numero;
		this.etat = etat ; 
		this.cree_par = cree_par;
		this.intitule = intitule;
		this.date_debut = date_debut;
		this.date_limite = date_limite;
		this.budget=budget;
		this.nom_client=nom_client;
		this.informations=informations;
	}
	
	//POUR ONGLET MESSAGE 
	public projet (int numero , String intitule)
	{
		this.numero = numero;
		this.intitule = intitule;
	}
	
	
	public projet (String intitule)
	{
		this.intitule=intitule;
	}
	
	public projet (int numero)
	{
		this.numero= numero;
	}
	
	public String date_debut_fr()
	{
		SimpleDateFormat format_fr = new SimpleDateFormat("MM/dd/yyyy");
		return format_fr.format(date_debut);
	}
	
	
	public String getInformations() {
		return informations;
	}

	public void setInformations(String informations) {
		this.informations = informations;
	}

	public int getBudget() {
		return budget;
	}

	public void setBudget(int budget) {
		this.budget = budget;
	}

	public String getNom_client() {
		return nom_client;
	}

	public void setNom_client(String nom_client) {
		this.nom_client = nom_client;
	}


	

	public int getNumero() {
		return numero;
	}


	public void setNumero(int numero) {
		this.numero = numero;
	}




	public String getEtat() {
		return etat;
	}


	public void setEtat(String etat) {
		this.etat = etat;
	}




	public String getCree_par() {
		return cree_par;
	}


	public void setCree_par(String cree_par) {
		this.cree_par = cree_par;
	}


	public String getIntitule() {
		return intitule;
	}


	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}


	public Date getDate_debut() {
		return date_debut;
	}


	public void setDate_debut(Date date_debut) {
		this.date_debut = date_debut;
	}


	public Date getDate_limite() {
		return date_limite;
	}


	public void setDate_limite(Date date_limite) {
		this.date_limite = date_limite;
	}
	
	public String affichage()
	{
	return 	""
			+ ""
			+ ""
			+ "Numéro de projet :      " + "\n" + numero 
			+ "\n"+ "\n"+
			"Intitule :              " + "\n" + intitule 
			+ "\n"+ "\n"+
			"Etat d'avancement :     " + "\n" + etat 
			+ "\n"+ "\n"+
			"Date début :           " + "\n" + date_debut
			+ "\n"+ "\n"+
			"Date limite :           " + "\n" + date_limite
			+ "\n"+ "\n"+
			"Budget      :           " + "\n" + budget 
			+ "\n"+ "\n"+
			"Client      :           " + "\n" + nom_client 
			+ "\n"+ "\n"+
			"Créer par   :           " + "\n" + cree_par
			+ "\n"+ "\n"+
			"Informations relatives au projet   :           " + "\n" + informations;
			
	
	}
	
}
