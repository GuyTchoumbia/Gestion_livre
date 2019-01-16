package depot.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Region {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty libelle = new SimpleStringProperty();
	private ListProperty<Depot> depots = new SimpleListProperty<>();
	
	public Region() {}
	
	public Region(Integer i, String s) {
		this.id.set(i);
		this.libelle.set(s);
	}
	public IntegerProperty idRegionProperty() { return this.id; }
	public StringProperty libelleProperty() { return this.libelle; }
	public ListProperty<Depot> depotsProperty() { return this.depots; }
	
	public final int getId() { return this.id.get(); }	
	public final String getLibelle() { return this.libelle.get(); }
	public final ObservableList<Depot> getDepots() { return this.depots.get(); }
	
	public final void setId(int i) { this.id.set(i); }
	public final void setLibelle(String s) { this.libelle.set(s); }
	public final void setDepots(ObservableList<Depot> l) { this.depots.set(l); }
	@Override
	public String toString() {
		return this.getLibelle();
	}
}
