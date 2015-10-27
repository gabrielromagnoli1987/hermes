package app;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.Notificacion;
import model.Paciente;
import model.dao.DAOFactory;
import model.dao.Storable;

public class TestHermes {

	public static void main(String[] args) {
		
		
		//Date fechaEnvio = new Date();
		
		
		//Paciente paciente = new model.Paciente("paciente 1", "apellido 1");
		
		Contexto contexto = new Contexto("Hogar", "La casa del paciente");
		
		List<Contexto> contextos = new ArrayList<Contexto>();
		contextos.add(contexto);
		
		//Notificacion notificacion = new Notificacion("la notificacion", fechaEnvio, fechaRecepcion, paciente, contexto);
		
		
		// prueba de crear una categoria y guardarla en la base
		
		Categoria categoria = new Categoria("el nombre", "la descripcion", contextos);
		
		Storable categoriaDAO = DAOFactory.getCategoriaDAO();
		categoriaDAO.create(categoria);
		

	}

}
