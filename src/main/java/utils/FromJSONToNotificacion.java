package utils;

import java.io.BufferedReader;
import java.lang.reflect.Type;
import java.util.List;

import model.Notificacion;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class FromJSONToNotificacion {
	
	public Notificacion fromJSONToNotificacion(BufferedReader br) {
		
		Gson gson = new Gson();
		Notificacion notificacion = gson.fromJson(br, Notificacion.class);
		return notificacion;
		
	}
	
	public List<Notificacion> fromJSONToNotificaciones(BufferedReader br) {
		
		Gson gson = new Gson();		
		
		Type type = new TypeToken<List<Notificacion>>(){}.getType();		
	    List<Notificacion> notificaciones = gson.fromJson(br, type);

		return notificaciones;
		
	}

}
