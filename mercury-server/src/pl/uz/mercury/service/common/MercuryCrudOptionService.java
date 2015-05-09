package pl.uz.mercury.service.common;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.common.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;
import pl.uz.mercury.filtercriteria.FilterCriteria;
import pl.uz.mercury.serviceremoteinterface.common.MercuryCrudOptionServiceInterface;
import pl.uz.mercury.util.EntityDtoAssigner;

public abstract class MercuryCrudOptionService <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	implements MercuryCrudOptionServiceInterface
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

	public Long save (Dto dto) throws Exception
	{
		try
		{
			Entity entity = entityClass.newInstance();
			entityDtoAssigner.assignEntityByDto(entity, dto);
			return dao.save(entity);
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			throw new Exception();
		}

	}

	public Dto retrieve (Long id) throws Exception
	{
		try
		{
			Dto dto = dtoClass.newInstance();
			entityDtoAssigner.assignDtoByEntity(dto, dao.retrive(entityClass, id));
			return dto;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}

	public void update (Dto dto) throws Exception
	{
		try
		{
			Entity entity = dao.retrive(entityClass, dto.getId());
			entityDtoAssigner.assignEntityByDto(entity, dto);

		}
		catch (IllegalArgumentException | IllegalAccessException e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}

	public void delete (Long id)
	{
		dao.delete(entityClass, id);
	}

	public List <Dto> getList () throws Exception
	{
		try
		{
			List <Entity> entityList = dao.getList(entityClass);
			List <Dto> dtoList = entityDtoAssigner.getDtosForEntities(entityList, dtoClass);
			return dtoList;
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			throw new Exception();
		}
	}

}
