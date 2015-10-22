package pl.uz.mercury.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import pl.uz.mercury.entity.Sale;

@Stateless
@Remote(SaleService.class)
public class SaleServiceImpl extends TransactionServiceImpl <Sale> implements SaleService
{	
	public SaleServiceImpl()
	{
		super(Sale.class);
	}

	@Override
	protected int getNewMerchandiseQuantity (int oldValue, int newValue)
	{
		return oldValue - newValue;
	}
}
