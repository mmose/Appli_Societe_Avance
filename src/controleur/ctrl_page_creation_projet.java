package controleur;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Observable;

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
import modele.utilisateur;
import mysql.Requete;
import vue.Vue_creation_client;
import vue.Vue_modification_projet;


public class ctrl_page_creation_projet {

	
	
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
	
	String nom , prenom , mail , mdp , type_user , pseudo;
	
	ObservableList<String>  etat = FXCollections.observableArrayList();
	ObservableList<String>  client = FXCollections.observableArrayList();
	private LocalDate localDate;
	
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
	
	
	public void initialize() throws SQLException
	{
		//CHARGEMENT DU COMBOBOX ETAT
		Requete r = new Requete();
		r.requete_select("SELECT * FROM etat");
		while (r.getResulSet().next())
		{	//TANT QUE IL Y A DU RESEULTAT ON AJOUTE DANS L'OBSERVABLE LIST
			etat.add(r.getResulSet().getString(2));	
		}
		
		//CHARGEMENT DU COMBOBOX CLIENT
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
		//INSERTION DANS LA BDD	
		Requete r = new Requete();
		Connection connection = r.getConnection();
		PreparedStatement requete;
		try {
			requete = connection.prepareStatement("INSERT INTO projet "+ "(intitule,etat,date_debut,date_limite,cree_par,nom_client,budget,informations) "+ "VALUES(?,?,?,?,?,?,?,?) ");
			requete.setString(1, saisie_intitule.getText().toString());
			requete.setString(2, saisie_etat.getValue().toString());
			requete.setDate(3, java.sql.Date.valueOf(saisie_date_debut.getValue()));
			requete.setDate(4, java.sql.Date.valueOf(saisie_date_limite.getValue()));
			requete.setString(5, pseudo);
			requete.setString(6, saisie_client.getValue().toString());
			requete.setInt(7, Integer.valueOf(saisie_budget.getText()));
			requete.setString(8, informations_projet.getText().toString());
			requete.executeUpdate();
			
			alert_correct.setTitle("Avance - Message ");
	    	alert_correct.setHeaderText("Projet crée - Ouai c'est bon !  ");
	    	alert_correct.showAndWait();
	    	
			}
			 catch (SQLException e) 
			{
				 alert_erreur.setTitle("Avance - Message ");
			     alert_erreur.setHeaderText("Il y 'a une erreur dans requete BDD ");
			     alert_erreur.showAndWait();
				e.printStackTrace();
			}
		
    	//FERMETURE DE LA PAGE
    	Stage stage = (Stage) saisie_intitule.getScene().getWindow();
		stage.close();
		}	
	}	
	
	public void nouveau_client ()
	{
		System.out.println("Création d'un nouveau client");
		Vue_creation_client vc = new Vue_creation_client();
	    Stage stage = new Stage();
        stage.setTitle("Création d'un nouveau client");
        stage.setScene(vc.getScene());
        stage.show();	
	}
	

	
	
}
