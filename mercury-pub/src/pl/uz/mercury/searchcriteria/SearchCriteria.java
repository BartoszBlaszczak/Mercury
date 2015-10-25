package pl.uz.mercury.searchcriteria;

import java.io.Serializable;

public class SearchCriteria
	implements Serializable
{
	private static final long		serialVersionUID	= 1L;

	public final String				fieldName;
	public final SearchPredicate	searchPredicate;
	public final String				value;
	public final String				subValue;

	public SearchCriteria(String dtoFieldName, SearchPredicate searchPredicate, String value)
	{
		this.fieldName = dtoFieldName;
		this.searchPredicate = searchPredicate;
		this.value = value;
		this.subValue = null;
	}

	public SearchCriteria(String dtoFieldName, String value, String subValue)
	{
		this.fieldName = dtoFieldName;
		this.searchPredicate = SearchPredicate.IS;
		this.value = value;
		this.subValue = subValue;
	}
	
	@Override
	public String toString ()
	{
		return subValue == null ? 
				String.format("%s %s %s", fieldName, searchPredicate.toString(), value): 
				String.format("%s %s %s", fieldName, searchPredicate.toString(), subValue);
	}
}
