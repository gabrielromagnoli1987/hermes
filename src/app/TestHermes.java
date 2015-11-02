package app;

import gui.Monitor;

import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.dao.DAOFactory;
import model.dao.SqliteHelper;
import model.dao.Storable;

public class TestHermes {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {		
		
		
		// prueba de crear una categoria y guardarla en la base
		
		Contexto contexto = new Contexto("Hogar", "La casa del paciente");
		Contexto contexto2 = new Contexto("Hogar 2", "La casa del paciente 2");
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		contextos.add(contexto);
		contextos.add(contexto2);
		
		Categoria categoria = new Categoria("el nombre", "la descripcion", contextos);
		Categoria categoria2 = new Categoria("CATEGORIA 2", "CATEGORIA descripcion 2", contextos);
		
		@SuppressWarnings("rawtypes")
		Storable categoriaDAO = DAOFactory.getCategoriaDAO();
		categoriaDAO.create(categoria);
		categoriaDAO.create(categoria2);
		
//		Categoria categoria2_db = (Categoria)categoriaDAO.retrieve(categoria2);
//		categoria2_db.setContextos(contextos);
//		categoriaDAO.update(categoria2_db);
		
		//Paciente paciente = new model.Paciente("paciente 1", "apellido 1");
		//Notificacion notificacion = new Notificacion("la notificacion", fechaEnvio, fechaRecepcion, paciente, contexto);
		
		//Categoria categoria = new Categoria();
		//categoria.setNombre("categoria 5");
//		Categoria db_categoria = null;
		
//		Storable categoriaDAO = DAOFactory.getCategoriaDAO();
//		categoriaDAO.create(categoria);
//		categoriaDAO.delete(categoria);
		
//		categoria = (Categoria)categoriaDAO.retrieve(categoria);
//		
//		categoria.setNombre("nuevo nombre");
//		categoriaDAO.update(categoria);
//		
//		db_categoria = (Categoria)categoriaDAO.retrieve(categoria);
//		System.out.println(db_categoria);
		
//		Monitor monitor = new Monitor();
//		monitor.setVisible(true);
		
//		List<Paciente> pacientes = new ArrayList<Paciente>();
//		Storable pacienteDAO = DAOFactory.getPacienteDAO();
//		pacientes = pacienteDAO.retrieveAll();
//		System.out.println(pacientes);
//		
//		Paciente paciente = pacientes.get(0);
//		paciente.setNombre("nombre nuevo");
//		pacienteDAO.update(paciente);
//		System.out.println(paciente);
//		
//		Paciente paciente_db;
//		paciente_db = (Paciente)pacienteDAO.retrieve(paciente);
//		System.out.println(paciente_db);
		
		SqliteHelper.closeConnection();

	}

}
