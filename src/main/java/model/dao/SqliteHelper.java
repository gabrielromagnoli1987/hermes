package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteHelper {
	
	private Connection connection;
	
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
