package pl.uz.mercury.service.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.interceptor.Interceptors;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.UpdatingException;
import pl.uz.mercury.interceptor.LoggingInterceptor;
import pl.uz.mercury.serviceremoteinterface.common.MercuryCrudOptionServiceInterface;
import pl.uz.mercury.util.EntityDtoAssigner;

@Interceptors(LoggingInterceptor.class)
public abstract class MercuryCrudOptionService <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	implements MercuryCrudOptionServiceInterface <Dto>
{
	private final Class <Entity>			entityClass;
	private final Class <Dto>				dtoClass;
	private EntityDtoAssigner <Entity, Dto>	entityDtoAssigner;

	@EJB
	protected MercuryDao					dao;

	public MercuryCrudOptionService(Class <Entity> entityClass, Class <Dto> dtoClass)
	{
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
		entityDtoAssigner = new EntityDtoAssigner <>(entityClass);
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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException e)
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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException e)
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
		catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException |
				NoSuchMethodException e)
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
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException |
				InvocationTargetException | NoSuchMethodException e)
		{
			throw new RetrievingException();
		}
	}
}
