package application;

import org.apache.log4j.Logger;

public class Application {
	public static final Logger logger=Logger.getLogger("stdout");
	
	public static void main(String[] args) {
		String message="this is a message";
		String url=Application.class.getClassLoader().getResource("").getPath();
		System.out.println(url);
		System.setProperty("LOG_DIR", url);
		logger.debug(message);
		logger.info(message);
		logger.warn(message);
		logger.error(message);
		logger.fatal(message);
	}

}
