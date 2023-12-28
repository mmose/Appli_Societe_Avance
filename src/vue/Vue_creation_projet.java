package vue;

import java.net.URL;


import controleur.ctrl_page_administrateur;
import controleur.ctrl_page_creation_projet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import modele.utilisateur;


public class Vue_creation_projet  {
	

	private Scene scene;
	
	public Scene getScene() {
		return scene;
	}

	public  Vue_creation_projet (utilisateur u) {
		 try {
			 final URL fxmlURL=
						getClass().getResource("page_creation_projet.fxml");
						 final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
						 final Pane node = (Pane)fxmlLoader.load();		 
						 
						scene = new Scene(node);
						
						ctrl_page_creation_projet controleur = fxmlLoader.getController();
						u= new utilisateur(u.getPseudo(), u.getNom() , u.getPrenom() , u.getMail() , u.getMdp() , u.getType_user());
						controleur.donnes_utilisateur(u);
						controleur.setScene(scene);
						
					 }
					 catch(Exception e) {
							e.printStackTrace();
						}
	
						
			 
		 }
	

}
