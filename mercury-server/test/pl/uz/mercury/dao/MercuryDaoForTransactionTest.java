package pl.uz.mercury.dao;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.Purchase;
import pl.uz.mercury.entity.Sale;
import pl.uz.mercury.entity.Transaction;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.filtercriteria.SearchCriteria;
import pl.uz.mercury.filtercriteria.SearchPredicate;

@RunWith(Arquillian.class)
@Transactional
public class MercuryDaoForTransactionTest extends MercuryDaoTest
{
	@Before
	public void cleanUp ()
			throws ValidationException
	{
		for (Sale sale : dao.getList(Sale.class, emptyCriteriaList))
			dao.delete(Sale.class, sale.getId());

		for (Purchase purchase : dao.getList(Purchase.class, emptyCriteriaList))
			dao.delete(Purchase.class, purchase.getId());
	}

	private List<Transaction> getRandomTransactions (int quantity)
	{
		List<Transaction> transactions = new ArrayList <>();
		Merchandise merchandise = getRandomMerchandise();
		dao.save(merchandise);
		
		if (randomizer.nextBoolean())
			for (int i = 0; i<quantity; i++)
				transactions.add(setUpTransaction(new Sale(), merchandise));
		
		else for (int i = 0; i<quantity; i++)
			transactions.add(setUpTransaction(new Purchase(), merchandise));
		
		return transactions;
	}
	private Transaction getRandomTransaction ()
	{
		return getRandomTransactions(1).get(0);
	}
	
	
	private Transaction setUpTransaction(Transaction transaction, Merchandise merchandise)
	{
		transaction.setDate(new Date());
		transaction.setQuantity(6);
		transaction.setPrice(new BigDecimal(100l).setScale(2));
		transaction.setCost(transaction.getPrice().multiply(new BigDecimal(transaction.getQuantity())));
		transaction.setMerchandise(merchandise);

		return transaction;
	}

	@Test
	public void shoudtSave ()
			throws ValidationException
	{
		Transaction transaction = getRandomTransaction();

		// when
		dao.save(transaction);
		Transaction retrivedEntity = dao.retrive(transaction.getClass(), transaction.getId());

		// then
		assertEquals(transaction.getId(), retrivedEntity.getId());
		assertEquals(transaction.getDate(), retrivedEntity.getDate());
		assertEquals(transaction.getPrice(), retrivedEntity.getPrice());
		assertEquals(transaction.getQuantity(), retrivedEntity.getQuantity());
		assertEquals(transaction.getMerchandise().getId(), retrivedEntity.getMerchandise().getId());
	}

	@Test
	public void shouldDelete ()
			throws ValidationException
	{
		// given
		Transaction transaction = getRandomTransaction();

		// when
		dao.save(transaction);
		Long id = transaction.getId();
		dao.delete(transaction.getClass(), id);

		// then
		assertNull(dao.retrive(transaction.getClass(), id));
	}

	@Test
	public void shouldgetList ()
			throws ValidationException
	{
		// given
		
		List<Transaction> transitions = getRandomTransactions(10);
		for (int i = 0; i < transitions.size(); i++)
		{
			Transaction transaction = transitions.get(i); 
			transaction.setQuantity(i+1);
			transaction.setPrice(new BigDecimal(i+1).setScale(2));
		}

		SearchCriteria quantityFromCriteria = new SearchCriteria(TransactionDto.QUANTITY, SearchPredicate.GREATER_OR_EQUAL, "2");
		SearchCriteria quantityToCriteria = new SearchCriteria(TransactionDto.QUANTITY, SearchPredicate.LESSER_OR_EQUAL, "6");
		
		SearchCriteria priceFromCriteria = new SearchCriteria(TransactionDto.PRICE, SearchPredicate.GREATER_OR_EQUAL, "3");
		SearchCriteria priceToCriteria = new SearchCriteria(TransactionDto.PRICE, SearchPredicate.LESSER_OR_EQUAL, "7");

		// when
		for (int i = 0; i < transitions.size(); i++)
			dao.save(transitions.get(i));
		
		// then
		assertEquals(9, dao.getList(transitions.get(0).getClass(), Arrays.asList(quantityFromCriteria)).size());
		assertEquals(6, dao.getList(transitions.get(0).getClass(), Arrays.asList(quantityToCriteria)).size());
		assertEquals(5, dao.getList(transitions.get(0).getClass(), Arrays.asList(quantityFromCriteria, quantityToCriteria)).size());
		assertEquals(5, dao.getList(transitions.get(0).getClass(), Arrays.asList(priceFromCriteria, priceToCriteria)).size());
		assertEquals(4, dao.getList(transitions.get(0).getClass(), Arrays.asList(quantityFromCriteria, quantityToCriteria, priceFromCriteria, priceToCriteria)).size());
		assertEquals(5, dao.getList(transitions.get(0).getClass(), Arrays.asList(quantityFromCriteria, priceFromCriteria, priceToCriteria)).size());
	}
}
