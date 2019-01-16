package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Editeur;
import depot.model.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOEditeur extends DAO<Editeur>{
	
	public DAOEditeur(Connection c) {
		super(c);	
	}
	
	@Override
	public String getTable() { return "editeur"; }
	
	public Editeur newEntity(ResultSet result) throws SQLException {
		return new Editeur(result.getInt("id"), result.getString("libelle"));
	}		
	
	public Editeur findByLibelle(String libelle) throws SQLException {
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
	public Integer insert(Editeur e) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (libelle) VALUES (?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, e.getLibelle());
		pstmt.executeUpdate();	
		return getLastId(pstmt);
	}

	@Override
	public void delete(Editeur e) throws SQLException {
		sql = "DELETE FROM "+getTable()+" JOIN editeur_livre ON editeur.id=editeur_livre.id_editeur WHERE editeur.id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, e.getId());
		pstmt.executeUpdate();
	}	

	@Override
	public void update(Editeur e) throws SQLException {
		sql = "UPDATE "+getTable()+" SET libelle=? WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1,  e.getLibelle());
		pstmt.setInt(2, e.getId());
		pstmt.executeUpdate();		
	}
	
	public ObservableList<Livre> findLivresByEditeur(Editeur editeur) throws SQLException {
		sql = "SELECT * from editeur_livre JOIN livre ON editeur_livre.id_livre=livre.id WHERE id_editeur=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, editeur.getId());
		result = pstmt.executeQuery();
		ObservableList<Livre> list = FXCollections.observableArrayList();
		DAOLivre daoLivre = new DAOLivre(cnx);
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(daoLivre.newEntity(result));
			}
			return list;
		}
		else return null;
	}
		
}
