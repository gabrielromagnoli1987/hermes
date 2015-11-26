package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	
	public void closeAll(ResultSet resultSet, PreparedStatement preparedStatement) {
		
		try {
			if (resultSet != null) {
				resultSet.close();
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	try {
    		if (preparedStatement != null) {
    			preparedStatement.close();
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	this.closeConnection();
	}
	
	public void closeAll(ResultSet resultSet, PreparedStatement preparedStatement, Statement statement) {
		
		try {
			if (resultSet != null) {
				resultSet.close();
			}
    	} catch (SQLException e) {
			e.printStackTrace();
		}
    	try {
    		if (preparedStatement != null) {
    			preparedStatement.close();
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	try {
    		if (statement != null) {
    			statement.close();
    		}
		} catch (SQLException e) {
			e.printStackTrace();
		}
    	this.closeConnection();
	}

}
