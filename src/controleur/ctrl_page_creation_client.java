package controleur;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import mysql.Requete;

public class ctrl_page_creation_client {

	//Variables
	@FXML
	private TextField saisie_nom_client,saisie_codepostal_client,saisie_adresse_client,saisie_ville_client,saisie_telephone_client,saisie_mail_client;
	Alert alert_erreur = new Alert(AlertType.ERROR);
	Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	
	public void setScene(Scene scene){}
	
	public void valider() throws SQLException
	{
		//VERIFICATION DES CHAMPS REMPLIS
		if (saisie_nom_client.getText().isEmpty() || saisie_codepostal_client.getText().isEmpty() || saisie_adresse_client.getText().isEmpty() || saisie_ville_client.getText().isEmpty() || saisie_telephone_client.getText().isEmpty() 
			|| saisie_telephone_client.getText().length()>10 || saisie_telephone_client.getText().length()<10)
		{
			alert_erreur.setTitle("Avance - Message ");
	    	alert_erreur.setHeaderText("Veuillez remplir les informations suivantes");
	    	alert_erreur.showAndWait();
	    	
	    	if (saisie_telephone_client.getText().length()>10 || saisie_telephone_client.getText().length()<10)
	    	{
	    		alert_erreur.setTitle("Avance - Message ");
		    	alert_erreur.setHeaderText("Numéro invalide");
		    	alert_erreur.showAndWait();
	    	}
	    	
	    	if (saisie_codepostal_client.getText().length()>5 || saisie_codepostal_client.getText().length()<5)
	    	{
	    		alert_erreur.setTitle("Avance - Message ");
		    	alert_erreur.setHeaderText("Code postal invalide");
		    	alert_erreur.showAndWait();
	    	}
		}
		else 
		{
			//INSERTIONS DES DONNES DANS LA BDD	
			Requete r = new Requete();
			r.requete_insert ("INSERT INTO client "+ "(nom_client,adresse_client,code_postal,ville_client,mail_client,telephone_client) "+ "VALUES( '"+saisie_nom_client.getText().toString()+"', '"+saisie_adresse_client.getText().toString()+"','"+saisie_codepostal_client.getText().toString()+"','"+saisie_ville_client.getText().toString()+"'"
					+ ",'"+saisie_mail_client.getText().toString()+"','"+saisie_telephone_client.getText().toString()+"')");
			alert_correct.setTitle("Avance - Message ");
	    	alert_correct.setHeaderText("Client ajouté !");
	    	alert_correct.showAndWait();
	    	
	    	//FERMETURE DE LA PAGE
	    	Stage stage = (Stage) saisie_nom_client.getScene().getWindow();
			stage.close();
		}
	}
	
}
