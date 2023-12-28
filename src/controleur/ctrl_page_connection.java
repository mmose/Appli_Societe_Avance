package controleur;


import java.awt.Desktop;
import java.io.Console;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.utilisateur;
import mysql.Connexion;
import mysql.Requete;
import vue.Vue_administrateur;
import vue.Vue_utilisateur;

public class ctrl_page_connection {

@FXML
private TextField txt_pseudo;
@FXML
private PasswordField txt_mdp;
	
private Stage primaryStage;

@FXML
private Pane parent;

	private double xOffset = 0 ;
	private double yOffset = 0 ;

	
	 Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	 Alert alert_erreur =  new Alert(AlertType.ERROR);	 
	 
	public void avancez() 
	{
		
		Requete r = new Requete();
		try {
			r.requete_select("SELECT * FROM utilisateur WHERE pseudo='"+txt_pseudo.getText().toString()+"' AND mdp='"+txt_mdp.getText().toString()+"'");
			if (r.getResulSet().next())
			{
				alert_correct.setTitle("Avance - Message ");
		    	alert_correct.setHeaderText("Connection réussie");
		    	alert_correct.showAndWait();
		    	System.out.println("Connection du client "+txt_pseudo.getText());
		    	
		    	
		    	//CREATION DE L'UTILISATEUR CONNECTÉ
		    	utilisateur user_connection = new utilisateur(r.getResulSet().getString(2), r.getResulSet().getString(3), r.getResulSet().getString(4), r.getResulSet().getString(5), r.getResulSet().getString(6), r.getResulSet().getString(7));
				
		    	if (user_connection.getType_user().equals("Administrateur"))
		    	{
		    	Vue_administrateur vc = new Vue_administrateur(user_connection);
				this.primaryStage.setTitle("Page Administrateur");
				this.primaryStage.setScene(vc.getScene());
		    	}
		    	else 
		    	{
		    	Vue_utilisateur vc2 = new Vue_utilisateur(user_connection);
				this.primaryStage.setTitle("Page Client");
				this.primaryStage.setScene(vc2.getScene());
		    	}
				
			}
			else 
			 {
				alert_erreur.setTitle("Avance - Message ");
				alert_erreur.setHeaderText("Erreur de connection");
				alert_erreur.setContentText("Veuillez vérifiez votre pseudo , ainsi que le mot de passe !");
				alert_erreur.showAndWait();
				
			}

		} catch (SQLException e) {
			alert_erreur.setTitle("Avance - Message ");
			alert_erreur.setHeaderText("Erreur de connection");
			alert_erreur.setContentText("Veuillez vérifiez que vous êtes connecté à la base de donnée ! "+e);
			alert_erreur.showAndWait();
			//e.printStackTrace();
		}
		
		
	}
	
	
	public void deplacer_fenetre()
	{
		parent.setOnMousePressed((event) -> {
		xOffset= event.getSceneX();
		yOffset=event.getSceneY();
		
		});
		parent.setOnMouseDragged((event) -> {
			primaryStage.setX(event.getScreenX() - xOffset);
			primaryStage.setY(event.getScreenY() - yOffset);
			primaryStage.setOpacity(0.8f);
		});	
		parent.setOnDragDone((event) -> {
			primaryStage.setOpacity(1.0f);
		});	
		parent.setOnMouseReleased((event) -> {
			primaryStage.setOpacity(1.0f);
		});	
		
	}
	
	public void logo_clique() throws MalformedURLException, IOException, URISyntaxException
	{
		try {
		    Desktop.getDesktop().browse(new URL("http://www.avance.fr").toURI());
		} catch (Exception e) {}
	}
	
	
	public void fermer()
	{
	System.out.println("Bye Bye ! :)");
	System.exit(0);	
	}
	
	public void reduire()
	{
	 primaryStage.setIconified(true);
	}
		
	public void setPrimaryStage(Stage s) {
		this.primaryStage = s;
	}
	
}
