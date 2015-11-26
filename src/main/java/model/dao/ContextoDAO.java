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
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Statement statement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO contexto (nombre, descripcion) VALUES (?, ?)";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() >= 1) {
	    		result = true;
	    		
	    		query = "SELECT MAX(id) FROM contexto";
		    	statement = connection.createStatement();
		    	resultSet = statement.executeQuery(query);
		    	contexto.setId(resultSet.getInt(1));
		    	
		    	// si no cierro la conexion en este punto me queda lockeada la base
		    	try {
		    		resultSet.close();
		    	} catch(SQLException e) {
		    		System.err.println(e.getMessage());
		    	}
		    	
	    		List<Categoria> categorias = contexto.getCategorias();
	    		
	    		if (! categorias.isEmpty()) {
	    			
	    			CategoriaDAO categoriaDAO = new CategoriaDAO();
	    			
	    			for (Categoria categoria : categorias) {
	    				
	    				// Se chequea si una categoria con el mismo nombre ya existe en la BD
	    				Categoria categoriaDB = categoriaDAO.retrieve(categoria);
	    				if (! categoriaDB.getNombre().equals(categoria.getNombre())) {
	    					result = result && categoriaDAO.create(categoria);	    					
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
	    	sqliteHelper.closeAll(null, preparedStatement, statement);
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
	public Contexto retrieve(Contexto contexto) {
		
		Contexto contexto_db = new Contexto("", "", new ArrayList<Categoria>());
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT id, nombre, descripcion FROM contexto WHERE nombre = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		contexto_db.setNombre(resultSet.getString("nombre"));
	    		contexto_db.setDescripcion(resultSet.getString("descripcion"));
	    		contexto_db.setId(resultSet.getInt("id"));
	    	}
	    	
//	    	resultSet.close();
//	    	preparedStatement.close();
	    	
	    	CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
	    	List<Categoria> categorias = categoriaContextoDAO.retrieveAllCategoriesOfContext(contexto_db);
	    	contexto_db.setCategorias(categorias);
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return contexto_db;
	}
	
	@Override
	public Contexto retrieveById(Contexto contexto) {
		
		Contexto contexto_db = new Contexto("", "", new ArrayList<Categoria>());
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM contexto WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, contexto.getId());
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		contexto_db.setNombre(resultSet.getString("nombre"));
	    		contexto_db.setDescripcion(resultSet.getString("descripcion"));
	    		contexto_db.setId(contexto.getId());
	    	}
	    	
	    	CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
	    	List<Categoria> categorias = categoriaContextoDAO.retrieveAllCategoriesOfContext(contexto_db);
	    	contexto_db.setCategorias(categorias);
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return contexto_db;
	}
	
	
	@Override
	public List<Contexto> retrieveAll() {
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM contexto";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
	    	
	    	while (resultSet.next()) {
	    		Contexto contexto = new Contexto();
	    		contexto.setId(resultSet.getInt("id"));
	    		contexto.setNombre(resultSet.getString("nombre"));
	    		contexto.setDescripcion(resultSet.getString("descripcion"));
	    			    		
		    	List<Categoria> categorias = categoriaContextoDAO.retrieveAllCategoriesOfContext(contexto);
		    	contexto.setCategorias(categorias);
	    		
	    		contextos.add(contexto);
	    	}
	    	
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
		
		return contextos;
	}

	@Override
	public boolean update(Contexto contexto) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "UPDATE contexto SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	preparedStatement.setString(2, contexto.getDescripcion());	    	
	    	preparedStatement.setInt(3, contexto.getId());
	    	
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
	public boolean delete(Contexto contexto) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	// necesario para realizar el delete en cascada
	    	String enableForeingKeys = "PRAGMA foreign_keys = ON";
	    	preparedStatement = connection.prepareStatement(enableForeingKeys);
	    	preparedStatement.executeUpdate();
	    	
	    	String query = "DELETE FROM contexto WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, contexto.getNombre());
	    	
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
	public List<Contexto> retrieveFilteredBy(Object[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
