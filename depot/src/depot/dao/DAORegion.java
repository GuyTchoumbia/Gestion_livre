package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Region;

public class DAORegion extends DAO<Region>{	

	public DAORegion(Connection c) {
		super(c);	
	}
	
	@Override
	public String getTable() { return "region"; }
	
	@Override
	public Region newEntity(ResultSet result) throws SQLException {
		return new Region(result.getInt("id"), result.getString("libelle"));
	}		
		
	@Override
	public Integer insert(Region r) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (libelle) VALUES (?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, r.getLibelle());
		pstmt.executeUpdate();	
		return getLastId(pstmt);
	}

	@Override
	public void delete(Region r) throws SQLException {
		sql = "DELETE FROM "+getTable()+" WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, r.getId());
		pstmt.executeUpdate();	
	}

	@Override
	public void update(Region r) throws SQLException {
		sql = "UPDATE "+getTable()+" SET libelle=? WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, r.getLibelle());
		pstmt.setInt(2, r.getId());
		pstmt.executeUpdate();		
	}

}
