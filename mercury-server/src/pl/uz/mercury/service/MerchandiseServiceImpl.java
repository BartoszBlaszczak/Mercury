package pl.uz.mercury.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import pl.uz.mercury.Properties;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.local.MerchandiseServiceLocal;
import pl.uz.mercury.service.common.MercuryCrudOptionService;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;
import pl.uz.mercury.util.PropertiesReader;

/**
 * Session Bean implementation class MerchandiseServiceImpl
 */
@Stateless
// @SecurityDomain("mercury-domain")
// @RolesAllowed(value = "rola")
public class MerchandiseServiceImpl
	extends MercuryCrudOptionService <Merchandise, MerchandiseDto>
	implements MerchandiseServiceRemote, MerchandiseServiceLocal
{
	@EJB
	PropertiesReader			propertiesReader;

	public MerchandiseServiceImpl()
	{
		super(Merchandise.class, MerchandiseDto.class);
	}

	@Override
	public String getResource ()
	{
		String property = propertiesReader.getProperty(Properties.persister_name);
		return property;
	}

	@Override
	public Long saveImpl (MerchandiseDto dto) throws SavingException
	{
		return 6l;
		// return super.save(dto);
	}
}
