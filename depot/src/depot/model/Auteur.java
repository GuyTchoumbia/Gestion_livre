package depot.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Auteur {
	
	private IntegerProperty id = new SimpleIntegerProperty();
	private StringProperty libelle = new SimpleStringProperty();
	private ListProperty<Livre> livres = new SimpleListProperty<Livre>();
	
	public Auteur() {}
	
	public Auteur(Integer id, String l) {
		this.id.set(id);
		this.libelle.set(l);
	}
	
	public IntegerProperty idProperty() { return this.id; }
	public StringProperty libelleProperty() { return this.libelle; }
	public ListProperty<Livre> livresProperty() { return this.livres; }
	
	public final int getId() { return this.id.get(); }	
	public final String getLibelle() { return this.libelle.get(); }
	public final ObservableList<Livre> getLivres() { return this.livres.get(); }
	
	public final void setId(int i) { this.id.set(i); }
	public final void setLibelle(String s) { this.libelle.set(s); }
	public final void setLivres(ObservableList<Livre> l) { this.livres.set(l); }
	
	public void addLivre(Livre livre) { this.livres.add(livre); }
	public void removeLivre(Livre livre) { this.livres.remove(livre); }
	
	@Override
	public String toString() {
		return this.getLibelle();
	}

}
