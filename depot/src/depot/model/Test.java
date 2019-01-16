package depot.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Test {
	
	private StringProperty nom = new SimpleStringProperty();
	
	public Test(String nom) {
		this.nom.set(nom);
	}
	
	public final String getNom() { return this.nom.get(); }
	public StringProperty nomProperty() { return this.nom; }
	public final void setNom(String s) { this.nom.set(s); }

}
