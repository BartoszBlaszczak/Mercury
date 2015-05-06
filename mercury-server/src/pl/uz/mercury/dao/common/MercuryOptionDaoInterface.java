package pl.uz.mercury.dao.common;

import pl.uz.mercury.entity.MercuryOptionEntity;

public interface MercuryOptionDaoInterface
{
	Long save (MercuryOptionEntity entity);
	MercuryOptionEntity retrive (Long id);
	void delete (Long id);
	
}
