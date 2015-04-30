package pl.uz.mercury.local;


import javax.ejb.Local;

import pl.uz.mercury.dao.MercuryOptionDaoInterface;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;

public interface MerchandiseDaoLocal extends MercuryOptionDaoInterface<Merchandise, MerchandiseDto>
{

}
