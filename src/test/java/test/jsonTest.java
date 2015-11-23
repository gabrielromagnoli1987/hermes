package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Categoria;
import model.Contexto;
import model.Notificacion;
import model.Paciente;

import com.google.gson.Gson;


public class jsonTest {

    public static void main(String[] args) {

    	
    	Paciente paciente = new Paciente("nombre 1", "apellido 1", 12345678);
    	Contexto contexto = new Contexto("el contexto", "la desc del contexto");
    	
    	Categoria categoria = new Categoria("cat 1", "desc cat 1");
    	Categoria categoria2 = new Categoria("cat 2", "desc cat 2");    	
    	List<Categoria> categorias = new ArrayList<Categoria>();
    	categorias.add(categoria);
    	categorias.add(categoria2);
		contexto.setCategorias(categorias);
    	
    	Date fechaEnvio = new Date();
    	Date fechaRecepcion = new Date();
		Notificacion notificacion = new Notificacion("not 1", fechaEnvio, fechaRecepcion, paciente, contexto);
				
		Gson gson = new Gson();
	
		// convert java object to JSON format,
		// and returned as JSON formatted string
		String json = gson.toJson(notificacion);
	
		try {
			//write converted json data to a file named "file.json"
			FileWriter writer = new FileWriter("DB/notificacion.json");
			writer.write(json);
			writer.close();
			System.out.println(json);
			
			notificacion = null;
			
			BufferedReader br = new BufferedReader(new FileReader("DB/notificacion.json"));
			
			//convert the json string back to object
			notificacion = gson.fromJson(br, Notificacion.class);
			
			System.out.println(notificacion);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

    }

}
