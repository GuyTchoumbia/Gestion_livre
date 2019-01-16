package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import depot.core.DAO;
import depot.model.Auteur;
import depot.model.Depot;
import depot.model.Editeur;
import depot.model.Livre;
import depot.model.Theme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOLivre extends DAO<Livre> {
			
	public DAOLivre(Connection c) { 
		super(c);
	}
	
	@Override
	public String getTable() { return "livre"; }
	
	@Override
	public Livre newEntity(ResultSet result) throws SQLException{
		int id = result.getInt("id");
		return new Livre(id, result.getString("titre"), result.getString("isbn"), result.getInt("date"));	
	}

	@Override
	public ObservableList<Livre> findAll() throws SQLException{
		sql = "SELECT * FROM "+getTable();
		stmt = cnx.createStatement();
		result = stmt.executeQuery(sql);
		ObservableList<Livre> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {			
				list.add(newEntity(result));		
			}
			return list;
		}
		else return null;
	}
	
	public ObservableList<Livre> findBy(String property, String value) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" WHERE "+property+" LIKE '%"+value+"%'";
		result = cnx.createStatement().executeQuery(sql);
		ObservableList<Livre> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {			
				list.add(newEntity(result));		
			}
			return list;
		}
		else return null;
	}	
	

	@Override
	public Integer insert(Livre livre) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (title, isbn, date) VALUES (?, ?, ?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, livre.getTitre());
		pstmt.setString(2, livre.getIsbn());
		pstmt.setInt(3, livre.getDate());
		pstmt.executeUpdate();
		return getLastId(pstmt);
	}

	@Override
	public void update(Livre livre) throws SQLException {
		sql = "UPDATE "+getTable()+" SET titre=?, isbn=?, date=? WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, livre.getTitre());
		pstmt.setString(2, livre.getIsbn());
		pstmt.setInt(3, livre.getDate());
		pstmt.setInt(4, livre.getId());
		pstmt.executeUpdate();		
	}

	@Override
	public void delete(Livre livre) throws SQLException {
		sql = "DELETE FROM "+getTable()+" "
				+ "JOIN auteur_livre ON auteur_livre.id_livre=livre.id "
				+ "JOIN editeur_livre ON editeur_livre.id_livre=livre.id "
				+ "JOIN theme_livre ON theme_livre.id_livre=livre.id "
				+ "JOIN depot_livre ON depot_livre.id_livre=livre.id "
				+ "WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.executeUpdate();		
	}
	
	/*
	 * This is were the usual DAO Pattern finds its limits: 
	 * in theory we'd have an entity for the tables formed by to OneToMany/ManyToOne relations, but is unsustainable
	 * We need each table-related object to contain each other to be able to access their respective properties at any time, 
	 * and the necessity for an entity made especially for said table becomes obsolete. 
	 * Each DAO becomes responsible for "filling" its entity, whatever the table it needs to consult.
	 * this is heavy, but i have no idea how to implement this more elegantly
	 */
	public ObservableList<Auteur> findAuteursByLivre(Livre livre) throws SQLException {
		sql = "SELECT * from auteur_livre JOIN auteur ON auteur_livre.id_auteur=auteur.id WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		result = pstmt.executeQuery();
		ObservableList<Auteur> list = FXCollections.observableArrayList();
		DAOAuteur daoAuteur = new DAOAuteur(cnx);
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoAuteur.newEntity(result));
			}
		}
		return list;
	}
	
	public void deleteAuteurLivre(Livre livre) throws SQLException {
		sql = "DELETE FROM auteur_livre WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.executeUpdate();
	}
	
	public void insertAuteurLivre(Livre livre, Auteur auteur) throws SQLException {
		sql = "INSERT into auteur_livre (id_livre, id_auteur) VALUES (?, ?)";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.setInt(2, auteur.getId());
		pstmt.executeUpdate();
	}
		
	public ObservableList<Editeur> findEditeursByLivre(Livre livre) throws SQLException {
		sql = "SELECT * from editeur_livre JOIN editeur ON editeur_livre.id_editeur=editeur.id WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		result = pstmt.executeQuery();
		ObservableList<Editeur> list = FXCollections.observableArrayList();
		DAOEditeur daoEditeur = new DAOEditeur(cnx);
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoEditeur.newEntity(result));
			}
		}
		return list;
	}
	
	public void deleteEditeurLivre(Livre livre) throws SQLException {
		sql = "DELETE FROM editeur_livre WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.executeUpdate();
	}
	
	public void insertEditeurLivre(Livre livre, Editeur editeur) throws SQLException {
		sql = "INSERT into editeur_livre (id_livre, id_editeur) VALUES (?, ?)";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.setInt(2, editeur.getId());
		pstmt.executeUpdate();
	}
	
	public ObservableList<Theme> findThemesByLivre(Livre livre) throws SQLException {
		sql = "SELECT * from theme_livre JOIN theme ON theme_livre.id_theme=theme.id WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		result = pstmt.executeQuery();
		ObservableList<Theme> list = FXCollections.observableArrayList();
		DAOTheme daoTheme = new DAOTheme(cnx);
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoTheme.newEntity(result));
			}
		}
		return list;
	}
	
	public void deleteThemeLivre(Livre livre) throws SQLException {
		sql = "DELETE FROM theme_livre WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.executeUpdate();
	}
	
	public void insertThemeLivre(Livre livre, Theme theme) throws SQLException {
		sql = "INSERT into theme_livre (id_livre, id_theme) VALUES (?, ?)";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.setInt(2, theme.getIdTheme());
		pstmt.executeUpdate();
	}
	
	public ObservableList<Depot> findDepotsByLivre(Livre livre) throws SQLException {
		sql = "SELECT * from depot_livre JOIN depot ON depot_livre.id_depot=depot.id WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		result = pstmt.executeQuery();
		ObservableList<Depot> list = FXCollections.observableArrayList();
		DAODepot daoDepot = new DAODepot(cnx);
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoDepot.newEntity(result));
			}
		}
		return list;
	}
	
	public void deleteDepotLivre(Livre livre) throws SQLException {
		sql = "DELETE FROM depot_livre WHERE id_livre= ?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.executeUpdate(sql);
	}
	
	public void insertDepotLivre(Livre livre, Depot depot) throws SQLException {
		sql = "INSERT into depot_livre (id_livre, id_depot) VALUES (?, ?)";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		pstmt.setInt(2, depot.getId());
		pstmt.executeUpdate();
	}
	/*
	 * method used to construct the sql from the different pinputs passed as parameters.
	 * according to the existence or not (test isEmpty) of each parameter, it will add the associated (through the object SqlBlock) JOIN strings and AND strings to the request String
	 * by first adding them to the component strings.
	 */
	public ObservableList<Livre> megaSearch(String titre, String isbn, String date, String auteur, String editeur, String theme, String depot) throws SQLException {				
		/*
		 * the 3 main components as StringBuilders.
		 */
		StringBuilder sql = new StringBuilder("SELECT * FROM livre");
		StringBuilder join = new StringBuilder("");
		StringBuilder and = new StringBuilder(" WHERE 1=1");
		
		/*
		 * the inner class associating parameters to their respective JOIN and AND Strings.
		 */
		final class Blk {
			private String param;
			private String join;
			private String and;
			
			public Blk (String p, String j, String a) {
				param = p;
				join = j;
				and = a;
			}			
		}
		/*
		 * the list containing the different objects, to facilitate iteration later
		 */
		List<Blk> list = new ArrayList<Blk>(); 
		list.add(new Blk(titre, "", " AND titre=?"));
		list.add(new Blk(isbn, "", " AND isbn=?"));
		list.add(new Blk(date, "", " AND date=?"));
		String joinAuteur = " JOIN auteur_livre ON livre.id=auteur_livre.id_livre JOIN auteur ON auteur_livre.id_auteur=auteur.id_auteur";
		list.add(new Blk(auteur, joinAuteur, " AND nom=?"));
		String joinEditeur = " JOIN editeur_livre ON livre.id=editeur_livre.id_livre JOIN editeur ON editeur_livre.id_editeur=editeur.id_editeur";
		list.add(new Blk(editeur, joinEditeur, " AND libelle=?"));
		String joinTheme = " JOIN theme_livre ON livre.id=theme_livre.id_livre JOIN theme ON theme.id_theme=theme_livre.id_theme";
		list.add(new Blk(theme, joinTheme, " AND libelle=?"));
		String joinDepot = " JOIN depot_livre ON livre.id=depot_livre.id_livre JOIN depot ON depot.id_depot=depot_livre.id_depot";
		list.add(new Blk(depot, joinDepot, " AND libelle=?"));
		/*
		Hashtable<String, String> param = new Hashtable<String, String>();
		param.put(titre, " AND titre LIKE '?%'");
		param.put(isbn, " AND isbn='"+isbn+"'");
		param.put(date, " AND date='"+date+"'");
		param.put(auteur, " AND id IN (SELECT id_livre FROM auteur_livre JOIN auteur ON auteur_livre.id_auteur=auteur.id_auteur WHERE nom='"+auteur+"')");
		param.put(editeur, " AND id IN (SELECT id_livre FROM editeur_livre JOIN editeur ON editeur_livre.id_editeur=editeur.id_editeur WHERE libelle='"+editeur+"')");
		param.put(theme, " AND id IN (SELECT id_livre FROM theme_livre JOIN theme ON theme_livre.id_theme=theme.id_theme WHERE libelle='"+theme+"')");
		param.put(depot, " AND id IN (SELECT id_livre FROM depot_livre JOIN depot ON depot_livre.id_depot=depot.id_depot WHERE libelle='"+depot+"')");
		
		param.put(auteur, " AND nom=?")
		param.forEach((key, value)-> {
			if (!key.isEmpty()) {
				sql.append(value);				
			}
		});	
		*/
		/*
		 * we filter the list thanks to a stream
		 * then append the differents JOINs and ANDs to the main components.
		 */
		list = list.stream()
			.filter(blk->!blk.param.isEmpty())
			.collect(Collectors.toCollection(ArrayList::new));
		list.forEach(blk-> {
					join.append(blk.join);
					and.append(blk.and);
				});
		//we build the final string by appending the 3 main components together
		sql.append(join);
		sql.append(and);
		//and prepare the statement
		System.out.println(sql.toString());
		pstmt = cnx.prepareStatement(sql.toString());
		
		/*
		 * unfortunatly we can't use a local int counter inside a lambda expression, so we use an iterator on the stream created above
		 * it is going to set the parameters of the prepare statement
		 */
		int i = 0;
		Iterator<Blk> it = list.iterator();
		while (it.hasNext()) {
			i++;
			Blk blk=it.next();
			pstmt.setString(i, blk.param);
		}	
		
		result = pstmt.executeQuery();
		ObservableList<Livre> listLivre = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				listLivre.add(newEntity(result));
			}
			return listLivre;
		}
		else return null;
	}
		/*
		findBy("titre", titre);
		findBy("isbn", isbn);
		findBy("date", date);
		DAOAuteur daoAuteur = new DAOAuteur(cnx);
		daoAuteur.findBy("nom", auteur);
		DAOEditeur daoEditeur = new DAOEditeur(cnx);
		daoEditeur.findBy("libelle", editeur);
		DAOTheme daoTheme = new DAOTheme(cnx);
		daoTheme.findBy("libelle", theme);
		DAODepot daoDepot = new DAODepot(cnx);
		daoDepot.findBy("libelle", depot);
			*/	
	
}
