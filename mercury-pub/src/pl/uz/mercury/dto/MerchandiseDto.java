package pl.uz.mercury.dto;

import pl.uz.mercury.annotation.TransientMercuryDtoField;
import pl.uz.mercury.dto.common.MercuryOptionDto;

public class MerchandiseDto implements MercuryOptionDto
{
	private static final long	serialVersionUID	= 1L;
	
	public static final String MERCHANDISE_ID = "Idmerchandise";
	public static final String NAME = "name";
	
	public Long Idmerchandise;
	public String name; 
	
	@TransientMercuryDtoField
	public String transientField;

	@Override
	public Long getId ()
	{
		return Idmerchandise;
	}
	
	@Override
	public String toString()
	{
		return new StringBuilder().append(getId()).append(" ").append(name).toString();
	}
}
