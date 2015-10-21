package pl.uz.mercury.service;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.Transaction;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.common.MercuryServiceImpl;
import pl.uz.mercury.util.EntityDtoAssigner;

public abstract class TransactionServiceImpl <Entity extends Transaction>
	extends MercuryServiceImpl <Entity, TransactionDto>
	implements SaleService
{
	@EJB
	private MerchandiseService merchandiseService;
	private EntityDtoAssigner <Merchandise, MerchandiseDto> merchandiseAssigner = new EntityDtoAssigner <>(Merchandise.class);
	
	public TransactionServiceImpl(Class <Entity> entityClass)
	{
		super(entityClass, TransactionDto.class);
	}
	
	@Override
	protected Entity getEntity (TransactionDto dto)
			throws SavingException
	{
		Entity entity = super.getEntity(dto);
		entity.setMerchandise(dao.retrive(Merchandise.class, dto.merchandiseDto.id));
		entity.setCost(dto.price.multiply(new BigDecimal(dto.quantity)));
		return entity;
	}
	
	@Override
	protected void validate (TransactionDto dto)
			throws ValidationException
	{
		StringBuilder info = new StringBuilder();
		
		if (dto.date == null) info.append(dto.date).append("\n");
		if (dto.quantity <= 0) info.append(dto.quantity).append("\n"); 
		if (dto.price == null || dto.price.compareTo(BigDecimal.ZERO) < 0) info.append(dto.price).append("\n");
		String validation = info.toString();
		if (!validation.isEmpty()) throw new ValidationException(validation);
	}
	
	@Override
	public List <MerchandiseDto> getMerchandiseDtos ()
	{
		try
		{
			return merchandiseService.getList(Collections.emptyList());
		}
		catch (RetrievingException | ValidationException e)
		{
			return Collections.emptyList();
		}
	}
	
	@Override
	protected List <TransactionDto> assignDtosByEntities (List <Entity> entityList)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException
	{
		List <TransactionDto> dtos = super.assignDtosByEntities(entityList);
		for (int i = 0; i < dtos.size(); i++)
			dtos.get(i).merchandiseDto = merchandiseAssigner.getDtoForEntity(entityList.get(i).getMerchandise(), MerchandiseDto.class);
		
		return dtos;
	}
}
