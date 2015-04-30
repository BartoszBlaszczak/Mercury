package pl.uz.mercury.dao;

import java.lang.reflect.ParameterizedType;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.uz.mercury.dao.util.EntityDtoAssigner;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.exception.EntityDtoAssignException;
import pl.uz.mercury.exception.SavingException;

public abstract class MercuryOptionDao <Entity extends MercuryOptionEntity, Dto extends MercuryOptionDto>
	implements MercuryOptionDaoInterface <Entity, Dto>
{
	// to wziąć może z getRes
	protected static final String			PERSISTER_NAME					= "mercuryPersister";

	@PersistenceContext(name = PERSISTER_NAME)
	EntityManager							entityManager;

	private EntityDtoAssigner <Entity, Dto>	entityDtoAssigner				= new EntityDtoAssigner <>();
	private static final int				GENERIC_TYPE_ORDER_FOR_ENTITY	= 0;
	private static final int				GENERIC_TYPE_ORDER_FOR_DTO		= 1;
	private GenericClassRetriver			genericClassRetriver			= new GenericClassRetriver();

	private Class <Entity>					entityClass;
	private Class <Dto>						dtoClass;

	public Entity test(Dto dto) throws InstantiationException, IllegalAccessException,
			EntityDtoAssignException
	{
		Entity entity = entityClass.newInstance();
		entityDtoAssigner.assignEntityByDto(entity, dto);
		return entity;
	}

	@SuppressWarnings("unchecked")
	public MercuryOptionDao() throws InstantiationException, IllegalAccessException
	{
		entityClass = genericClassRetriver.getGenericClassObject(getClass(), GENERIC_TYPE_ORDER_FOR_ENTITY);
		dtoClass = genericClassRetriver.getGenericClassObject(getClass(), GENERIC_TYPE_ORDER_FOR_DTO);
	}

	@Override
	public Long save(Dto dto) throws SavingException
	{
		try
		{
			Entity entity = entityClass.newInstance();
			entityDtoAssigner.assignEntityByDto(entity, dto);
			entityManager.persist(entity);
			entityManager.flush();

			return entity.getId();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			e.printStackTrace();
			throw new SavingException("Can not instantiate entity", e);
		}
		catch (EntityDtoAssignException e)
		{
			e.printStackTrace();
			throw new SavingException("Problem with entity-dto assigment", e);
		}
	}

	public Dto retrive(Long id)
	{
		entityManager.find(entityClass, id);
		return null;
	}

	// Long save(T dto);
	// T retrive (Long id);
	// void update(T dto);
	// void delete (Long id);

	private class GenericClassRetriver
	{

		@SuppressWarnings("rawtypes")
		Class getGenericClassObject(Class <? extends MercuryOptionDao> daoClass, final int GENERIC_TYPE_ORDER)
				throws InstantiationException, IllegalAccessException
		{
			Class <? extends MercuryOptionDao> genericSource = findGenericSourceClass(daoClass);
			Class classObject = (Class) ((ParameterizedType) genericSource.getGenericSuperclass())
					.getActualTypeArguments()[GENERIC_TYPE_ORDER];

			return classObject;
		}

		@SuppressWarnings({ "rawtypes", "unchecked" })
		private Class <? extends MercuryOptionDao> findGenericSourceClass(
				final Class <? extends MercuryOptionDao> clazz)
		{
			Class <? extends MercuryOptionDao> superclass = (Class <? extends MercuryOptionDao>) clazz
					.getSuperclass();
			if (superclass.equals(MercuryOptionDao.class)) { return clazz; }
			return findGenericSourceClass(superclass);

		}
	}
}