package controleur;

import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import modele.personnel;
import modele.projet;
import modele.tache;
import modele.utilisateur;
import mysql.Requete;
import vue.Vue_ajouter_tache;
import vue.Vue_creation_projet;
import vue.Vue_modification_projet;
import vue.Vue_modifier_tache;

public class ctrl_details_projet {

	//VARIABLES FXML
	@FXML
	private Label lbl_intitule_projet;
	@FXML
	private Label lbl_etat_projet;
	@FXML
	private Label lbl_date_debut_projet;
	@FXML
	private Label lbl_date_fin_projet;
	@FXML
	private Label lbl_cree_par_projet;
	@FXML
	private Label lbl_informations_projet;
	@FXML
	private TableView<utilisateur> table_participants;
	@FXML
	private TableView<tache> table_tache;
	
	@FXML
	private TableColumn<utilisateur,String> col_pseudo_participant;
	@FXML
	private TableColumn<utilisateur,String> col_fonction_participant;
	
	@FXML
	private TableColumn<tache,Integer> col_numero_tache;
	@FXML
	private TableColumn<tache,Integer> col_id_tache;
	@FXML
	private TableColumn<tache,String> col_description_tache;
	
	
	//VARIABLES
	ObservableList<utilisateur> oblist_participant = FXCollections.observableArrayList();
	ObservableList<tache> oblist_tache = FXCollections.observableArrayList();
	
	Alert alert_erreur =  new Alert(AlertType.ERROR);
	Alert alert_correct =  new Alert(AlertType.CONFIRMATION);
	
	private tache tacheselect;
	
	//VARIABLES DE CONNECTION
	String pseudo , mail , prenom , type_user , nom , mdp;
	int num_projet_personnel;
	
	
	
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
	
	public void donnes_personnel (personnel p)
	{
		//INFORMATIONS DU PROJET SELECTIONNÉ DANS LE TABLEAU UTILISATEUR AU CLIQUE DU BOUTON DETAILS
		num_projet_personnel =p.getNum_projet();	
	}
	
	
	public void start() 
	{
		//RECUPERATION DES DETAILS DU PROJET
		Requete r1 = new Requete();
		try {
			r1.requete_select("SELECT * FROM projet WHERE numero='"+num_projet_personnel+"'");
			if (r1.getResulSet().next())
			{	
				lbl_intitule_projet.setText(r1.getResulSet().getString(2));
				lbl_etat_projet.setText(r1.getResulSet().getString(3));
				lbl_date_debut_projet.setText(""+r1.getResulSet().getDate(4));
				lbl_date_fin_projet.setText(""+r1.getResulSet().getDate(5));
				lbl_cree_par_projet.setText(r1.getResulSet().getString(6));
				lbl_informations_projet.setText(r1.getResulSet().getString(9));
			}	
				} 
					catch (SQLException e) {System.out.println("Impossible d'afficher les informations du projet");
						e.printStackTrace();
				}
		
		//AFFICHER LES PERSONNES QUI PARTICIPENT AU PROJET SELECTIONNÉ
				Requete r2 = new Requete();
				try {
					r2.requete_select("SELECT pseudo_personnel , fonction FROM personnel P , utilisateur U WHERE num_projet= '"+num_projet_personnel+"' AND P.pseudo_personnel=U.pseudo ");
					while (r2.getResulSet().next())
					{
						oblist_participant.add(new utilisateur(r2.getResulSet().getString(1), r2.getResulSet().getString(2)));
					}
				} catch (SQLException e) {
					System.out.println("Impossible d'afficher les valeurs au tableau");
					e.printStackTrace();
				}
				col_pseudo_participant.setCellValueFactory(new PropertyValueFactory<>("Pseudo"));
				col_fonction_participant.setCellValueFactory(new PropertyValueFactory<>("Fonction"));

				table_participants.setItems(oblist_participant);	
		
				
				
	     	//AFFICHER LES TACHES DU PERSONNEL
				Requete r3 = new Requete();
				int i = 1;
				try {
					// J'AI SELECTIONNE NUM_TACHE MAIS JE NE l'UTILISE PAS POUR LE REMPLACER PAR I
					// CELA VEUT DIRE QUE POUR CHAQUE RESULTAT ON INCREMENTE I POUR LE NUMERO DE TACHE --> C'EST MIEUX ! :) 
					r3.requete_select("SELECT num_tache ,id_tache , description FROM taches  WHERE num_projet= '"+num_projet_personnel+"' AND pseudo='"+pseudo+"' ");	
					while (r3.getResulSet().next())
					{
						oblist_tache.add(new tache(i,r3.getResulSet().getInt(2), r3.getResulSet().getString(3)));		
						i++;
					}
					
				} catch (SQLException e) {
					System.out.println("Impossible d'afficher les valeurs au tableau");
					e.printStackTrace();
				}
				col_numero_tache.setCellValueFactory(new PropertyValueFactory<>("Numero"));
				col_id_tache.setCellValueFactory(new PropertyValueFactory<>("id_tache"));
				col_description_tache.setCellValueFactory(new PropertyValueFactory<>("Description"));

				table_tache.setItems(oblist_tache);
				
				
				
	}
	
	public void ajouter_tache()
	{
		 System.out.println("Ajouter une tache demandé");
		 utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
		 projet      numero_projet      = new projet(num_projet_personnel);
		 Vue_ajouter_tache vc = new Vue_ajouter_tache(donnes_utilisateur,numero_projet);
	     Stage stage = new Stage();
         stage.setTitle("Ajouter une tâche");
         stage.setScene(vc.getScene());
         stage.setResizable(false);
         stage.show();
         reset();
	}
	
	public void modifier_tache()
	{
		tacheselect = table_tache.getSelectionModel().getSelectedItem();	
		if (tacheselect.equals(table_tache.getSelectionModel().getSelectedItem()) )
		{
			System.out.println("Modification d'une tache");
			utilisateur donnes_utilisateur = new utilisateur(pseudo, nom, prenom, mail, mdp, type_user);
			tache       donnes_taches      = new tache (tacheselect.getNumero(),tacheselect.getId_tache(),tacheselect.getDescription());
			Vue_modifier_tache vm = new Vue_modifier_tache(donnes_utilisateur,donnes_taches);
		    Stage stage = new Stage();
	        stage.setTitle("Modification d'un projet");
	        stage.setScene(vm.getScene());
	        stage.setResizable(false);
	        stage.show();	
	        reset();
		} 
		else
		{
			alert_erreur.setTitle("Avance - Message ");
			alert_erreur.setHeaderText("Erreur de sélection");
			alert_erreur.setContentText("Veuillez selectionnez un projet pour le modifier ! ");
			alert_erreur.showAndWait();
		}
	}
	
	
	
	public void supprimer_tache() throws SQLException
	{
		tacheselect = table_tache.getSelectionModel().getSelectedItem();
		
		if (tacheselect.equals(table_tache.getSelectionModel().getSelectedItem()))
		{
			
			Requete r2 = new Requete();
			//r2.requete_insert("delete from taches where description='"+tacheselect.getDescription()+"'  and num_projet='"+num_projet_personnel+"' " );
			r2.requete_insert("delete from taches where id_tache='"+tacheselect.getId_tache()+"'" );
			alert_erreur.setTitle("Avance - Message ");
			alert_erreur.setHeaderText("La tâche "+tacheselect.getId_tache()+" a été supprimé");
			alert_erreur.showAndWait();
			reset();
		}
		else 
		{
			alert_erreur.setTitle("Avance - Message ");
			alert_erreur.setHeaderText("Erreur de sélection");
			alert_erreur.setContentText("Veuillez selectionnez la tache à supprimer !");
			alert_erreur.showAndWait();
		}
	}
	

	public void reset() 
	{
		table_participants.getItems().clear();
		table_tache.getItems().clear();
		start();	
	}
	
}
