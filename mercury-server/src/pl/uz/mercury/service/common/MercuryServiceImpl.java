package pl.uz.mercury.service.common;

import java.lang.reflect.InvocationTargetException;
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
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.interceptor.LoggingInterceptor;
import pl.uz.mercury.service.common.MercuryService;
import pl.uz.mercury.util.EntityDtoAssigner;

@Interceptors(LoggingInterceptor.class)
public abstract class MercuryServiceImpl <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	implements MercuryService <Dto>
{
	@EJB
	protected MercuryDao							dao;

	private final Class <Entity>					entityClass;
	private final Class <Dto>						dtoClass;
	private final EntityDtoAssigner <Entity, Dto>	entityDtoAssigner;

	public MercuryServiceImpl(Class <Entity> entityClass, Class <Dto> dtoClass)
	{
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
		entityDtoAssigner = new EntityDtoAssigner <>(entityClass);
	}

	@Override
	public final Long save (Dto dto) throws SavingException, ValidationException
	{
		validate(dto);
		return dao.save(getEntity(dto));
	}
	
	protected Entity getEntity (Dto dto) throws SavingException
	{
		try
		{
			Entity entity;
			if (dto.getId() == null) entity = entityClass.newInstance();
			else entity = dao.retrive(entityClass, dto.getId());
			
			entityDtoAssigner.assignEntityByDto(entity, dto);
			return entity;
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
			return entityDtoAssigner.getDtoForEntity(dao.retrive(entityClass, id), dtoClass);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			e.printStackTrace();
			throw new RetrievingException();
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
	public List <Dto> getList (List <SearchCriteria> criteria) throws RetrievingException, ValidationException
	{
		try
		{
			List <Entity> entityList = dao.getList(entityClass, criteria);
			return assignDtosByEntities(entityList);
		}
		catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException e)
		{
			throw new RetrievingException();
		}
	}
	
	protected List <Dto> assignDtosByEntities(List <Entity> entityList) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		return entityDtoAssigner.getDtosForEntities(entityList, dtoClass);
	}

	protected void validate (Dto dto) throws ValidationException
	{}
}
