package depot.core;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
	
	private static java.sql.Connection cnx;
	private static String user = "root";
	private static String password = "";
	private static String url = "jdbc:mysql://localhost:3306/depot"; 
	
	private Connect() {
		try {
			cnx = DriverManager.getConnection(url,user,password);			
		}
		catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public static java.sql.Connection getInstance() {
		if (cnx == null)
			new Connect();
		return cnx;
	}

}
