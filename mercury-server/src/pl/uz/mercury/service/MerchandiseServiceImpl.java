package pl.uz.mercury.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.ejb3.annotation.SecurityDomain;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.local.MerchandiseDaoLocal;
import pl.uz.mercury.local.MerchandiseServiceLocal;
import pl.uz.mercury.service.common.MercuryCrudOptionService;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;
import pl.uz.mercury.zobaczmy.ZobaczmyServiceLocal;

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
	ZobaczmyServiceLocal zobaczmy;

	@Override
	public String getResource ()
	{
//		MerchandiseDto dto = new MerchandiseDto();
//		dto.name = "name";
//		dto.transientField = "transient - do not save it";

//		return dao.save(dto).toString();
//		return dao.saveUniversally(dto).toString();
		
		
		dao.getList();
		
		return "OK";
	}

}
