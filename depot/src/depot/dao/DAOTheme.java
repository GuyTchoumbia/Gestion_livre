package depot.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import depot.core.DAO;
import depot.model.Livre;
import depot.model.Theme;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DAOTheme extends DAO<Theme> {

	public DAOTheme(Connection c) {
		super(c);	
	}
	
	@Override
	public String getTable() { return "theme"; }
	
	@Override
	public Theme newEntity(ResultSet result) throws SQLException {
		return new Theme(result.getInt("id"), result.getString("libelle"));
	}	
	
	public Theme findByLibelle(String libelle) throws SQLException {
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
	public Integer insert(Theme t) throws SQLException {
		sql = "INSERT INTO "+getTable()+" (libelle) VALUES (?)";
		pstmt = cnx.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		pstmt.setString(1, t.getLibelle());
		pstmt.executeUpdate();
		return getLastId(pstmt);
	}

	@Override
	public void delete(Theme t) throws SQLException {
		sql = "DELETE FROM "+getTable()+" JOIN theme_livre ON theme.id=theme_livre.id_theme WHERE theme.id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, t.getIdTheme());
		pstmt.executeUpdate();	
	}		

	@Override
	public void update(Theme t) throws SQLException {
		sql = "UPDATE "+getTable()+" SET libelle=? WHERE id=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setString(1, t.getLibelle());
		pstmt.setInt(2, t.getIdTheme());
		pstmt.executeUpdate();
	}	
	
	public ObservableList<Livre> findLivresByTheme(Theme theme) throws SQLException {
		sql = "SELECT * from theme_livre JOIN livre ON livre.id=theme_livre.id_livre WHERE id_theme=?";
		pstmt = cnx.prepareStatement(sql);
		pstmt.setInt(1, theme.getIdTheme());
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
