package com.sauce.demo.automation.utilities;

import static com.sauce.demo.automation.utilities.AppConstants.CONFIG_FILENAME;
import static com.sauce.demo.automation.utilities.AppConstants.CONFIG_PATH;
import static com.sauce.demo.automation.utilities.AppConstants.PROPERTIES;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.apache.log4j.Logger;

public class ConfigHelper {

	private static ConfigHelper configHelper;
	private Properties prop = null;
	private static Logger logger = Logger.getLogger(ConfigHelper.class);

	private ConfigHelper() {
	}

	public static ConfigHelper getInstance() {
		if (configHelper == null) {
			synchronized (ConfigHelper.class) {
				if (configHelper == null) {
					configHelper = new ConfigHelper();
					configHelper.loadConfigProperties();
				}
			}
		}
		return configHelper;
	}

	private String getConfigFileName() {
		if (PropHolder.getEnvironment() == null) {
			return CONFIG_PATH + CONFIG_FILENAME + PROPERTIES;
		}
		return CONFIG_PATH + CONFIG_FILENAME + "_" + PropHolder.getEnvironment() + PROPERTIES;
	}

	private void loadConfigProperties() {
		String configPropertiesFilePath = getConfigFileName();

		if (prop == null) {
			prop = new Properties();
			try {
				InputStream is = getClass().getResourceAsStream(getConfigFileName());
				prop.load(is);
				is.close();
				logger.info("Loaded config properties file: " + configPropertiesFilePath);
			} catch (FileNotFoundException fnfe) {
				logger.error(fnfe.getMessage());
				fnfe.printStackTrace();
			} catch (IOException ioe) {
				logger.error(ioe.getMessage());
				ioe.printStackTrace();
			} catch (Exception e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * This will return the value set against the propName in config.properties file	 * 
	 * @param propName
	 * @return value
	 */
	public String getConfigProperty(String propName) {
		if (prop != null) {
			logger.info("Get config for property: " + propName);
			String propertyValue = prop.getProperty(propName);
			return propertyValue;
		}
		return null;
	}

}
