package pl.uz.mercury.dao.common;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import pl.uz.mercury.entity.common.MercuryOptionEntity;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.util.MercuryDateFormat;

@Stateless
public class MercuryDao
{
	protected static final String	PERSISTER_NAME	= "mercuryPersister";

	@PersistenceContext(name = PERSISTER_NAME)
	protected EntityManager			entityManager;
	MercuryDateFormat				dateFormat		= new MercuryDateFormat();

	public Long save (MercuryOptionEntity entity)
	{
		entityManager.merge(entity);
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

	public <Entity extends MercuryOptionEntity> List <Entity> getList (Class <Entity> entityClass, List <SearchCriteria> criteria)
			throws ValidationException
	{
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery <Entity> criteriaQuery = criteriaBuilder.createQuery(entityClass);
		Root <Entity> root = criteriaQuery.from(entityClass);
		List<Predicate> restrictions = new ArrayList <>();
		for (SearchCriteria criterium : criteria)
		{
			try
			{
				switch (criterium.searchPredicate)
				{
					case IS:
						restrictions.add(criteriaBuilder.equal(root.get(criterium.fieldName).get(criterium.value), criterium.subValue));
						break;
					case LIKE:
						restrictions.add(criteriaBuilder.like(root.get(criterium.fieldName), "%" + criterium.value + "%"));
						break;
					case EQUAL:
						restrictions.add(criteriaBuilder.equal(root.get("merchandise"), criterium.value));
						break;
					case GREATER_OR_EQUAL:
						restrictions.add(criteriaBuilder.greaterThan(root.get(criterium.fieldName), criterium.value));
						break;
					case SMALLER_OR_EQUAL:
						restrictions.add(criteriaBuilder.lessThan(root.get(criterium.fieldName), criterium.value));
						break;
					case AFTER_OR_EQUAL:
						restrictions.add(criteriaBuilder.lessThan(root.get(criterium.fieldName), dateFormat.parse(criterium.value)));
						break;
					case BEFORE_OR_EQUAL:
						restrictions.add(criteriaBuilder.lessThan(root.get(criterium.fieldName), dateFormat.parse(criterium.value)));
						break;
				}
			}
			catch (ParseException e)
			{
				throw new ValidationException(criterium.value);
			}
		}
		criteriaQuery.where(restrictions.toArray(new Predicate[restrictions.size()]));
		TypedQuery <Entity> createQuery = entityManager.createQuery(criteriaQuery);
		return createQuery.getResultList();
	}
}
