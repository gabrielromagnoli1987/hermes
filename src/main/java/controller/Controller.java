package controller;

import java.util.List;

import model.Notificacion;
import model.dao.DAOFactory;
import model.dao.Storable;

public class Controller {
	
	public void asignarEtiqueta(String nombreEtiqueta, Notificacion notificacion) {
		
	}
	
	public Object[][] getTableData(String[] headers) {
		
		// headers = "Fecha/Hora envío", "Contenido", "Contexto", "Categoría", "Paciente", "Etiquetas"
		
		Storable<?> notificacionDAO = DAOFactory.getNotificacionDAO();
		List<Notificacion> notificaciones = (List<Notificacion>) notificacionDAO.retrieveAll();
		
		Object[][] data = new Object[notificaciones.size()][headers.length];
		
		for (int i = 0; i < notificaciones.size(); i++) {
			Notificacion notificacion = notificaciones.get(i);
			data[i][0] = notificacion.getFechaEnvio();
			data[i][1] = notificacion.getText();
			data[i][2] = notificacion.getContexto().getNombre();
			
			if (! notificacion.getContexto().getCategorias().isEmpty()) {
				data[i][3] = notificacion.getContexto().getCategorias().toArray();
			}
			
			data[i][4] = notificacion.getPaciente().getNombre();
			
			if (! notificacion.getEtiquetas().isEmpty()) {
				data[i][5] = notificacion.getEtiquetas().toString();
			}
		}
		
		return data;
		
	}

}
