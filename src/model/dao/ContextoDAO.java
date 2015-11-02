package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;

public class ContextoDAO implements Storable<Contexto> {

	@Override
	public boolean create(Contexto contexto) {
		
		boolean result = false;
			
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO contexto (nombre, descripcion) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {	    		
	    		result = true;
	    		
	    		List<Categoria> categorias = contexto.getCategorias();
	    		
	    		if (! categorias.isEmpty()) {
	    			
	    			Contexto contextoDB = this.retrieve(contexto);
	    			
	    			CategoriaDAO categoriaDAO = new CategoriaDAO();
	    			
	    			for (Categoria categoria : categorias) {
	    				Categoria categoriaDB = categoriaDAO.retrieve(categoria);
	    				if (! categoriaDB.getNombre().equals(categoria.getNombre())) {
	    					result = result && categoriaDAO.create(categoria);
	    					categoriaDB = categoriaDAO.retrieve(categoria);
	    				}
						result = result && saveOnMapTable(categoriaDB.getId(), contextoDB.getId());
					}
	    			
		    	}
	    	}
	    	
	    	return result;
	    		    
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    }
	    
	    return result;
	}
	
	private boolean saveOnMapTable(Integer categoriaID, Integer contextoID) {
		
		boolean result = false;
			
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO categoria_contexto (categoriaID, contextoID) VALUES (?, ?)";
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoriaID);
	    	preparedStatement.setInt(2, contextoID);
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}	    	
	    	
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    }
		
		return result;
		
	}

	@Override
	public Contexto retrieve(Contexto contexto) {
		
		Contexto contexto_db = new Contexto("", "", new ArrayList<Categoria>());
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM contexto WHERE nombre = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		contexto_db.setNombre(resultSet.getString("nombre"));
	    		contexto_db.setDescripcion(resultSet.getString("descripcion"));
	    	}
	    	
//	    	query = "select last_insert_rowid()";
//	    	Statement statement = connection.createStatement();
//	    	resultSet = statement.executeQuery(query);
//	    	contexto.setId(resultSet.getInt(1));
	    	
	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	    	if(generatedKeys.next()) {
	    		contexto_db.setId(generatedKeys.getInt(1));
            }
	    	
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return contexto_db;
	}
	
	@Override
	public List<Contexto> retrieveAll() {
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM contexto";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Contexto contexto = new Contexto();
	    		contexto.setId(resultSet.getInt("id"));
	    		contexto.setNombre(resultSet.getString("nombre"));
	    		contexto.setDescripcion(resultSet.getString("descripcion"));
	    		contextos.add(contexto);
	    	}
	    	
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return contextos;
	}

	@Override
	public boolean update(Contexto contexto) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "UPDATE contexto SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());	    	
	    	preparedStatement.setInt(3, contexto.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

	@Override
	public boolean delete(Contexto contexto) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	// necesario para realizar el delete en cascada
	    	String enableForeingKeys = "PRAGMA foreign_keys = ON";
	    	PreparedStatement preparedStatement = connection.prepareStatement(enableForeingKeys);
	    	preparedStatement.executeUpdate();
	    	
	    	String query = "DELETE FROM contexto WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    		    	
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

}
