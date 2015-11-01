package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
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
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "INSERT INTO paciente (nombre, apellido) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, paciente.getNombre());
	    	preparedStatement.setString(2, paciente.getApellido());
	    	
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
	public Paciente retrieve(Paciente paciente) {
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "SELECT nombre, apellido FROM paciente WHERE nombre = ? and apellido = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	    	preparedStatement.setString(1, paciente.getNombre());
	    	preparedStatement.setString(1, paciente.getApellido());
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {	    		
	    		paciente.setNombre(resultSet.getString("nombre"));
	    		paciente.setApellido(resultSet.getString("apellido"));
	    	}
	    	
	    	ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
	    	if(generatedKeys.next()) {
	    		paciente.setId(generatedKeys.getInt(1));
            }
	    	
	    	connection.close();	    	
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return paciente;
	}
	
	@Override
	public List<Paciente> retrieveAll() {
		
		List<Paciente> pacientes = new ArrayList<Paciente>();
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "SELECT * FROM paciente";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Paciente paciente = new Paciente();
	    		paciente.setId(resultSet.getInt("id"));
	    		paciente.setNombre(resultSet.getString("nombre"));
	    		paciente.setApellido(resultSet.getString("apellido"));
	    		pacientes.add(paciente);
	    	}
	    	
	    	connection.close();	    	
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return pacientes;
	}

	@Override
	public boolean update(Paciente paciente) {
		
		boolean result = false;
		
		try {
	    	Class.forName("org.sqlite.JDBC");
	    	
	    	Connection connection = DriverManager.getConnection("jdbc:sqlite:DB/database.db");
	    	
	    	String query = "UPDATE paciente SET nombre = ? , apellido = ? WHERE id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, paciente.getNombre());
	    	preparedStatement.setString(2, paciente.getApellido());	    	
	    	preparedStatement.setInt(3, paciente.getId());
	    	
	    	if (preparedStatement.executeUpdate() != 0) {
	    		result = true;
	    	}
	    	
	    	connection.close();
	    	
	    } catch (ClassNotFoundException e) {
	    	System.out.println("Driver not found");
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return result;
	}

	@Override
	public boolean delete(Paciente paciente) {
		// borrar el paciente implica borrar todas las notificaciones asociadas al paciente 
		// y las entradas de la tabla que mapea las notificaciones con las etiquetas
		return false;
	}

}
