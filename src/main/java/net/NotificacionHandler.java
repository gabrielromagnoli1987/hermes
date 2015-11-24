package net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import model.Notificacion;
import model.dao.DAOFactory;
import model.dao.Storable;
import utils.FromJSONToNotificacion;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class NotificacionHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		InputStream is = arg0.getRequestBody();
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		
		Notificacion notificacion = FromJSONToNotificacion.fromJSONToNotificacion(br);
		
		Storable notificacionDAO = DAOFactory.getNotificacionDAO();
		notificacionDAO.create(notificacion);
		
		String response = "Se agrego la notificacion";
		arg0.sendResponseHeaders(200, response.length());
		OutputStream os = arg0.getResponseBody();
		os.write(response.getBytes());

	}

}
