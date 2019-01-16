package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAOAuteur;
import depot.dao.DAOEditeur;
import depot.dao.DAOLivre;
import depot.dao.DAOTheme;
import depot.model.Auteur;
import depot.model.Editeur;
import depot.model.Livre;
import depot.model.Theme;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ListView.EditEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class ControllerModifier extends Controller {
	
	DAOLivre daoLivre = new DAOLivre(cnx);
	DAOAuteur daoAuteur = new DAOAuteur(cnx);
	DAOEditeur daoEditeur = new DAOEditeur(cnx);
	DAOTheme daoTheme = new DAOTheme(cnx);
	/*
	 * this controller has a constructor with parameter to pass the data of the object from window to window
	 */
	private Livre livre;
	
	public ControllerModifier() {
		this.livre= new Livre();
	}
	
	public ControllerModifier(Livre livre) {
		this.livre = livre;
	}
	
	public void setLivre(Livre l) { this.livre = l; }
	
	
	@FXML
	private ComboBox<Livre> inputTitre;
	@FXML
	private TextField inputIsbn;
	@FXML
	private TextField inputDate; 
	@FXML
	private ListView<Auteur> listAuteurs;	
	@FXML
	private ListView<Editeur> listEditeurs;
	@FXML
	private ListView<Theme> listThemes;
	@FXML
	private Button editAuteurs;
	@FXML
	private Button editEditeurs;
	@FXML
	private Button editThemes;
	@FXML
	private void initialize() {		
		inputTitre.getEditor().setText(livre.getTitre());
		inputIsbn.setText(livre.getIsbn());
		if (livre.getDate() == 0) {
			inputDate.setText("");
		}
		else {
			inputDate.setText(livre.getDate().toString());
		}
		
		listAuteurs.setCellFactory(listView -> {
			TextFieldListCell<Auteur> cell = new TextFieldListCell<>();
			cell.setConverter(new StringConverter<Auteur>() {
				@Override
				public String toString(Auteur auteur) {
					return auteur.toString();
				}
				
				@Override
				public Auteur fromString(String s) {
					Auteur auteur = cell.getItem();
					auteur.setLibelle(s);
					return auteur;
				}				
			});
			return cell;
		});
		
		listEditeurs.setCellFactory(listView -> {
			TextFieldListCell<Editeur> cell = new TextFieldListCell<>();
			cell.setConverter(new StringConverter<Editeur>() {
				@Override
				public String toString(Editeur editeur) {
					return editeur.toString();
				}
				
				@Override
				public Editeur fromString(String s) {
					Editeur editeur = cell.getItem();
					editeur.setLibelle(s);
					return editeur;
				}				
			});
			return cell;
		});
		
		listThemes.setCellFactory(listView -> {
			TextFieldListCell<Theme> cell = new TextFieldListCell<>();
			cell.setConverter(new StringConverter<Theme>() {
				@Override
				public String toString(Theme theme) {
					return theme.toString();
				}
				
				@Override
				public Theme fromString(String s) {
					Theme theme = cell.getItem();
					theme.setLibelle(s);
					return theme;
				}				
			});
			return cell;
		});
									
		listAuteurs.setItems(livre.getAuteurs());
		listEditeurs.setItems(livre.getEditeurs());
		listThemes.setItems(livre.getThemes());
		
		editAuteurs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				editSelectedLine(listAuteurs);
			}
		});
		
		editEditeurs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				editSelectedLine(listEditeurs);
			}
		});
		
		editAuteurs.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				editSelectedLine(listThemes);
			}
		});
	}
	
	/*
	 * validation button:
	 * do the appropriate sql requests then closes the window
	 */
	public Livre validate() throws SQLException {		
		confirm.showAndWait();
		livre.setTitre((inputTitre.getEditor().getText()));
		
		String regex = "^\\d{1,13}$";
		if (inputIsbn.getText().matches(regex)) 
			livre.setIsbn(inputIsbn.getText());
		else {
			empty.show();
			inputIsbn.requestFocus();
		}
		regex = "^\\d{4}$";
		if (inputDate.getText().matches(regex)) 
			livre.setDate(Integer.valueOf(inputDate.getText()));
		else {
			empty.show();
			inputDate.requestFocus();
		}				
		daoLivre.update(livre);	
		
		daoLivre.deleteEditeurLivre(livre);
		for (Editeur editeur : livre.getEditeurs()) {
			if (editeur.getId() == 0) {
				Editeur e = daoEditeur.findByLibelle(editeur.getLibelle());
				if (e == null)
					editeur.setId(daoEditeur.insert(editeur));
				else 
					editeur = e;
			}
			daoLivre.insertEditeurLivre(livre, editeur);
		}
		
		daoLivre.deleteAuteurLivre(livre);
		for (Auteur auteur : livre.getAuteurs()) {
			if (auteur.getId() == 0) {
				Auteur a = daoAuteur.findByLibelle(auteur.getLibelle());
				if (a == null)
					auteur.setId(daoAuteur.insert(auteur));
				else 
					auteur = a;
			}
			daoLivre.insertAuteurLivre(livre, auteur);
		}
		
		daoLivre.deleteThemeLivre(livre);
		for (Theme theme : livre.getThemes()) {
			if (theme.getIdTheme() == 0) {
				Theme t = daoTheme.findByLibelle(theme.getLibelle());
				if (t == null)
					theme.setIdTheme(daoTheme.insert(theme));
				else
					theme = t;
			}
			daoLivre.insertThemeLivre(livre, theme);
	
		}
		
		return livre;
	}
	/*
	 * button actions
	 */
	public void insertAuteur() {
		Auteur auteur = new Auteur();
		livre.getAuteurs().add(auteur);
		listAuteurs.layout();
		listAuteurs.edit(listAuteurs.getItems().size()-1);		
	}
	
	public void removeAuteur() {
		if (listAuteurs.getSelectionModel().getSelectedItem() != null) {
			livre.getAuteurs().remove(listAuteurs.getSelectionModel().getSelectedItem());
			initialize();
		}		
	}
	
	public <T> void editSelectedLine(ListView<T> list) {
		list.edit(list.getSelectionModel().getSelectedIndex());
	}
	
	public void editCommitAuteur(EditEvent<Auteur> e) throws SQLException {
		e.getSource().getItems().get(e.getIndex()).setLibelle(e.getNewValue().getLibelle());
	}	
	
	public void insertEditeur() {
		Editeur editeur = new Editeur();
		livre.getEditeurs().add(editeur);
		listEditeurs.layout();
		listEditeurs.edit(listEditeurs.getItems().size()-1);
	}
	
	public void removeEditeur() {
		if (listEditeurs.getSelectionModel().getSelectedItem() != null) {
			livre.getEditeurs().remove(listEditeurs.getSelectionModel().getSelectedItem());
		}		
	}	
	
	public void editCommitEditeur(EditEvent<Editeur> e) throws SQLException {
		e.getSource().getItems().get(e.getIndex()).setLibelle(e.getNewValue().getLibelle());
	}
	
	public void insertTheme() {
		Theme theme = new Theme();
		livre.getThemes().add(theme);
		listThemes.layout();
		listThemes.edit(listThemes.getItems().size()-1);
	}
	
	public void removeTheme() {
		if (listThemes.getSelectionModel().getSelectedItem() != null) {
			livre.getThemes().remove(listThemes.getSelectionModel().getSelectedItem());
		}		
	}
	
	public void editCommitTheme(EditEvent<Theme> e) throws SQLException {
		e.getSource().getItems().get(e.getIndex()).setLibelle(e.getNewValue().getLibelle());
	}
}

