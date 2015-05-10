package pl.uz.mercury.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
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
	private static final int				ENTITY_GENERIC_TYPE_ORDER	= 0;
	private static final int				DTO_GENERIC_TYPE_ORDER		= 1;

	private Class <Entity>					entityClass					= retriveGenericClass(ENTITY_GENERIC_TYPE_ORDER);
	private Class <Dto>						dtoClass					= retriveGenericClass(DTO_GENERIC_TYPE_ORDER);
	private EntityDtoAssigner <Entity, Dto>	entityDtoAssigner			= new EntityDtoAssigner <>(entityClass);

	@EJB
	protected MercuryDao					dao;

	@SuppressWarnings({ "unchecked" })
	private <GenericType> Class <GenericType> retriveGenericClass (int genericTypeOrder)
	{
		Class <?> genericSource = findGenericSourceClass(getClass());
		Class <GenericType> classObject = (Class <GenericType>) ((ParameterizedType) genericSource
				.getGenericSuperclass()).getActualTypeArguments()[genericTypeOrder];

		return classObject;
	}

	@SuppressWarnings({ "rawtypes" })
	static private Class findGenericSourceClass (final Class clazz)
	{
		Class superclass = (Class) clazz.getSuperclass();
		if (superclass.equals(MercuryCrudOptionService.class)) { return (Class) clazz; }
		return findGenericSourceClass(superclass);

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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			e.printStackTrace();
			
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
		catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e)
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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new RetrievingException();
		}
	}
}
