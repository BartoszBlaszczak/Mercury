package pl.uz.mercury.controler.option;

import java.io.IOException;
import javax.naming.NamingException;

import pl.uz.mercury.Properties;
import pl.uz.mercury.Properties.Locale;
import pl.uz.mercury.constants.MercuryServiceJndiNames;
import pl.uz.mercury.controler.option.common.MercuryClientOption;
import pl.uz.mercury.controler.option.common.PropertiesReader;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.service.MerchandiseService;
import pl.uz.mercury.view.optioninternalframe.MerchandiseInternalFrame;

public class MerchandiseOption
	extends MercuryClientOption <MerchandiseDto, MerchandiseService, MerchandiseInternalFrame>
{
	public MerchandiseOption(PropertiesReader localizationReader, PropertiesReader messageReader)
			throws NamingException, IOException
	{
		super(MercuryServiceJndiNames.MERCHANDISE, new MerchandiseInternalFrame(localizationReader), 
				Properties.Option.MERCHANDISE, messageReader);
	}

	@Override
	protected Object[] getData (MerchandiseDto dto)
	{
		Object[] dataSet = new Object[2];

		dataSet[0] = dto.id;
		dataSet[1] = dto.name;

		return dataSet;
	}

	@Override
	protected MerchandiseDto getDto (Object[] data)
	{
		MerchandiseDto dto = new MerchandiseDto();
		dto.id = (Long) data[0];
		dto.name = (String) data[1];
		return dto;
	}

	@Override
	protected String getNameForRow (int row)
	{
		return (String) optionInternalFrame.getValue(row, localizatioReader.getProperty(Locale.NAME));
	}
}
