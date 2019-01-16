package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Depot;
import depot.model.Depot_livre;
import depot.model.Livre;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAODepot_livre extends DAO<Depot_livre> {
	
	private DAOLivre daoLivre = new DAOLivre(cnx);
	private DAODepot daoDepot = new DAODepot(cnx);
	
	public DAODepot_livre(Connection c) {
		super(c);
	}
	
	@Override
	public String getTable() { return "depot_livre"; }

	@Override
	public Integer insert(Depot_livre dl) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (id_livre, id_depot, quantite) VALUES (?, ?, ?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setInt(1, dl.getLivre().getId());
		pstmt.setInt(2, dl.getDepot().getId());
		pstmt.setInt(3, dl.getQuantite());
		pstmt.executeUpdate();
		return getLastId(pstmt);
	}

	@Override
	public void delete(Depot_livre dl) throws SQLException {
		sql = "DELETE FROM "+getTable()+" WHERE id_livre=? AND id_depot=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, dl.getLivre().getId());
		pstmt.setInt(2, dl.getDepot().getId());
		pstmt.executeUpdate();
	}

	@Override
	public void update(Depot_livre dl) throws SQLException {
		sql = "UPDATE "+getTable()+" SET (quantite) VALUES (?) WHERE id_livre=? AND id_depot=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, dl.getQuantite());
		pstmt.setInt(2, dl.getLivre().getId());
		pstmt.setInt(3, dl.getDepot().getId());
		pstmt.executeUpdate();
	}
	
	public ObservableList<Depot_livre> findLivresByDepot(Depot depot) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" JOIN livre ON depot_livre.id_livre=livre.id JOIN depot ON depot_livre.id_depot=depot.id WHERE depot.id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, depot.getId());
		result = pstmt.executeQuery();
		ObservableList<Depot_livre> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(newEntity(result));
			}
			return list;
		}
		else return null;
	}
	
	public ObservableList<Depot_livre> findDepotByLivre(Livre livre) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" JOIN livre ON depot_livre.id_livre=livre.id JOIN depot ON depot_livre.id_depot=depot.id WHERE id_livre=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, livre.getId());
		result = pstmt.executeQuery();
		ObservableList<Depot_livre> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(newEntity(result));
			}
			return list;
		}
		else return null;
	}
	 

	@Override
	public Depot_livre newEntity(ResultSet result) throws SQLException {		
		return new Depot_livre(daoLivre.newEntity(result), daoDepot.newEntity(result), result.getInt("quantite"));
	}

}
