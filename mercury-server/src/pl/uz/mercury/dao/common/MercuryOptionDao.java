package pl.uz.mercury.dao.common;

import java.lang.reflect.ParameterizedType;

import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;

public abstract class MercuryOptionDao <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	extends MercuryDao
	implements MercuryOptionDaoInterface
{
	private static final int	GENERIC_TYPE_ORDER_FOR_ENTITY	= 0;

	@SuppressWarnings("unchecked")
	private Class <Entity>		entityClass						= (Class <Entity>) ((ParameterizedType) getClass()
																		.getGenericSuperclass())
																		.getActualTypeArguments()[GENERIC_TYPE_ORDER_FOR_ENTITY];
	@Override
	public MercuryOptionEntity retrive (Long id)
	{
		return super.retrive(entityClass, id);
	}

	@Override
	public void delete (Long id)
	{
		super.delete(entityClass, id);
	}

}
