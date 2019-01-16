package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAOTheme;
import depot.model.Theme;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;

public class ControllerTheme extends Controller {
	
	private DAOTheme daoTheme = new DAOTheme(cnx);
	
	@FXML
	private TableView<Theme> themeTable;
	@FXML
	private TableColumn<Theme, Number> idTheme;
	@FXML 
	private TableColumn<Theme, String> libelleTheme;
	@FXML
	private TextField inputLibelle;
		
	@FXML
	private void initialize() throws SQLException {
		idTheme.setCellValueFactory(cellData -> cellData.getValue().idThemeProperty());
		libelleTheme.setCellValueFactory(cellData -> cellData.getValue().libelleProperty());
		libelleTheme.setCellFactory(TextFieldTableCell.forTableColumn());
		themeTable.setEditable(true);
		themeTable.setItems(daoTheme.findAll());
	}	
	
	public void addTheme() throws SQLException {
		if (!inputLibelle.getText().trim().isEmpty()) {
			Theme theme =  new Theme();
			theme.setLibelle(inputLibelle.getText());
			if (daoTheme.findBy("libelle", theme.getLibelle()) != null) {
				daoTheme.insert(theme);
				initialize();
			}
			else 
				existant.showAndWait();
		}
		else
			empty.showAndWait();
	}
	
	public void deleteTheme() throws SQLException {
		confirm.showAndWait();
		daoTheme.delete(themeTable.getSelectionModel().getSelectedItem());	
		initialize();
	}
	
	public void editTheme(CellEditEvent<Theme, String> e) throws SQLException {
		confirm.showAndWait();
		e.getRowValue().setLibelle(e.getNewValue());
		daoTheme.update(e.getRowValue());
		initialize();
	}

}
