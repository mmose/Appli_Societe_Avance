package vue;

import java.net.URL;

import controleur.ctrl_page_administrateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.utilisateur;

public class Vue_administrateur  {
	
	private Scene scene;
	
	public Scene getScene() {
		return scene;
	}
	
	
	public  Vue_administrateur(utilisateur u) {
		 try {
			 			// CHARGEMENT DU FXML
			 			 final URL fxmlURL=getClass().getResource("page_administrateur.fxml");
						 final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
						 final Pane node = (Pane)fxmlLoader.load();		 
						 
						//CREATION D'UNE NOUVELLE SCENE 
						scene = new Scene(node);
						ctrl_page_administrateur controleur = fxmlLoader.getController();
						
						//PASSAGE DES INFORMATIONS DE LA CONNECTION DANS LE CONTROLEUR
						u= new utilisateur(u.getPseudo(), u.getNom() , u.getPrenom() , u.getMail() , u.getMdp() , u.getType_user());
						controleur.donnes_utilisateur(u);
						controleur.initialize_onglet_tableau_bord();
						controleur.initialize_onglet_message();
						controleur.initialize_onglet_archive();
						controleur.initialize_onglet_calendrier();
						controleur.setScene(scene);
					
					 }
					 catch(Exception e) {
							e.printStackTrace();
						}
	}

	public static void main(String[] args) {

	}
	
	


}
