package pl.uz.mercury.filtercriteria;

public enum SearchPredicate
{
	IS("="),
	EQUAL("="),
	LESSER_OR_EQUAL("<="),
	GREATER_OR_EQUAL(">="),
	LIKE("~"),
	AFTER_OR_EQUAL(">="),
	BEFORE_OR_EQUAL("<=");
	
	SearchPredicate(String printValue)
	{
		this.printValue = printValue;
	}

	public final String	printValue;
	
	@Override
	public String toString ()
	{
		return printValue;
	}
}
