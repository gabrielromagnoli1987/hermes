package model.dao;

import java.util.List;

import model.Contexto;

public class ContextoDAO implements Storable<Contexto> {

	@Override
	public boolean create(Contexto contexto) {
		
		return false;
	}

	@Override
	public Contexto retrieve(Contexto contexto) {
		
		return null;
	}
	
	@Override
	public List<Contexto> retrieveAll(Contexto object) {
		
		return null;
	}

	@Override
	public boolean update(Contexto contexto) {
		
		return false;
	}

	@Override
	public boolean delete(Contexto contexto) {
		
		return false;
	}

}