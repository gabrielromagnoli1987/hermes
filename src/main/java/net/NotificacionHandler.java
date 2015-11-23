package net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class NotificacionHandler implements HttpHandler {

	@Override
	public void handle(HttpExchange arg0) throws IOException {
		InputStream is = arg0.getRequestBody();

		//read(is); hay que parsear el body que tiene el json 
		String response = "Esta es la respuesta";
		arg0.sendResponseHeaders(200, response.length());
		OutputStream os = arg0.getResponseBody();
		os.write(response.getBytes());

	}

}
