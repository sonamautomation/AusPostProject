package com.htcindia.trracs.config.properties;

import java.util.HashMap;
import java.util.Map;

import com.htcindia.trracs.helpers.MyException;
import static com.htcindia.trracs.constants.Constants.*;

public class ConfigHolder {

	/* <---------- Map To Hold All Properties ---------> */
	public final Map<String, String> properties = new HashMap<>();

	/* <---------- Single Ton Instance ---------> */
	private static ConfigHolder singleInstance = null;

	private ConfigHolder() throws MyException {
		this.getAll();
	}

	/* <---------- Create/Return Single Ton Instance ---------> */
	public static ConfigHolder getInstance() throws MyException {
		if (singleInstance == null) {
			singleInstance = new ConfigHolder();
		}
		return singleInstance;
	}

	/* <---------- Put All The Properties Inside A Map ---------> */
	private void getAll() throws MyException {
		ConfigLoader loader = new ConfigLoader(CONFIG_FILE_PATH, CONFIG_FILE_NAME);
		properties.putAll(loader.getAll());
	}

}