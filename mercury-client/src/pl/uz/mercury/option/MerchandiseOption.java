package pl.uz.mercury.option;

import java.io.IOException;

import javax.naming.NamingException;

import pl.uz.mercury.constants.MercuryServiceJndiNames;
import pl.uz.mercury.option.common.MercuryClientOption;
import pl.uz.mercury.util.Properties;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.exception.ValidationException;
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
		Object[] dataSet = new Object[3];

		dataSet[0] = dto.id;
		dataSet[1] = dto.name;
		dataSet[2] = dto.quantity.toString();

		return dataSet;
	}

	@Override
	protected MerchandiseDto getDto (Object[] data) throws ValidationException
	{
		MerchandiseDto dto = new MerchandiseDto();
		dto.id = (Long) data[0];
		dto.name = (String) data[1];
		if (data[2] == null) dto.quantity = 0;
		else try { dto.quantity = Integer.parseInt((String)data[2]);} catch (Exception e){throw new ValidationException((String)data[2]);}
		return dto;
	}
}
