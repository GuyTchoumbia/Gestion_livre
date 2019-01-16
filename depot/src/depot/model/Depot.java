package depot.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Depot {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty libelle = new SimpleStringProperty();
	private ObjectProperty<Region> region = new SimpleObjectProperty<Region>();
	
	public Depot() {}
	
	public Depot(int id, String libelle, Region region) {
		this.id.set(id);
		this.libelle.set(libelle);
		this.region.set(region);
	}
	/*
	 * bean accessors
	 */
	public IntegerProperty idProperty() { return this.id; }
	public StringProperty libelleProperty() { return this.libelle; }
	public ObjectProperty<Region> regionProperty() { return this.region; }	

	public final int getId() { return this.id.get(); }	
	public final String getLibelle() { return this.libelle.get(); }
	public final Region getRegion() { return this.region.get(); }

	public final void setId(int i) { this.id.set(i); }
	public final void setLibelle(String s) { this.libelle.set(s); }
	public final void setRegion(Region r) { this.region.set(r); }
	
	@Override
	public String toString() {
		return this.getLibelle();
	}

}
