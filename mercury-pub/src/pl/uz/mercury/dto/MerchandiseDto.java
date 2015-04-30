package pl.uz.mercury.dto;

import pl.uz.mercury.annotation.TransientMercuryDtoField;

public class MerchandiseDto implements MercuryOptionDto
{
	public Long merchandiseId;
	public String name; 
	
	@TransientMercuryDtoField
	public String transientField;
}
