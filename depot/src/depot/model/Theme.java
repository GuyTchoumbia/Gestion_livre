package depot.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Theme {
	
	private IntegerProperty idTheme = new SimpleIntegerProperty();
	private StringProperty libelle = new SimpleStringProperty();
	private ListProperty<Livre> livres = new SimpleListProperty<Livre>();

	public Theme() {}
	
	public Theme(int id, String s) {
		this.idTheme.set(id);
		this.libelle.set(s);
		
	}
	
	public IntegerProperty idThemeProperty() { return this.idTheme; }
	public StringProperty libelleProperty() { return this.libelle; }
	public ListProperty<Livre> livresProperty() { return this.livres; }
	
	public final int getIdTheme() { return this.idTheme.get(); }	
	public final String getLibelle() { return this.libelle.get(); }
	public final ObservableList<Livre> getLivres() { return this.livres.get(); }
	
	public final void setIdTheme(int i) { this.idTheme.set(i); }
	public final void setLibelle(String s) { this.libelle.set(s); }
	public final void setLivres(ObservableList<Livre> l) { this.livres.set(l); }
	
	public void addLivre(Livre livre) { this.livres.add(livre); }
	public void removeLivre(Livre livre) { this.livres.remove(livre); }
	
	@Override
	public String toString() {
		return this.getLibelle();
	}
}
