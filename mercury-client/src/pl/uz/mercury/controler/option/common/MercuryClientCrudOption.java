package pl.uz.mercury.controler.option.common;

import javax.naming.NamingException;

import pl.uz.mercury.controler.listener.MercuryCrudOptionListener;
import pl.uz.mercury.dto.common.MercuryOptionDto;
import pl.uz.mercury.serviceremoteinterface.common.MercuryCrudOptionServiceInterface;
import pl.uz.mercury.util.PropertiesReader;
import pl.uz.mercury.view.optioninternalframe.common.MercuryClientCrudOptionInternalFrame;

public abstract class MercuryClientCrudOption <Dto extends MercuryOptionDto, Service extends MercuryCrudOptionServiceInterface <Dto>, InternalFrame extends MercuryClientCrudOptionInternalFrame>
	extends MercuryClientOption <Dto, Service, InternalFrame>
{
	public MercuryClientCrudOption(String jndiName, InternalFrame optionInternalFrame, PropertiesReader optionPropertiesReader,
			PropertiesReader messageReader)
			throws NamingException
	{
		super(jndiName, optionInternalFrame, optionPropertiesReader, messageReader);
		optionInternalFrame.setUpListener(new MercuryCrudActionPerformer());
	}

	protected abstract Object[] getData (Dto dto);

	class MercuryCrudActionPerformer
		implements MercuryCrudOptionListener
	{
		@Override
		public void doAdd ()
		{
			// try
			// {
			// service.save(prepareDto());
			// }
			// catch (SavingException e)
			// {
			// showMessage(messageReader, e.getMessage());
			// }
			// catch (ValidationException e)
			// {
			// showMessage(messageReader, e.getMessage());
			// }
		}

		@Override
		public void doShow ()
		{
			// optionInternalFrame.
			// service.retrieve(id);
		}

		@Override
		public void doUpdate ()
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void doDelete ()
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void doRefresh ()
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void doFiltr ()
		{
			// TODO Auto-generated method stub

		}

		@Override
		public void doFind ()
		{
			// TODO Auto-generated method stub

		}
	}
}
