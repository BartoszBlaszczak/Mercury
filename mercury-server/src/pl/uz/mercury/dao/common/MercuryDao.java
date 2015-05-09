package pl.uz.mercury.dao.common;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import pl.uz.mercury.entity.common.MercuryOptionEntity;

@Stateless
public class MercuryDao
{
	// to wziąć może z getRes
	protected static final String	PERSISTER_NAME	= "mercuryPersister";

	@PersistenceContext(name = PERSISTER_NAME)
	protected EntityManager			entityManager;
	
	public Long save (MercuryOptionEntity entity)
	{
		entityManager.persist(entity);
		entityManager.flush();

		return entity.getId();
	}

	public <Entity extends MercuryOptionEntity> Entity retrive (Class <Entity> clazz, Long id)
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

	public <Entity extends MercuryOptionEntity> List <Entity> getList (Class <Entity> entityClass)
	{
		CriteriaQuery <Entity> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(entityClass);
		criteriaQuery.from(entityClass);

		return entityManager.createQuery(criteriaQuery).getResultList();
	}

}
