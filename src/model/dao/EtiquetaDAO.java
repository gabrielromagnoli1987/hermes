package model.dao;

import java.util.List;

import model.Etiqueta;

public class EtiquetaDAO implements Storable<Etiqueta> {

	@Override
	public boolean create(Etiqueta etiqueta) {
		
		return false;
	}

	@Override
	public Etiqueta retrieve(Etiqueta etiqueta) {
		
		return null;
	}
	
	@Override
	public List<Etiqueta> retrieveAll() {
		
		return null;
	}

	@Override
	public boolean update(Etiqueta etiqueta) {
		
		return false;
	}

	@Override
	public boolean delete(Etiqueta etiqueta) {
		
		return false;
	}

}
