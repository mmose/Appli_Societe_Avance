package controleur;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import modele.projet;
import modele.utilisateur;
import mysql.Connexion;
import mysql.Requete;


public class ctrl_page_modification_projet {

	//Variables
		@FXML
		private TextField saisie_intitule;
		@FXML
		private TextField saisie_budget;
		@FXML
		private TextArea informations_projet;
		@FXML
		private ComboBox<String> saisie_etat;
		@FXML
		private ComboBox<String> saisie_client;
		@FXML
		private DatePicker saisie_date_debut;
		@FXML
		private DatePicker saisie_date_limite;
	
	Alert alert_erreur = new Alert(AlertType.ERROR);
	Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	
	String pseudo , mail , prenom , mdp ,type_user , nom ;
	int numero_projet;
	
	ObservableList<String>  etat = FXCollections.observableArrayList();
	ObservableList<String>  client = FXCollections.observableArrayList();
	
	
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
		
		saisie_intitule.setText(p.getIntitule());
		saisie_etat.setValue(p.getEtat());
		saisie_client.setValue(p.getNom_client());
		saisie_date_debut.setValue(p.getDate_debut().toLocalDate());
		saisie_date_limite.setValue(p.getDate_limite().toLocalDate());
		informations_projet.setText(p.getInformations());
		saisie_budget.setText(Integer.toString(p.getBudget()));
	}
	
	public void initialize() throws SQLException
	{
		//CHARGEMENT DU COMBOBOX ETAT
		Requete r = new Requete();
		r.requete_select("SELECT * FROM etat");
		while (r.getResulSet().next())
		{	//TANT QUE IL Y A DU RESEULTAT ON AJOUTE DANS L'OBSERVABLE LIST
		etat.add(r.getResulSet().getString(2));	
		}
		
		//CHARGEMENT DU COMBOBOX ETAT
	    Requete r2 = new Requete();
		r2.requete_select("SELECT * FROM client");
		while (r2.getResulSet().next())
		{	//TANT QUE IL Y A DU RESEULTAT ON AJOUTE DANS L'OBSERVABLE LIST
		client.add(r2.getResulSet().getString(2));	
		}		

		//INTRODUCTION DES ITEMS
		saisie_etat.setItems(etat);	
		saisie_client.setItems(client);	

	}
	
	
	public void valider() throws SQLException
	{
		LocalDate date = LocalDate.now();
		//VERIFICATION DES CHAMPS REMPLIS
		if (saisie_etat.getSelectionModel().getSelectedIndex() == -1 || saisie_intitule.getText().isEmpty() || saisie_date_limite.getValue().equals(null) ||saisie_client.getSelectionModel().getSelectedIndex() == -1 
				|| saisie_date_debut.getValue().isBefore(date) || saisie_date_limite.getValue().isBefore(date))
		{
			alert_erreur.setTitle("Avance - Message ");
	    	alert_erreur.setHeaderText("Veuillez remplir les informations suivantes");
	    	alert_erreur.showAndWait();
	    	
	    	if (saisie_etat.getSelectionModel().getSelectedIndex() == -1)
	    	{
	    		alert_erreur.setTitle("Avance - Message ");
		    	alert_erreur.setHeaderText("Choissiez l'état du projet");
		    	alert_erreur.showAndWait();
	    	}
	    	if (saisie_client.getSelectionModel().getSelectedIndex() == -1)
	    	{
	    		alert_erreur.setTitle("Avance - Message ");
		    	alert_erreur.setHeaderText("Choissiez un client");
		    	alert_erreur.showAndWait();
	    	}
	    	if(saisie_date_debut.getValue().isBefore(date) || saisie_date_limite.getValue().isBefore(date))
	    	{
	    		alert_erreur.setTitle("Avance - Message ");
		    	alert_erreur.setHeaderText("Veuillez choisir une date supérieur ou égal à la date du jour");
		    	alert_erreur.showAndWait();
	    	}
	   
		}
		else 
		{
		//REQUETE UPDATE DANS BDD	
		//Requete r = new Requete();
		Connexion connexion = new Connexion();
		Connection  connection = connexion.creeConnexion();
		PreparedStatement requete;
		try {
		requete=connection.prepareStatement("UPDATE projet SET intitule=?, etat=? , date_debut=? , date_limite=? , cree_par=? , nom_client=?, budget=?, informations=?  WHERE numero=?");
		requete.setString(1, saisie_intitule.getText().toString());
		requete.setString(2, saisie_etat.getValue().toString());
		requete.setDate(3, java.sql.Date.valueOf(saisie_date_debut.getValue()));
		requete.setDate(4, java.sql.Date.valueOf(saisie_date_limite.getValue()));
		requete.setString(5, pseudo);
		requete.setString(6, saisie_client.getValue().toString());
		requete.setInt(7, Integer.valueOf(saisie_budget.getText()));
		requete.setString(8, informations_projet.getText().toString());
		requete.setInt(9, numero_projet);
		requete.executeUpdate();
		
		alert_correct.setTitle("Avance - Message ");
    	alert_correct.setHeaderText("Ok c'est modifié ! N'oubliez pas de rafraichir la page ");
    	alert_correct.showAndWait();
		}
		 catch (SQLException e) 
		{
			 alert_erreur.setTitle("Avance - Message ");
		     alert_erreur.setHeaderText("Il y 'a une erreur dans requete BDD ");
		     alert_erreur.showAndWait();
			e.printStackTrace();
		}
	
    	
    	//FERMETURE DE LA FENETRE
    	Stage stage = (Stage) saisie_intitule.getScene().getWindow();
		stage.close();
		}
				
	}	
	

	

}
