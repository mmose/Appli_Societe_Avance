package vue;

import java.net.URL;

import controleur.ctrl_page_administrateur;
import controleur.ctrl_page_utilisateur;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import modele.utilisateur;

public class Vue_utilisateur  {
	
	private Scene scene;
	
	public Scene getScene() {
		return scene;
	}

	
	
	
	public  Vue_utilisateur(utilisateur u) {
		 try { 
			 			//CHARGEMENT DU FXML
			 			 final URL fxmlURL=getClass().getResource("page_utilisateur.fxml");
						 final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
						 final Pane node = (Pane)fxmlLoader.load();		 
						 
						// CREATION D'UNE NOUVELLE SCENE
						scene = new Scene(node);				
						ctrl_page_utilisateur controleur = fxmlLoader.getController();
						
						//PASSAGE DES INFORMATIONS DE LA CONNECTION DANS LE CONTROLEUR
						u= new utilisateur(u.getPseudo(), u.getNom() , u.getPrenom() , u.getMail() , u.getMdp() , u.getType_user());
						controleur.donnes_utilisateur(u);
						controleur.start();
						controleur.initialize_onglet_message();
						controleur.setScene(scene);
					 }
					 catch(Exception e) {
							e.printStackTrace();
						}
	}

	public static void main(String[] args) {

	}
	
	


}
