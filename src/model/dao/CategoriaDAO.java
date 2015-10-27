package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Categoria;

public class CategoriaDAO implements Storable<Categoria> {

	@Override
	public boolean create(Categoria categoria) {
		
		
		// TODO
		// setear en la base un unique al nombre y si no se puede hacer el chequeo aca en el metodo
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
	    	System.err.println("Connection to database failed");
	    	System.err.println(e.getMessage());
	    }
	    
	    return result;
	    
	}
	

	@Override
	public Categoria retrieve(Categoria categoria) {
		
		return null;
	}

	@Override
	public boolean update(Categoria categoria) {
		
		return false;
	}

	@Override
	public boolean delete(Categoria categoria) {
		
		return false;
	}

}
