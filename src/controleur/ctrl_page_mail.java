package controleur;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;
import mail.Mail;
import modele.utilisateur;

public class ctrl_page_mail {
	
	@FXML
	private WebView webview_mail;
	@FXML
	private ImageView btn_repondre;
	@FXML
	private Pane parent_mail;
	@FXML 
	private Label lbl_date_reception,lbl_destinateur,lbl_objet_message;
	
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
			lbl_date_reception.setText(date_reception);
			lbl_destinateur.setText(destinateur);
			lbl_objet_message.setText(objet);
			WebEngine engine = webview_mail.getEngine();
			engine.loadContent(message);  
		}
		
		public void repondre_mail() throws IOException
		{
			
			final URL fxmlURL=getClass().getResource("/vue/page_mail_repondre.fxml");
			final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			final Pane root = (Pane)fxmlLoader.load();	
			
			Scene scene = btn_repondre.getScene();
			root.translateYProperty().set(scene.getHeight());
			parent_mail.getChildren().add(root);
			
			utilisateur u= new utilisateur(pseudo, nom ,prenom , mail , mdp, type_user);
			Mail m= new Mail(numero, objet, destinateur, message, date_reception);
			ctrl_page_mail_repondre controleur = fxmlLoader.getController();
			controleur.start();
			controleur.donnes_mail(m);
			controleur.donnes_utilisateur(u);
			controleur.start();
			
			Timeline timeline = new Timeline();
			KeyValue kv = new KeyValue(root.translateYProperty(),0,Interpolator.EASE_IN);
			KeyFrame kf = new KeyFrame (Duration.seconds(1),kv);
			timeline.getKeyFrames().add(kf);
			timeline.play();
			
		}




}
