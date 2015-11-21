package model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import model.Etiqueta;
import model.Notificacion;

public class EtiquetaNotificacionDAO implements Storable<Object> {
	
	@Override
	public boolean create(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object retrieve(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object retrieveById(Object object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> retrieveAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean update(Object object) {
		
		boolean result = false;
		
		try {
			
			Connection connection = SqliteHelper.getConnection();
			
			Etiqueta etiqueta = (Etiqueta)((Object[])object)[0];
			Notificacion notificacion = (Notificacion)((Object[])object)[1];
			
			Storable notificacionDAO = DAOFactory.getNotificacionDAO();
			Notificacion notificacion_db = (Notificacion) notificacionDAO.retrieve(notificacion);
			
	    	String query = "INSERT INTO etiqueta_notificacion (etiquetaID, notificacionID) VALUES (?, ?)";
	    	
	    	PreparedStatement preparedStatement = connection.prepareStatement(query);
	    	preparedStatement.setInt(1, etiqueta.getId());
	    	preparedStatement.setInt(2, notificacion_db.getId());
	    	
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
	public boolean delete(Object object) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Object> retrieveFilteredBy(Object[] filters) {
		// TODO Auto-generated method stub
		return null;
	}

}
