package pl.uz.mercury.exception;

public class ValidationException
	extends MercuryException
{
	private static final long	serialVersionUID	= 1L;
	
	public ValidationException (String validationInfo)
	{
		super(validationInfo);
	}
	
	public static class Message
	{
		public static final String UNKNOWN_ERROR = "unknown error";
	}
}
