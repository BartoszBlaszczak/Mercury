package pl.uz.mercury.service.common;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.UpdatingException;
import pl.uz.mercury.serviceremoteinterface.common.MercuryCrudOptionServiceInterface;
import pl.uz.mercury.util.EntityDtoAssigner;

public abstract class MercuryCrudOptionService <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	implements MercuryCrudOptionServiceInterface <Dto>
{
	private EntityDtoAssigner <Entity, Dto>	entityDtoAssigner			= new EntityDtoAssigner <>();
	private static final int				ENTITY_GENERIC_TYPE_ORDER	= 0;
	private static final int				DTO_GENERIC_TYPE_ORDER		= 1;

	private Class <Entity>					entityClass					= retriveGenericClass(ENTITY_GENERIC_TYPE_ORDER);
	private Class <Dto>						dtoClass					= retriveGenericClass(DTO_GENERIC_TYPE_ORDER);

	@EJB
	protected MercuryDao					dao;

	@SuppressWarnings({ "unchecked" })
	private <GenericType> Class <GenericType> retriveGenericClass (int genericTypeorder)
	{
		return (Class <GenericType>) ((ParameterizedType) getClass().getGenericSuperclass())
				.getActualTypeArguments()[genericTypeorder];
	}
	
	@Override
	public Long save (Dto dto) throws SavingException
	{
		try
		{
			Entity entity = entityClass.newInstance();
			entityDtoAssigner.assignEntityByDto(entity, dto);
			return dao.save(entity);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new SavingException();
		}

	}

	@Override
	public Dto retrieve (Long id) throws RetrievingException
	{
		try
		{
			Dto dto = dtoClass.newInstance();
			entityDtoAssigner.assignDtoByEntity(dto, dao.retrive(entityClass, id));
			return dto;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RetrievingException();
		}
	}
	
	@Override
	public void update (Dto dto) throws UpdatingException
	{
		try
		{
			Entity entity = dao.retrive(entityClass, dto.getId());
			entityDtoAssigner.assignEntityByDto(entity, dto);

		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			throw new UpdatingException();
		}
	}
	
	@Override
	public void delete (Long id) throws DeletingException
	{
		try
		{
			dao.delete(entityClass, id);
		}
		catch (EJBTransactionRolledbackException e)
		{
			throw new DeletingException();
		}
	}
	
	@Override
	public List <Dto> getList () throws RetrievingException
	{
		try
		{
			List <Entity> entityList = dao.getList(entityClass);
			List <Dto> dtoList = entityDtoAssigner.getDtosForEntities(entityList, dtoClass);
			return dtoList;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new RetrievingException();
		}
	}
}
