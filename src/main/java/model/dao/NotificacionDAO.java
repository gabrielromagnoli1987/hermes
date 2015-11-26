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
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "INSERT INTO notificacion (text, fechaEnvio, fechaRecepcion, pacienteID, contextoID) VALUES (?, ?, ?, ?, ?)";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, notificacion.getText());
	    	preparedStatement.setLong(2, notificacion.getFechaEnvio().getTime());
	    	preparedStatement.setLong(3, notificacion.getFechaRecepcion().getTime());
	    	preparedStatement.setInt(4, notificacion.getPaciente().getId());
	    	preparedStatement.setInt(5, notificacion.getContexto().getId());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}

	    	return result;
	    
		// TODO 
		// ESTA ES LA FORMA CORRECTA DE CERRAR LOS RECURSOS, 
	    // HAY QUE REPETIR ESTE COMPORTAMIENTO POR TODOS LOS DEMAS DAOS	
	    	
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	try {
				preparedStatement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    	sqliteHelper.closeConnection();
	    }
	    
	    return result;
	}

	@Override
	public Notificacion retrieve(Notificacion notificacion) {
		
		Notificacion notificacion_db = new Notificacion("", null, null, null, null);
		
		SqliteHelper sqliteHelper = new SqliteHelper();		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM notificacion WHERE text = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, notificacion.getText());
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	if (resultSet.next()) {
	    		notificacion_db.setId(resultSet.getInt("id"));
	    		notificacion_db.setText(resultSet.getString("text"));
	    		
	    		Long integerFechaEnvio = resultSet.getLong("fechaEnvio");
	    		Date fechaEnvio = new Date(integerFechaEnvio);
	    		notificacion_db.setFechaEnvio(fechaEnvio);
	    		
	    		Long integerFechaRecepcion = resultSet.getLong("fechaRecepcion");
	    		Date fechaRecepcion = new Date(integerFechaRecepcion);
	    		notificacion_db.setFechaEnvio(fechaRecepcion);
	    		
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
	    		
	    	}	
	    	
		} catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {	    	
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return notificacion_db;
	}
	
	@Override
	public Notificacion retrieveById(Notificacion object) {
		
		return null;
	}
	
	@Override
	public List<Notificacion> retrieveAll() {
		
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "SELECT * FROM notificacion";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	
	    	resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Notificacion notificacion = new Notificacion();
	    		notificacion.setId(resultSet.getInt("id"));
	    		notificacion.setText(resultSet.getString("text"));
	    		
	    		Long integerFechaEnvio = resultSet.getLong("fechaEnvio");
	    		Date fechaEnvio = new Date(integerFechaEnvio);
	    		notificacion.setFechaEnvio(fechaEnvio);
	    		
	    		Long integerFechaRecepcion = resultSet.getLong("fechaRecepcion");
	    		Date fechaRecepcion = new Date(integerFechaRecepcion);
	    		notificacion.setFechaRecepcion(fechaRecepcion);
	    		
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
	    		
	    		// CAMBIAR: ACTUALMENTE ESTA TRAYENDO TODAS LAS ETIQUETAS, TIENE QUE TRAER LAS ASOCIADAS
	    		
	    		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
	    		List<Etiqueta> etiquetas = etiquetaDAO.retrieveAll();
	    		notificacion.setEtiquetas(etiquetas);
	    		
	    		notificaciones.add(notificacion);
	    	}
	    	
		} catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return notificaciones;
	}

	@Override
	public boolean update(Notificacion notificacion) {
		
		boolean result = false;
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
	    	String query = "UPDATE notificacion SET text = ? , fechaEnvio = ? , fechaRecepcion = ? , pacienteID = ? , contextoID = ?";
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setString(1, notificacion.getText());
	    	preparedStatement.setLong(2, notificacion.getFechaEnvio().getTime());
	    	preparedStatement.setLong(3, notificacion.getFechaRecepcion().getTime());
	    	preparedStatement.setInt(4, notificacion.getPaciente().getId());
	    	preparedStatement.setInt(5, notificacion.getContexto().getId());
	    	
	    	if (preparedStatement.executeUpdate() == 1) {
	    		result = true;
	    	}
	    		    	
	    	preparedStatement.close();
	    	
	    	return result;
	    		    
	    } catch(SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(null, preparedStatement);
	    }
	    
	    return result;
	}

	@Override
	public boolean delete(Notificacion notificacion) {
		
		return false;
	}
	
	public List<Notificacion> retrieveFilteredBy(Object[] filters) {
		
		List<Notificacion> notificaciones = new ArrayList<Notificacion>();
		
		SqliteHelper sqliteHelper = new SqliteHelper();
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			
			Connection connection = sqliteHelper.getConnection();
	    	
//			filters[0] contenido
//			filters[1] contexto
//			filters[2] categoria
//			filters[3] paciente
//			filters[4] fechaEnvio++posicionParametro
//			filters[5] fechaRecepcion
//			filters[6] etiqueta
				    	
	    	String query = "SELECT * "
   				 + "FROM notificacion, etiqueta_notificacion, etiqueta "
   				 + "WHERE etiqueta_notificacion.notificacionID = notificacion.id "
   				 + "AND etiqueta_notificacion.etiquetaID = etiqueta.id "
   				 + "AND text = ?";	    	

	    	if (filters[1] != null) {
	    		query += " AND contextoID = ?";	    		
	    	}
	    	
	    	if (filters[3] != null) {
	    		query += " AND pacienteID = ?";
	    	}
	    	
	    	if (filters[4] != null) {
	    		query += " AND fechaEnvio >= ?";
	    	}
	    	
	    	if (filters[5] != null) {
	    		query += " AND fechaRecepcion <= ?";	    		
	    	}
	    	
	    	if (filters[6] != null) {
	    		query += " AND etiquetaID = ?";
	    	}
	    	
	    	preparedStatement = connection.prepareStatement(query);
	    	
	    	int posicionParametro = 1;
	    	
	    	preparedStatement.setString(posicionParametro, ((Notificacion) filters[0]).getText());
	    	
	    	if (filters[1] != null) {
	    		preparedStatement.setInt(++posicionParametro, ((Contexto) filters[1]).getId());	    		
	    	}
	    	
	    	if (filters[3] != null) {
	    		preparedStatement.setInt(++posicionParametro, ((Paciente) filters[3]).getId());
	    	}
	    	
	    	if (filters[4] != null) {
	    		preparedStatement.setDate(++posicionParametro, toSQLDate((Date) filters[4]));
	    	}
	    	
	    	if (filters[5] != null) {
	    		preparedStatement.setDate(++posicionParametro, toSQLDate((Date) filters[5]));	    		
	    	}
	    	
	    	if (filters[6] != null) {
	    		preparedStatement.setInt(++posicionParametro, ((Etiqueta) filters[6]).getId());
	    	}

	    	resultSet = preparedStatement.executeQuery();
	    		    	
	    	while (resultSet.next()) {
	    		Notificacion notificacion = new Notificacion();
	    		notificacion.setId(resultSet.getInt(1));
	    		notificacion.setText(resultSet.getString("text"));
	    		
	    		Long longFechaEnvio = resultSet.getLong("fechaEnvio");
	    		Date fechaEnvio = new Date(longFechaEnvio);
	    		notificacion.setFechaEnvio(fechaEnvio);
	    		
	    		Long longFechaRecepcion = resultSet.getLong("fechaRecepcion");
	    		Date fechaRecepcion = new Date(longFechaRecepcion);
	    		notificacion.setFechaRecepcion(fechaRecepcion);
	    		
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
	    		
	    		// CAMBIAR: ACTUALMENTE ESTA TRAYENDO TODAS LAS ETIQUETAS, TIENE QUE TRAER LAS ASOCIADAS
	    		
	    		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
	    		List<Etiqueta> etiquetas = etiquetaDAO.retrieveAll();
	    		notificacion.setEtiquetas(etiquetas);
	    		
	    		notificaciones.add(notificacion);
	    	}
	    	
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    } finally {
	    	sqliteHelper.closeAll(resultSet, preparedStatement);
	    }
		
		return notificaciones;
		
	}
	
	private static java.sql.Date toSQLDate(Date date) {	    
	    return new java.sql.Date(date.getTime());
	}

}
