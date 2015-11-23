package conf;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {
	
	public String getPort() {
		
		Properties prop = new Properties();
		InputStream input = null;

		try {

			input = new FileInputStream("src/main/java/conf/config.properties");

			prop.load(input);
			prop.getProperty("port");
			
			return prop.getProperty("port");

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
		
		return prop.getProperty("port");
	}

}
