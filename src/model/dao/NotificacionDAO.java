package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Etiqueta;
import model.Notificacion;

public class NotificacionDAO implements Storable<Notificacion> {

	@Override
	public boolean create(Notificacion notificacion) {
		
		return false;
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
	    	
	    	String query = "SELECT * FROM notificaciones";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	
	    	ResultSet resultSet = preparedStatement.executeQuery();
	    	
	    	while (resultSet.next()) {
	    		Notificacion notificacion = new Notificacion();
	    		notificacion.setId(resultSet.getInt("id"));
	    		notificacion.setText(resultSet.getString("text"));
	    		
	    		Integer integerFechaEnvio = resultSet.getInt("fechaEnvio");
	    		Date fechaEnvio = new Date(integerFechaEnvio);	    		
	    		notificacion.setFechaEnvio(fechaEnvio);
	    		
	    		Integer integerFechaRecepcion = resultSet.getInt("fechaRecepcion");
	    		Date fechaRecepcion = new Date(integerFechaRecepcion);	    		
	    		notificacion.setFechaEnvio(fechaRecepcion);
	    		
//	    		String getContextoQuery = "SELECT * FROM contexto WHERE ";
//	    		preparedStatement.executeQuery(getContextoQuery);
//	    		
//	    		Contexto contexto = 
//	    		notificacion.setContexto(contexto);
	    		
	    		// aca hay que traer todas las etiquetas de la notificacion actual
	    		
	    		notificaciones.add(notificacion);
	    	}
	    		    
	    } catch (SQLException e) {
	    	System.err.println(e.getMessage());
	    }
		
		return notificaciones;
	}

	@Override
	public boolean update(Notificacion notificacion) {
		
		return false;
	}

	@Override
	public boolean delete(Notificacion notificacion) {
		
		return false;
	}

}
