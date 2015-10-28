package model.dao;

import java.util.List;

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
	public List<Notificacion> retrieveAll(Notificacion object) {
		
		return null;
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
