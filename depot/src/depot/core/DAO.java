package depot.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class DAO<T> {
	
	protected Connection cnx = null;
	protected String sql;
	protected Statement stmt;
	protected PreparedStatement pstmt;
	protected ResultSet result;
	protected ResultSet lastId;
	
	public DAO (Connection c) {
		this.cnx = c;
	}
	
	public abstract String getTable();
	
	/*
	 * the usual suspects
	 */	
	
	public T find(Integer id) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, id);
		result = pstmt.executeQuery();
		if (result.isBeforeFirst()) {
			result.next();
			return newEntity(result);	
		}
		else return null;
	}
	
	public ObservableList<T> findAll() throws SQLException {
		sql = "SELECT * FROM "+getTable();
		result = cnx.createStatement().executeQuery(sql);
		ObservableList<T> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(newEntity(result));
			}
			return list;
		}
		else return null;
	}
	
	public ObservableList<T> findBy(String property, String value) throws SQLException {
		sql = "SELECT * FROM "+getTable()+" WHERE "+property+" LIKE '%"+value+"%'";
		result = cnx.createStatement().executeQuery(sql);
		ObservableList<T> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {
				list.add(newEntity(result));
			}
			return list;
		}
		else return null;
	}
	
	//this one returns the last generated id, useful for insertion into related tables
	public abstract Integer insert(T t) throws SQLException;
	
	public abstract void delete(T t) throws SQLException;
	
	public abstract void update(T t) throws SQLException;
	
	/*
	 * this one to simplify the instanciation of new entities.
	 */
	public abstract T newEntity(ResultSet result) throws SQLException;
	
	
	/*
	 * shortcut method to get the lastId, apposed to every insert method.
	 */
	protected Integer getLastId(PreparedStatement pstmt) throws SQLException{
		lastId = pstmt.getGeneratedKeys();
		if (lastId.isBeforeFirst()) {
			lastId.next();
			return lastId.getInt(1);
		}
		else return null;
	}
	/*
	public ObservableList<T> findBy(String column, String value) throws SQLException {
		sql = "SELECT * FROM "+table+" WHERE "+column+" LIKE "+value+"%";
		result = cnx.createStatement().executeQuery(sql);
		ObservableList<T> list = FXCollections.observableArrayList();
		if (result.isBeforeFirst()) {
			while (result.next()) {			
				list.add(newEntity(result));		
			}
			return list;
		}
		else return null;
	}
	*/
}
