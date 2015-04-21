package pl.uz.mercury.service.impl;

import javax.ejb.Stateless;

import pl.uz.mercury.service.local.MerchandiseServiceLocal;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;

/**
 * Session Bean implementation class MerchandiseServiceImpl
 */
@Stateless
public class MerchandiseServiceImpl implements MerchandiseServiceRemote, MerchandiseServiceLocal
{

	/**
	 * Default constructor.
	 */
	public MerchandiseServiceImpl()
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getResource()
	{
		return "RESOURCE";
	}

}
