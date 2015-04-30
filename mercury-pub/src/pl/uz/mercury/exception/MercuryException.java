package pl.uz.mercury.exception;

public class MercuryException extends Exception
{
	private static final long	serialVersionUID	= 1L;

	public MercuryException()
	{
		super();
	}

	public MercuryException(String message)
	{
		super(message);
	}
	
	public MercuryException(String message, Throwable cause)
	{
		super(message, cause);
	}
}
