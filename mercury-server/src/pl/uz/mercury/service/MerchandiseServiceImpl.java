package pl.uz.mercury.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.MerchandiseService;
import pl.uz.mercury.service.common.MercuryServiceImpl;

@Stateless
@Remote(MerchandiseService.class)
public class MerchandiseServiceImpl
	extends MercuryServiceImpl <Merchandise, MerchandiseDto>
	implements MerchandiseService
{	
	public MerchandiseServiceImpl()
	{
		super(Merchandise.class, MerchandiseDto.class);
	}
	
	@Override
	protected void validate (MerchandiseDto dto)
			throws ValidationException
	{
		if (dto.name == null || dto.name.isEmpty())
			throw new ValidationException(MerchandiseDto.NAME);
	}
}
