package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Auteur;
import depot.model.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOAuteur extends DAO<Auteur> {
	
	private DAOLivre daoLivre = new DAOLivre(cnx);
	
	public DAOAuteur(Connection c) {
		super(c);
	}
	
	@Override
	public String getTable() { return "auteur"; }
	
	@Override
	public Auteur newEntity(ResultSet result) throws SQLException {
		return new Auteur(result.getInt("id"), result.getString("libelle"));
	}		
	/*
	 * used by comboboxes to get a list of suggestions.
	 */
	public Auteur findByLibelle(String libelle) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" WHERE libelle=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, libelle);
		result = pstmt.executeQuery();
		if (result.isBeforeFirst()) {
			result.next();
			return newEntity(result);
		}
		else return null;		
	}
	
	@Override
	public Integer insert(Auteur a) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (libelle) VALUES (?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, a.getLibelle());
		pstmt.executeUpdate();	
		return getLastId(pstmt);		
	}

	@Override
	public void delete(Auteur a) throws SQLException {
		sql = "DELETE FROM "+getTable()+" JOIN auteur_livre ON auteur.id=auteur_livre.id_auteur WHERE auteur.id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, a.getId());
		pstmt.executeUpdate();		
	}

	@Override
	public void update(Auteur a) throws SQLException {
		sql = "UPDATE "+getTable()+" SET libelle=? WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, a.getLibelle());
		pstmt.setInt(2, a.getId());
		pstmt.executeUpdate();
	}	
	
	public ObservableList<Livre> findLivresByAuteur(Auteur auteur) throws SQLException {
		sql = "SELECT * from livre JOIN auteur_livre ON auteur_livre.id_livre=livre.id WHERE auteur_livre.id_auteur=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, auteur.getId());
		result = pstmt.executeQuery();
		ObservableList<Livre> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoLivre.newEntity(result));
			}
			return list;
		}
		else return null;
	}	

}
