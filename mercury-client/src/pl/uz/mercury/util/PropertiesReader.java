package pl.uz.mercury.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader
{
	private final Properties	properties	= new Properties();
	private final InputStream	input;
	private String				prefix		= "";

	public PropertiesReader(String propertiesFileName) throws IOException
	{
		input = getClass().getClassLoader().getResourceAsStream(propertiesFileName);

		if (input == null) { throw new RuntimeException("Unable to find " + propertiesFileName); }

		properties.load(input);

	}
	
	public PropertiesReader(String propertiesFileName, String prefix) throws IOException
	{
		this(propertiesFileName);
		this.prefix = prefix + ".";
	}

	public String getProperty (String key)
	{
		return properties.getProperty(prefix + key, prefix + key);
	}
}
