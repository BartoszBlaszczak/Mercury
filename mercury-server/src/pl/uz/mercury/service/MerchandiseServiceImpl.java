package pl.uz.mercury.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
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
	@EJB
	PropertiesReader propertiesReader;
	
	@Override
	public String getResource ()
	{
//		MerchandiseDto dto = new MerchandiseDto();
//		dto.name = "name";
//		dto.transientField = "transient - do not save it";

//		return dao.save(dto).toString();
//		return dao.saveUniversally(dto).toString();
//		zobaczmy.em();
		
//		try
//		{
//			for (MerchandiseDto dto : getList())
//			{
//				System.out.println(dto.name);
//			}
//			
//		}
//		catch (Exception e)
//		{
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

		
		
		String property = propertiesReader.getProperty(PropertyName.PERSISTER_NAME);
		
		return property;
	}
}
