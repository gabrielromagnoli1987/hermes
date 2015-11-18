package controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.Categoria;
import model.Contexto;
import model.Etiqueta;
import model.Notificacion;
import model.dao.CategoriaContextoDAO;
import model.dao.DAOFactory;
import model.dao.Storable;

public class Controller {
	
	/*
	 * Se utiliza la clase controller como intermediario de la GUI y los DAOs
	 * 
	 * */
	
	public void asignarEtiqueta(Etiqueta etiqueta, Notificacion notificacion) {
		Storable etiquetaNotificacionDAO = DAOFactory.getEtiquetaNotificacionDAO();
		
		Object[] object = new Object[2];
		object[0] = etiqueta;
		object[1] = notificacion;
		etiquetaNotificacionDAO.update(object);
		
	}
	
	public Object[][] getTableData(String[] headers, Object[] filters) {
		
		// headers = "Fecha/Hora envío", "Contenido", "Contexto", "Categoría", "Paciente", "Etiquetas"
		
		Storable<?> notificacionDAO = DAOFactory.getNotificacionDAO();
		
		List<Notificacion> notificaciones;
		
		if (filters == null) {
			notificaciones = (List<Notificacion>) notificacionDAO.retrieveAll();
		}
		else {
			notificaciones = (List<Notificacion>) notificacionDAO.retrieveFilteredBy(filters);
		}
		
		Object[][] data = new Object[notificaciones.size()][headers.length];
		
		for (int i = 0; i < notificaciones.size(); i++) {
			Notificacion notificacion = notificaciones.get(i);
			data[i][0] = notificacion.getFechaEnvio();
			data[i][1] = notificacion.getText();
			data[i][2] = notificacion.getContexto().getNombre();
			
			if (! notificacion.getContexto().getCategorias().isEmpty()) {
				data[i][3] = notificacion.getContexto().getCategorias().toString();
			}
			
			data[i][4] = notificacion.getPaciente().getNombre();
			
			if (! notificacion.getEtiquetas().isEmpty()) {
				data[i][5] = notificacion.getEtiquetas().toString();
			}
		}
		
		return data;
		
	}
	
	public List<Etiqueta> getAllEtiquetas() {
		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
		return etiquetaDAO.retrieveAll();
	}

	public Object[] getContenidosDeNotificaciones() {
		Storable notificacionDAO = DAOFactory.getNotificacionDAO();
		List<Notificacion> notificaciones = notificacionDAO.retrieveAll();
		
//		int size = notificaciones.size();
//		Object[] contenidos = new Object[size];
//		
//		for (int i = 0; i < size; i++) {
//			contenidos[i] = notificaciones.get(i).getText();
//		}
		
//		return contenidos;
		
		return notificaciones.toArray();
		
	}

	public Object[] getContextos() {
		Storable contextoDAO = DAOFactory.getContextoDAO();
		return contextoDAO.retrieveAll().toArray();
	}

	public Object[] getPacientes() {
		Storable pacienteDAO = DAOFactory.getPacienteDAO();
		return pacienteDAO.retrieveAll().toArray();
	}

	public List<Categoria> getCategoriasDelContexto(Object selectedItem) {
		List<Categoria> categorias = new ArrayList<Categoria>();
		if (selectedItem != null) {
			CategoriaContextoDAO categoriaContextoDAO = new CategoriaContextoDAO();
			Contexto contexto = (Contexto)selectedItem;		
			categorias = categoriaContextoDAO.retrieveAllCategoriesOfContext(contexto);			
		}
		return categorias;
	}

	public void crearEtiqueta(String text) {
		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
		Etiqueta etiqueta = new Etiqueta();
		etiqueta.setNombre(text);
		etiquetaDAO.create(etiqueta);
		
	}

	public void updateEtiqueta(Object etiquetaActual, String text) {
		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();
		((Etiqueta)etiquetaActual).setNombre(text);
		etiquetaDAO.update((Etiqueta)etiquetaActual);
	}

	public void deleteEtiqueta(Object etiquetaActual) {
		Storable etiquetaDAO = DAOFactory.getEtiquetaDAO();		
		etiquetaDAO.delete((Etiqueta)etiquetaActual);
		
	}

}
