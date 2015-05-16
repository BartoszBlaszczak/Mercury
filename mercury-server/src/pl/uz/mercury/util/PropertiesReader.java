package pl.uz.mercury.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.ejb.Stateless;

@Stateless
public class PropertiesReader
{
	private static final String	PROPERTIES_FILENAME	= "MercuryServer.properties";
	private Properties			properties			= new Properties();
	private InputStream			input;

	public PropertiesReader() throws IOException
	{
		input = getClass().getClassLoader().getResourceAsStream(PROPERTIES_FILENAME);

		if (input == null)
		{
			throw new RuntimeException("Unable to find " + PROPERTIES_FILENAME);
		}

		properties.load(input);
	}

	public String getProperty (String key)
	{
		return properties.getProperty(key);
	}

	public static class PropertyName
	{
		public static final String	PERSISTER_NAME	= "persister_name";
	}
}
