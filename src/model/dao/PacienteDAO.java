package model.dao;

import java.util.List;

import model.Paciente;

public class PacienteDAO implements Storable<Paciente> {

	@Override
	public boolean create(Paciente paciente) {
		
		return false;
	}

	@Override
	public Paciente retrieve(Paciente paciente) {
		
		return null;
	}
	
	@Override
	public List<Paciente> retrieveAll(Paciente object) {
		
		return null;
	}

	@Override
	public boolean update(Paciente paciente) {
		
		return false;
	}

	@Override
	public boolean delete(Paciente paciente) {
		
		return false;
	}

}
