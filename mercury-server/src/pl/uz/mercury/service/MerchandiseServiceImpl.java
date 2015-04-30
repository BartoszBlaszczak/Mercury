package pl.uz.mercury.service;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.jboss.ejb3.annotation.SecurityDomain;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.local.MerchandiseDaoLocal;
import pl.uz.mercury.local.MerchandiseServiceLocal;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;

/**
 * Session Bean implementation class MerchandiseServiceImpl
 */
@Stateless
@SecurityDomain("mercury-domain")
@RolesAllowed(value="rola")
public class MerchandiseServiceImpl implements MerchandiseServiceRemote, MerchandiseServiceLocal
{
	@EJB
	MerchandiseDaoLocal	dao;

	@Override
	public String getResource()
	{
		MerchandiseDto dto = new MerchandiseDto();
		dto.name = "2";
		dto.transientField = "transient - do not save it";

		try
		{
			return dao.save(dto).toString();
		}
		catch (SavingException e)
		{
			e.printStackTrace();
			return ":(";
		}

	}

}
