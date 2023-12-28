package vue;

import java.net.URL;

import controleur.ctrl_personnel;
import controleur.ctrl_page_administrateur;
import controleur.ctrl_page_creation_projet;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modele.projet;
import modele.utilisateur;


public class Vue_personnel  {
	

	private Scene scene;

	
	public Scene getScene() {
		return scene;
	}

	public  Vue_personnel (utilisateur u , projet p) {
		 try {
			 final URL fxmlURL=
						getClass().getResource("page_personnel.fxml");
						 final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
						 final Pane node = (Pane)fxmlLoader.load();		 
	 
						scene = new Scene(node);
		
						
						ctrl_personnel controleur = fxmlLoader.getController();
						p = new projet(p.getNumero(), p.getIntitule(),  p.getEtat(), p.getDate_debut(), p.getDate_limite(), p.getCree_par(),p.getNom_client(),p.getBudget(),p.getInformations());
						u= new utilisateur(u.getPseudo(), u.getNom() , u.getPrenom() , u.getMail() , u.getMdp() , u.getType_user());
						controleur.donnes_utilisateur(u);
						controleur.donnes_projet(p);
						controleur.start();
						controleur.setScene(scene);
						
						
						
					 }
					 catch(Exception e) {
							e.printStackTrace();
						}
	
						
			 
		 }
	

}
