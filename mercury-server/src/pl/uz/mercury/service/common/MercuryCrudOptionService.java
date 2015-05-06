package pl.uz.mercury.service.common;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;
import pl.uz.mercury.filtercriteria.FilterCriteria;
import pl.uz.mercury.util.EntityDtoAssigner;

public abstract class MercuryCrudOptionService <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
{
	private EntityDtoAssigner <Entity, Dto>	entityDtoAssigner				= new EntityDtoAssigner <>();
	private static final int				GENERIC_TYPE_ORDER_FOR_ENTITY	= 0;
	private static final int				GENERIC_TYPE_ORDER_FOR_DTO		= 1;

	private Class <Entity>					entityClass = retriveGenericClass(GENERIC_TYPE_ORDER_FOR_ENTITY);
	private Class <Dto>						dtoClass = retriveGenericClass(GENERIC_TYPE_ORDER_FOR_DTO);

	@EJB
	protected MercuryDao	dao;

	

	@SuppressWarnings({ "unchecked" })
	private <GenericType> Class <GenericType> retriveGenericClass (int genericTypeorder)
	{
		return (Class <GenericType>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[genericTypeorder];
	}
	
	
	
	public Long save (Dto dto) throws InstantiationException, IllegalAccessException, EntityDtoAssignException
	{
		Entity entity = entityClass.newInstance();
		entityDtoAssigner.assignEntityByDto(entity, dto);
		
		return dao.save(entity);
	}
	
	
	public List<Dto> getList ()
	{
		List <Entity> entityList = dao.getList();
		List<Dto> dtoList = new ArrayList<Dto>();
		
		for (Entity entity : entityList)
		{
			dtoList.add(entity);
		}
		
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}












