package controleur;


import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;

import javax.mail.Address;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import mail.Mail;
import mail.Receiver;
import mail.Receiver_IMAP;
import mail.Sender;
import modele.personnel;
import modele.personnel2;
import modele.projet;
import modele.utilisateur;
import mysql.Requete;
import vue.Vue_personnel;
import vue.Vue_connection;
import vue.Vue_creation_projet;
import vue.Vue_details_projet;
import vue.Vue_mail;
import vue.Vue_modification_projet;

public class ctrl_page_utilisateur {

	//ONGLET TABLEAU DE BORD
	@FXML
	private Label identification_connection;
	@FXML
	private TableView<personnel2> table_personnel;
	@FXML
	private TableColumn<personnel2,Integer> col_numero;
	@FXML
	private TableColumn<personnel2,String> col_description;
	@FXML
	private TableColumn<personnel2,String> col_nom_client ;
	@FXML
	private Label lbl_mail , lbl_identifiant;
	@FXML
	private WebView page_web;
	
	private personnel2 personnelselect;
	
	//ONGLET MESSAGE
	@FXML
	private Label lbl_destinateur,lbl_objet;
	@FXML
	private VBox vbox_projet_message;
	@FXML
	private  ListView<String> listview_projet;
	@FXML
	private  ListView<String> listview_personnel_projet;
	@FXML
	private Button btn_envoyer_message;
	@FXML
	private TextArea contenu_message;
	@FXML
	private VBox vbox_message;
	String intitule_projet_select,mail_destinataire;
		
	@FXML
	private TableView<Mail> tableview_mail;
	@FXML
	private TableColumn<Mail,String> col_date_reception_mail;
	@FXML
	private TableColumn<Mail,String> col_destinateur_mail;
	@FXML
	private TableColumn<Mail,String> col_objet_mail;
	@FXML
	private TableColumn<Mail,Integer> col_numero_mail;
	@FXML
	private TableColumn<Mail,Integer> col_message_mail;
	
	private Mail mailselect;


	//ONGLET AIDE
	@FXML
	private TextField saisie_objet_message;
	
	
	Alert alert_erreur =  new Alert(AlertType.ERROR);
	Alert alert_correct =  new Alert(AlertType.CONFIRMATION);
	
	ObservableList<personnel2> oblist = FXCollections.observableArrayList();
	
	String pseudo , nom , prenom , mail , mdp , type_user;
	
	//POUR LE DEPLACEMENT DE LA FENETRE
		@FXML
		private Pane parent;
		private double xOffset = 0 ;
		private double yOffset = 0 ;
		
	
		
		
		
		public void donnes_utilisateur (utilisateur u)
	{
		//INFORMATIONS DE CONNECTION
		pseudo = u.getPseudo();
		mail      = u.getMail();
		prenom    = u.getPrenom();
		mdp      = u.getMdp();
		type_user = u.getType_user();
		nom     = u.getNom();		
		lbl_identifiant.setText(nom+" "+prenom);
		lbl_mail.setText(mail);
		
	}
	
	
	
	//JE PEUT PAS METTRE INITIALIZE CAR PSEUDO_PERSONNEL EST DEFINIS
	public void start()
	{
	
		//AFFICHER LES INFORMATIONS DE TOUS LES PROJETS SUR LE TABLEAU
		Requete r1 = new Requete();
		try {
			r1.requete_select("SELECT num_projet , description_personnel , nom_client  FROM personnel P, projet T WHERE pseudo_personnel='"+pseudo.toString()+"' AND P.num_projet=T.numero AND numero NOT IN (SELECT numero FROM projet WHERE etat='Livre Facture')");
			while (r1.getResulSet().next())
			{
				 oblist.add(new personnel2(r1.getResulSet().getInt(1), r1.getResulSet().getString(2),r1.getResulSet().getString(3)));
			}
		} catch (SQLException e) {
			System.out.println("Impossible d'afficher les valeurs au tableau");
			e.printStackTrace();
		}
		col_numero.setCellValueFactory(new PropertyValueFactory<>("num_projet"));
		col_description.setCellValueFactory(new PropertyValueFactory<>("description_personnel"));
		col_nom_client.setCellValueFactory(new PropertyValueFactory<>("nom_client"));
		
		table_personnel.setItems(oblist);
		
		//AFFICHER UNE PAGE WEB --> TEMPS REELS(ONGLET)
		WebEngine engine = page_web.getEngine();
		engine.load("http://localhost/agence_avance/calendrier_avance/demos/calendrier_avance_java_theme_user.php?pseudo="+pseudo);
		
		//engine.setJavaScriptEnabled(true);
		//engine.executeScript("javafx(mesa)");
		//engine.executeScript("updateHello('bonjour')");
		//https://o7planning.org/fr/11151/tutoriel-javafx-webview-et-webengine	
	
	
	}
	
	public void initialize_onglet_message() throws Exception
	{
		//RECUPERE TOUT LES INTITULE DE TOUT LES PROJETS
				Requete r3 = new Requete();
				r3.requete_select("SELECT intitule FROM projet T , personnel P  WHERE pseudo_personnel='"+pseudo+"' AND P.num_projet=T.numero AND numero NOT IN (SELECT numero FROM projet WHERE etat='Livre Facture')");
				ObservableList<String> results = FXCollections.observableArrayList();
				while(r3.getResulSet().next())
				{
				results.add((r3.getResulSet().getString(1)));
				}
				listview_projet.setItems(results);
				btn_envoyer_message.setDisable(true);
	}

	
	public void details_projet()
	{
		personnelselect = table_personnel.getSelectionModel().getSelectedItem();
		if (personnelselect.equals(table_personnel.getSelectionModel().getSelectedItem()))
		{	
			//ON OUVRE LA FENTRE DETAILS AVEC LE NUMERO DE PROJET & LES DONNES DE CONNECTION EN PARAMETRE
			 System.out.println("Détails d'un projet ");
			 utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
			 personnel 	 donnes_personnel   = new personnel(personnelselect.getNum_projet());
			 Vue_details_projet vc = new Vue_details_projet(donnes_utilisateur,donnes_personnel);
		     Stage stage = new Stage();
	         stage.setTitle("Détails d'un projet");
	         stage.setScene(vc.getScene());
	         stage.setResizable(false);
	         stage.show();	

		}		
	}
	
	
	//ONGLET MESSAGE
	public void selection_listview() throws Exception
	{
		intitule_projet_select = listview_projet.getSelectionModel().getSelectedItem();
		
		//QUAND ON CLICQUE SUR L'INTITULE ON --> CHARGE TOUT LES UTILISATEURS DU PROJET SELECTIONNE DANS LA LISTVIEW2
		if(intitule_projet_select.equals(listview_projet.getSelectionModel().getSelectedItem()))
				{	
				Requete r2 = new Requete();
							r2.requete_select("SELECT pseudo_personnel  FROM personnel P , projet T WHERE intitule='"+intitule_projet_select+"' AND P.num_projet=T.numero AND pseudo_personnel NOT IN (SELECT pseudo_personnel FROM personnel WHERE pseudo_personnel='"+pseudo+"') ");
							ObservableList<String> results = FXCollections.observableArrayList();
							while(r2.getResulSet().next())
							{
								results.add((r2.getResulSet().getString(1)));
							}
							listview_personnel_projet.setItems(results);				
				}
	}
	
	//ONGLET MESSAGE
	public void selection_listview2() throws Exception
	{
		String destinataire;
	    destinataire = listview_personnel_projet.getSelectionModel().getSelectedItem();
	    
	    //QUAND ON CLICQUE SUR LE PERSONNEL  ON --> RECUPERE SON MAIL
		if(destinataire.equals(listview_personnel_projet.getSelectionModel().getSelectedItem()))
		{	
			Requete r3 = new Requete();
			r3.requete_select("SELECT mail FROM utilisateur WHERE pseudo='"+destinataire+"' ");
			if(r3.getResulSet().next())
			{
				mail_destinataire=r3.getResulSet().getString(1);
				lbl_destinateur.setText("A : "+mail_destinataire);
				lbl_objet.setText("Objet : "+intitule_projet_select);
				btn_envoyer_message.setDisable(false);
			} 
		}
	}
	
	public void envoyer_message()
	{
		if (contenu_message.getText().isEmpty())
		{
			alert_erreur.setTitle("Avance - Message ");
			alert_erreur.setHeaderText("Ecris au moins quelque chose "+prenom);
			alert_erreur.showAndWait();
		} 
		
		else {
			
		Sender Email = new Sender(mail, mail_destinataire,mdp,intitule_projet_select, contenu_message.getText().toString());
		try {
			Email.Envoyer();
			if(Email.isErreur()==true)
			{
				alert_erreur.setTitle("Avance - Message d'erreur ");
				alert_erreur.setHeaderText("Message non envoyé "+Email.getMessage_erreur());
				alert_erreur.showAndWait();
				initialize_onglet_message();
			}
			else if (Email.isErreur()==false)
			{
				alert_correct.setTitle("Avance - Message ");
				alert_correct.setHeaderText(Email.getMessage_erreur());
				alert_correct.showAndWait();
				contenu_message.clear();
				initialize_onglet_message();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
			}	
	}
	
	
	public void charger_email() throws IOException, ParseException
	{
		
		Receiver_IMAP r = new Receiver_IMAP("MAIL","MDP");
		r.Recevoir();
		
		col_date_reception_mail.setCellValueFactory(new PropertyValueFactory<>("date_reception"));
		col_destinateur_mail.setCellValueFactory(new PropertyValueFactory<>("destinateur"));
		col_message_mail.setCellValueFactory(new PropertyValueFactory<>("message"));
		col_numero_mail.setCellValueFactory(new PropertyValueFactory<>("numero"));
		col_objet_mail.setCellValueFactory(new PropertyValueFactory<>("objet"));
		
		tableview_mail.setItems(r.getOblist_mail());	
	}
	
	
	public void selection_mail()
	{
		//DOUBLE CLICK
		tableview_mail.setOnMousePressed(new EventHandler<MouseEvent>() {
		    @Override 
		    public void handle(MouseEvent event) {
		        if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
		        	
		        	mailselect = tableview_mail.getSelectionModel().getSelectedItem();
		    		if (mailselect.equals(tableview_mail.getSelectionModel().getSelectedItem()))
		    		{
		    		 utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
		    		 Vue_mail vm = new Vue_mail(donnes_utilisateur,mailselect);
		    	     Stage stage = new Stage();
		             stage.setTitle("Mail");
		             stage.setScene(vm.getScene());
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
	
	
	public void deconnexion() throws Exception
	{
		System.out.println("Déconnection du client "+pseudo);	
		Stage stage = (Stage) identification_connection.getScene().getWindow();
		stage.close();
		
		Vue_connection open = new Vue_connection();
		Stage open_stage = new Stage();
		open.start(open_stage);	
	}
	
	
	public void selection()
	{
		/*
		projetselect = table_projet.getSelectionModel().getSelectedItem();
		if (projetselect.equals(table_projet.getSelectionModel().getSelectedItem()))
		{	
		details_projet.setText(projetselect.affichage());	
		//System.out.println("Vous avez selectionné le produit " +projetselect.toString());
		//btn_modif.setDisable(false);
		//btn_suppr.setDisable(false);
		}
		*/
	}
	
	
	//ONGLET AIDE
	public void aide_serveur()
	{saisie_objet_message.setText("Aide pour le serveur");}
	
	public void aide_bdd()
	{saisie_objet_message.setText("Aide pour la base de donnée");}
	
	public void aide_adobe()
	{saisie_objet_message.setText("Aide pour les logiciels");}
	
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
	
	public void setScene(Scene scene) {}

	public void reset() 
	{
		table_personnel.getItems().clear();
		start();	
	}

	



}
