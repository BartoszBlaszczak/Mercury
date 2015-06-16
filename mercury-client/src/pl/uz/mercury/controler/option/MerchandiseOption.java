package pl.uz.mercury.controler.option;

import java.io.IOException;

import javax.naming.NamingException;

import pl.uz.mercury.Properties;
import pl.uz.mercury.constants.MercuryServiceJndiNames;
import pl.uz.mercury.controler.option.common.MercuryClientCrudOption;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.serviceremoteinterface.MerchandiseServiceRemote;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.view.optioninternalframe.MerchandiseInternalFrame;
import pl.uz.mercury.view.optioninternalframe.common.MercuryCrudOptionLocalization;

public class MerchandiseOption
	extends MercuryClientCrudOption <MerchandiseDto, MerchandiseServiceRemote, MerchandiseInternalFrame>
{
	public MerchandiseOption(MercuryCrudOptionLocalization localization, PropertiesReader messageReader)
			throws NamingException, IOException
	{
		super(MercuryServiceJndiNames.MERCHANDISE, new MerchandiseInternalFrame(localization), new PropertiesReader(Properties.OPTIONS_FILENAME,
				Properties.Option.merchandise), messageReader);
	}

	@Override
	protected Object[] getData (MerchandiseDto dto)
	{
		Object[] dataSet = new Object[2];

		dataSet[0] = dto.name;
		dataSet[1] = dto.transientField;

		return dataSet;
	}
}
