package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Contexto;
import model.Etiqueta;
import model.Notificacion;
import model.Paciente;

public class NotificacionDAO implements Storable<Notificacion> {

	@Override
	public boolean create(Notificacion notificacion) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO notificacion (text, fechaEnvio, fechaRecepcion, pacienteID, contextoID) VALUES (?, ?, ?, ?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, notificacion.getText());
	    	preparedStatement.setLong(2, notificacion.getFechaEnvio().getTime());
	    	preparedStatement.setLong(3, notificacion.getFechaRecepcion().getTime());
	    	preparedStatement.setInt(4, notificacion.getPaciente().getId());
	    	preparedStatement.setInt(5, notificacion.getContexto().getId());
	    	
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
	public Notificacion retrieve(Notificacion notificacion) {
		
		return null;
	}
	
	@Override
	public List<Notificacion> retrieveAll() {
		
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM notificacion";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Notificacion notificacion = new Notificacion();
	    		notificacion.setId(resultSet.getInt("id"));
	    		notificacion.setText(resultSet.getString("text"));
	    		
	    		Long integerFechaEnvio = resultSet.getLong("fechaEnvio");
	    		Date fechaEnvio = new Date(integerFechaEnvio);
	    		notificacion.setFechaEnvio(fechaEnvio);
	    		
	    		Long integerFechaRecepcion = resultSet.getLong("fechaRecepcion");
	    		Date fechaRecepcion = new Date(integerFechaRecepcion);
	    		notificacion.setFechaEnvio(fechaRecepcion);
	    		
	    		Integer contextoID = resultSet.getInt("contextoID");
	    		Contexto contextoTemp = new Contexto();
	    		contextoTemp.setId(contextoID);
	    		Storable contextoDAO = DAOFactory.getContextoDAO();
	    		Contexto contexto_db = (Contexto)contextoDAO.retrieve(contextoTemp);
	    		notificacion.setContexto(contexto_db);
	    		
	    		Integer pacienteID = resultSet.getInt("pacienteID");
	    		Paciente pacienteTemp = new Paciente();
	    		pacienteTemp.setId(pacienteID);
	    		Storable pacienteDAO = DAOFactory.getPacienteDAO();
	    		Paciente paciente_db = (Paciente)pacienteDAO.retrieve(pacienteTemp);
	    		notificacion.setPaciente(paciente_db);
	    			    		
	    		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
	    		List<Etiqueta> etiquetas = etiquetaDAO.retrieveAll();
	    		notificacion.setEtiquetas(etiquetas);
	    		
	    		notificaciones.add(notificacion);
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return notificaciones;
	}

	@Override
	public boolean update(Notificacion notificacion) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
	    	String query = "UPDATE notificacion SET text = ? , fechaEnvio = ? , fechaRecepcion = ? , pacienteID = ? , contextoID = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, notificacion.getText());
	    	preparedStatement.setLong(2, notificacion.getFechaEnvio().getTime());
	    	preparedStatement.setLong(3, notificacion.getFechaRecepcion().getTime());
	    	preparedStatement.setInt(4, notificacion.getPaciente().getId());
	    	preparedStatement.setInt(5, notificacion.getContexto().getId());
	    	
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
	public boolean delete(Notificacion notificacion) {
		
		return false;
	}

}
