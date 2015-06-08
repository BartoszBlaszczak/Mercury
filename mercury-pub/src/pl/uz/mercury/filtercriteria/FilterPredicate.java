package pl.uz.mercury.filtercriteria;

public enum FilterPredicate
{
	EQUAL("="),
	SMALLER("<"),
	SMALLER_OR_EQUAL("<="),
	GREATER(">"),
	GREATER_OR_EQUAL(">="),
	NOT("<>");
	
	FilterPredicate(String printValue)
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
