package pl.uz.mercury;

public interface Properties
{
	String LOCALE_FILENAME = "locale.properties";
	String MESSAGES_FILENAME = "messages.properties";
	
	public interface Option
	{
		String MERCHANDISE = "merchandise";
		String SALE = "sale";
		String PURCHASE = "purchase";
		
		public interface Element
		{
			String PLURAL_NAME = "pluralname";
			String ICON = "icon";
		}
	}
	
	public interface IconPath
	{
		String ADD = "iconAdd";
		String UPDATE = "iconUpdate";
		String DELETE = "iconDelete";
		String REFRESH = "iconRefresh";
	}
	
	public interface Locale
	{
		String ADD = "add";
		String UPDATE = "update";
		String DELETE = "delete";
		String REFRESH = "refresh";
		String OPTIONS = "options";
		String SEARCH_CRITERIA = "searchCriteria";
		
		String NAME = "name";
		String DATE = "date";
		String PRICE = "price";
		String QUANTITY = "quantity";
		String COST = "cost";
		String MERCHANDISE = "merchandise";
	}
	
	public interface Message
	{
		String COULD_NOT_READ_LOCALE_FILE = "COULD_NOT_READ_LOCALE_FILE";
		String SERVER_PROBLEM = "SERVER_PROBLEM";
		String WRONG_JNDI_NAME = "WRONG_JNDI_NAME";
		String COULD_NOT_DELETE = "COULD_NOT_DELETE";
		String COULD_NOT_SAVE = "COULD_NOT_SAVE";
		String BAD_CRITERIA = "BAD_CRITERIA";
		String VALIDATION_ERROR = "VALIDATION_ERROR";
		String COULD_NOT_RETRIEVE = "COULD_NOT_RETRIEVE";
	}
}
