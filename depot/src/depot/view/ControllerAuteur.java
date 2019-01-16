package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAOAuteur;
import depot.model.Auteur;
import depot.model.Livre;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class ControllerAuteur extends Controller {
	
	private DAOAuteur daoAuteur = new DAOAuteur(cnx);
		
	@FXML
	private TableView<Auteur> auteurTable;		
	@FXML
	private TableColumn<Auteur, String> nomAuteur;
	@FXML
	private TableView<Livre> livreTable;
	@FXML
	private MenuItem modifMenu;
	@FXML
	private TableColumn<Livre,String> titreLivre;
	@FXML
	private TableColumn<Livre, String> isbnLivre; 
 	@FXML
	private TextField inputNomAuteur;
 	@FXML
 	private TextField inputPrenomAuteur;
	@FXML
	public void initialize() throws SQLException {	
		auteurTable.setEditable(true);
		
		nomAuteur.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
		nomAuteur.setCellFactory(TextFieldTableCell.forTableColumn());		
		
		livreTable.setEditable(true);
		
		titreLivre.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
		titreLivre.setCellFactory(TextFieldTableCell.forTableColumn());
		isbnLivre.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
		isbnLivre.setCellFactory(TextFieldTableCell.forTableColumn());
		
		auteurTable.setItems(daoAuteur.findAll());	
		
		auteurTable.getSelectionModel().selectedItemProperty().addListener(
			(obs, oldValue, newValue) -> {
				if (newValue!=null)
				try {
					livreTable.setItems(daoAuteur.findLivresByAuteur(newValue));
				} 
				catch (SQLException e) {
					e.printStackTrace();
				}
			});
		
		modifMenu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				editMenu(livreTable.getSelectionModel().getSelectedItem());
			}
		});
	}
	
	public void addAuteur() throws SQLException {	
		
		if (!inputNomAuteur.getText().trim().isEmpty() || !inputPrenomAuteur.getText().trim().isEmpty()) {
			Auteur auteur = new Auteur();
			auteur.setLibelle(inputNomAuteur.getText());
			if (daoAuteur.findBy("nom", auteur.getLibelle()) != null) {
				daoAuteur.insert(auteur);
			}
			else 
				existant.showAndWait();
		}		
		else {
			select.setHeaderText("Veuillez entrer un nom");
			select.showAndWait();
		}		
		
	}
	
	public void deleteAuteur() throws SQLException {
		confirm.showAndWait();
		daoAuteur.delete(auteurTable.getSelectionModel().getSelectedItem());
		initialize();
	}
	
	public void editNomAuteur(CellEditEvent<Auteur, String> e) throws SQLException {
		confirm.showAndWait();
		e.getRowValue().setLibelle(e.getNewValue());
		daoAuteur.update(e.getRowValue());
		initialize();
	}	
	
	public void refresh() throws SQLException {
		initialize();
	}
			
}
