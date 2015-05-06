package pl.uz.mercury.dao.common;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NamedQuery;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.MercuryOptionDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.MercuryOptionEntity;
import pl.uz.mercury.filtercriteria.FilterCriteria;

@Stateless
public class MercuryDao
{
	// to wziąć może z getRes
	protected static final String	PERSISTER_NAME	= "mercuryPersister";

	@PersistenceContext(name = PERSISTER_NAME)
	EntityManager					entityManager;

	public Long save (MercuryOptionEntity entity)
	{
		entityManager.persist(entity);
		entityManager.flush();

		return entity.getId();
	}

	public MercuryOptionEntity retrive (Class <? extends MercuryOptionEntity> clazz, Long id)
	{
		return entityManager.find(clazz, id);
	}

	public void delete (Class <? extends MercuryOptionEntity> clazz, Long id)
	{
		delete(retrive(clazz, id));
	}

	public void delete (MercuryOptionEntity entity)
	{
		entityManager.remove(entity);
	}
	
	public <Entity extends MercuryOptionEntity> List<Entity> getList()
	{
		
		
		
		
		
		return null;
	}

}






















