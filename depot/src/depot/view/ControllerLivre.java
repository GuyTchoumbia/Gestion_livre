package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAOAuteur;
import depot.dao.DAODepot;
import depot.dao.DAOEditeur;
import depot.dao.DAOLivre;
import depot.dao.DAOTheme;
import depot.model.Auteur;
import depot.model.Depot;
import depot.model.Editeur;
import depot.model.Livre;
import depot.model.Theme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ControllerLivre extends Controller {
	
	private DAOLivre daoLivre = new DAOLivre(cnx);
	private DAOAuteur daoAuteur = new DAOAuteur(cnx);
	private DAOEditeur daoEditeur = new DAOEditeur(cnx);
	private DAOTheme daoTheme = new DAOTheme(cnx);
	private DAODepot daoDepot = new DAODepot(cnx);
	private ObservableList<Livre> listLivre = FXCollections.observableArrayList();
	
	@FXML 
	private TableView<Livre> livreTable;
	@FXML
	private TableColumn<Livre, String> titreColumn;
	@FXML
	private TableColumn<Livre, String> isbnColumn;
	@FXML 
	private TableColumn<Livre, Number> dateColumn;
	@FXML 
	private TableColumn<Livre, String> auteurColumn;
	@FXML
	private TableColumn<Livre, String> editeurColumn;
	@FXML
	private TableColumn<Livre, String> themeColumn;
	@FXML 
	private TableColumn<Livre, String> depotColumn;	
	@FXML 
	private MenuItem modifMenu;
	@FXML
	private ComboBox<Livre> inputTitre;
	@FXML
	private TextField inputIsbn;
	@FXML
	private TextField inputDate;
	@FXML
	private ComboBox<Auteur> inputAuteur;
	@FXML
	private ComboBox<Editeur> inputEditeur;
	@FXML
	private ComboBox<Theme> inputTheme;
	@FXML
	private ComboBox<Depot> inputDepot;
	
		
	@FXML
	private void initialize() throws SQLException {
		titreColumn.setCellValueFactory(cellData -> cellData.getValue().titreProperty());
		isbnColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());	
		auteurColumn.setCellValueFactory(cellData -> explode(cellData.getValue().auteursProperty()));	
		editeurColumn.setCellValueFactory(cellData -> explode(cellData.getValue().editeursProperty()));	
		themeColumn.setCellValueFactory(cellData -> explode(cellData.getValue().themesProperty()));	
		depotColumn.setCellValueFactory(cellData -> explode(cellData.getValue().depotsProperty()));	

		livreTable.setEditable(true);
		livreTable.setItems(listLivre);
		modifMenu.setOnAction(e -> {
			Livre livre = editMenu(livreTable.getSelectionModel().getSelectedItem());
			if (livre != null) {
				listLivre.set(livreTable.getSelectionModel().getSelectedIndex(), livre);
				livreTable.layout();
			}
		});
		
	}	
	
	
	/*
	 * ComboBox events:
	 * extract the title/libelle from lists of Entities
	 */
	public void getLivreList() throws SQLException {
		String input = inputTitre.getEditor().getText();
		if (input != "") {
			ObservableList<Livre> listLivre = daoLivre.findBy("titre", input);
			inputTitre.setItems(listLivre);
			}
	}
	
	public void getAuteurList() throws SQLException {
		String input = inputAuteur.getEditor().getText();
		if (input != "") {
			ObservableList<Auteur> listAuteur = daoAuteur.findBy("nom", input);
			inputAuteur.setItems(listAuteur);
		}
	}
	
	public void getEditeurList() throws SQLException {
		String input = inputEditeur.getEditor().getText();
		if (input != "") {
			ObservableList<Editeur> listEditeur = daoEditeur.findBy("libelle", input);
			inputEditeur.setItems(listEditeur);	
		}
	}
	
	public void getThemeList() throws SQLException {
		String input = inputTheme.getEditor().getText();
		if (input != "") {
			ObservableList<Theme> listTheme = daoTheme.findBy("libelle", input);
			inputTheme.setItems(listTheme);	
		}
	}
	
	public void getDepotList() throws SQLException {
		String input = inputDepot.getEditor().getText();
		if (input != "") {
			ObservableList<Depot> listDepot = daoDepot.findBy("libelle", input);
			inputDepot.setItems(listDepot);	
		}
	}
	public void refresh() throws SQLException {
		livreTable.layout();
	}
	
	public void search() throws SQLException {
		listLivre.setAll(daoLivre.megaSearch(inputTitre.getEditor().getText(), inputIsbn.getText(), inputDate.getText(), inputAuteur.getEditor().getText(), inputEditeur.getEditor().getText(), inputTheme.getEditor().getText(), inputDepot.getEditor().getText()));
		for (Livre livre : listLivre) {
			livre.setAuteurs(daoLivre.findAuteursByLivre(livre));
			livre.setEditeurs(daoLivre.findEditeursByLivre(livre));
			livre.setThemes(daoLivre.findThemesByLivre(livre));
			livre.setDepots(daoLivre.findDepotsByLivre(livre));
		}
	}
}
