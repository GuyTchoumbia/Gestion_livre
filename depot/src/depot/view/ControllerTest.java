package depot.view;

import depot.model.Test;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControllerTest {
	
	Test test = new Test("Marc");
	
	@FXML
	private TextField inputNom;
	@FXML
	private TextField result;
	@FXML
	private Button lucieButton;
	@FXML
	private Button violetteButton;
	@FXML
	public void initialize() {
		inputNom.textProperty().bindBidirectional(test.nomProperty());
		result.textProperty().bindBidirectional(inputNom.textProperty());
		lucieButton.setOnAction(e -> {
			test.setNom("Lucie");
		});
		violetteButton.setOnAction(e -> {
			test.setNom("Violette");
		});
		
	}
	

}
