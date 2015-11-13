package test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.Notificacion;
import model.Paciente;
import model.dao.DAOFactory;
import model.dao.SqliteHelper;
import model.dao.Storable;

public class CreateNotificaciones {

	public static void main(String[] args) {
		
		Storable notificacionDAO = DAOFactory.getNotificacionDAO();
		Storable contextoDAO = DAOFactory.getContextoDAO();
		Storable categoriaDAO = DAOFactory.getCategoriaDAO();
		Storable pacienteDAO = DAOFactory.getPacienteDAO();
		
		
		for (int i = 0; i < 10; i++) {
			
			List<Categoria> categorias = new ArrayList<Categoria>();
			
			for (int j = 0; j < 10; j++) {
				Categoria categoria = new Categoria("categoria " + i + "" + j, "desc " + i + "" + j);
				categorias.add(categoria);
			}
			
			Contexto contexto = new Contexto("contexto " + i, "desc " + i, categorias);
			contextoDAO.create(contexto);
			Contexto contexto_db = (Contexto)contextoDAO.retrieve(contexto);
			
			Paciente paciente = new Paciente("nombre " + i, "apellido " + i, i);
			pacienteDAO.create(paciente);
			Paciente paciente_db = (Paciente)pacienteDAO.retrieve(paciente);
			
			Calendar fechaEnvio = new GregorianCalendar(2015, 11, i);
			Calendar fechaRecepcion = new GregorianCalendar(2015, 11, i);
			
			Notificacion notificacion = new Notificacion("contenido " + i, fechaEnvio.getTime(), fechaRecepcion.getTime(), paciente_db, contexto_db);
			notificacionDAO.create(notificacion);
			
		}
		
		SqliteHelper.closeConnection();

	}

}
