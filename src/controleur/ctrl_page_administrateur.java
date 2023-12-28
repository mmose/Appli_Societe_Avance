package controleur;

//IMPORTATION

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mail.Sender;
import modele.projet;
import modele.utilisateur;
import modele.utilisateur_basic;
import mysql.Requete;
import outils.Fichier;
import vue.Vue_personnel;
import vue.Vue_connection;
import vue.Vue_creation_projet;
import vue.Vue_modification_projet;




public class ctrl_page_administrateur {

	//HAUT DE PAGE
	@FXML
	private Label identification_connection;
	@FXML
	private Label lbl_mail , lbl_identifiant;
	
	
	//ONGLET TABLEAU DE BORD
	@FXML
	private TableView<projet> table_projet , table_projet_archive;
	@FXML
	private TableColumn<projet,Integer> col_numero,col_numero_archive;
	@FXML
	private TableColumn<projet,String> col_intitule,col_intitule_archive;
	@FXML
	private TableColumn<projet,String> col_etat,col_etat_archive;
	@FXML
	private TableColumn<projet,LocalDate> col_date_debut,col_date_debut_archive;
	@FXML
	private TableColumn<projet,LocalDate> col_date_limite,col_date_limite_archive;
	@FXML
	private TableColumn<projet,String> col_cree_par,col_cree_par_archive;
	@FXML
	private TableColumn<projet,String> col_nom_client,col_nom_client_archive;
	@FXML
	private TableColumn<projet,Integer> col_budget,col_budget_archive;
	@FXML
	private TableColumn<projet,String> col_informations,col_informations_archive;
	
	//OBSERVABLELIST POUR CREER UNE LISTE SELON INSTANCIATION
	ObservableList<projet> oblist = FXCollections.observableArrayList();

	
	//INSTANCIATION DU MODELE PROJET
	private projet projetselect;


	
	
	//FENETRE
	//POUR LE DEPLACEMENT 
	@FXML
	private Pane parent;
	private double xOffset = 0 ;
	private double yOffset = 0 ;
	
	//POUR LES BOITES D'ALERTES
	Alert alert_erreur =  new Alert(AlertType.ERROR);
	Alert alert_correct =  new Alert(AlertType.CONFIRMATION);
	
	
	//DEFINITION DES VARIABLES POUR LA CONNECTION  (ON LES DECLARE ICI , CAR ON LES UTILISES DANS TOUTE LA CLASS)
	String pseudo;
	
	
	
	
	
	
	
	// ------------------------------------------------------------------------------------------------------------------------- //
													/*CONSTRUCTEURS ET METHODES*/
	
	public void setScene(Scene scene) {}
	
	public void donnes_utilisateur (utilisateur u)
	{
		//INFORMATIONS DE CONNECTION
		pseudo = u.getPseudo();

	}
	
	

	public void initialize_onglet_tableau_bord()
	{
		oblist.add(new projet());
	
		col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		col_intitule.setCellValueFactory(new PropertyValueFactory<>("intitule"));
		col_etat.setCellValueFactory(new PropertyValueFactory<>("etat"));
		col_date_debut.setCellValueFactory(new PropertyValueFactory<>("date_debut"));
		col_date_limite.setCellValueFactory(new PropertyValueFactory<>("date_limite"));
		col_cree_par.setCellValueFactory(new PropertyValueFactory<>("cree_par"));
		col_nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
		col_budget.setCellValueFactory(new PropertyValueFactory<>("budget"));
		col_informations.setCellValueFactory(new PropertyValueFactory<>("informations"));
		col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
		
		table_projet.setItems(oblist);
	}
	
	
	


		
	//ONGLET TABLEAU DE BORD
	//BOUTONS
	public void creer_projet()
	{
		 System.out.println("Création d'un projet ");
		 utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
		 Vue_creation_projet vc = new Vue_creation_projet(donnes_utilisateur);
	     Stage stage = new Stage();
         stage.setTitle("Création d'un projet");
         stage.setScene(vc.getScene());
         stage.show();	
	}
	



	

	
	
	//TABLEAU DE BORD - PERMETTRE LE DOUBLE CLICK
	public void selection()
	{
		//DOUBLE CLICK
		table_projet.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	
		        	projetselect = table_projet.getSelectionModel().getSelectedItem();
		    		if (projetselect.equals(table_projet.getSelectionModel().getSelectedItem()))
		    		{
		    		 utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
		    		 Vue_personnel vc = new Vue_personnel(donnes_utilisateur,projetselect);
		    	     Stage stage = new Stage();
		             stage.setTitle("Personnel");
		             stage.setScene(vc.getScene());
		             stage.setResizable(false);
		             stage.show();
		    		} else {		
		    			alert_erreur.setTitle("Avance - Message ");
		    			alert_erreur.setHeaderText("Erreur de sélection");
		    			alert_erreur.setContentText("Veuillez selectionnez un projet pour ajouter un personnel !");
		    			alert_erreur.showAndWait();
		    		}       
		        }
		    }
		});
		
	}
	
	
	
	
	//DRAGGABLE
	public void deplacer_fenetre()
	{
		//GETWINDOWS = ON PREND LA FENETRE DU LABEL IDENTIFICATION CONNECTION
		parent.setOnMousePressed((event) -> {
			xOffset= event.getSceneX();
			yOffset=event.getSceneY();
			
			});
			parent.setOnMouseDragged((event) -> {
				identification_connection.getScene().getWindow().setX(event.getScreenX() - xOffset);
				identification_connection.getScene().getWindow().setY(event.getScreenY() - yOffset);
				identification_connection.getScene().getWindow().setOpacity(0.8f);
			});	
			parent.setOnDragDone((event) -> {
				identification_connection.getScene().getWindow().setOpacity(1.0f);
			});	
			parent.setOnMouseReleased((event) -> {
				identification_connection.getScene().getWindow().setOpacity(1.0f);
			});
	}
	
	
	
	public void reduire ()
	{
		//ON PREND LE STAGE DU LABEL DE CONNECTION UN PEU BIZARRE MAIS C'EST UNE SOLUTION
		((Stage) identification_connection.getScene().getWindow()).setIconified(true);
	}
	
	
	public void deconnexion() throws Exception
	{
		System.out.println("Déconnection du client "+pseudo);	
		Stage stage = (Stage) identification_connection.getScene().getWindow();
		stage.close();
		
		Vue_connection open = new Vue_connection();
		Stage open_stage = new Stage();
		open.start(open_stage);
	}
	


	public void reset() throws SQLException 
	{
		table_projet.getItems().clear();
		table_projet_archive.getItems().clear();
		initialize_onglet_tableau_bord();
;
		
	}

	



}
