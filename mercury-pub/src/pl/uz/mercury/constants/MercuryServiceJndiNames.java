package pl.uz.mercury.constants;

public interface MercuryServiceJndiNames
{
	String	MERCHANDISE	= "ejb:mercury/mercury-server-1.0.0-SNAPSHOT/MerchandiseServiceImpl!pl.uz.mercury.service.MerchandiseService";
	String	SALE		= "ejb:mercury/mercury-server-1.0.0-SNAPSHOT/SaleServiceImpl!pl.uz.mercury.service.SaleService";
	String	PURCHASE	= "ejb:mercury/mercury-server-1.0.0-SNAPSHOT/PurchaseServiceImpl!pl.uz.mercury.service.PurchaseService";
}
