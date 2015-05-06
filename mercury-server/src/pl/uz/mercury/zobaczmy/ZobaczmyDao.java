package pl.uz.mercury.zobaczmy;

import java.lang.reflect.ParameterizedType;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import pl.uz.mercury.entity.MercuryOptionEntity;

@Stateless
public class ZobaczmyDao implements ZobaczmyDaoLocal
{
	// to wziąć może z getRes
	protected static final String	PERSISTER_NAME					= "mercuryPersister";

	@PersistenceContext(name = PERSISTER_NAME)
	EntityManager					entityManager;

	@Override
	public <E extends MercuryOptionEntity> Long saveUniversally (E entity)
	{
		entityManager.persist(entity);
		entityManager.flush();

		return entity.getId();
	}
	
	@Override
	public Long saveUniversally2 (MercuryOptionEntity entity)
	{
		entityManager.persist(entity);
		entityManager.flush();

		return entity.getId();
	}
	
}