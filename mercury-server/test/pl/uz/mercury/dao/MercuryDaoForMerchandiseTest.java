package pl.uz.mercury.dao;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.searchcriteria.SearchCriteria;
import pl.uz.mercury.searchcriteria.SearchPredicate;

@RunWith(Arquillian.class)
@Transactional
public class MercuryDaoForMerchandiseTest extends MercuryDaoTest
{	
	@Before
	public void cleanUp() throws ValidationException
	{
		for (Merchandise merchandise : dao.getList(Merchandise.class, emptyCriteriaList) )
			dao.delete(Merchandise.class, merchandise.getId());
	}

	@Test
	public void shoudtSave () throws ValidationException
	{
		// given
		Merchandise merchandise = getRandomMerchandise();
		
		// when
		dao.save(merchandise);
		Merchandise retrivedEntity = dao.retrive(Merchandise.class, merchandise.getId());
		
		// then
		assertEquals(merchandise.getId(), retrivedEntity.getId());
		assertEquals(merchandise.getName(), retrivedEntity.getName());
		assertEquals(merchandise.getQuantity(), retrivedEntity.getQuantity());
	}

	@Test
	public void shouldDelete () throws ValidationException
	{
		// given
		Merchandise merchandise = getRandomMerchandise();
		
		// when
		dao.save(merchandise);
		Long id = merchandise.getId();
		dao.delete(Merchandise.class, id);
		
		// then
		assertNull(dao.retrive(Merchandise.class, id));
	}

	@Test
	public void shouldGetList () throws ValidationException
	{
		// given
		String prefix = "AAA";
		Merchandise merchandise1 = getRandomMerchandise();
		merchandise1.setName(prefix+"A");
		Merchandise merchandise2 = getRandomMerchandise();
		merchandise2.setName(prefix+"B");
		Merchandise merchandise3 = getRandomMerchandise();
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
}
