package test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.Notificacion;
import model.Paciente;
import model.dao.DAOFactory;
import model.dao.SqliteHelper;
import model.dao.Storable;

public class CreateNotificacionTest {

	public static void main(String[] args) {
		
		// si este test se corre dos veces seguidas rompe porque al haber una constraint unique sobre el nombre
		// de las categorias y contextos, en este caso la categoria no se crea y por ende el contexto tampoco
		// y llega en null en la creacion de la notificacion
		// se puede agregar una validacion mas adelante
		
		// si fuera realmente un test habria que usar junit y hacer algunos assertions
		
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
		
		Paciente paciente = new model.Paciente("paciente 1", "apellido 1");
		Storable pacienteDAO = DAOFactory.getPacienteDAO();
		pacienteDAO.create(paciente);
		Paciente paciente_db = (Paciente)pacienteDAO.retrieve(paciente);
		
		Date fechaRecepcion = new Date();
		Date fechaEnvio = new Date();
		Notificacion notificacion = new Notificacion("la notificacion", fechaEnvio, fechaRecepcion, paciente_db, contexto);
		
		Storable notificacionDAO = DAOFactory.getNotificacionDAO();
		notificacionDAO.create(notificacion);

		SqliteHelper.closeConnection();

	}

}
