package controleur;

import java.util.Date;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import mail.Mail;
import modele.utilisateur;

public class ctrl_page_mail_repondre {
	
	@FXML
	private WebView webview_mail;
	
	//UTILISATEUR
	String pseudo , nom , prenom , mail , mdp , type_user;
	//MAIL
	int numero ;
	String objet , message ;
	String destinateur;
	String date_reception;
	
	public void setScene(Scene scene) {}
	
	
	public void donnes_utilisateur (utilisateur u)
	{
		//INFORMATIONS DE CONNECTION
		pseudo = u.getPseudo();
		mail      = u.getMail();
		prenom    = u.getPrenom();
		mdp      = u.getMdp();
		type_user = u.getType_user();
		nom     = u.getNom();			
	}
	
	public void donnes_mail (Mail m)
	{
		//MAIL SELECTIONNE 
		numero=m.getNumero();
		objet=m.getObjet();
		message=m.getMessage();
		destinateur=m.getDestinateur();
		date_reception=m.getDate_reception();
		
	}

		public void start() 
		{
			WebEngine engine = webview_mail.getEngine();
			engine.loadContent("Vous allez écrire un message à l'attention de : "+destinateur);
			
		}
		
		public void envoyer()
		{
			System.out.println("Message envoyé ! ");
		}




}
