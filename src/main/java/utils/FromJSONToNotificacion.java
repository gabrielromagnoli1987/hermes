package utils;

import java.io.BufferedReader;

import model.Notificacion;

import com.google.gson.Gson;

public class FromJSONToNotificacion {
	
	public static Notificacion fromJSONToNotificacion(BufferedReader br) {
		
		Gson gson = new Gson();		
		Notificacion notificacion = gson.fromJson(br, Notificacion.class);
		return notificacion;
		
	}

}
