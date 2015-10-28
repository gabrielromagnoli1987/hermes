package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Categoria;

public class CategoriaDAO implements Storable<Categoria> {

	@Override
	public boolean create(Categoria categoria) {
		
		
		// TODO		
		// ver el tema de relaciones M to M, si meter la logica de que la categoria se crea con los contextos o si se setean despues
		
		boolean result = false;
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	return result;
	    	
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    }
	    
	    return result;
	    
	}
	

	@Override
	public Categoria retrieve(Categoria categoria) {
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "SELECT nombre, descripcion FROM categoria WHERE nombre = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if(resultSet.next()) {
	    		categoria.setId(resultSet.getInt("id"));
	    		categoria.setNombre(resultSet.getString("nombre"));
	    		categoria.setDescripcion(resultSet.getString("descripcion"));	    		
	    	}
	    	
	    	connection.close();	    	
	    	
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return categoria;
		
	}
	
	@Override
	public List<Categoria> retrieveAll(Categoria object) {
		
		return null;
	}

	
	@Override
	public boolean update(Categoria categoria) {
		
		boolean result = false;
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "UPDATE categoria SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());	    	
	    	preparedStatement.setInt(3, categoria.getId());
	    	
	    	if(preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

	@Override
	public boolean delete(Categoria categoria) {
		
		boolean result = false;
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "DELETE FROM categoria WHERE nombre = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	if(preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	
	    } catch(ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

}
