package depot.core;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

import depot.model.Livre;
import depot.view.ControllerModifier;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

public class Controller {	
	
	protected static Alert existant = new Alert(AlertType.ERROR);	
	protected static Alert confirm = new Alert(AlertType.CONFIRMATION);
	protected static Alert select = new Alert(AlertType.WARNING);
	protected static Alert empty = new Alert(AlertType.ERROR);
	
	protected Connection cnx = Connect.getInstance();	
	
	/*
	 * converts lists into a string(property) for display
	 */
	protected <T> SimpleStringProperty explode(ObservableList<T> list) {
		String s = "";
		for (T element : list) {
			s += element.toString();
			if (list.indexOf(element)!= list.size()-1)
				s += ", ";
		}
		return new SimpleStringProperty(s);
	}
	
	public Livre editMenu(Livre livre) {		
		
		Dialog<Livre> dialog = new Dialog<Livre>();	
		dialog.initStyle(StageStyle.UTILITY);
		dialog.setTitle("Editer Livre");
		dialog.setResizable(true);
		dialog.setHeight(800);
		dialog.setWidth(1000);
		ButtonType valider = new ButtonType("Valider", ButtonData.OK_DONE);
		ButtonType annuler = new ButtonType("Annuler", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(valider, annuler);		
		
		ControllerModifier controller = new ControllerModifier(livre);
		try {
			FXMLLoader loader = new FXMLLoader();		
			loader.setLocation(getClass().getResource("modifier.fxml"));
			loader.setController(controller);
			AnchorPane editContainer = (AnchorPane) loader.load();
			dialog.getDialogPane().setContent(editContainer);
						
		}
		catch (Exception e) {
			e.printStackTrace();
		}		
		dialog.setResultConverter(dialogButton -> {
		    if (dialogButton == valider) {
		        try {
					return controller.validate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		    }
		    return null;
		});
		
		Optional<Livre> result = dialog.showAndWait();
		if (result.isPresent()) {
			return result.get();
		}
		else return null;				
	}	
}
