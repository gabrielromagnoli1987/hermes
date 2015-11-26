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
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;		
		ResultSet resultSet = null;
		Statement statement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO categoria (nombre, descripcion) VALUES (?, ?)";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() >= 1) {
	    		result = true;
	    		
	    		query = "SELECT MAX(id) FROM categoria";
		    	statement = connection.createStatement();
		    	resultSet = statement.executeQuery(query);
		    	categoria.setId(resultSet.getInt(1));
	    		
	    		List<Contexto> contextos = categoria.getContextos();
	    		
	    		if (! contextos.isEmpty()) {
	    			
	    			ContextoDAO contextoDAO = new ContextoDAO();
	    			
	    			for (Contexto contexto : contextos) {
	    				
	    				// Se chequea si un contexto con el mismo nombre ya existe en la BD
	    				Contexto contextoDB = contextoDAO.retrieve(contexto);
	    				if (! contextoDB.getNombre().equals(contexto.getNombre())) {
	    					result = result && contextoDAO.create(contexto);
	    				}
	    				// Se guardan los IDs en la tabla intermediaria
						result = result && saveOnMapTable(categoria.getId(), contexto.getId());
					}
	    			
		    	}
	    	}
	    	
	    	return result;
	    	
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement, statement);
	    }
	    
	    return result;
	    
	}
	
	
	private boolean saveOnMapTable(Integer categoriaID, Integer contextoID) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO categoria_contexto (categoriaID, contextoID) VALUES (?, ?)";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoriaID);
	    	preparedStatement.setInt(2, contextoID);
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
		
		return result;
		
	}


	@Override
	public Categoria retrieve(Categoria categoria) {
		
		Categoria categoria_db = new Categoria("", "", new ArrayList<Contexto>());
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM categoria WHERE nombre = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	resultSet = preparedStatement.executeQuery();	    	
	    	
	    	if (resultSet.next()) {
	    		categoria_db.setNombre(resultSet.getString("nombre"));
	    		categoria_db.setDescripcion(resultSet.getString("descripcion"));
	    		categoria_db.setId(resultSet.getInt("id"));
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return categoria_db;
		
	}
	
	@Override
	public Categoria retrieveById(Categoria categoria) {
		
		Categoria categoria_db = new Categoria("", "", new ArrayList<Contexto>());
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM categoria WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, categoria.getId());
	    	
	    	resultSet = preparedStatement.executeQuery();	    	
	    	
	    	if (resultSet.next()) {
	    		categoria_db.setNombre(resultSet.getString("nombre"));
	    		categoria_db.setDescripcion(resultSet.getString("descripcion"));
	    		categoria_db.setId(categoria.getId());
	    	}
	    	
//	    	CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
//	    	List<Contexto> contextos = categoriaContextoDAO.retrieveAllContextsOfCategory(categoria_db);
//	    	categoria_db.setContextos(contextos);
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return categoria_db;
	}
	
	
	@Override
	public List<Categoria> retrieveAll() {
		
		List<Categoria> categorias = new ArrayList<Categoria>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM categoria";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
	    	
	    	while (resultSet.next()) {
	    		Categoria categoria = new Categoria();
	    		categoria.setId(resultSet.getInt("id"));
	    		categoria.setNombre(resultSet.getString("nombre"));
	    		categoria.setDescripcion(resultSet.getString("descripcion"));
	    		
	    		List<Contexto> contextos = categoriaContextoDAO.retrieveAllContextsOfCategory(categoria);
	    		categoria.setContextos(contextos);
	    		
	    		categorias.add(categoria);
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return categorias;
	}

	
	@Override
	public boolean update(Categoria categoria) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "UPDATE categoria SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	preparedStatement.setString(2, categoria.getDescripcion());	    	
	    	preparedStatement.setInt(3, categoria.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	// falta el manejo de update sobre los contextos asociados a la categoria actual
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
		
		return result;
	}

	@Override
	public boolean delete(Categoria categoria) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	// necesario para realizar el delete en cascada
	    	String enableForeingKeys = "PRAGMA foreign_keys = ON";
	    	preparedStatement = connection.prepareStatement(enableForeingKeys);
	    	preparedStatement.executeUpdate();
	    	
	    	String query = "DELETE FROM categoria WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, categoria.getNombre());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
		
		return result;
	}


	@Override
	public List<Categoria> retrieveFilteredBy(Object[] filters) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
