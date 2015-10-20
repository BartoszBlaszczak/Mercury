package pl.uz.mercury.dto;

import pl.uz.mercury.dto.common.MercuryOptionDto;

public class MerchandiseDto implements MercuryOptionDto
{
	private static final long	serialVersionUID	= 1L;

	public static final String ID = "id";
	public static final String NAME = "name";
	
	public Long id;
	public String name;

	@Override
	public Long getId ()
	{
		return id;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
