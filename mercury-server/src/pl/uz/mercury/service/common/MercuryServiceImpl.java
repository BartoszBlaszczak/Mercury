package pl.uz.mercury.service.common;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.interceptor.Interceptors;

import org.jboss.ejb3.annotation.SecurityDomain;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryEntity;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.EntityDtoCopyException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.searchcriteria.SearchCriteria;
import pl.uz.mercury.interceptor.LoggingInterceptor;
import pl.uz.mercury.service.common.MercuryService;
import pl.uz.mercury.util.EntityDtoCopier;

@Interceptors(LoggingInterceptor.class)
@SecurityDomain("other")
public abstract class MercuryServiceImpl <Entity extends MercuryEntity, Dto extends MercuryOptionDto>
	implements MercuryService <Dto>
{
	@EJB
	protected MercuryDao						dao;

	private final Class <Entity>				entityClass;
	private final Class <Dto>					dtoClass;
	private final EntityDtoCopier <Entity, Dto>	entityDtoAssigner;

	public MercuryServiceImpl(Class <Entity> entityClass, Class <Dto> dtoClass)
	{
		this.entityClass = entityClass;
		this.dtoClass = dtoClass;
		entityDtoAssigner = new EntityDtoCopier <>(entityClass);
	}

	@Override
	@RolesAllowed(MercuryService.USER_ROLE)
	public Long save (Dto dto)
			throws SavingException, ValidationException
	{
		try
		{
			validate(dto);
			Entity entity = getEntity(dto);
			if (entity.getId() == null) dao.save(entity);
			return entity.getId();
		}
		catch (EJBTransactionRolledbackException e)
		{
			throw new SavingException();
		}
	}

	protected Entity getEntity (Dto dto)
			throws SavingException
	{
		try
		{
			Entity entity;
			if (dto.getId() == null)
				entity = entityClass.newInstance();
			else entity = dao.retrive(entityClass, dto.getId());

			entityDtoAssigner.fillEntityByDto(entity, dto);
			return entity;
		}
		catch (EntityDtoCopyException | InstantiationException | IllegalAccessException e)
		{
			throw new SavingException();
		}
	}

	@Override
	@RolesAllowed(MercuryService.USER_ROLE)
	public void delete (Long id)
			throws DeletingException
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
	@RolesAllowed({ MercuryService.USER_ROLE, MercuryService.OBSERVER_ROLE })
	public List <Dto> getList (List <SearchCriteria> criteria)
			throws RetrievingException, ValidationException
	{
		try
		{
			List <Entity> entityList = dao.getList(entityClass, criteria);
			return assignDtosByEntities(entityList);
		}
		catch (EntityDtoCopyException e)
		{
			throw new RetrievingException();
		}
	}

	protected List <Dto> assignDtosByEntities (List <Entity> entityList)
			throws EntityDtoCopyException
	{
		return entityDtoAssigner.getDtosForEntities(entityList, dtoClass);
	}

	protected void validate (Dto dto)
			throws ValidationException
	{}
}
