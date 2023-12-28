package controleur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import modele.personnel;
import modele.projet;
import modele.utilisateur;
import mysql.Requete;

public class ctrl_personnel {
	
	@FXML
	private TableView<utilisateur> table_personnel;
	@FXML
	private TableColumn<utilisateur,String> col_nom_personnel;
	@FXML
	private TableColumn<utilisateur,String> col_fonction_personnel;
	@FXML
	private TextArea area_personnel , area_details , area_commentaire;
	@FXML
	private Label label_personnel;
	@FXML
	private TitledPane titre_pane_commentaire;
	@FXML
	private Button btn_commentaire;
	
	private utilisateur personnelselect;
	
	Alert alert_erreur =  new Alert(AlertType.ERROR);
	Alert alert_correct = new Alert(AlertType.CONFIRMATION);
	
	ObservableList<utilisateur> oblist = FXCollections.observableArrayList();
	ObservableList<utilisateur> oblist_pseudo = FXCollections.observableArrayList();

	//VARIABLES POUR AFFECTER LES DONNES DE CONNECTION ET DU PROJET SELECTIONNER DEPUIS LE TABLEAU
	String nom , pseudo , mail , mdp , type_user , prenom , intitule , etat , cree_par; 
	Date date_debut , date_limite;
	int numero_projet;
	
	String donnes_projet_affichage;
	
	
	
	
	public void setScene(Scene scene) 
	{
	}
	
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
	
	public void donnes_projet (projet p )
	{
		//INFORMATIONS DE PROJET
		numero_projet = p.getNumero();
		intitule = p.getIntitule();
		etat = p.getEtat();
		date_debut = p.getDate_debut();
		date_limite = p.getDate_limite();
		cree_par = p.getCree_par();
		donnes_projet_affichage=p.affichage();
		
		//INFORMATIONS DE PROJET DANS ZONE AREA
		area_details.setText(donnes_projet_affichage.toString());
	}
	

	public void initialize()
	{	
		//AFFICHER LES PERSONNES SUR LE TABLEAU PERSONNEL
				Requete r1 = new Requete();
				try {
					r1.requete_select("SELECT pseudo , fonction FROM utilisateur");
					while (r1.getResulSet().next())
					{
						oblist.add(new utilisateur(r1.getResulSet().getString(1), r1.getResulSet().getString(2)));
					}
				} catch (SQLException e) {
					System.out.println("Impossible d'afficher les valeurs au tableau");
					e.printStackTrace();
				}
				col_nom_personnel.setCellValueFactory(new PropertyValueFactory<>("Pseudo"));
				col_fonction_personnel.setCellValueFactory(new PropertyValueFactory<>("Fonction"));
		
				table_personnel.setItems(oblist);
				
		//GRISER LE BOUTON ENVOYER LE COMMENTAIRE 		
				btn_commentaire.setDisable(true);
				
	}
	
			public void start()
			{
				//IMPOSSIBLE DE FAIRE CECI DANS L'INITIALIZE A CAUSE DE NUMERO PROJET -->  DONC CREATION D'UNE METHODE START
				
				//AFFICHER LES PERSONNELS DANS L'ONGLET TEXTE PERSONNEL
				Requete r2 = new Requete();
				try {
					r2.requete_select("SELECT pseudo_personnel FROM personnel WHERE num_projet='"+numero_projet+"'   ");
					while (r2.getResulSet().next())
					{		
						oblist_pseudo.add(new utilisateur (r2.getResulSet().getString(1)));
						area_personnel.setText(oblist_pseudo.toString());
					}
				} catch (SQLException e) {
					System.out.println("Impossible d'afficher les personnels");
					e.printStackTrace();
				}
					
				
			}
			
			
			
			
	
	public void ajouter_personnel()
	{	
		// table_personnel.getSelectionModel().getTableView().setStyle("-fx-background-color:lightcoral");
		
		//VIDER LES COMMENTAIRES 
		area_commentaire.clear();
	
		 personnelselect = table_personnel.getSelectionModel().getSelectedItem();
		if (personnelselect.equals(table_personnel.getSelectionModel().getSelectedItem()))
		{	
			if (area_personnel.getText().isEmpty())
			{
				area_personnel.setText(personnelselect.affichage());	
				
				//INSERTION DES INFORMATION DANS LA BDD POUR CHAQUE PSEUDO	
				Requete r = new Requete();
				Connection connection = r.getConnection();
				PreparedStatement requete;
				try {
					requete = connection.prepareStatement("INSERT INTO personnel "+ "(num_projet,pseudo_personnel,description_personnel) "+ "VALUES(? ,?, ?)");
					requete.setInt(1, numero_projet);
					requete.setString(2, personnelselect.affichage());
					requete.setString(3, area_commentaire.getText());
					requete.executeUpdate();
					
					alert_correct.setTitle("Avance - Message");
					alert_correct.setHeaderText("Le personnel "+area_personnel.getText().toString() + " a été bien ajouté au projet "+numero_projet);
					alert_correct.showAndWait();	
				
				} catch (SQLException e) 
				{
					alert_erreur.setTitle("Avance - Message ");
					alert_erreur.setHeaderText(e.getMessage());
					alert_erreur.showAndWait();	
				}
				 
			}
			else if (area_personnel.getText().contains(personnelselect.affichage()))
			{ 
				alert_erreur.setTitle("Avance - Message ");
				alert_erreur.setHeaderText("Vous avez déja selectionnez cette personne");
				alert_erreur.setContentText("Veuillez en selectionnez une autre !");
				alert_erreur.showAndWait();		
			} 
			else {
				area_personnel.setText(area_personnel.getText() + "\n" + personnelselect.affichage());
				//INSERTION DES INFORMATION DANS LA BDD POUR CHAQUE PSEUDO	
				Requete r = new Requete();
				Connection connection = r.getConnection();
				PreparedStatement requete;
				try {
					requete = connection.prepareStatement("INSERT INTO personnel "+ "(num_projet,pseudo_personnel,description_personnel) "+ "VALUES(? ,?, ?)");
					requete.setInt(1, numero_projet);
					requete.setString(2, personnelselect.affichage());
					requete.setString(3, area_commentaire.getText());
					requete.executeUpdate();
					
					alert_correct.setTitle("Avance - Message");
					alert_correct.setHeaderText("Le personnel "+personnelselect.affichage() + " a été bien ajouté au projet "+numero_projet);
					alert_correct.showAndWait();	
				
				} catch (SQLException e) 
				{
					alert_erreur.setTitle("Avance - Message ");
					alert_erreur.setHeaderText(e.getMessage());
					alert_erreur.showAndWait();	
				}
			}
		}	
		else 
		System.out.println("Personnel selectionné");
	}
	
	
	
	public void supprimer_personnel() throws SQLException
	{
		//VIDER LES COMMENTAIRES 
		area_commentaire.clear();
		
		personnelselect = table_personnel.getSelectionModel().getSelectedItem();
		if (personnelselect.equals(table_personnel.getSelectionModel().getSelectedItem()))
		{
			if (area_personnel.getText().contains(personnelselect.affichage()))
			{	
				String area_text = area_personnel.getText().toString(); 
				area_text = area_text.replaceAll(personnelselect.affichage().toString(),"");
				area_personnel.setText(area_text);
				Requete r2 = new Requete();
				r2.requete_insert("DELETE FROM personnel WHERE pseudo_personnel='"+personnelselect.affichage().toString()+"' AND num_projet='"+numero_projet+"' ");
				
				alert_correct.setTitle("Avance - Message");
				alert_correct.setHeaderText(personnelselect.affichage().toString()+" a été supprimé du projet !");
				alert_correct.showAndWait();
				
			} else 
			{
				alert_erreur.setTitle("Avance - Message ");
				alert_erreur.setHeaderText(personnelselect.affichage().toString()+" est déja supprimé !");
				alert_erreur.showAndWait();	
			}
		}
		
	}
	
	
	public void selection()
	{
		//VIDER LES COMMENTAIRES 
		area_commentaire.clear();
		
		//SI ON SELECTIONNE UNE PERSONNE DANS LA LISTE
		personnelselect = table_personnel.getSelectionModel().getSelectedItem();
		if (personnelselect.equals(table_personnel.getSelectionModel().getSelectedItem()) && area_personnel.getText().contains(personnelselect.affichage()))
		{	
			titre_pane_commentaire.setDisable(false);
			titre_pane_commentaire.setText("Description pour "+personnelselect.affichage().toString());
			
			//AFFICHER LES COMMENTAIRES DU PERSONNEL SELECTIONNÉ DANS L'ONGLET COMMENTAIRE
			Requete r3 = new Requete();
			try {
				r3.requete_select("SELECT description_personnel FROM personnel WHERE num_projet='"+numero_projet+"' AND pseudo_personnel='"+personnelselect.affichage().toString()+"'     ");
				while (r3.getResulSet().next())
				{		
					area_commentaire.setText(r3.getResulSet().getString(1));
				}
			} catch (SQLException e) {
				System.out.println("Impossible d'afficher les commentaire du personnel selectionné");
				e.printStackTrace();
			}
	
		}
		else 
		{	
			titre_pane_commentaire.setDisable(true);
			titre_pane_commentaire.setText("Description");
		}
		
	}
	
	
	//ON KEYPRESSED
	public void verification_commentaire ()
	{
		btn_commentaire.setDisable(false);
	}
	
	
	
	public void commentaire_pour_personnel() 
	{	
		//String message = StringEscapeUtils.escapeSql(area_commentaire.getText());

			personnelselect = table_personnel.getSelectionModel().getSelectedItem();
			if (personnelselect.equals(table_personnel.getSelectionModel().getSelectedItem()))
			{
				Requete r = new Requete();
				Connection connection = r.getConnection();
				PreparedStatement requete;
				try {
				requete=connection.prepareStatement("UPDATE personnel SET description_personnel=? WHERE num_projet=?   AND pseudo_personnel=?  " );				
				requete.setString(1, area_commentaire.getText());
				requete.setInt(2, numero_projet);
				requete.setString(3, personnelselect.affichage());
				requete.executeUpdate();
				
				alert_correct.setTitle("Avance - Message");
				alert_correct.setHeaderText("Commentaire envoyé !");
				alert_correct.showAndWait();
				
				//VIDER LES COMMENTAIRES 
				area_commentaire.clear();
				
				//FERMETURE DE LA FENETRE
		    	//Stage stage = (Stage) area_commentaire.getScene().getWindow();
				//stage.close();
			
			} catch (SQLException e) {
				alert_erreur.setTitle("Avance - Message ");
				alert_erreur.setHeaderText(e.getMessage());
				alert_erreur.showAndWait();	
			}
			
			}
			
	}
	
	

	
	
}
