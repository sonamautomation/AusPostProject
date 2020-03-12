package com.htcindia.trracs.config.properties;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;

import com.htcindia.trracs.helpers.MyException;

public class ConfigLoader {

	/* <---------- Properties Instance ---------> */
	private final Properties props;

	/* <---------- Constructor Loads Configuration Properties ---------> */
	/* Property File Path */
	/* Property File Name */
	public ConfigLoader(String propertFilePath, String propertyFileName) throws MyException {
		this.props = new Properties();
		String fileFormat = FilenameUtils.getExtension(propertyFileName);
		if (!propertFilePath.isEmpty()) {
			if (!propertyFileName.isEmpty()) {
				if (fileFormat.equals("properties")) {
					try {
						this.props.load(new FileInputStream(propertFilePath + propertyFileName));
					} catch (IOException e) {
						throw new MyException(
								"Failed to load user properties from :" + propertyFileName + "\n" + e.getMessage());
					}
				} else {
					throw new MyException("Property file format is incorrect");
				}
			} else {
				throw new MyException("Property file name is empty");
			}
		} else {
			throw new MyException("Property file path is empty");
		}
	}

	/* <---------- Get Specific Value For Given Key ---------> */
	/* Property Key */
	public String get(String key) throws MyException {
		String value = "";
		if (!key.isEmpty()) {
			value = this.props.getProperty(key);
			if (value == null) {
				throw new MyException("Property value returned is null, please check the key");
			}
		} else {
			throw new MyException("Property Key is empty");
		}
		return value;
	}

	/* <---------- Get All Values From Property File ---------> */
	public Map<String, String> getAll() throws MyException {
		HashMap<String, String> configData = new HashMap<>();
		for (Object k : getAllKeys()) {
			String key = (String) k;
			configData.put(key, get(key));
		}
		return configData;
	}

	/* <---------- Get All Keys From Property File ---------> */
	private Set<Object> getAllKeys() throws MyException {
		if (this.props.keySet() == null) {
			throw new MyException("Property Key set is null");
		}
		return this.props.keySet();
	}

}