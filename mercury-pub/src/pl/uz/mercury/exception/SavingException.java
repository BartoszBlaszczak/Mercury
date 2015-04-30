package pl.uz.mercury.exception;

public class SavingException extends MercuryException
{
	private static final long	serialVersionUID	= 1L;

	public SavingException(String message)
	{
		super(message);
	}
	
	public SavingException(String message, Throwable cause)
	{
		super(message, cause);
	}
	
}
