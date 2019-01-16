package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAOEditeur;
import depot.model.Editeur;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class ControllerEditeur extends Controller {
	
	private DAOEditeur daoEditeur = new DAOEditeur(cnx);	
	@FXML
	private TableView<Editeur> editeurTable;
	@FXML
	private TableColumn<Editeur, Number> idEditeur;
	@FXML 
	private TableColumn<Editeur, String> libelleEditeur;
	@FXML
	private TextField inputEditeur;
		
	@FXML
	private void initialize() throws SQLException{
		idEditeur.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		libelleEditeur.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
		libelleEditeur.setCellFactory(TextFieldTableCell.forTableColumn());
		editeurTable.setEditable(true);
		editeurTable.setItems(daoEditeur.findAll());
	}	
	
	public void addEditeur() throws SQLException {
		if (!inputEditeur.getText().trim().isEmpty()) {
			Editeur editeur = new Editeur();
			editeur.setLibelle(inputEditeur.getText());
			if (daoEditeur.findBy("libelle", editeur.getLibelle()) != null) {
				daoEditeur.insert(editeur);
				initialize();
			}
			else {
				existant.showAndWait();
			}
		}
		else 
			empty.showingProperty();
	}
	
	public void deleteEditeur() throws SQLException {
		confirm.showAndWait();
		daoEditeur.delete(editeurTable.getSelectionModel().getSelectedItem());
		initialize();
	}
	
	public void editEditeur(CellEditEvent<Editeur, String> e) throws SQLException {
		confirm.showAndWait();
		e.getRowValue().setLibelle(e.getNewValue());
		daoEditeur.update(e.getRowValue());		
	}
	
}
