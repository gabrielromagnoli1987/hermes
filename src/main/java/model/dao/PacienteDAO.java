package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Paciente;

public class PacienteDAO implements Storable<Paciente> {

	@Override
	public boolean create(Paciente paciente) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO paciente (nombre, apellido, dni) VALUES (?, ?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, paciente.getNombre());
	    	preparedStatement.setString(2, paciente.getApellido());
	    	preparedStatement.setInt(3, paciente.getDni());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}
	    	
	    	preparedStatement.close();
	    	
	    	return result;
	    		    
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeConnection();
	    }
	    
	    return result;
	}

	@Override
	public Paciente retrieve(Paciente paciente) {
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT id, nombre, apellido, dni FROM paciente WHERE dni = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);//, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setInt(1, paciente.getDni());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		paciente.setNombre(resultSet.getString("nombre"));
	    		paciente.setApellido(resultSet.getString("apellido"));
	    		paciente.setDni(resultSet.getInt("dni"));
	    		paciente.setId(resultSet.getInt("id"));
	    	}
	    	
//	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
//	    	if(generatedKeys.next()) {
//	    		paciente.setId(generatedKeys.getInt(1));
//            }
	    	
	    	resultSet.close();
	    	preparedStatement.close();
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeConnection();
	    }
		
		return paciente;
	}
	
	@Override
	public Paciente retrieveById(Paciente paciente) {
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT nombre, apellido, dni FROM paciente WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, paciente.getId());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		paciente.setNombre(resultSet.getString("nombre"));
	    		paciente.setApellido(resultSet.getString("apellido"));
	    		paciente.setDni(resultSet.getInt("dni"));
	    		paciente.setId(paciente.getId());
	    	}
	    	
	    	resultSet.close();
	    	preparedStatement.close();
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeConnection();
	    }
		
		return paciente;
		
	}
	
	@Override
	public List<Paciente> retrieveAll() {
		
		List<Paciente> pacientes = new ArrayList<Paciente>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM paciente";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Paciente paciente = new Paciente();
	    		paciente.setId(resultSet.getInt("id"));
	    		paciente.setNombre(resultSet.getString("nombre"));
	    		paciente.setApellido(resultSet.getString("apellido"));
	    		paciente.setDni(resultSet.getInt("dni"));
	    		pacientes.add(paciente);
	    	}
	    	
	    	resultSet.close();
	    	preparedStatement.close();
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeConnection();
	    }
		
		return pacientes;
	}

	@Override
	public boolean update(Paciente paciente) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "UPDATE paciente SET nombre = ? , apellido = ? , dni = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, paciente.getNombre());
	    	preparedStatement.setString(2, paciente.getApellido());
	    	preparedStatement.setInt(3, paciente.getDni());
	    	preparedStatement.setInt(4, paciente.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    		    	
	    	preparedStatement.close();
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeConnection();
	    }
		
		return result;
	}

	@Override
	public boolean delete(Paciente paciente) {
		// borrar el paciente implica borrar todas las notificaciones asociadas al paciente 
		// y las entradas de la tabla que mapea las notificaciones con las etiquetas
		return false;
	}

	@Override
	public List<Paciente> retrieveFilteredBy(Object[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
