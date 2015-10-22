package pl.uz.mercury.service;

import javax.ejb.Remote;
import javax.ejb.Stateless;

import pl.uz.mercury.entity.Purchase;

@Stateless
@Remote(PurchaseService.class)
public class PurchaseServiceImpl extends TransactionServiceImpl <Purchase> implements PurchaseService
{	
	public PurchaseServiceImpl()
	{
		super(Purchase.class);
	}

	@Override
	protected int getNewMerchandiseQuantity (int oldValue, int newValue)
	{
		return newValue - oldValue;
	}
}
