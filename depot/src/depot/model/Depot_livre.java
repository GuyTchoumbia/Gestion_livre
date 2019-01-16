package depot.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Depot_livre {
	
	private final SimpleObjectProperty<Livre> livre = new SimpleObjectProperty<Livre>();
	private final SimpleObjectProperty<Depot> depot = new SimpleObjectProperty<Depot>();
	private final SimpleIntegerProperty quantite = new SimpleIntegerProperty();
	
	public Depot_livre() {}
	
	public Depot_livre(Livre livre, Depot depot, int quantite) {
		this.livre.set(livre);
		this.depot.set(depot);
		this.quantite.set(quantite);		
	}
	
	public SimpleObjectProperty<Livre> livreProperty() { return this.livre; }
	public SimpleObjectProperty<Depot> depotProperty() { return this.depot; }
	public SimpleIntegerProperty quantiteProperty() { return this.quantite; }

	public final Livre getLivre() { return this.livre.get(); }
	public final Depot getDepot() { return this.depot.get(); }
	public final int getQuantite() { return this.quantite.get(); }
	
	public final void setLivre(Livre l) { this.livre.set(l); }
	public final void setDepot(Depot d) { this.depot.set(d); }
	public final void setQuantite(int q) { this.quantite.set(q); }
	
}
