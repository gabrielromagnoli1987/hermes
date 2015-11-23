package net;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.net.httpserver.HttpServer;

import conf.ConfigReader;

public class Server {
	
	public void start() {
		ConfigReader configReader = new ConfigReader();
		Integer port = Integer.parseInt(configReader.getPort());
		
		try {
			HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/hermes/", new NotificacionHandler());
			
			ExecutorService executorService = Executors.newCachedThreadPool();
			
			server.setExecutor(executorService);
			server.start();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}

}
