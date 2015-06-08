package pl.uz.mercury;

public class Properties
{
	public static final String PROPERTIES_FILENAME = "MercuryClientProperties.properties";
	public static final String LOCALE_FILENAME = "locale.properties";
	public static final String OPTIONS_FILENAME = "options.properties";
	public static final String MESSAGES_FILENAME = "messages.properties";
	
	public static class Option
	{
		public static final String merchandise = "merchandise";
		
		public static class Element
		{
			public static final String singularname = "singularname";
			public static final String pluralname = "pluralname";
			public static final String icon = "icon";
		}
	}
	
	public static class Locale
	{
		public static final String add = "add";
		public static final String show = "show";
		public static final String update = "update";
		public static final String delete = "delete";
		public static final String refresh = "refresh";
		public static final String filtr = "filtr";
		public static final String find = "find";
		
	}
	
	public static class Message
	{
		public static final String COULD_NOT_READ_LOCALE_FILE = "Couldn't read " + Properties.LOCALE_FILENAME;
	}
}
