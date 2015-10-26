package pl.uz.mercury.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.transaction.Transactional;

import org.jboss.ejb3.annotation.SecurityDomain;

import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.entity.Merchandise;
import pl.uz.mercury.entity.Transaction;
import pl.uz.mercury.exception.DeletingException;
import pl.uz.mercury.exception.EntityDtoCopyException;
import pl.uz.mercury.exception.RetrievingException;
import pl.uz.mercury.exception.SavingException;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.common.MercuryService;
import pl.uz.mercury.service.common.MercuryServiceImpl;
import pl.uz.mercury.util.EntityDtoCopier;

@SecurityDomain("other")
public abstract class TransactionServiceImpl <Entity extends Transaction>
	extends MercuryServiceImpl <Entity, TransactionDto>
	implements SaleService
{
	private final Class <Entity> entityClass;
	
	@EJB
	protected MerchandiseServiceLocal merchandiseService;
	private EntityDtoCopier <Merchandise, MerchandiseDto> merchandiseAssigner = new EntityDtoCopier <>(Merchandise.class);
	
	public TransactionServiceImpl(Class <Entity> entityClass)
	{
		super(entityClass, TransactionDto.class);
		this.entityClass = entityClass;
	}
	
	@Override
	@Transactional
	@RolesAllowed(MercuryService.USER_ROLE)
	public Long save (TransactionDto dto)
			throws SavingException, ValidationException
	{
		int oldQuantity = 0;
		if (dto.id != null) oldQuantity = dao.retrive(entityClass, dto.id).getQuantity();
		Long id = super.save(dto);
		int change = getNewMerchandiseQuantity(oldQuantity, dto.quantity);
		merchandiseService.changeQuantity(dto.merchandiseDto.id, change);
		return id;
	}

	@Override
	@Transactional
	@RolesAllowed(MercuryService.USER_ROLE)
	public void delete (Long id)
			throws DeletingException
	{
		Entity trasaction = dao.retrive(entityClass, id);
		int quantity = trasaction.getQuantity();
		Long idMerchandise = trasaction.getMerchandise().getId();
		super.delete(id);
		int change = getNewMerchandiseQuantity(quantity, 0);
		merchandiseService.changeQuantity(idMerchandise, change);
	}
	
	protected abstract int getNewMerchandiseQuantity(int oldValue, int newValue);
	
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
		if (dto.quantity == null || dto.quantity <= 0) info.append(dto.quantity).append("\n"); 
		if (dto.price == null || dto.price.compareTo(BigDecimal.ZERO) < 0) info.append(dto.price).append("\n");
		if (dto.merchandiseDto == null) info.append(dto.merchandiseDto).append("\n");
		String validation = info.toString();
		if (!validation.isEmpty()) throw new ValidationException(validation);
	}
	
	@Override
	@RolesAllowed({MercuryService.USER_ROLE, MercuryService.OBSERVER_ROLE})
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
	protected List <TransactionDto> assignDtosByEntities (List <Entity> entityList) throws EntityDtoCopyException 
	{
		List <TransactionDto> dtos = super.assignDtosByEntities(entityList);
		for (int i = 0; i < dtos.size(); i++)
			dtos.get(i).merchandiseDto = merchandiseAssigner.getDtoForEntity(entityList.get(i).getMerchandise(), MerchandiseDto.class);
		
		return dtos;
	}
}
