package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Etiqueta;


public class EtiquetaDAO implements Storable<Etiqueta> {

	@Override
	public boolean create(Etiqueta etiqueta) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO etiqueta (nombre, descripcion) VALUES (?, ?)";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	preparedStatement.setString(2, etiqueta.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}
	    	
	    	return result;
	    	
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
	    
	    return result;
	}

	@Override
	public Etiqueta retrieve(Etiqueta etiqueta) {
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Etiqueta etiqueta_db = new Etiqueta("", "");
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT id, nombre, descripcion FROM categoria WHERE nombre = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	
	    	resultSet = preparedStatement.executeQuery();	    	
	    	
	    	if (resultSet.next()) {	    		
	    		etiqueta_db.setNombre(resultSet.getString("nombre"));
	    		etiqueta_db.setDescripcion(resultSet.getString("descripcion"));		    	
	    		etiqueta_db.setId(resultSet.getInt("id"));
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return etiqueta_db;
	}
	
	@Override
	public Etiqueta retrieveById(Etiqueta etiqueta) {
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		Etiqueta etiqueta_db = new Etiqueta("", "");
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, descripcion FROM categoria WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, etiqueta.getId());
	    	
	    	resultSet = preparedStatement.executeQuery();	    	
	    	
	    	if (resultSet.next()) {	    		
	    		etiqueta_db.setNombre(resultSet.getString("nombre"));
	    		etiqueta_db.setDescripcion(resultSet.getString("descripcion"));
	    		etiqueta_db.setId(etiqueta.getId());
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return etiqueta_db;
	}
	
	@Override
	public List<Etiqueta> retrieveAll() {
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM etiqueta";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Etiqueta etiqueta = new Etiqueta();
	    		etiqueta.setId(resultSet.getInt("id"));
	    		etiqueta.setNombre(resultSet.getString("nombre"));
	    		etiqueta.setDescripcion(resultSet.getString("descripcion"));
	    		
	    		// aca hay que traer todas las notificaciones de la etiqueta actual
	    		
	    		etiquetas.add(etiqueta);
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return etiquetas;
	}

	@Override
	public boolean update(Etiqueta etiqueta) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "UPDATE etiqueta SET nombre = ? , descripcion = ? WHERE id = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	preparedStatement.setString(2, etiqueta.getDescripcion());	    	
	    	preparedStatement.setInt(3, etiqueta.getId());
	    	
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
	public boolean delete(Etiqueta etiqueta) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "DELETE FROM etiqueta WHERE nombre = ?";
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	
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
	public List<Etiqueta> retrieveFilteredBy(Object[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
