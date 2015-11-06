package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
	
	private static Connection connection = null;
	
	
	public static Connection getConnection() throws SQLException {
		
		if (connection == null) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
			}
			catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
		return connection;
		
	}
	
	public static void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
