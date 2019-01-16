package depot.view;

import java.sql.SQLException;

import depot.core.Controller;
import depot.dao.DAODepot;
import depot.dao.DAODepot_livre;
import depot.dao.DAORegion;
import depot.model.Depot;
import depot.model.Depot_livre;
import depot.model.Livre;
import depot.model.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ControllerDepot extends Controller {
	
	private DAODepot_livre daoDepot_livre = new DAODepot_livre(cnx);
	private DAORegion daoRegion = new DAORegion(cnx);
	private DAODepot daoDepot = new DAODepot(cnx);
	
	private ObservableList<Depot_livre> list = FXCollections.observableArrayList();
	
	@FXML
	private TableView<Depot_livre> livreTable;		
	@FXML
	private TableColumn<Depot_livre, String> titreColumn;
	@FXML
	private TableColumn<Depot_livre, String> isbnColumn;
	@FXML
	private TableColumn<Depot_livre, Number> dateColumn;
	@FXML
	private TableColumn<Depot_livre, Number> quantiteColumn;
	@FXML
	private ComboBox<Depot> inputDepot;	
	@FXML
	private ComboBox<Region> inputRegion;
	@FXML
	private Button add;
	
	@FXML
	private void initialize() throws SQLException {	
		titreColumn.setCellValueFactory(cellData -> cellData.getValue().getLivre().titreProperty());
		isbnColumn.setCellValueFactory(cellData -> cellData.getValue().getLivre().isbnProperty());	
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getLivre().dateProperty());
		quantiteColumn.setCellValueFactory(cellData -> cellData.getValue().quantiteProperty());
		inputRegion.setItems(daoRegion.findAll());
		inputRegion.valueProperty().addListener(
				(obs, oldValue, newValue) -> {
					if (newValue!=null) {
						try {
							inputDepot.setItems(daoDepot.findDepotsByRegion(newValue));
						} 
						catch (SQLException e) {
							e.printStackTrace();
						}
					}
				});
		livreTable.setItems(list);
		add.setOnAction(e -> {
			Depot_livre dl = new Depot_livre();
			dl.setLivre(editMenu(new Livre()));
			System.out.println(dl.getLivre()==null);
			if (dl.getLivre()!=null) {
				list.add(dl);
				livreTable.layout();
			}
		});
		/*
		list.addListener(new ListChangeListener<Depot_livre> () {
			@Override
			public void onChanged(Change<? extends Depot_livre> c) {
				System.out.println("changed");
				System.out.println(list.isEmpty());
				if (!list.isEmpty()) {
					add.setDisable(false);
				}
				else {
					add.setDisable(true);
				}								
			}
		});
		*/
	}
	
	public void find() throws SQLException {
		if (inputDepot.getSelectionModel().getSelectedItem() != null) {
			list.setAll(daoDepot_livre.findLivresByDepot(inputDepot.getSelectionModel().getSelectedItem()));
			add.setDisable(false);
		}
	}
}
