package depot.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public class Livre {
	
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final StringProperty isbn = new SimpleStringProperty();
	private final StringProperty titre = new SimpleStringProperty();
	private final IntegerProperty date = new SimpleIntegerProperty();
	private final ListProperty<Auteur> auteurs = new SimpleListProperty<Auteur>();
	private final ListProperty<Editeur> editeurs = new SimpleListProperty<Editeur>();
	private final ListProperty<Theme> themes = new SimpleListProperty<Theme>();
	private final ListProperty<Depot> depots = new SimpleListProperty<Depot>();
	
	public Livre() {}
	
	public Livre(int id, String title, String isbn, Integer date) {
		this.id.set(id);
		this.titre.set(title);
		this.isbn.set(isbn);		
		this.date.set(date);		
	}
	
	/*
	 * bean accessors
	 */
	public IntegerProperty idProperty() { return this.id; }
	public StringProperty isbnProperty() { return this.isbn; }
	public StringProperty titreProperty() { return this.titre; }
	public IntegerProperty dateProperty() { return this.date; }
	public ListProperty<Auteur> auteursProperty() { return this.auteurs; }
	public ListProperty<Editeur> editeursProperty() { return this.editeurs; }
	public ListProperty<Theme> themesProperty() { return this.themes; }
	public ListProperty<Depot> depotsProperty() { return this.depots; }
	
	public final Integer getId() { return this.id.get(); }
	public final String getIsbn() { return this.isbn.get(); }
	public final Integer getDate() { return this.date.get(); }
	public final String getTitre() { return this.titre.get(); }
	public final ObservableList<Auteur> getAuteurs() { return this.auteurs.get(); }
	public final ObservableList<Editeur> getEditeurs() { return this.editeurs.get(); }
	public final ObservableList<Theme> getThemes() { return this.themes.get(); }
	public final ObservableList<Depot> getDepots() { return this.depots.get(); }
	
	public final void setId(int i) { this.id.set(i); }
	public final void setIsbn(String s) { this.isbn.set(s); }
	public final void setTitre(String s) { this.titre.set(s); }
	public final void setDate(Integer i) { this.date.set(i); }
	public final void setAuteurs(ObservableList<Auteur> l) { this.auteurs.set(l); }
	public final void setEditeurs(ObservableList<Editeur> l) { this.editeurs.set(l); }
	public final void setThemes(ObservableList<Theme> l) { this.themes.set(l); }
	public final void setDepots(ObservableList<Depot> l) { this.depots.set(l); }
	
	public void addAuteur(Auteur auteur) { this.auteurs.add(auteur); }
	public void addEditeur(Editeur editeur) { this.editeurs.add(editeur); }
	public void addTheme(Theme theme) { this.themes.add(theme); }
	public void addDepot(Depot depot) { this.depots.add(depot); }
	
	public void removeAuteur(Auteur auteur) { this.auteurs.remove(auteur); }
	public void removeEditeur(Editeur editeur) { this.editeurs.remove(editeur); }
	public void removeTheme(Theme theme) { this.themes.remove(theme); }
	public void removeDepot(Depot depot) { this.depots.remove(depot); }
	
	@Override
	public String toString() {
		return this.getTitre();
	}


}
