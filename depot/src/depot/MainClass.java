package depot;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClass extends Application {
		
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Depot Livre");		
		try {
			//gets the content elements from the fxml
			FXMLLoader loader = new FXMLLoader();		
			// setLocation to set the location of the file
			//loader.setLocation(getClass().getResource("view/index.fxml"));
			loader.setLocation(getClass().getResource("view/index.fxml"));	
			//load returns a pane (any pane type extending Pane) lets store it in a var.
			AnchorPane MainContainer = (AnchorPane) loader.load();
			// here is usual, attach the pane to the scene
			Scene MainScene = new Scene(MainContainer);
			// attach the scene to the stage
		    primaryStage.setScene(MainScene);
		    // shows the stage
		    primaryStage.show();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
	    
	}

	public static void main(String[] args) {
		launch(args);
	}
	
}
