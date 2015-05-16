package pl.uz.mercury.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.local.MerchandiseServiceLocal;
import pl.uz.mercury.service.common.MercuryCrudOptionService;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.util.PropertiesReader.PropertyName;

/**
 * Session Bean implementation class MerchandiseServiceImpl
 */
@Stateless
//@SecurityDomain("mercury-domain")
//@RolesAllowed(value = "rola")

public class MerchandiseServiceImpl 
	extends MercuryCrudOptionService <Merchandise, MerchandiseDto>
	implements MerchandiseServiceRemote, MerchandiseServiceLocal
{
	
	public MerchandiseServiceImpl()
	{
		super(Merchandise.class, MerchandiseDto.class);
	}

	@EJB
	PropertiesReader propertiesReader;
	
	public String getResource ()
	{
		String property = propertiesReader.getProperty(PropertyName.PERSISTER_NAME);
		
		
		return property;
	}
	
	@Override
	public Long save (MerchandiseDto dto) throws SavingException
	{
		return 6l;
//		return super.save(dto);
	}
}
