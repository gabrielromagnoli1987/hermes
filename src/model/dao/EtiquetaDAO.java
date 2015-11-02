package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Etiqueta;


public class EtiquetaDAO implements Storable<Etiqueta> {

	@Override
	public boolean create(Etiqueta etiqueta) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO etiquetas (nombre, descripcion) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	preparedStatement.setString(2, etiqueta.getDescripcion());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}
	    	
	    	return result;
	    	
	    } catch(SQLException e) {	    	
	    	System.err.println(e.getMessage());
		}
	    
	    return result;
	}

	@Override
	public Etiqueta retrieve(Etiqueta etiqueta) {
		
		return null;
	}
	
	@Override
	public List<Etiqueta> retrieveAll() {
		
		List<Etiqueta> etiquetas = new ArrayList<Etiqueta>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM paciente";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
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
	    }
		
		return etiquetas;
	}

	@Override
	public boolean update(Etiqueta etiqueta) {
		
		return false;
	}

	@Override
	public boolean delete(Etiqueta etiqueta) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "DELETE FROM etiqueta WHERE nombre = ?";
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, etiqueta.getNombre());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {	    		
	    		result = true;
	    	}
	    		    	
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

}
