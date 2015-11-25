package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
	
	private Connection connection;// = null;
	
	public SqliteHelper() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		try {
			connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
//	public static Connection getConnection() throws SQLException {
//		
//		if (connection.isClosed()) {
//			try {
//				Class.forName("org.sqlite.JDBC");
//				connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
//			}
//			catch (ClassNotFoundException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return connection;
//		
//	}
	
	public Connection getConnection() throws SQLException {
		return connection;
	}
	
	public void closeConnection() {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
