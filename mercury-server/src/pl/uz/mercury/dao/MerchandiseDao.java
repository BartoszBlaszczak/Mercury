package pl.uz.mercury.dao;

import javax.ejb.Stateless;

import pl.uz.mercury.dao.common.MercuryOptionDao;
import pl.uz.mercury.dao.common.MercuryOptionDaoInterface;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.local.MerchandiseDaoLocal;

@Stateless
public class MerchandiseDao
	extends MercuryOptionDao <Merchandise, MerchandiseDto>
	implements MerchandiseDaoLocal, MercuryOptionDaoInterface
{

}

// klasa opcjonalna - jeśli chcemy dodatkowe funkcje od dao