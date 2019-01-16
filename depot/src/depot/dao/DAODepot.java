package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Depot;
import depot.model.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAODepot extends DAO<Depot>{
	
	public DAODepot(Connection c) {
		super(c);
	}	
	
	@Override
	public String getTable() { return "depot"; }
	/*
	 * returns a Depot from the resultSet line;
	 * basicly used for every query
	 */
	public Depot newEntity(ResultSet result) throws SQLException{
		DAORegion dao = new DAORegion(cnx);
		return new Depot(result.getInt("id"), result.getString("libelle"), dao.find(result.getInt("id_region")));
	}	
		
	public ObservableList<Depot> findDepotsByRegion(Region region) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" WHERE id_region=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, region.getId());
		result = pstmt.executeQuery();
		ObservableList<Depot> list = FXCollections.observableArrayList();
		DAODepot daoDepot = new DAODepot(cnx);
		if (result.isBeforeFirst()) {			
			while (result.next()) {
				list.add(daoDepot.newEntity(result));
			}
			return list;
		}
		else return null;
		
	}
	
	@Override
	public Integer insert(Depot d) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (libelle, id_region) VALUES (?, ?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, d.getLibelle());
		pstmt.setInt(2, d.getRegion().getId());
		pstmt.executeUpdate();	
		return getLastId(pstmt);		
	}

	@Override
	public void delete(Depot d) throws SQLException {
		sql = "DELETE FROM "+getTable()+" JOIN depot_livre ON depot.id=depot_livre.id_depot WHERE depot.id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, d.getId());
		pstmt.executeUpdate();		
	}

	@Override
	public void update(Depot d) throws SQLException {
		sql = "UPDATE "+getTable()+" libelle=?, WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, d.getLibelle());
		pstmt.setInt(2, d.getId());
		pstmt.executeUpdate();
	}	
	
}
