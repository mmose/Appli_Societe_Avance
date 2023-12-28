package controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import modele.projet;
import modele.tache;
import modele.utilisateur;
import mysql.Requete;

public class ctrl_page_modifier_tache {
	
	@FXML
	private TextArea area_tache;
	Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	
	String nom , prenom , mail , mdp , type_user , pseudo;
	int id_tache ,numero_tache;
	String description_tache;
	
	Alert alert_erreur =  new Alert(AlertType.ERROR);
	
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
	
	public void donnes_tache (tache t)
	{
		id_tache = t.getId_tache();
		numero_tache=t.getNumero();
		area_tache.setText(t.getDescription()); 
	}
	
	
	// IL FAUT RECUPERER LE ID _ TACHE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
	
	public void valider() throws SQLException 
	{
		        //VERIFICATIONS DU CHAMPS 
				if  (area_tache.getText().isEmpty() )
				{
					alert_erreur.setTitle("Avance - Message ");
			    	alert_erreur.setHeaderText("Veuillez remplir les informations suivantes");
			    	alert_erreur.showAndWait();
				}
				else 
				{
				//REQUETE UPDATE DANS BDD	
				Requete r = new Requete();
				Connection connection = r.getConnection();
				PreparedStatement requete;
				try {
				requete=connection.prepareStatement("UPDATE taches SET description=?, num_tache=?  WHERE id_tache=? ");
				requete.setString(1, area_tache.getText());
				requete.setInt(2, numero_tache);
				requete.setInt(3, id_tache);
				requete.executeUpdate();
				
				alert_correct.setTitle("Avance - Message ");
		    	alert_correct.setHeaderText("Ok c'est modifié ! N'oubliez pas de rafraichir la page ");
		    	alert_correct.showAndWait();
		    	
		    	//FERMETURE DE LA FENETRE
		    	Stage stage = (Stage) area_tache.getScene().getWindow();
				stage.close();
				
					} catch (SQLException e) {
						e.printStackTrace();
					}

				}
				
				
	}
	

	
}
