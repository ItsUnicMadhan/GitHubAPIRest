package org.base;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class BaseClass {
	
	public static String getPropertyFileValue(String value) throws IOException {
		FileInputStream file = new FileInputStream("Properties\\data.properties");
		Properties p = new Properties();
		p.load(file);
		return p.getProperty(value);

	}

}
