package app;

import gui.Monitor;
import net.Server;


public class Hermes {

	
	public static void main(String[] args) {
		
		Monitor monitor = new Monitor();
		monitor.setVisible(true);		
		
		Server server = new Server();
		server.start();
		
	}

}
