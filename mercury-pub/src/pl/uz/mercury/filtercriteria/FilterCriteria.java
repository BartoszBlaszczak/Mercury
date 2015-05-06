package pl.uz.mercury.filtercriteria;

import java.io.Serializable;

public class FilterCriteria
	implements Serializable
{
	private static final long		serialVersionUID	= 1L;

	public final String				dtoFieldName;
	public final FilterPredicate	filterPredicate;
	public final String				value;

	public FilterCriteria(String dtoFieldName, FilterPredicate filterPredicate, String value)
	{
		this.dtoFieldName = dtoFieldName;
		this.filterPredicate = filterPredicate;
		this.value = value;
	}
}
