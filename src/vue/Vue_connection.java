package vue;

import java.net.URL;


import controleur.ctrl_page_connection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Vue_connection extends Application {
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		try {
		
			//CHARGEMENT DU FXML 
			URL fxmlURL=getClass().getResource("page_connection.fxml");
			FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL);
			Node root = fxmlLoader.load();
			Scene scene = new Scene((Pane) root);
			
			//CHARGEMENT DU CONTROLEUR
			ctrl_page_connection ctrl = fxmlLoader.getController();
			ctrl.setPrimaryStage(primaryStage);
			
			//CHARGEMENT DE l'ICONE DE l'APPLICATION
			Image icone = new Image("/icones/avance_client.PNG");
			
			//AFFECTATIONS DES PARAMETRES DE LA FENETRE
			primaryStage.setTitle("Menu Principal");
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.initStyle(StageStyle.UNDECORATED); // SA VEUT DIRE QU'ON ENLEVE LA FENETRE WINDOWS PAR DEFAUT  (fermer,agrandir,reduire)
			primaryStage.getIcons().add(icone);
			primaryStage.show();
	
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
