package controleur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.projet;
import modele.utilisateur;
import mysql.Requete;

public class ctrl_page_ajouter_tache {
	
	@FXML
	private TextArea area_tache;
	Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	
	String nom , prenom , mail , mdp , type_user , pseudo;
	int numero_projet;
	int i=1;
	
	private ctrl_details_projet controleur_details;
	
	public void setScene(Scene scene){}
	
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
	
	public void donnes_projet (projet p)
	{
		numero_projet=p.getNumero();
	}
	
	
	public void valider() throws IOException 
	{
		i++;
		Requete r = new Requete();
		Connection connection = r.getConnection();
		PreparedStatement requete;
		try {
			requete=connection.prepareStatement("INSERT INTO taches "+ "(num_tache,num_projet,pseudo,description) "+ "VALUES(?,?,?,?) ");
			requete.setInt(1, i);
			requete.setInt(2, numero_projet);
			requete.setString(3, pseudo);
			requete.setString(4, area_tache.getText());
			requete.executeUpdate();
			
			alert_correct.setTitle("Avance - Message ");
	    	alert_correct.setHeaderText("Tâche ajouté !");
	    	alert_correct.showAndWait();
	    	
					 
			//FERMETURE DE LA PAGE		 
	    	Stage stage = (Stage) area_tache.getScene().getWindow();
			stage.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	

	
}
