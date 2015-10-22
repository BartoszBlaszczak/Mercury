package pl.uz.mercury.option;

import java.io.IOException;

import javax.naming.NamingException;

import pl.uz.mercury.Properties;
import pl.uz.mercury.constants.MercuryServiceJndiNames;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.service.PurchaseService;

public class PurchaseOption
	extends TransactionOption <PurchaseService>
{
	public PurchaseOption(PropertiesReader localizationReader, PropertiesReader messageReader)
			throws NamingException, IOException
	{
		super(localizationReader, messageReader, Properties.Option.PURCHASE, MercuryServiceJndiNames.PURCHASE);
	}
}
