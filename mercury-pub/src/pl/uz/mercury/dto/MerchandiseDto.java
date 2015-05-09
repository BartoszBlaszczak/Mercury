package pl.uz.mercury.dto;

import pl.uz.mercury.annotation.TransientMercuryDtoField;

public class MerchandiseDto implements MercuryOptionDto
{
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
}
