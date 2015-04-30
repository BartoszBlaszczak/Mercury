package pl.uz.mercury.dao;

import javax.ejb.Local;
import javax.ejb.Stateless;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.local.MerchandiseDaoLocal;

@Stateless
public class MerchandiseDao extends MercuryOptionDao<Merchandise, MerchandiseDto>
	implements MerchandiseDaoLocal
{

	public MerchandiseDao() throws InstantiationException, IllegalAccessException
	{
		super();
	}

}