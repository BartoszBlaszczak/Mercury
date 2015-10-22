package pl.uz.mercury.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.MerchandiseService;
import pl.uz.mercury.service.common.MercuryService;
import pl.uz.mercury.service.common.MercuryServiceImpl;

@Stateless
@Remote(MerchandiseService.class)
@Local(MerchandiseServiceLocal.class)
public class MerchandiseServiceImpl
	extends MercuryServiceImpl <Merchandise, MerchandiseDto>
	implements MerchandiseService, MerchandiseServiceLocal
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
		if (dto.quantity == null)
			throw new ValidationException(MerchandiseDto.QUANTITY);
	}
	
	@Override
	@RolesAllowed(MercuryService.USER_ROLE)
	public void changeQuantity(Long id, int change)
	{
		Merchandise merchandise = dao.retrive(Merchandise.class, id);
		merchandise.setQuantity(merchandise.getQuantity()+change);
	}
}
