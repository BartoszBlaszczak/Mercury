package pl.uz.mercury.option;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

import javax.naming.NamingException;

import pl.uz.mercury.option.common.MercuryClientOption;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.dto.MerchandiseDto;
import pl.uz.mercury.dto.TransactionDto;
import pl.uz.mercury.exception.ValidationException;
import pl.uz.mercury.service.TransactionService;
import pl.uz.mercury.util.MercuryDateFormat;
import pl.uz.mercury.view.optioninternalframe.TransactionInternalFrame;

public abstract class TransactionOption <Service extends TransactionService>
	extends MercuryClientOption <TransactionDto, Service, TransactionInternalFrame>
{
	MercuryDateFormat dateFormat = new MercuryDateFormat();
	
	public TransactionOption(PropertiesReader localizationReader, PropertiesReader messageReader, String optionPrefix, String JndiName)
			throws NamingException, IOException
	{
		super(JndiName, new TransactionInternalFrame(localizationReader), optionPrefix, messageReader);
	}
	
	@Override
	protected void actualize ()
	{
		optionInternalFrame.setUpMerchandiseComboBoxs(service.getMerchandiseDtos());
	}

	@Override
	protected Object[] getData (TransactionDto dto)
	{
		Object[] dataSet = new Object[6];

		dataSet[0] = dto.id;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		dataSet[1] = dateFormat.format(dto.date);
		dataSet[2] = dto.merchandiseDto;
		dataSet[3] = dto.quantity.toString();
		dataSet[4] = dto.price.setScale(2).toString();
		dataSet[5] = dto.cost.setScale(2).toString();

		return dataSet;
	}

	@Override
	protected TransactionDto getDto (Object[] data) throws ValidationException
	{
		TransactionDto dto = new TransactionDto();
		
		try { dto.id = (Long) data[0];} catch (Exception e){throw new ValidationException((String)data[0]);}
		try { dto.date = dateFormat.parse((String) data[1]);} catch (Exception e){throw new ValidationException((String)data[1]);}
		try { dto.merchandiseDto = (MerchandiseDto) data[2];} catch (Exception e){throw new ValidationException((String)data[2]);}
		try { dto.quantity = Integer.parseInt((String)data[3]);} catch (Exception e){throw new ValidationException((String)data[3]);}
		try { dto.price = new BigDecimal((String)data[4]);} catch (Exception e){throw new ValidationException((String)data[4]);}
		return dto;
	}
	
	@Override
	protected String getNameForRow (int row)
	{
		return String.valueOf(row+1);
	}
}
