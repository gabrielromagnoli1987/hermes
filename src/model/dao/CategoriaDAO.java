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


public class CategoriaDAO implements Storable<Categoria> {

	@Override
	public boolean create(Categoria categoria) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {	    		
	    		result = true;
	    		
	    		List<Contexto> contextos = categoria.getContextos();
	    		
	    		if (! contextos.isEmpty()) {
	    			
	    			// categoriaDB a diferencia de cateogia, SI tiene el id generado por la base de datos
	    			Categoria categoriaDB = this.retrieve(categoria);
	    			
	    			ContextoDAO contextoDAO = new ContextoDAO();
	    			
	    			for (Contexto contexto : contextos) {
						result = result && contextoDAO.create(contexto);
						Contexto contextoDB = contextoDAO.retrieve(contexto);
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
	public Categoria retrieve(Categoria categoria) {
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM categoria WHERE nombre = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();	    	
	    	
	    	if (resultSet.next()) {	    		
	    		categoria.setNombre(resultSet.getString("nombre"));
	    		categoria.setDescripcion(resultSet.getString("descripcion"));
	    	}
	    		    	
	    	query = "select last_insert_rowid()";
	    	Statement statement = connection.createStatement();
	    	resultSet = statement.executeQuery(query);
	    	categoria.setId(resultSet.getInt(1));
	    	
//	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//	    	if(generatedKeys.next()) {
//	    		categoria.setId(generatedKeys.getInt(1));
//            }
	    	
	    	// al traerse la categoria deberia traerse los contextos	    	
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return categoria;
		
	}
	
	@Override
	public List<Categoria> retrieveAll() {
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Categoria categoria = new Categoria();
	    		categoria.setId(resultSet.getInt("id"));
	    		categoria.setNombre(resultSet.getString("nombre"));
	    		categoria.setDescripcion(resultSet.getString("descripcion"));
	    		
	    		// aca hay que traer todos los contextos de la categoria actual
	    		
	    		categorias.add(categoria);
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return categorias;
	}

	
	@Override
	public boolean update(Categoria categoria) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "UPDATE categoria SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());	    	
	    	preparedStatement.setInt(3, categoria.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	// falta el manejo de update sobre los contextos asociados a la categoria actual
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

	@Override
	public boolean delete(Categoria categoria) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	// necesario para realizar el delete en cascada
	    	String enableForeingKeys = "PRAGMA foreign_keys = ON";
	    	PreparedStatement preparedStatement = connection.prepareStatement(enableForeingKeys);
	    	preparedStatement.executeUpdate();
	    	
	    	String query = "DELETE FROM categoria WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

}
