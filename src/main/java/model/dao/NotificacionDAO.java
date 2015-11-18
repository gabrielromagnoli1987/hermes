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
	public Notificacion retrieveById(Notificacion object) {
		
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
	    		Contexto contexto_db = (Contexto)contextoDAO.retrieveById(contextoTemp);
	    		notificacion.setContexto(contexto_db);
	    		
	    		Integer pacienteID = resultSet.getInt("pacienteID");
	    		Paciente pacienteTemp = new Paciente();
	    		pacienteTemp.setId(pacienteID);
	    		Storable pacienteDAO = DAOFactory.getPacienteDAO();
	    		Paciente paciente_db = (Paciente)pacienteDAO.retrieveById(pacienteTemp);
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
	
	public List<Notificacion> retrieveFilteredBy(Object[] filters) {
		
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
	    	
//			filters[0] contenido
//			filters[1] contexto
//			filters[2] categoria
//			filters[3] paciente
//			filters[4] fechaEnvio
//			filters[5] fechaRecepcion
//			filters[6] etiqueta
			
	    	String query = "SELECT * "
	    				 + "FROM notificacion INNER JOIN etiqueta_notificacion ON notificacion.id = etiqueta_notificacion.notificacionID "
	    				 + "INNER JOIN etiqueta ON etiqueta.id = etiqueta_notificacion.etiquetaID "	    				 
	    				 + "WHERE text = ? AND contextoID = ? AND pacienteID = ? AND fechaEnvio >= ? AND fechaRecepcion <= ? AND etiqueta.id = ?";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, (String) filters[0]);
	    	
	    	if (filters[1] != null) {
	    		//query += " AND contextoID = ?";
	    		preparedStatement.setInt(2, ((Contexto) filters[1]).getId());
	    	}
	    	if (filters[3] != null) {
	    		//query += " AND pacienteID = ?";
	    		preparedStatement.setInt(3, ((Paciente) filters[3]).getId());
	    	}
	    	if (filters[4] != null) {
	    		//query += " AND fechaEnvio = ?";
	    		preparedStatement.setDate(4, toSQLDate((Date) filters[4]));
	    	}
	    	if (filters[5] != null) {
	    		//query += " AND fechaRecepcion = ?";
	    		preparedStatement.setDate(5, toSQLDate((Date) filters[5]));
	    	}
	    	if (filters[6] != null) {
	    		//query += " AND etiqueta.id = ?";
	    		preparedStatement.setInt(6, ((Etiqueta) filters[6]).getId());
	    	}
	    	
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
	    		Contexto contexto_db = (Contexto)contextoDAO.retrieveById(contextoTemp);
	    		notificacion.setContexto(contexto_db);
	    		
	    		Integer pacienteID = resultSet.getInt("pacienteID");
	    		Paciente pacienteTemp = new Paciente();
	    		pacienteTemp.setId(pacienteID);
	    		Storable pacienteDAO = DAOFactory.getPacienteDAO();
	    		Paciente paciente_db = (Paciente)pacienteDAO.retrieveById(pacienteTemp);
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
	
	private static java.sql.Date toSQLDate(Date date) {	    
	    return new java.sql.Date(date.getTime());
	}

}