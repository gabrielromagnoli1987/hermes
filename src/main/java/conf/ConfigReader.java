package conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	public String getPort() {
		
		// puerto por default en el caso de que no se pueda leer el archivo de properties
		String port = "8888";
		InputStream input = null;
		
		try {
			
			Properties prop = new Properties();
			
			input = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
			prop.load(input);
			port = prop.getProperty("port");
			
			return port;

		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return port;
	}

}
