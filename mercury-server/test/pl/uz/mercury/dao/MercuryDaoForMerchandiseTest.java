package pl.uz.mercury.dao;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.uz.mercury.dao.common.MercuryDao;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.filtercriteria.SearchPredicate;

@RunWith(Arquillian.class)
@Transactional
public class MercuryDaoForMerchandiseTest
{
	@EJB
	MercuryDao	dao;
	
	private List <SearchCriteria> emptyCriteriaList = new ArrayList<>();

	@Deployment
	public static JavaArchive createArchiveAndDeploy ()
	{
		return ShrinkWrap.create(JavaArchive.class)
				.addPackages(true, "pl.uz.mercury", "pl.uz.mercury.entity", "pl.uz.mercury.entity.common", "pl.uz.mercury.exception")
				.addAsResource(new File("src/META-INF/testPersistence.xml"), "META-INF/persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}
	
	@After
	public void cleanUp() throws ValidationException
	{
		for (Merchandise merchandise : dao.getList(Merchandise.class, emptyCriteriaList) )
			dao.delete(Merchandise.class, merchandise.getId());
	}

	@Test
	public void shoudtSave () throws ValidationException
	{
		// given
		Merchandise merchandise = getRandonMerchandise();
		
		// when
		dao.save(merchandise);
		Merchandise retrivedEntity = dao.retrive(Merchandise.class, merchandise.getId());
		
		// then
		assertEquals(merchandise.getId(), retrivedEntity.getId());
		assertEquals(merchandise.getName(), retrivedEntity.getName());
	}

	@Test
	public void shouldDelete () throws ValidationException
	{
		// given
		Merchandise merchandise = getRandonMerchandise();
		
		// when
		dao.save(merchandise);
		Long id = merchandise.getId();
		
		// then
		assertEquals(dao.retrive(Merchandise.class, id).getId(), id);
		
		// when
		dao.delete(Merchandise.class, id);
		
		// then
		assertNull(dao.retrive(Merchandise.class, id));
	}

	@Test
	public void shouldgetList () throws ValidationException
	{
		// given
		String prefix = "AAA";
		Merchandise merchandise1 = new Merchandise();
		merchandise1.setName(prefix+"A");
		Merchandise merchandise2 = new Merchandise();
		merchandise2.setName(prefix+"B");
		Merchandise merchandise3 = new Merchandise();
		merchandise3.setName("ZZZ");
		SearchCriteria searchCriteria = new SearchCriteria(MerchandiseDto.NAME, SearchPredicate.LIKE, prefix);
		
		// when
		dao.save(merchandise1);
		dao.save(merchandise2);
		dao.save(merchandise3);
		List <Merchandise> results = dao.getList(Merchandise.class, Arrays.asList(searchCriteria));
		
		// then
		assertEquals(2, results.size());
		List <Long> ids = results.stream().map(Merchandise::getId).collect(Collectors.toList());
		assertTrue(ids.containsAll(Arrays.asList(merchandise1.getId(), merchandise2.getId())));
		assertFalse(results.contains(merchandise3));
	}
	
	private Merchandise getRandonMerchandise()
	{
		Merchandise merchandise = new Merchandise();
		merchandise.setName(String.valueOf(new Random().nextInt()));
		return merchandise;
	}
	
}
